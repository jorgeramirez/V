package v.client.rpc;

import com.extjs.gxt.ui.client.data.FilterPagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;

import v.modelo.Usuario;

public interface AdministradorServiceAsync {
	public void listarUsuarios(FilterPagingLoadConfig config, AsyncCallback<PagingLoadResult<Usuario>> callback);
}
