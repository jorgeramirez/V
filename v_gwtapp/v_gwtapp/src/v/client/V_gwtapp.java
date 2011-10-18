package v.client;

import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Html;
import com.extjs.gxt.ui.client.widget.Viewport;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class V_gwtapp implements EntryPoint {

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		Viewport viewport = new Viewport();
		final BorderLayout borderLayout = new BorderLayout();
		viewport.setLayout(borderLayout);
		
		BorderLayoutData westData = new BorderLayoutData(LayoutRegion.WEST,200, 150, 300);
		westData.setCollapsible(true);
		westData.setSplit(true);
		
		BorderLayoutData centerData = new BorderLayoutData(LayoutRegion.CENTER);
		centerData.setCollapsible(false);
		
		ContentPanel navPanel = new ContentPanel();
		viewport.add(navPanel, westData);
		
		Html centerHtml = new Html();
		centerHtml.setHtml("<h1> Under Construction </h1>");
		viewport.add(centerHtml, centerData);
		
		RootPanel.get().add(viewport);
	}
}
