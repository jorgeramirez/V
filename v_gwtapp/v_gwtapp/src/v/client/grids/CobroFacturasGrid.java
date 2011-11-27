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

public class CobroFacturasGrid extends CustomGrid<FacturaVenta> {
	final CajeroServiceAsync service = (CajeroServiceAsync)Registry.get(AppConstants.CAJERO_SERVICE);
	private Button addPagoButton;
	
	public CobroFacturasGrid(String title) {
		super(title, true, true);
	}

	public Button getAddPagoButton() {
		return addPagoButton;
	}

	public void setAddPagoButton(Button addPagoButton) {
		this.addPagoButton = addPagoButton;
	}

	@Override
	protected ToolBar createTopToolBar() {
		ToolBar tb = super.createTopToolBar();
		addPagoButton = new Button("Registrar Pago");
		addPagoButton.setEnabled(false);
		addPagoButton.setIconStyle("icon-add");
		tb.add(addPagoButton);
		return tb;
	}

	public void onRender(Element target, int index) {
		super.onRender(target, index);
		
		//Controlamos enabled/disabled del botón addPagoButton del ToolBar
		this.getGrid().getSelectionModel().addSelectionChangedListener(new SelectionChangedListener<BeanModel>() {

			@Override
			public void selectionChanged(SelectionChangedEvent<BeanModel> se) {
				addPagoButton.setEnabled(se.getSelection().size() > 0);
			}
		});
	}	
	
	@Override
	public RpcProxy<PagingLoadResult<FacturaVenta>> buildProxy() {
		return new RpcProxy<PagingLoadResult<FacturaVenta>>() {
			@Override
			public void load(Object loadConfig, AsyncCallback<PagingLoadResult<FacturaVenta>> callback) {
				service.listarFacturasPendientes((FilterPagingLoadConfig)loadConfig, callback);
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
			fc.put("nombreCliente", AppConstants.Filtros.STRING_FILTER);
			fc.put("cliente.nombre", AppConstants.Filtros.STRING_FILTER);
			fc.put("vendedor.nombre", AppConstants.Filtros.STRING_FILTER);
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
		ColumnConfig column = new ColumnConfig("numeroFactura", "Número de Factura", 100);
		columns.add(column);

		// fecha
		column = new ColumnConfig("fecha", "Fecha", 100);
		columns.add(column);

		// total
		column = new ColumnConfig("total", "Total", 100);
		columns.add(column);

		// saldo
		column = new ColumnConfig("saldo", "Saldo", 100);
		columns.add(column);
		
		// cliente
		column = new ColumnConfig("cliente.nombre", "Cliente", 100);
		column.setRenderer(new GridCellRenderer<BeanModel>() {

			@Override
			public Object render(BeanModel model, String property,
					ColumnData config, int rowIndex, int colIndex,
					ListStore<BeanModel> store, Grid<BeanModel> grid) {
				return ((FacturaVenta)model.getBean()).getCliente().getNombre();
			}
		
		});
		columns.add(column);

		// vendedor
		column = new ColumnConfig("vendedor.nombre", "Vendedor", 100);
		column.setRenderer(new GridCellRenderer<BeanModel>() {

			@Override
			public Object render(BeanModel model, String property,
					ColumnData config, int rowIndex, int colIndex,
					ListStore<BeanModel> store, Grid<BeanModel> grid) {
				return ((FacturaVenta)model.getBean()).getVendedor().getNombre();
			}
		
		});
		columns.add(column);
		
		return new ColumnModel(columns);
	}

}
