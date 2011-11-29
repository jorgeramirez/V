package v.client.rpc;

import java.util.List;

import com.extjs.gxt.ui.client.data.FilterPagingLoadConfig;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;

import v.modelo.Caja;
import v.modelo.Usuario;

public interface AdministradorServiceAsync {
	void listarUsuarios(FilterPagingLoadConfig config, AsyncCallback<PagingLoadResult<Usuario>> callback);
	void listarNrosCaja(AsyncCallback<List<Integer>> callback);
	void listarCajas(AsyncCallback<ListLoadResult<Caja>> callback);
	void agregarUsuario(Usuario u, AsyncCallback<Usuario> callback);
	void modificarUsuario(Usuario u, AsyncCallback<Boolean> asyncCallback);
	void existeUsername(String username, AsyncCallback<Boolean> asyncCallback);
	void eliminarUsuarios(List<Usuario> users, AsyncCallback<Boolean> asyncCallback);
	void listarCajas(FilterPagingLoadConfig loadConfig, AsyncCallback<PagingLoadResult<Caja>> callback);
	void agregarCaja(Caja cashBox, AsyncCallback<Caja> asyncCallback);
	void modificarCaja(Caja cashBox, AsyncCallback<Boolean> asyncCallback);
	void eliminarCajas(List<Caja> cashBoxes, AsyncCallback<Boolean> asyncCallback);
}
