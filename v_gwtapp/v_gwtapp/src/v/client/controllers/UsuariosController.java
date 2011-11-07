package v.client.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.data.FilterPagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.google.gwt.user.client.rpc.AsyncCallback;

import v.client.AppConstants;
import v.client.AppViewport;
import v.client.rpc.AdministradorServiceAsync;
import v.client.widgets.CrudGrid;
import v.modelo.Usuario;

public class UsuariosController extends AbstractController {

	public UsuariosController() {
		super(AppConstants.USUARIOS_LABEL);
	}
	
	@Override
	public void init() {
		final AdministradorServiceAsync service = Registry.get(AppConstants.ADMINISTRADOR_SERVICE);
		RpcProxy<PagingLoadResult<Usuario>> proxy = new RpcProxy<PagingLoadResult<Usuario>>() {
			@Override
			public void load(Object loadConfig, AsyncCallback<PagingLoadResult<Usuario>> callback) {
				service.listarUsuarios((FilterPagingLoadConfig)loadConfig, callback);
			}
		};
		
		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
		columns.add(new ColumnConfig("username", "Username", 200));
		columns.add(new ColumnConfig("nombre", "Nombre", 200));
		columns.add(new ColumnConfig("apellido", "Apellido", 200));
		columns.add(new ColumnConfig("cedula", "Cédula", 200));
		columns.add(new ColumnConfig("rol", "Rol", 200));
		columns.add(new ColumnConfig("direccion", "Dirección", 200));
		columns.add(new ColumnConfig("telefono", "Telefono", 200));
		ColumnModel cm = new ColumnModel(columns);
		
		HashMap<String, AppConstants.Filtros> fc = new HashMap<String, AppConstants.Filtros>();
		fc.put("username", AppConstants.Filtros.STRING_FILTER);
		fc.put("cedula", AppConstants.Filtros.STRING_FILTER);
		fc.put("rol", AppConstants.Filtros.STRING_FILTER);
		
		CrudGrid<Usuario> usuariosGrid = new CrudGrid<Usuario>("ABM Usuarios", cm, fc, proxy);
		LayoutContainer cp = (LayoutContainer)Registry.get(AppViewport.CENTER_REGION);
		cp.add(usuariosGrid);
		cp.layout();
	}

}
