package v.client.grids;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import v.client.AppConstants;
import v.client.rpc.VendedorServiceAsync;
import v.client.widgets.CustomGrid;
import v.modelo.Cliente;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.data.FilterPagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class ClientesGrid extends CustomGrid<Cliente> {
	final VendedorServiceAsync service = Registry.get(AppConstants.VENDEDOR_SERVICE);

	public ClientesGrid(String title, boolean useCheckBoxSm, boolean hasFilters) {
		super(title, useCheckBoxSm, hasFilters);
	}
	
	public Map<String, AppConstants.Filtros> buildFiltersConfig() {
		// establecemos los filtros
		Map<String, AppConstants.Filtros> fc = null;
		if(hasFilters){
		fc = new HashMap<String, AppConstants.Filtros>();
			fc.put("nombre", AppConstants.Filtros.STRING_FILTER);
			fc.put("apellido", AppConstants.Filtros.STRING_FILTER);
			fc.put("cedula", AppConstants.Filtros.STRING_FILTER);
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
		// nombre
		ColumnConfig column = new ColumnConfig("nombre", "Nombre", 100);
		columns.add(column);

		// apellido
		column = new ColumnConfig("apellido", "Apellido", 100);
		columns.add(column);

		// cedula
		column = new ColumnConfig("cedula", "Cédula", 100);
		columns.add(column);

		// Dirección
		column = new ColumnConfig("direccion", "Dirección", 200);
		columns.add(column);

		// teléfono
		column = new ColumnConfig("telefono", "Teléfono", 100);
		columns.add(column);


		return new ColumnModel(columns);
	}

	public RpcProxy<PagingLoadResult<Cliente>> buildProxy() {
		return new RpcProxy<PagingLoadResult<Cliente>>() {
			@Override
			public void load(Object loadConfig, AsyncCallback<PagingLoadResult<Cliente>> callback) {
				service.listarClientes((FilterPagingLoadConfig)loadConfig, callback);
			}
		};			
	}
}
