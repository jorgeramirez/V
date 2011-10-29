package v.client.rpc;

import java.util.List;

import com.google.gwt.user.client.rpc.AsyncCallback;

import v.modelo.Usuario;

public interface AdministradorServiceAsync {
	public void listarUsuarios(AsyncCallback<List<Usuario>> callback);
}
