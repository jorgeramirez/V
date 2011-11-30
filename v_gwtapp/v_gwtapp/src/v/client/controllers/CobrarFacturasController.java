package v.client.controllers;

import v.client.AppConstants;
import v.client.AppViewport;
import v.client.Util;
import v.client.dialogs.ListarPagosDialog;
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
	final LoginServiceAsync loginService = (LoginServiceAsync)Registry.get(AppConstants.LOGIN_SERVICE);
	private Usuario user;

	public CobrarFacturasController() {
		super(AppConstants.COBRAR_FACTURA_LABEL);
	}

	@Override
	public void init() {

		loginService.getSessionAttribute("usuario", new AsyncCallback<Usuario>() {
			
			@Override
			public void onSuccess(Usuario u) {
				user = u;
				if(u.getRol().compareTo(AppConstants.CAJERO_ROL) != 0){
					MessageBox.alert("Advertencia", "Sr. Administrador, esta funcionalidad puede ser realizada solo por cajeros", null);
					return;
				}

				// creamos grid para Cobro de Facturas.
				grid = new CobroFacturasGrid("Facturas Pendientes");
				bindHandlers();

				LayoutContainer cp = (LayoutContainer)Registry.get(AppViewport.CENTER_REGION);
				cp.add(grid);
				cp.layout();
				
			}
			
			@Override
			public void onFailure(Throwable caught) {
				MessageBox.alert("Error en el servidor", caught.getMessage(), null);
			}
		});		

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
		service.registrarPago(pago, new AsyncCallback<String>() {

			@Override
			public void onFailure(Throwable caught) {
				MessageBox.alert("Error en el Servidor", caught.getMessage(), null);
			}

			@Override
			public void onSuccess(String errorMsg) {
				if(errorMsg == null){
					grid.getGrid().getStore().getLoader().load();
				}else{
					MessageBox.alert("Error", errorMsg, null);
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
				FacturaVenta factura = (FacturaVenta)grid.getGrid().getSelectionModel().getSelectedItem().getBean();
				final Pago p = new Pago();
				p.setFactura(factura);
				p.setMonto(factura.getSaldo());
				p.setEstado(AppConstants.PAGO_NO_CERRADO);
				p.setUsuario(user);
				p.setCaja(user.getCaja());
				form.getFormBindings().bind(Util.createBeanModel(p));
				bindButtonsHandlers(form, true);
			}
		});
		form.show();
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
