package v.client.rpc;

import v.modelo.FacturaVenta;
import v.modelo.Pago;

import com.extjs.gxt.ui.client.data.FilterPagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.AsyncCallback;

public interface CajeroServiceAsync {

	void listarFacturasPendientes(FilterPagingLoadConfig config,
			AsyncCallback<PagingLoadResult<FacturaVenta>> callback);

	void registrarPago(Pago pago, AsyncCallback<String> asyncCallback);
	void cierreCaja(Long idCajero, AsyncCallback<String> asyncCallback);

	void listarPagos(FilterPagingLoadConfig config, FacturaVenta factura, AsyncCallback<PagingLoadResult<Pago>> callback);
}
