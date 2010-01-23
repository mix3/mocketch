package org.mix3.mocketch.page;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.markup.html.CSSPackageResource;
import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.list.ListItem;
import org.apache.wicket.markup.html.list.ListView;
import org.apache.wicket.model.CompoundPropertyModel;
import org.mix3.mocketch.WicketApplication;

public class InputPage extends WebPage{
	public static List<Input> inputList = new ArrayList<Input>();
	public static Input input;
	
	@SuppressWarnings("serial")
	public InputPage(){
		input = new Input();
		
		add(CSSPackageResource.getHeaderContribution(WicketApplication.class, "resources/base.css"));
		add(new ListView<Input>("list", inputList){
			@Override
			protected void populateItem(final ListItem<Input> item) {
				final Input inputu = item.getModelObject();
				item.add(new Link<Void>("link"){
					@Override
					public void onClick() {
						input = inputu;
						setResponsePage(ShowPage.class, new PageParameters("id="+inputList.indexOf(input)));
					}
					@Override
					protected void onComponentTagBody(
							MarkupStream markupStream, ComponentTag openTag) {
						replaceComponentTagBody(markupStream, openTag, item.getModelObject().getName());
					}
				});
				item.add(new Link<Void>("edit"){
					@Override
					public void onClick() {
						setResponsePage(new InputPage(item.getModelObject()));
					}
				});
			}
		});
		final TextArea<String> html = new TextArea<String>("html");
		final TextField<String> name = new TextField<String>("name");
		Form<Void> inputForm = new Form<Void>("input"){
			@Override
			protected void onSubmit() {
				inputList.add(input);
				setResponsePage(ShowPage.class, new PageParameters("id="+inputList.indexOf(input)));
			}
		};
		inputForm.setDefaultModel(new CompoundPropertyModel<Input>(input));
		inputForm.add(name);
		inputForm.add(html);
		add(inputForm);
		add(new BookmarkablePageLink<Void>("home", InputPage.class));
	}
	
	@SuppressWarnings("serial")
	public InputPage(final Input inputData){
		input = inputData;
		
		add(CSSPackageResource.getHeaderContribution(WicketApplication.class, "resources/base.css"));
		add(new ListView<Input>("list", inputList){
			@Override
			protected void populateItem(final ListItem<Input> item) {
				final Input inputu = item.getModelObject();
				item.add(new Link<Void>("link"){
					@Override
					public void onClick() {
						input = inputu;
						setResponsePage(ShowPage.class, new PageParameters("id="+inputList.indexOf(input)));
					}
					@Override
					protected void onComponentTagBody(
							MarkupStream markupStream, ComponentTag openTag) {
						replaceComponentTagBody(markupStream, openTag, item.getModelObject().getName());
					}
				});
				item.add(new Link<Void>("edit"){
					@Override
					public void onClick() {
						setResponsePage(new InputPage(item.getModelObject()));
					}
				});
			}
		});
		final TextArea<String> html = new TextArea<String>("html");
		final TextField<String> name = new TextField<String>("name");
		Form<Void> inputForm = new Form<Void>("input"){
			@Override
			protected void onSubmit() {
				setResponsePage(ShowPage.class, new PageParameters("id="+inputList.indexOf(input)));
			}
		};
		inputForm.setDefaultModel(new CompoundPropertyModel<Input>(input));
		inputForm.add(name);
		inputForm.add(html);
		add(inputForm);
		add(new BookmarkablePageLink<Void>("home", InputPage.class));
	}
	
	@SuppressWarnings("serial")
	public class Input implements Serializable{
		private String name;
		private String html;
		
		public Input(){}
		public Input(String name, String html){
			this.name = name;
			this.html = html;
		}
		
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getHtml() {
			return html;
		}
		public void setHtml(String html) {
			this.html = html;
		}
	}
}
