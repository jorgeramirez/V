package v.client.grids;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import v.client.AppConstants;
import v.client.AppConstants.Filtros;
import v.client.rpc.VendedorServiceAsync;
import v.client.widgets.CustomGrid;
import v.modelo.FacturaDetalleVenta;
import v.modelo.FacturaVenta;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style.SelectionMode;
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
 * Define un {@link Grid} para Listado de Detalles de Venta
 * 
 * @author Jorge Ramírez <jorgeramirez1990@gmail.com>
 **/
public class VentasDetallesGrid extends CustomGrid<FacturaDetalleVenta> {
	private final VendedorServiceAsync service = (VendedorServiceAsync)Registry.get(AppConstants.VENDEDOR_SERVICE);
	private FacturaVenta venta;

	public VentasDetallesGrid(FacturaVenta venta, String title, boolean useCheckBoxSm, boolean hasFilters) {
		super(title, useCheckBoxSm, hasFilters);
		this.venta = new FacturaVenta();
		this.venta.setNumeroFactura(venta.getNumeroFactura());
	}
	
	@Override
	public RpcProxy<PagingLoadResult<FacturaDetalleVenta>> buildProxy() {
		return new RpcProxy<PagingLoadResult<FacturaDetalleVenta>>() {
			@Override
			public void load(Object loadConfig, AsyncCallback<PagingLoadResult<FacturaDetalleVenta>> callback) {
				service.listarVentasDetalles((FilterPagingLoadConfig)loadConfig, venta,  callback);
			}
		};
	}

	@Override
	public Map<String, Filtros> buildFiltersConfig() {
		// establecemos los filtros
		Map<String, AppConstants.Filtros> fc = null;
		if(hasFilters){
		fc = new HashMap<String, AppConstants.Filtros>();
			fc.put("producto.codigo", AppConstants.Filtros.STRING_FILTER);
			fc.put("producto.nombre", AppConstants.Filtros.STRING_FILTER);
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
			cbsm.setSelectionMode(SelectionMode.SINGLE);
		}
		
		// producto
		ColumnConfig column = new ColumnConfig("producto.codigo", "Código de Producto", 100);
		column.setRenderer(new GridCellRenderer<BeanModel>() {

			@Override
			public Object render(BeanModel model, String property,
					ColumnData config, int rowIndex, int colIndex,
					ListStore<BeanModel> store, Grid<BeanModel> grid) {
				FacturaDetalleVenta fd = (FacturaDetalleVenta)model.getBean();
				return fd.getProducto().getCodigo();
			}
		});

		columns.add(column);

		// nombre de producto
		column = new ColumnConfig("producto.nombre", "Nombre de Producto", 100);
		column.setRenderer(new GridCellRenderer<BeanModel>() {

			@Override
			public Object render(BeanModel model, String property,
					ColumnData config, int rowIndex, int colIndex,
					ListStore<BeanModel> store, Grid<BeanModel> grid) {
				FacturaDetalleVenta fd = (FacturaDetalleVenta)model.getBean();

				return fd.getProducto().getNombre();
			}
		});

		columns.add(column);

		// precio
		column = new ColumnConfig("precio", "Precio", 100);
		column.setRenderer(new GridCellRenderer<BeanModel>() {

			@Override
			public Object render(BeanModel model, String property,
					ColumnData config, int rowIndex, int colIndex,
					ListStore<BeanModel> store, Grid<BeanModel> grid) {
				FacturaDetalleVenta fd = (FacturaDetalleVenta)model.getBean();
				return fd.getProducto().getPrecioVenta();

			}
		});
		
		// cantidad
		column = new ColumnConfig("cantidad", "Cantidad", 50);     
		columns.add(column);

		// subtotal
		column = new ColumnConfig("subtotal", "Subtotales", 100);
		column.setRenderer(new GridCellRenderer<BeanModel>() {

			@Override
			public Object render(BeanModel model, String property,
					ColumnData config, int rowIndex, int colIndex,
					ListStore<BeanModel> store, Grid<BeanModel> grid) {
				FacturaDetalleVenta fd = (FacturaDetalleVenta)model.getBean();

				return fd.getProducto().getPrecioVenta() * fd.getCantidad();

			}
		});
		columns.add(column);

		return new ColumnModel(columns);
	}

}
