package v.client.rpc;

import java.util.List;

import v.modelo.Producto;
import v.modelo.Proveedor;

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

	void listarProveedores(FilterPagingLoadConfig loadConfig,
			AsyncCallback<PagingLoadResult<Proveedor>> callback);

	void existeRucProveedor(String ruc, AsyncCallback<Boolean> asyncCallback);

	void agregarProveedor(Proveedor provider,
			AsyncCallback<Proveedor> asyncCallback);

	void modificarProveedor(Proveedor provider,
			AsyncCallback<Boolean> asyncCallback);

	void eliminarProveedores(List<Proveedor> providers,
			AsyncCallback<Boolean> asyncCallback);

	void listarProductosConExistencia(FilterPagingLoadConfig loadConfig,
			AsyncCallback<PagingLoadResult<Producto>> callback);

}
