package v.client.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.FilterPagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.grid.CheckBoxSelectionModel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.google.gwt.user.client.rpc.AsyncCallback;

import v.client.AppConstants;
import v.client.AppViewport;
import v.client.rpc.AdministradorServiceAsync;
import v.client.widgets.CrudGrid;
import v.modelo.Usuario;

/**
 * Controlador de ABM de Usuarios
 * 
 * @author Jorge Ramírez <jorgeramirez1990@gmail.com> 
 **/
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

		// Selection Model para el grid
		CheckBoxSelectionModel<BeanModel> cbsm = new CheckBoxSelectionModel<BeanModel>();
		columns.add(cbsm.getColumn());		
		
		columns.add(new ColumnConfig("username", "Username", 100));
		columns.add(new ColumnConfig("rol", "Rol", 100));
		columns.add(new ColumnConfig("cedula", "Cédula", 100));

		ColumnConfig nroCajaColumn = new ColumnConfig("nroCaja", "Nro. de Caja", 100);
		nroCajaColumn.setRenderer(new GridCellRenderer<BeanModel>() {

			@Override
			public Object render(BeanModel model, String property,
								 ColumnData config, int rowIndex, int colIndex,
								 ListStore<BeanModel> store, Grid<BeanModel> grid) {
				Usuario u = (Usuario)model.getBean();
				if(u.getRol().equals(AppConstants.CAJERO_ROL)){
					return u.getCaja().getNumeroCaja();
				}
				return "-----";
			}
		});
		
		columns.add(nroCajaColumn);
		columns.add(new ColumnConfig("nombre", "Nombre", 100));
		columns.add(new ColumnConfig("apellido", "Apellido", 100));
		columns.add(new ColumnConfig("direccion", "Dirección", 200));
		columns.add(new ColumnConfig("telefono", "Telefono", 100));

	
		ColumnModel cm = new ColumnModel(columns);
		
		
		HashMap<String, AppConstants.Filtros> fc = new HashMap<String, AppConstants.Filtros>();
		fc.put("username", AppConstants.Filtros.STRING_FILTER);
		fc.put("cedula", AppConstants.Filtros.STRING_FILTER);
		fc.put("rol", AppConstants.Filtros.STRING_FILTER);
		
		CrudGrid<Usuario> usuariosGrid = new CrudGrid<Usuario>("ABM Usuarios", cm, fc, proxy, cbsm);
		LayoutContainer cp = (LayoutContainer)Registry.get(AppViewport.CENTER_REGION);
		cp.add(usuariosGrid);
		cp.layout();

	}

}
