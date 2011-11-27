package v.client.controllers;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.google.gwt.user.client.rpc.AsyncCallback;

import v.client.AppConstants;
import v.client.rpc.LoginServiceAsync;
import v.client.widgets.ReportViewer;
import v.modelo.Usuario;

public class CerrarCajaController extends AbstractController {

	public CerrarCajaController() {
		super(AppConstants.CIERRE_CAJA_LABEL);
	}

	@Override
	public void init() {
		final LoginServiceAsync service = (LoginServiceAsync)Registry.get(AppConstants.LOGIN_SERVICE);
		service.getSessionAttribute("usuario", new AsyncCallback<Usuario>() {
			
			@Override
			public void onSuccess(Usuario user) {
				//ReportViewer viewer = new ReportViewer(user.getCaja().getNumeroCaja().toString(), "cierre_caja", "Cierre de Caja");
				//viewer.show();
			}
			
			@Override
			public void onFailure(Throwable caught) {
				MessageBox.alert("Error en el servidor", caught.getMessage(), null);
			}
		});
		ReportViewer viewer = new ReportViewer("1", "factura_venta", "Cierre de Caja");
		viewer.show();
	}
}