package v.client.grids;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import v.client.AppConstants;
import v.client.rpc.CompradorServiceAsync;
import v.client.widgets.CustomGrid;
import v.modelo.Producto;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.data.FilterPagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Define un {@link Grid} para Productos
 * 
 * @author Jorge Ramírez <jorgeramirez1990@gmail.com>
 **/
public class ProductosGrid extends CustomGrid<Producto> {

	final CompradorServiceAsync service = Registry.get(AppConstants.COMPRADOR_SERVICE);

	public ProductosGrid(String title, boolean useCheckBoxSm, boolean hasFilters) {
		super(title, useCheckBoxSm, hasFilters);
	}
	
	public Map<String, AppConstants.Filtros> buildFiltersConfig() {
		// establecemos los filtros
		Map<String, AppConstants.Filtros> fc = null;
		if(hasFilters){
		fc = new HashMap<String, AppConstants.Filtros>();
			fc.put("codigo", AppConstants.Filtros.STRING_FILTER);
			fc.put("nombre", AppConstants.Filtros.STRING_FILTER);
			fc.put("costo", AppConstants.Filtros.NUMERIC_FILTER);
			fc.put("cantidad", AppConstants.Filtros.INTEGER_FILTER);
			fc.put("porcentajeGanancia", AppConstants.Filtros.NUMERIC_FILTER);
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
		// codigo
		ColumnConfig column = new ColumnConfig("codigo", "Codigo", 100);
		columns.add(column);

		// nombre
		column = new ColumnConfig("nombre", "Nombre", 100);
		columns.add(column);

		// costo
		column = new ColumnConfig("costo", "Costo", 100);
		columns.add(column);

		// cantidad
		column = new ColumnConfig("cantidad", "Cantidad", 100);
		columns.add(column);

		// porcentaje ganancia
		column = new ColumnConfig("porcentajeGanancia", "Porcentaje Ganancia", 200);
		columns.add(column);

		return new ColumnModel(columns);
	}

	public RpcProxy<PagingLoadResult<Producto>> buildProxy() {
		return new RpcProxy<PagingLoadResult<Producto>>() {
			@Override
			public void load(Object loadConfig, AsyncCallback<PagingLoadResult<Producto>> callback) {
				service.listarProductos((FilterPagingLoadConfig)loadConfig, callback);
			}
		};			
	}

}
