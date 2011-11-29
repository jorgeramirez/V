package v.eao;
import java.util.List;

import javax.ejb.Local;

import util.SimpleFilter;
import v.modelo.FacturaDetalleVenta;

@Local
public interface FacturaDetalleVentaEaoLocal {

	List<FacturaDetalleVenta> listarVentasDetalles(List<SimpleFilter> filters,
			int start, int limit);

	int getTotalDetallesVenta(Integer numeroFactura);

}
