package v.facade;
import java.util.List;

import javax.ejb.Local;

import util.SimpleFilter;
import v.excepciones.GuardarException;
import v.modelo.FacturaVenta;
import v.modelo.Pago;

@Local
public interface CajeroFacadeLocal {

	List<Pago> cerrarCaja(Long idCaja) throws GuardarException;

	int getTotalFacturasPendientes();

	List<FacturaVenta> listarFacturasPendientes(
			List<SimpleFilter> plainFilters, int start, int limit);

}
