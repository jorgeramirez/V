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

	boolean registrarPago(Pago pago) throws GuardarException;

	String registrarPagos(List<PagoWs> pagos) throws GuardarException;

}