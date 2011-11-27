package v.client.controllers;

import v.client.AppConstants;
import v.client.rpc.LoginServiceAsync;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class LogoutController extends AbstractController {

	public LogoutController() {
		super(AppConstants.LOGOUT_LABEL);
	}

	@Override
	public void init() {
		LoginServiceAsync loginService = Registry.get(AppConstants.LOGIN_SERVICE);
		loginService.logout(new AsyncCallback<Void>(){

			@Override
			public void onFailure(Throwable caught) {
				MessageBox.alert("Error", caught.getMessage(), null);
			}

			@Override
			public void onSuccess(Void result) {
				Window.Location.reload();				
			}
			
		});
	}

}
