package v.client.controllers;

import v.client.AppConstants;
import v.client.AppViewport;
import v.client.dialogs.ListarPagosDialog;
import v.client.grids.FacturasGrid;
import v.modelo.FacturaVenta;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.LayoutContainer;

/**
 * Controlador de Listado de Facturas
 * 
 * @author Jorge Ramírez <jorgeramirez1990@gmail.com> 
 **/
public class ListarFacturasController extends AbstractController {
	
	private FacturasGrid grid;
	
	public ListarFacturasController() {
		super(AppConstants.LISTAR_FACTURAS_LABEL);
	}

	@Override
	public void init() {
		// creamos grid para Cobro de Facturas.
		grid = new FacturasGrid("Facturas", true, true);
		bindHandlers();

		LayoutContainer cp = (LayoutContainer)Registry.get(AppViewport.CENTER_REGION);
		cp.add(grid);
		cp.layout();		
	}

	/**
	 * Asocia a cada evento con su respectivo handler definido
	 * en el controlador.
	 **/
	private void bindHandlers() {
		//Asociamos a cada botón con su respectivo handler del controlador
		grid.addListener(Events.Render, new Listener<BaseEvent>() {

			@Override
			public void handleEvent(BaseEvent be) {
				grid.getVerPagosButton().addSelectionListener(new SelectionListener<ButtonEvent>() {

					@Override
					public void componentSelected(ButtonEvent ce) {
						onVerPagosClicked();
					}
				});
			}
		
		});
	}

	/**
	 * Despliega el Dialog con la lista de pagos para esta factura.
	 **/
	private void onVerPagosClicked() {
		FacturaVenta factura = (FacturaVenta)grid.getGrid().getSelectionModel().getSelectedItem().getBean();
		ListarPagosDialog pagos = new ListarPagosDialog(factura);
		pagos.show();
	}	
}
