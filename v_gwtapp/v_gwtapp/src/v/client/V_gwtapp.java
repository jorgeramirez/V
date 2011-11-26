package v.client;

import java.util.ArrayList;
import java.util.List;

import v.client.controllers.AbstractController;
import v.client.controllers.CajasController;
import v.client.controllers.ClientesController;
import v.client.controllers.ProductosController;
import v.client.controllers.ProveedoresController;
import v.client.controllers.UsuariosController;
import v.client.rpc.AdministradorService;
import v.client.rpc.AdministradorServiceAsync;
import v.client.rpc.CompradorService;
import v.client.rpc.CompradorServiceAsync;
import v.client.rpc.VendedorService;
import v.client.rpc.VendedorServiceAsync;
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
		CompradorServiceAsync compradorService = (CompradorServiceAsync)GWT.create(CompradorService.class);
		VendedorServiceAsync vendedorService = (VendedorServiceAsync)GWT.create(VendedorService.class);
		
		Registry.register(AppConstants.ADMINISTRADOR_SERVICE, adminService);
		Registry.register(AppConstants.COMPRADOR_SERVICE, compradorService);
		Registry.register(AppConstants.VENDEDOR_SERVICE, vendedorService);
		
		
		//seteamos controladores
		List<AbstractController> controllers = new ArrayList<AbstractController>();
		controllers.add(new UsuariosController());
		controllers.add(new CajasController());
		controllers.add(new ProductosController());
		controllers.add(new ProveedoresController());
		controllers.add(new ClientesController());
		
		Dispatcher d = new Dispatcher(controllers);
		Registry.register("dispatcher", d);
		
		AppViewport viewport = new AppViewport(Roles.ADMINISTRADOR);
		RootPanel.get().add(viewport);
	}
}
