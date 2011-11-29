package v.eao;
import java.util.List;

import javax.ejb.Local;

import util.SimpleFilter;
import v.excepciones.EliminarException;
import v.excepciones.GuardarException;
import v.modelo.FacturaDetalleCompra;

@Local
public interface FacturaDetalleCompraEaoLocal {

	FacturaDetalleCompra agregar(FacturaDetalleCompra facturaDetalleCompra)
			throws GuardarException;

	void modificar(FacturaDetalleCompra facturaDetalleCompra)
			throws GuardarException;

	void eliminar(FacturaDetalleCompra facturaDetalleCompra)
			throws EliminarException;

	int getTotalDetallesCompra(Integer numeroFactura);

	List<FacturaDetalleCompra> listarComprasDetalles(
			List<SimpleFilter> plainFilters, int start, int limit);

	int getTotalDetallesCompraFilters(List<SimpleFilter> plainFilters);

}
