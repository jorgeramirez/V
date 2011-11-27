package v.client.grids;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import v.client.AppConstants;
import v.client.rpc.CompradorServiceAsync;
import v.client.widgets.CustomGrid;
import v.modelo.Proveedor;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.data.FilterPagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Define un {@link Grid} para {@link Proveedor}
 * 
 * @author Jorge Ramírez <jorgeramirez1990@gmail.com>
 **/
public class ProveedoresGrid extends CustomGrid<Proveedor> {
	final CompradorServiceAsync service = Registry.get(AppConstants.COMPRADOR_SERVICE);

	public ProveedoresGrid(String title, boolean useCheckBoxSm, boolean hasFilters) {
		super(title, useCheckBoxSm, hasFilters);
	}
	
	public Map<String, AppConstants.Filtros> buildFiltersConfig() {
		// establecemos los filtros
		Map<String, AppConstants.Filtros> fc = null;
		if(hasFilters){
		fc = new HashMap<String, AppConstants.Filtros>();
			fc.put("ruc", AppConstants.Filtros.STRING_FILTER);
			fc.put("nombre", AppConstants.Filtros.STRING_FILTER);
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
		// ruc
		ColumnConfig column = new ColumnConfig("ruc", "RUC", 100);
		columns.add(column);

		// nombre
		column = new ColumnConfig("nombre", "Nombre", 100);
		columns.add(column);

		// direccion
		column = new ColumnConfig("direccion", "Dirección", 200);
		columns.add(column);

		// telefono
		column = new ColumnConfig("telefono", "Telefono", 100);
		columns.add(column);

		return new ColumnModel(columns);
	}

	public RpcProxy<PagingLoadResult<Proveedor>> buildProxy() {
		return new RpcProxy<PagingLoadResult<Proveedor>>() {
			@Override
			public void load(Object loadConfig, AsyncCallback<PagingLoadResult<Proveedor>> callback) {
				service.listarProveedores((FilterPagingLoadConfig)loadConfig, callback);
			}
		};			
	}

}
