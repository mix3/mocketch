package org.mix3.mocketch;

import junit.framework.TestCase;
import org.apache.wicket.util.tester.WicketTester;
import org.mix3.mocketch.page.InputPage;

/**
 * Simple test using the WicketTester
 */
public class TestHomePage extends TestCase
{
	private WicketTester tester;

	@Override
	public void setUp()
	{
		tester = new WicketTester(new WicketApplication());
	}

	public void testRenderMyPage()
	{
		//start and render the test page
		tester.startPage(InputPage.class);
//
//		//assert rendered page class
//		tester.assertRenderedPage(HomePage.class);
//
//		//assert rendered label component
//		tester.assertLabel("message", "If you see this message wicket is properly configured and running");
	}
}
