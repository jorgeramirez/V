package v.client.grids;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import v.client.AppConstants;
import v.client.AppConstants.Filtros;
import v.client.rpc.AdministradorServiceAsync;
import v.client.widgets.CustomGrid;
import v.modelo.Caja;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.data.FilterPagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.google.gwt.user.client.rpc.AsyncCallback;


/**
 * Define un {@link Grid} para Cajas
 * 
 * @author Jorge Ramírez <jorgeramirez1990@gmail.com>
 **/
public class CajasGrid extends CustomGrid<Caja> {
	final AdministradorServiceAsync service = Registry.get(AppConstants.ADMINISTRADOR_SERVICE);

	public CajasGrid(String title, boolean useCheckBoxSm, boolean hasFilters) {
		super(title, useCheckBoxSm, hasFilters);
	}

	@Override
	public RpcProxy<PagingLoadResult<Caja>> buildProxy() {
		return new RpcProxy<PagingLoadResult<Caja>>() {
			@Override
			public void load(Object loadConfig, AsyncCallback<PagingLoadResult<Caja>> callback) {
				service.listarCajas((FilterPagingLoadConfig)loadConfig, callback);
			}
		};	
	}

	@Override
	public Map<String, Filtros> buildFiltersConfig() {
		// establecemos los filtros
		Map<String, AppConstants.Filtros> fc = null;
		if(hasFilters){
			fc = new HashMap<String, AppConstants.Filtros>();
			fc.put("numeroCaja", AppConstants.Filtros.STRING_FILTER);
		}
		return fc;
	}

	@Override
	public ColumnModel buildColumnModel() {
		// Creamos los ColumnConfigs
		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();

		// Selection Model para el grid
		if(useCheckBoxSm){
			columns.add(cbsm.getColumn());
		}
		// Nro de Caja
		ColumnConfig column = new ColumnConfig("numeroCaja", "Nro de Caja", 100);
		columns.add(column);

		// Descripcion
		column = new ColumnConfig("descripcion", "Descripcion", 230);
		columns.add(column);

		return new ColumnModel(columns);
	}

}
