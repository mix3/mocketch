package org.mix3.mocketch.page;

import java.io.IOException;

import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.Markup;
import org.apache.wicket.markup.MarkupParser;
import org.apache.wicket.markup.MarkupStream;
import org.apache.wicket.util.resource.ResourceStreamNotFoundException;
import org.mix3.mocketch.util.MyWebPage;

public class ShowPage extends MyWebPage{
	
	@Override
	public MarkupStream getAssociatedMarkupStream(boolean throwException) {
		Markup m = null;
		try {
			MarkupParser mp = new MarkupParser(	"<html>\n" +
												"<head>\n" +
												"<script type=\"text/javascript\">\n" +
												"<!--\n" +
												"if (document.createElement) window.onload = function() {\n" +
													"var ele = document.createElement('div');\n" +
													"var style = ele.style;\n" +
													"style.border = 'gray 2px solid';\n" +
													"style.width = '200px';\n" +
													"style.zIndex = 10000;\n" +
													"style.top = '0px';\n" +
													"style.right = '0px';\n" +
													"style.position = 'absolute';\n" +
													"style.background = 'white';\n" +
													"var home = document.createElement('a');\n" +
													"home.href = \"/\";\n" +
													"var home_str = document.createTextNode('[HOME]');\n" +
													"home.appendChild(home_str);\n" +
													"ele.appendChild(home);\n" +
													"var reset = document.createElement('a');\n" +
													"reset.href = \"/reset\";\n" +
													"var reset_str = document.createTextNode('[RESET]');\n" +
													"reset.appendChild(reset_str);\n" +
													"ele.appendChild(reset);\n" +
													"document.body.appendChild(ele);\n" +
												"}\n" +
												"// -->\n" +
												"</script>\n" +
												"</head>\n" +
												"<body>\n"+
													InputPage.input.getHtml()+
												"</body>\n" +
												"</html>\n");
			m = mp.parse();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ResourceStreamNotFoundException e) {
			e.printStackTrace();
		}
		
		return new MarkupStream(m);
	}
	
	public ShowPage(PageParameters params){
	}
}
