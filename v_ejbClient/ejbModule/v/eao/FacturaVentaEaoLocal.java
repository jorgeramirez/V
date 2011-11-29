package v.eao;
import java.util.List;

import javax.ejb.Local;

import util.SimpleFilter;
import v.excepciones.EliminarException;
import v.excepciones.GuardarException;
import v.modelo.FacturaVenta;

@Local
public interface FacturaVentaEaoLocal {
	FacturaVenta agregar(FacturaVenta fv) throws GuardarException;
	void modificar(FacturaVenta fv) throws GuardarException;
	void eliminar(FacturaVenta fv) throws EliminarException;
	List<FacturaVenta> listar(List<SimpleFilter> filters, int start, int limit);
	int getTotalPendientes();
	FacturaVenta findById(Integer id);
	int getTotalFacturas();
	int getTotalFacturasFilters(List<SimpleFilter> filters);
}
