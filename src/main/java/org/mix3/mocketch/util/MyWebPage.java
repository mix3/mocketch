package org.mix3.mocketch.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.IChoiceRenderer;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.upload.FileUpload;
import org.apache.wicket.markup.html.form.upload.FileUploadField;
import org.apache.wicket.markup.html.image.Image;
import org.apache.wicket.markup.html.image.resource.DynamicImageResource;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.markup.html.list.PageableListView;
import org.apache.wicket.markup.html.navigation.paging.IPageable;
import org.apache.wicket.markup.html.navigation.paging.PagingNavigator;
import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.markup.resolver.IComponentResolver;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.Model;
import org.mix3.mocketch.page.InputPage;
import org.mix3.mocketch.page.ShowPage;

public class MyWebPage extends WebPage implements IComponentResolver{
	public static Map<String, Object> sampleModel =  new HashMap<String, Object>();
	public static Map<String, Component> componentMap = new HashMap<String, Component>();
	
	public Map<String, ?> getMapData(){
		return sampleModel;
	}
	
	@SuppressWarnings({ "serial", "unchecked" })
	public boolean resolve(MarkupContainer container, MarkupStream markupStream, ComponentTag tag) {
		if(tag.isAutoComponentTag()){
			return false;
		}
		
		if(tag.getName().equalsIgnoreCase("span") || tag.getName().equalsIgnoreCase("div") || tag.getName().equalsIgnoreCase("label")){
			if(tag.getId().equalsIgnoreCase("feedback") || tag.getId().equalsIgnoreCase("feedbackpanel")){
				Component component = new FeedbackPanel(tag.getId());
				return addForRender(component, container, markupStream);
			}else if(tag.getId().equalsIgnoreCase("paging")){
				Component listView = componentMap.get(tag.getAttribute("target"));
				if(listView == null){
					listView = new PageableListView(tag.getAttribute("target"), new Model(), 3){
						@Override
						protected void populateItem(ListItem item) {
							item.setDefaultModel(new CompoundPropertyModel<Map<String, Object>>(item.getModelObject()));
						}
					};
					componentMap.put(tag.getAttribute("target"), listView);
				}
				Component component = new PagingNavigator(tag.getId(), (IPageable) listView);
				return addForRender(component, container, markupStream);
			}else{
				final String metaTable = tag.getAttribute("meta:table");
				final String metaColumn = tag.getAttribute("meta:column");
				if(metaTable == null || metaColumn == null){
					Component component = new Label(tag.getId());
					return addForRender(component, container, markupStream);
				}else{
					Map<String, Object> map = (Map<String, Object>) container.getDefaultModelObject();
					String column = "null";
					if((Map<String, Object>)map.get(metaTable) != null && ((Map<String, Object>)map.get(metaTable)).get(metaColumn) != null){
						column = (String) ((Map<String, Object>)map.get(metaTable)).get(metaColumn);
					}
					Component component = new Label(tag.getId(), column);
					return addForRender(component, container, markupStream);
				}
			}
		}else if("input".equalsIgnoreCase(tag.getName())){
            FormComponent<?> component;
            String type = tag.getAttributes().getString("type");
            if ("text".equalsIgnoreCase(type)) {
                component = new TextField<Object>(tag.getId());
                component.setType(String.class);
            } else if ("password".equalsIgnoreCase(type)) {
                component = new PasswordTextField(tag.getId());
            } else if ("checkbox".equalsIgnoreCase(type)) {
                component = new CheckBox(tag.getId());
            } else if ("file".equalsIgnoreCase(type)){
            	component = new FileUploadField(tag.getId());
            	componentMap.put(tag.getId(), component);
            } else {
                return false;
            }
            component.setRequired(tag.getAttributes().containsKey("require"));
            return addForRender(component, container, markupStream);
        }else if("select".equalsIgnoreCase(tag.getName())) {
			final String metaTable = tag.getAttribute("meta:table");
			final String metaColumn = tag.getAttribute("meta:column");
			if(sampleModel.get(metaTable) == null){
				sampleModel.put(metaTable, new ArrayList<Map<String, Object>>());
			}
			IChoiceRenderer<Map<String, Object>> icr = new IChoiceRenderer<Map<String, Object>>(){
				public Object getDisplayValue(Map<String, Object> object) {
					return object.get(metaColumn);
				}
				public String getIdValue(Map<String, Object> object, int index) {
					return String.valueOf(index);
				}
			};
			FormComponent<?> component = new DropDownChoice<Map<String, Object>>(tag.getId(), (List<Map<String, Object>>) sampleModel.get(metaTable), icr);
			component.setRequired(tag.getAttributes().containsKey("require"));
        	return addForRender(component, container, markupStream);
		}else if(tag.getName().equalsIgnoreCase("li") || tag.getName().equalsIgnoreCase("tr")){
			final String metaTable = tag.getAttribute("meta:table");
			if(sampleModel.get(metaTable) == null){
				sampleModel.put(metaTable, new ArrayList<Map<String, Object>>());
			}
			List<Map<String, Object>> list = (List<Map<String, Object>>) sampleModel.get(metaTable);
			if(container.getParent() != null){
				Map<String, Object> map = (Map<String, Object>)container.getDefaultModelObject();
				List<Map<String, Object>> listObject = (List<Map<String, Object>>)map.get(metaTable);
				list = listObject;
			}
			if(tag.getAttribute("num") != null){
				Integer num = Integer.parseInt(tag.getAttribute("num"));
				Component c;
				if(componentMap.get(tag.getId()) != null){
					PageableListView listView = (PageableListView)componentMap.get(tag.getId());
					listView.setDefaultModelObject(list);
					listView.setRowsPerPage(num);
					c = listView;
				}else{
					c = new PageableListView<Map<String, Object>>(tag.getId(), list, num){
						@Override
						protected void populateItem(ListItem<Map<String, Object>> item) {
							item.setDefaultModel(new CompoundPropertyModel<Map<String, Object>>(item.getModelObject()));
						}
					};
					componentMap.put(tag.getId(), c);
				}
				return addForRender(c, container, markupStream);
			}else{
				Component c = new ListView<Map<String, Object>>(tag.getId(), list){
					@Override
					protected void populateItem(ListItem<Map<String, Object>> item) {
						item.setDefaultModel(new CompoundPropertyModel<Map<String, Object>>(item.getModelObject()));
					}
				};
				return addForRender(c, container, markupStream);
			}
		}else if(tag.getName().equalsIgnoreCase("img")){
			Map<String, Object> map = (Map<String, Object>) container.getDefaultModelObject();
			System.out.println("img model?: "+map.toString());
			final byte[] byteData = (byte[]) map.get(tag.getId());
			Component component = new Image(tag.getId(), new DynamicImageResource(){
				@Override
				protected byte[] getImageData() {
					return byteData;
				}
			});
			return addForRender(component, container, markupStream);
		}else if(tag.getName().equalsIgnoreCase("form")){
			final String metaTable = tag.getAttribute("meta:table");
			if(sampleModel.get(metaTable) == null){
				sampleModel.put(metaTable, new ArrayList<Map<String, Object>>());
			}
			if(container.getParent() != null){
				System.out.println("[DEBUG] Model: "+container.getDefaultModelObject());
				Map<String, Object> map = (Map<String, Object>) container.getDefaultModelObject();
				if(map.get(tag.getId()) == null){
					map.put(tag.getId(), new ArrayList<Map<String, Object>>());
				}
				final List<Map<String, Object>> newList = (List<Map<String, Object>>) map.get(tag.getId());
				Form<Map<String, Object>> c = new Form<Map<String, Object>>(tag.getId(), new CompoundPropertyModel<Map<String, Object>>(new HashMap<String, Object>())){
					@Override
					protected void onSubmit() {
						List<Map<String, Object>> list = (List<Map<String, Object>>) sampleModel.get(metaTable);
						list.add(getModelObject());
						newList.add(getModelObject());
						setResponsePage(ShowPage.class, new PageParameters("id="+InputPage.inputList.indexOf(InputPage.input)));
					}
				};
				return addForRender(c, container, markupStream);
			}else{
				Form<Map<String, Object>> component = new Form<Map<String, Object>>(tag.getId(), new CompoundPropertyModel<Map<String, Object>>(new HashMap<String, Object>())){
					@Override
					protected void onSubmit() {
						Map<String, Object> result = getModelObject();
						for(Map.Entry<String, Object> map : result.entrySet()){
							if(map.getValue() instanceof FileUpload){
								result.put(map.getKey(), ((FileUpload)map.getValue()).getBytes());
							}
						}
						System.out.println("[DEBUG] "+metaTable+" :"+result.toString());
						List<Map<String, Object>> list = (List<Map<String, Object>>) sampleModel.get(metaTable);
						list.add(result);
						sampleModel.put(metaTable, list);
						setDefaultModelObject(new CompoundPropertyModel<Map<String, Object>>(new HashMap<String, Object>()));
						setResponsePage(ShowPage.class, new PageParameters("id="+InputPage.inputList.indexOf(InputPage.input)));
					}
				};
				return addForRender(component, container, markupStream);
			}
		}
		return true;
	}
	
	private boolean addForRender(Component component, MarkupContainer container, MarkupStream markupStream) {
		container.internalAdd(component);
		if(component instanceof Form<?>){
			((Form<?>) component).setMultiPart(true);
		}
		component.prepareForRender();
		try {
			if (markupStream == null) {
				component.render();
			} else {
				component.render(markupStream);
			}
		} finally {
			component.afterRender();
		}
		return true;
	}
}
