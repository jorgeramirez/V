package v.client.rpc;

import java.util.List;

import v.modelo.Cliente;
import v.modelo.FacturaDetalleVenta;
import v.modelo.FacturaVenta;

import com.extjs.gxt.ui.client.data.FilterPagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface VendedorServiceAsync {
	
	void listarClientes(FilterPagingLoadConfig config, AsyncCallback<PagingLoadResult<Cliente>> callback);
	void agregarCliente(Cliente c, AsyncCallback<Cliente> asyncCallback);
	void modificarCliente(Cliente c, AsyncCallback<Boolean> callback);
	void eliminarClientes(List<Cliente> clientes, AsyncCallback<Boolean> callback);

	void agregarVenta(FacturaVenta v, AsyncCallback<Boolean> callback);
	void listarVentasDetalles(FilterPagingLoadConfig config,
			FacturaVenta venta,
			AsyncCallback<PagingLoadResult<FacturaDetalleVenta>> callback);
}
