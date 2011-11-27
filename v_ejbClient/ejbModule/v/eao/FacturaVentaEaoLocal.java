package v.eao;
import java.util.List;

import javax.ejb.Local;

import util.SimpleFilter;
import v.excepciones.EliminarException;
import v.excepciones.GuardarException;
import v.modelo.FacturaVenta;

@Local
public interface FacturaVentaEaoLocal {
	FacturaVenta agregar(FacturaVenta usuario) throws GuardarException;
	void modificar(FacturaVenta usuario) throws GuardarException;
	void eliminar(FacturaVenta usuario) throws EliminarException;
	List<FacturaVenta> listar(List<SimpleFilter> filters, int start, int limit);
	int getTotalPendientes();
}
