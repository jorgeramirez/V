package v.client.rpc;

import java.util.List;

import v.modelo.Cliente;
import v.modelo.Producto;
import v.modelo.Usuario;

import com.extjs.gxt.ui.client.data.FilterPagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface VendedorServiceAsync {
	//modificar
	void existeCodigoProducto(String codigo, AsyncCallback<Boolean> asyncCallback);

	void listarProductos(FilterPagingLoadConfig loadConfig,
			AsyncCallback<PagingLoadResult<Producto>> callback);

	void agregarProducto(Producto product, AsyncCallback<Producto> asyncCallback);

	void modificarProducto(Producto product,
			AsyncCallback<Boolean> asyncCallback);

	void eliminarProductos(List<Producto> products,
			AsyncCallback<Boolean> asyncCallback);
	
	void listarClientes(FilterPagingLoadConfig config, AsyncCallback<PagingLoadResult<Cliente>> callback);
	void agregarCliente(Cliente c, AsyncCallback<Cliente> asyncCallback);
	void modificarCliente(Cliente c, AsyncCallback<Void> callback);
	void eliminarClientes(List<Cliente> clientes, AsyncCallback<Boolean> callback);
}
