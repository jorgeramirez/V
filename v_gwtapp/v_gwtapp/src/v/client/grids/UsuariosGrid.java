package v.client.grids;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import v.client.AppConstants;
import v.client.rpc.AdministradorServiceAsync;
import v.client.widgets.CustomGrid;
import v.modelo.Usuario;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.FilterPagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.google.gwt.user.client.rpc.AsyncCallback;


/**
 * Define un {@link Grid} para Usuarios
 * 
 * @author Jorge Ramírez <jorgeramirez1990@gmail.com>
 **/
public class UsuariosGrid extends CustomGrid<Usuario> {
	final AdministradorServiceAsync service = Registry.get(AppConstants.ADMINISTRADOR_SERVICE);

	public UsuariosGrid(String title, boolean useCheckBoxSm, boolean hasFilters) {
		super(title, useCheckBoxSm, hasFilters);
	}

	public Map<String, AppConstants.Filtros> buildFiltersConfig() {
		// establecemos los filtros
		Map<String, AppConstants.Filtros> fc = null;
		if(hasFilters){
			fc = new HashMap<String, AppConstants.Filtros>();
			fc.put("username", AppConstants.Filtros.STRING_FILTER);
			fc.put("cedula", AppConstants.Filtros.STRING_FILTER);
			fc.put("rol", AppConstants.Filtros.STRING_FILTER);
			fc.put("nombre", AppConstants.Filtros.STRING_FILTER);
			fc.put("apellido", AppConstants.Filtros.STRING_FILTER);
			fc.put("email", AppConstants.Filtros.STRING_FILTER);
			fc.put("direccion", AppConstants.Filtros.STRING_FILTER);
			fc.put("telefono", AppConstants.Filtros.STRING_FILTER);
		}
		return fc;
	}

	public ColumnModel buildColumnModel() {
		// Creamos los ColumnConfigs
		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();

		// Selection Model para el grid
		if(useCheckBoxSm){
			columns.add(cbsm.getColumn());
		}
		// username
		ColumnConfig column = new ColumnConfig("username", "Username", 100);
		columns.add(column);

		// rol
		column = new ColumnConfig("rol", "Rol", 100);
		columns.add(column);

		// cedula
		column = new ColumnConfig("cedula", "Cédula", 100);
		columns.add(column);

		// nro. de caja field.
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
				return null;
			}
		});
		columns.add(nroCajaColumn);

		// nombre
		column = new ColumnConfig("nombre", "Nombre", 100);
		columns.add(column);

		// apellido
		column = new ColumnConfig("apellido", "Apellido", 100);
		columns.add(column);

		// direccion
		column = new ColumnConfig("direccion", "Dirección", 200);
		columns.add(column);

		// telefono
		column = new ColumnConfig("telefono", "Teléfono", 100);
		columns.add(column);

		// email
		column = new ColumnConfig("email", "Email", 200);
		columns.add(column);

		return new ColumnModel(columns);
	}

	public RpcProxy<PagingLoadResult<Usuario>> buildProxy() {
		return new RpcProxy<PagingLoadResult<Usuario>>() {
			@Override
			public void load(Object loadConfig, AsyncCallback<PagingLoadResult<Usuario>> callback) {
				service.listarUsuarios((FilterPagingLoadConfig)loadConfig, callback);
			}
		};			
	}

}
