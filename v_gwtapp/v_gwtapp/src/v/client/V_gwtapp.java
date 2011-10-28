package v.client;

import v.shared.model.Roles;

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
		AppViewport viewport = new AppViewport(Roles.ADMINISTRADOR);
		RootPanel.get().add(viewport);
	}
}
