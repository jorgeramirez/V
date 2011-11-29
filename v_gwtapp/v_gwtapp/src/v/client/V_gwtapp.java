package v.client;

import java.util.ArrayList;
import java.util.List;

import v.client.controllers.AbstractController;
import v.client.controllers.CajasController;
import v.client.controllers.CerrarCajaController;
import v.client.controllers.ClientesController;
import v.client.controllers.CobrarFacturasController;
import v.client.controllers.ListarFacturasController;
import v.client.controllers.ListarVentasController;
import v.client.controllers.LogoutController;
import v.client.controllers.ProductosController;
import v.client.controllers.ProveedoresController;
import v.client.controllers.UsuariosController;
import v.client.controllers.VentasController;
import v.client.rpc.AdministradorService;
import v.client.rpc.AdministradorServiceAsync;
import v.client.rpc.CajeroService;
import v.client.rpc.CajeroServiceAsync;
import v.client.rpc.CompradorService;
import v.client.rpc.CompradorServiceAsync;
import v.client.rpc.LoginService;
import v.client.rpc.LoginServiceAsync;
import v.client.rpc.VendedorService;
import v.client.rpc.VendedorServiceAsync;

import v.modelo.Usuario;

import com.extjs.gxt.ui.client.Registry;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.rpc.AsyncCallback;
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
		final LoginServiceAsync loginService = (LoginServiceAsync)GWT.create(LoginService.class);
		VendedorServiceAsync vendedorService = (VendedorServiceAsync)GWT.create(VendedorService.class);
		CajeroServiceAsync cajeroService = (CajeroServiceAsync)GWT.create(CajeroService.class);
		
		Registry.register(AppConstants.LOGIN_SERVICE, loginService);
		Registry.register(AppConstants.ADMINISTRADOR_SERVICE, adminService);
		Registry.register(AppConstants.COMPRADOR_SERVICE, compradorService);
		Registry.register(AppConstants.VENDEDOR_SERVICE, vendedorService);
		Registry.register(AppConstants.CAJERO_SERVICE, cajeroService);
		
		
		//seteamos controladores
		List<AbstractController> controllers = new ArrayList<AbstractController>();
		controllers.add(new UsuariosController());
		controllers.add(new CajasController());
		controllers.add(new ProductosController());
		controllers.add(new ProveedoresController());
		controllers.add(new ClientesController());
		controllers.add(new CobrarFacturasController());
		controllers.add(new VentasController());
		controllers.add(new CerrarCajaController());
		controllers.add(new LogoutController());
		controllers.add(new ListarFacturasController());
		controllers.add(new ListarVentasController());
		
		Dispatcher d = new Dispatcher(controllers);
		Registry.register("dispatcher", d);
		
		loginService.alreadyLoggedIn(new AsyncCallback<Usuario>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(Usuario u) {
				if(u != null){
					AppViewport viewport = new AppViewport(u.getRol());
					RootPanel.get().add(viewport);					
				}else{
					LoginDialog login = new LoginDialog();
					login.show();					
				}				
			}
			
		});
	}
}
