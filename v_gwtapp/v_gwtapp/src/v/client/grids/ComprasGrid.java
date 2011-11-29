package v.client.grids;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import v.client.AppConstants;
import v.client.AppConstants.Filtros;
import v.client.rpc.CompradorServiceAsync;
import v.client.widgets.CustomGrid;
import v.modelo.FacturaCompra;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style.SelectionMode;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.FilterPagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Define un {@link Grid} para Listado de Compras
 * 
 * @author Jorge Ramírez <jorgeramirez1990@gmail.com>
 **/
public class ComprasGrid extends CustomGrid<FacturaCompra> {
	private final CompradorServiceAsync service = (CompradorServiceAsync)Registry.get(AppConstants.COMPRADOR_SERVICE);
	private Button verDetallesButton;
	
	public ComprasGrid(String title, boolean useCheckBoxSm, boolean hasFilters) {
		super(title, useCheckBoxSm, hasFilters);
	}
	
	public Button getVerDetallesButton() {
		return verDetallesButton;
	}

	public void setVerDetallesButton(Button verDetallesButton) {
		this.verDetallesButton = verDetallesButton;
	}

	@Override
	protected ToolBar createTopToolBar() {
		ToolBar tb = super.createTopToolBar();
		verDetallesButton = new Button("Ver Detalles");
		verDetallesButton.setEnabled(false);
		verDetallesButton.setIconStyle("icon-detalles");
		tb.add(verDetallesButton);
		return tb;
	}

	public void onRender(Element target, int index) {
		super.onRender(target, index);
		
		//Controlamos enabled/disabled del botón verDetallesButton del ToolBar
		this.getGrid().getSelectionModel().addSelectionChangedListener(new SelectionChangedListener<BeanModel>() {

			@Override
			public void selectionChanged(SelectionChangedEvent<BeanModel> se) {
				verDetallesButton.setEnabled(se.getSelection().size() > 0);
			}
		});
	}	
	
	@Override
	public RpcProxy<PagingLoadResult<FacturaCompra>> buildProxy() {
		return new RpcProxy<PagingLoadResult<FacturaCompra>>() {
			@Override
			public void load(Object loadConfig, AsyncCallback<PagingLoadResult<FacturaCompra>> callback) {
				service.listarCompras((FilterPagingLoadConfig)loadConfig, callback);
			}
		};
	}

	@Override
	public Map<String, Filtros> buildFiltersConfig() {
		// establecemos los filtros
		Map<String, AppConstants.Filtros> fc = null;
		if(hasFilters){
		fc = new HashMap<String, AppConstants.Filtros>();
			fc.put("numeroFactura", AppConstants.Filtros.INTEGER_FILTER);
			fc.put("fecha", AppConstants.Filtros.DATE_FILTER);
			fc.put("proveedor.ruc", AppConstants.Filtros.STRING_FILTER);
			fc.put("proveedor.nombre", AppConstants.Filtros.STRING_FILTER);
			fc.put("comprador.nombre", AppConstants.Filtros.STRING_FILTER);
			fc.put("comprador.cedula", AppConstants.Filtros.STRING_FILTER);
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
		
		// numero factura
		ColumnConfig column = new ColumnConfig("numeroFactura", "Número de Compra", 100);
		columns.add(column);

		// fecha
		column = new ColumnConfig("fecha", "Fecha", 100);
		columns.add(column);

		// proveedor nombre
		column = new ColumnConfig("proveedor.nombre", "Nombre del Proveedor", 100);
		column.setRenderer(new GridCellRenderer<BeanModel>() {

			@Override
			public Object render(BeanModel model, String property,
					ColumnData config, int rowIndex, int colIndex,
					ListStore<BeanModel> store, Grid<BeanModel> grid) {
				return ((FacturaCompra)model.getBean()).getProveedor().getNombre();
			}
		
		});
		columns.add(column);

		// proveedor ruc
		column = new ColumnConfig("proveedor.ruc", "RUC del Proveedor", 100);
		column.setRenderer(new GridCellRenderer<BeanModel>() {

			@Override
			public Object render(BeanModel model, String property,
					ColumnData config, int rowIndex, int colIndex,
					ListStore<BeanModel> store, Grid<BeanModel> grid) {
				return ((FacturaCompra)model.getBean()).getProveedor().getRuc();
			}
		
		});
		columns.add(column);
		
		
		// comprador nombre
		column = new ColumnConfig("comprador.nombre", "Nombre del Comprador", 100);
		column.setRenderer(new GridCellRenderer<BeanModel>() {

			@Override
			public Object render(BeanModel model, String property,
					ColumnData config, int rowIndex, int colIndex,
					ListStore<BeanModel> store, Grid<BeanModel> grid) {
				return ((FacturaCompra)model.getBean()).getComprador().getNombre();
			}
		
		});

		// comprador cedula
		column = new ColumnConfig("comprador.cedula", "Cédula del Comprador", 100);
		column.setRenderer(new GridCellRenderer<BeanModel>() {

			@Override
			public Object render(BeanModel model, String property,
					ColumnData config, int rowIndex, int colIndex,
					ListStore<BeanModel> store, Grid<BeanModel> grid) {
				return ((FacturaCompra)model.getBean()).getComprador().getCedula();
			}
		
		});
		
		columns.add(column);
		
		// total
		column = new ColumnConfig("total", "Total", 100);
		columns.add(column);

		return new ColumnModel(columns);
	}

}
