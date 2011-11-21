package v.client.rpc;

import java.util.List;

import com.extjs.gxt.ui.client.data.FilterPagingLoadConfig;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;

import v.modelo.Caja;
import v.modelo.Usuario;

public interface AdministradorServiceAsync {
	public void listarUsuarios(FilterPagingLoadConfig config, AsyncCallback<PagingLoadResult<Usuario>> callback);
	public void listarNrosCaja(AsyncCallback<List<Integer>> callback);
	public void listarCajas(AsyncCallback<ListLoadResult<Caja>> callback);
}
