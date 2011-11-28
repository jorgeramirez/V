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
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Define un {@link Grid} para Listado de Facturas
 * 
 * @author Jorge Ramírez <jorgeramirez1990@gmail.com>
 **/
public class FacturasGrid extends CustomGrid<FacturaVenta> {
	final CajeroServiceAsync service = (CajeroServiceAsync)Registry.get(AppConstants.CAJERO_SERVICE);
	private Button verPagosButton;
	
	public FacturasGrid(String title, boolean useCheckBoxSm, boolean hasFilters) {
		super(title, useCheckBoxSm, hasFilters);
	}
	public Button getVerPagosButton() {
		return verPagosButton;
	}
	public void setVerPagosButton(Button verPagosButton) {
		this.verPagosButton = verPagosButton;
	}
	@Override
	protected ToolBar createTopToolBar() {
		ToolBar tb = super.createTopToolBar();
		verPagosButton = new Button("Ver Pagos");
		verPagosButton.setEnabled(false);
		verPagosButton.setIconStyle("icon-ver-pagos");
		tb.add(verPagosButton);
		return tb;
	}

	public void onRender(Element target, int index) {
		super.onRender(target, index);
		
		//Controlamos enabled/disabled del botón verPagosButton del ToolBar
		this.getGrid().getSelectionModel().addSelectionChangedListener(new SelectionChangedListener<BeanModel>() {

			@Override
			public void selectionChanged(SelectionChangedEvent<BeanModel> se) {
				verPagosButton.setEnabled(se.getSelection().size() > 0);
			}
		});
	}	
	
	@Override
	public RpcProxy<PagingLoadResult<FacturaVenta>> buildProxy() {
		return new RpcProxy<PagingLoadResult<FacturaVenta>>() {
			@Override
			public void load(Object loadConfig, AsyncCallback<PagingLoadResult<FacturaVenta>> callback) {
				service.listarFacturas((FilterPagingLoadConfig)loadConfig, callback);
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
			fc.put("estado", AppConstants.Filtros.STRING_FILTER);
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
		
		return new ColumnModel(columns);
	}

}
