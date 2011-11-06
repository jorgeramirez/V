package v.client;

import java.util.ArrayList;
import java.util.List;

import v.client.controllers.AbstractController;
import v.client.controllers.UsuariosController;
import v.client.rpc.AdministradorService;
import v.client.rpc.AdministradorServiceAsync;
import v.shared.model.Roles;

import com.extjs.gxt.ui.client.Registry;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.RootPanel;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class V_gwtapp implements EntryPoint {

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		//creamos los servicios
		AdministradorServiceAsync adminService = (AdministradorServiceAsync)GWT.create(AdministradorService.class);
		Registry.register(AppConstants.ADMINISTRADOR_SERVICE, adminService);
		
		//seteamos controladores
		List<AbstractController> controllers = new ArrayList<AbstractController>();
		controllers.add(new UsuariosController());
		Dispatcher d = new Dispatcher(controllers);
		Registry.register("dispatcher", d);
		
		AppViewport viewport = new AppViewport(Roles.ADMINISTRADOR);
		RootPanel.get().add(viewport);
	}
}
