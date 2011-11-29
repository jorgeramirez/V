package v.client.controllers;

import v.client.AppConstants;
import v.client.AppViewport;
import v.client.dialogs.ListarCompraDetallesDialog;
import v.client.grids.ComprasGrid;
import v.modelo.FacturaCompra;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.LayoutContainer;

/**
 * Controlador de Listado de Compras
 * 
 * @author Jorge Ramírez <jorgeramirez1990@gmail.com> 
 **/
public class ListarComprasController extends AbstractController {

	private ComprasGrid grid;
	
	public ListarComprasController() {
		super(AppConstants.LISTAR_COMPRAS_LABEL);
	}

	@Override
	public void init() {
		// creamos grid para listado de compras
		grid = new ComprasGrid("Compras", false, true);
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
				grid.getVerDetallesButton().addSelectionListener(new SelectionListener<ButtonEvent>() {

					@Override
					public void componentSelected(ButtonEvent ce) {
						onVerDetallesClicked();
					}
				});
			}
		
		});
	}

	/**
	 * Despliega el {@link Dialog} con la lista de detalles para esta compra
	 **/
	private void onVerDetallesClicked() {
		FacturaCompra compra = (FacturaCompra)grid.getGrid().getSelectionModel().getSelectedItem().getBean();
		ListarCompraDetallesDialog detalles = new ListarCompraDetallesDialog(compra);
		detalles.show();
	}

}
