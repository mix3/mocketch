package org.mix3.mocketch;

import org.apache.wicket.markup.html.WebPage;
import org.apache.wicket.protocol.http.WebApplication;
import org.mix3.mocketch.page.InputPage;
import org.mix3.mocketch.page.ResetPage;
import org.mix3.mocketch.page.ShowPage;

public class WicketApplication extends WebApplication{
	@Override
	protected void init() {
		getRequestCycleSettings().setResponseRequestEncoding("UTF-8");
		getMarkupSettings().setDefaultMarkupEncoding("UTF-8");
		
		mountBookmarkablePage("/show", ShowPage.class);
		mountBookmarkablePage("/reset", ResetPage.class);
	}
	
	public WicketApplication(){
	}
	
	public Class<? extends WebPage> getHomePage(){
		return InputPage.class;
	}
}
