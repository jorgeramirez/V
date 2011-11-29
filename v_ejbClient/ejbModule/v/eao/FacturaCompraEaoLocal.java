package v.eao;
import java.util.List;

import javax.ejb.Local;

import util.SimpleFilter;
import v.excepciones.EliminarException;
import v.excepciones.GuardarException;
import v.modelo.FacturaCompra;

@Local
public interface FacturaCompraEaoLocal {

	FacturaCompra agregar(FacturaCompra facturaCompra) throws GuardarException;

	void modificar(FacturaCompra facturaCompra) throws GuardarException;

	void eliminar(FacturaCompra facturaCompra) throws EliminarException;

	int getCount();

	List<FacturaCompra> listarCompras(List<SimpleFilter> filters, int start, int limit);

	int getTotalComprasFilters(List<SimpleFilter> plainFilters);

}
