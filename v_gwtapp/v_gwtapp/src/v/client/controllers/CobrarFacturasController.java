package v.client.controllers;

import v.client.AppConstants;
import v.client.AppViewport;
import v.client.Util;
import v.client.forms.PagoEditorForm;
import v.client.grids.CobroFacturasGrid;
import v.client.rpc.CajeroServiceAsync;
import v.client.rpc.LoginServiceAsync;
import v.client.widgets.EditorForm;
import v.modelo.FacturaVenta;
import v.modelo.Pago;
import v.modelo.Usuario;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Controlador de Cobro de Facturas
 * 
 * @author Jorge Ramírez <jorgeramirez1990@gmail.com> 
 **/
public class CobrarFacturasController extends AbstractController {

	private CobroFacturasGrid grid;
	private final CajeroServiceAsync service = Registry.get(AppConstants.CAJERO_SERVICE);

	public CobrarFacturasController() {
		super(AppConstants.COBRAR_FACTURA_LABEL);
	}

	@Override
	public void init() {

		// creamos grid para Cobro de Facturas.
		grid = new CobroFacturasGrid("Facturas Pendientes");
		bindHandlers();

		LayoutContainer cp = (LayoutContainer)Registry.get(AppViewport.CENTER_REGION);
		cp.add(grid);
		cp.layout();

	}

	/**
	 * Método que se encarga de construir el {@link PagoEditorForm}
	 **/
	private EditorForm buildEditorForm(boolean create) {
		return new PagoEditorForm("Registrar Pago", create, 500, 280);
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
				grid.getAddPagoButton().addSelectionListener(new SelectionListener<ButtonEvent>() {

					@Override
					public void componentSelected(ButtonEvent ce) {
						onAddClicked();
					}
				});

			}
		
		});
	}

	/**
	 * Asocia los botones del {@link EditorForm} con sus respectivos handlers
	 * definidos en el {@link CobrarFacturasController}
	 **/
	private void bindButtonsHandlers(final EditorForm form, final boolean create){
		form.getButtonById(Dialog.OK).addSelectionListener(new SelectionListener<ButtonEvent>() {

			@Override
			public void componentSelected(ButtonEvent ce) {
				if(form.getForm().isValid()){
					BeanModel p = (BeanModel)form.getFormBindings().getModel();
					savePago(p);
				}else{ // si hay campos invalidos mostramos de vuelta el form
					form.show();
				}
			}
		});		
	}

	/**
	 * Guarda el pago creado
	 **/
	private void savePago(BeanModel p){
		Pago pago = (Pago)p.getBean();
		service.registrarPago(pago, new AsyncCallback<Pago>() {

			@Override
			public void onFailure(Throwable caught) {
				MessageBox.alert("Error en el Servidor", caught.getMessage(), null);
			}

			@Override
			public void onSuccess(Pago pago) {
				if(pago == null){
					MessageBox.alert("Error", "No se pudo registrar el pago", null);
				}else{
					grid.getGrid().getStore().getLoader().load();
				}
			}
		});
	}


	/**
	 * Handler para el evento click del botón addPagoButton del {@link CobroFacturasGrid}
	 **/
	private void onAddClicked() {
		final EditorForm form = buildEditorForm(true);
		/**
		 * Hacemos el bind de los handlers para los botones
		 * del Form
		 **/		
		form.addListener(Events.Render, new Listener<BaseEvent>() {

			@Override
			public void handleEvent(BaseEvent be) {
				final LoginServiceAsync loginService = (LoginServiceAsync)Registry.get(AppConstants.LOGIN_SERVICE);
				FacturaVenta factura = (FacturaVenta)grid.getGrid().getSelectionModel().getSelectedItem().getBean();
				final Pago p = new Pago();
				p.setFactura(factura);
				loginService.getSessionAttribute("usuario", new AsyncCallback<Usuario>() {

					@Override
					public void onFailure(Throwable caught) {
						MessageBox.alert("Error en el Servidor", caught.getMessage(), null);
					}

					@Override
					public void onSuccess(Usuario cajero) {
						p.setUsuario(cajero);
						p.setCaja(cajero.getCaja());
						form.getFormBindings().bind(Util.createBeanModel(p));
					}
				});
				bindButtonsHandlers(form, true);
			}
		});
		form.show();
	}
}
