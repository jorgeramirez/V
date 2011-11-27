package v.client.rpc;

import v.modelo.Usuario;

import com.google.gwt.user.client.rpc.AsyncCallback;

public interface LoginServiceAsync {

	void login(String usuario, String password, AsyncCallback<Usuario> callback);
	void logout(AsyncCallback<Void> callback);
	void alreadyLoggedIn(AsyncCallback<Usuario> callback);
	void getSessionAttribute(String attribute, AsyncCallback<Usuario> callback);
}
