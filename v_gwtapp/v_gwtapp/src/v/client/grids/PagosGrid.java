package v.client.grids;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import v.client.AppConstants;
import v.client.AppConstants.Filtros;
import v.client.rpc.CajeroServiceAsync;
import v.client.widgets.CustomGrid;
import v.modelo.FacturaVenta;
import v.modelo.Pago;

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
 * Define un {@link Grid} para Pagos
 * 
 * @author Jorge Ramírez <jorgeramirez1990@gmail.com>
 **/
public class PagosGrid extends CustomGrid<Pago> {
	final CajeroServiceAsync service = (CajeroServiceAsync)Registry.get(AppConstants.CAJERO_SERVICE);
	FacturaVenta factura;
	private boolean facturaNull;
	
	public PagosGrid(FacturaVenta factura) {
		super(factura == null ? "Listado de Pagos": null, true, true);
		facturaNull = factura == null;
		if(!facturaNull){
			this.factura = new FacturaVenta();
			this.factura.setNumeroFactura(factura.getNumeroFactura());
		}
	}

	public FacturaVenta getFactura() {
		return factura;
	}

	public void setFactura(FacturaVenta factura) {
		this.factura = factura;
	}

	@Override
	public RpcProxy<PagingLoadResult<Pago>> buildProxy() {
		return new RpcProxy<PagingLoadResult<Pago>>() {
			@Override
			public void load(Object loadConfig, AsyncCallback<PagingLoadResult<Pago>> callback) {
				service.listarPagos((FilterPagingLoadConfig)loadConfig, factura, callback);
			}
		};
	}

	@Override
	public Map<String, Filtros> buildFiltersConfig() {
		// establecemos los filtros
		Map<String, AppConstants.Filtros> fc = null;
		if(hasFilters){
			fc = new HashMap<String, AppConstants.Filtros>();
			if(facturaNull){
				fc.put("factura.numeroFactura", AppConstants.Filtros.INTEGER_FILTER);
			}
			fc.put("usuario.username", AppConstants.Filtros.STRING_FILTER);
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
		// monto
		ColumnConfig column = new ColumnConfig("monto", "Monto", 100);
		columns.add(column);

		// estado
		column = new ColumnConfig("estado", "Estado", 100);
		columns.add(column);

		// fecha
		column = new ColumnConfig("fecha", "Fecha", 100);
		columns.add(column);
		
		
		if(facturaNull){
			// factura
			column = new ColumnConfig("factura.numeroFactura", "Factura", 100);
			column.setRenderer(new GridCellRenderer<BeanModel>() {

				@Override
				public Object render(BeanModel model, String property,
						ColumnData config, int rowIndex, int colIndex,
						ListStore<BeanModel> store, Grid<BeanModel> grid) {
					return ((Pago)model.getBean()).getFactura().getNumeroFactura();
				}
			});
			columns.add(column);
		}

		// cajero
		column = new ColumnConfig("usuario.username", "Cajero", 100);
		column.setRenderer(new GridCellRenderer<BeanModel>() {

			@Override
			public Object render(BeanModel model, String property,
					ColumnData config, int rowIndex, int colIndex,
					ListStore<BeanModel> store, Grid<BeanModel> grid) {
				return ((Pago)model.getBean()).getUsuario().getUsername();
			}
		});
		columns.add(column);

		// Caja
		column = new ColumnConfig("caja", "Caja", 100);
		column.setRenderer(new GridCellRenderer<BeanModel>() {

			@Override
			public Object render(BeanModel model, String property,
					ColumnData config, int rowIndex, int colIndex,
					ListStore<BeanModel> store, Grid<BeanModel> grid) {
				return ((Pago)model.getBean()).getCaja().getNumeroCaja();
			}
		});
		columns.add(column);
		return new ColumnModel(columns);
	}

}
