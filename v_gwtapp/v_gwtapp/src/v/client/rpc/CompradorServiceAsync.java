package v.client.rpc;

import java.util.List;

import v.modelo.Producto;

import com.extjs.gxt.ui.client.data.FilterPagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface CompradorServiceAsync {

	void existeCodigoProducto(String codigo, AsyncCallback<Boolean> asyncCallback);

	void listarProductos(FilterPagingLoadConfig loadConfig,
			AsyncCallback<PagingLoadResult<Producto>> callback);

	void agregarProducto(Producto product, AsyncCallback<Producto> asyncCallback);

	void modificarProducto(Producto product,
			AsyncCallback<Boolean> asyncCallback);

	void eliminarProductos(List<Producto> products,
			AsyncCallback<Boolean> asyncCallback);

}
