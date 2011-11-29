package v.facade;
import java.util.List;

import javax.ejb.Local;

import util.SimpleFilter;
import v.excepciones.GuardarException;
import v.modelo.FacturaVenta;
import v.modelo.Pago;
import v.ws.PagoWs;

@Local
public interface CajeroFacadeLocal {

	void cierredeCaja(Long idCajero) throws GuardarException;

	int getTotalFacturasPendientes();

	List<FacturaVenta> listarFacturasPendientes(
			List<SimpleFilter> plainFilters, int start, int limit);

	Pago registrarPago(Pago pago) throws GuardarException;

	List<PagoWs> registroPagosWebService(List<PagoWs> pagos)
			throws GuardarException;

	int getTotalPagos();

	int getTotalPagosFactura(Integer numeroFactura);

	List<Pago> listarPagos(List<SimpleFilter> filters, int start, int limit);

	int getTotalFacturas();
	
	List<FacturaVenta> listarFacturas(List<SimpleFilter> plainFilters, int start, int limit);

}