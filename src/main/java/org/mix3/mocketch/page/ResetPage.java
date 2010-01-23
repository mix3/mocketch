package org.mix3.mocketch.page;

import java.util.HashMap;

import org.apache.wicket.Component;
import org.apache.wicket.RestartResponseException;
import org.apache.wicket.markup.html.WebPage;
import org.mix3.mocketch.util.MyWebPage;

public class ResetPage extends WebPage{
	public ResetPage(){
		MyWebPage.componentMap = new HashMap<String, Component>();
		MyWebPage.sampleModel = new HashMap<String, Object>();
		setRedirect(true);
		throw new RestartResponseException(InputPage.class);
	}
}
