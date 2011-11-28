package v.client.controllers;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.google.gwt.user.client.rpc.AsyncCallback;

import v.client.AppConstants;
import v.client.rpc.CajeroServiceAsync;
import v.client.rpc.LoginServiceAsync;
import v.client.widgets.ReportViewer;
import v.modelo.Usuario;

/**
 * Controlador para Cierre de Caja
 * 
 * @author Jorge Ramírez <jorgeramirez1990@gmail.com>
 **/
public class CerrarCajaController extends AbstractController {
	private ReportViewer viewer;
	private Usuario user;
	final LoginServiceAsync loginService = (LoginServiceAsync)Registry.get(AppConstants.LOGIN_SERVICE);
	final CajeroServiceAsync cajeroService = (CajeroServiceAsync)Registry.get(AppConstants.CAJERO_SERVICE);
	
	public CerrarCajaController() {
		super(AppConstants.CIERRE_CAJA_LABEL);
	}

	@Override
	public void init() {
		loginService.getSessionAttribute("usuario", new AsyncCallback<Usuario>() {
			
			@Override
			public void onSuccess(Usuario u) {
				user = u;
				viewer = new ReportViewer(u.getCaja().getNumeroCaja().toString(), "cierre_caja", "Cierre de Caja");
				bindHandlers();
				viewer.show();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				MessageBox.alert("Error en el servidor", caught.getMessage(), null);
			}
		});
	}
	
	/**
	 * Asocia los handlers para los botones del {@link ReportViewer}
	 **/
	private void bindHandlers(){
		viewer.addListener(Events.Render, new Listener<BaseEvent>() {

			@Override
			public void handleEvent(BaseEvent be) {
				viewer.getButtonById(ReportViewer.ACEPTAR).addSelectionListener(new SelectionListener<ButtonEvent>() {
					
					@Override
					public void componentSelected(ButtonEvent ce) {
						cajeroService.cierreCaja(user.getId(), new AsyncCallback<Boolean>() {

							@Override
							public void onFailure(Throwable caught) {
								MessageBox.alert("Error en el servidor", caught.getMessage(), null);
							}

							@Override
							public void onSuccess(Boolean ok) {
								if(ok){
									MessageBox.info("Operación Exitosa", "El cierre de caja finalizó exitosamente", null);
								}else{
									MessageBox.alert("Error", "Ocurrieron problemas y la operación no finalizó con éxito", null);
								}
							}
						
						});
					}
				});
			}
		});
	}
}