package v.eao;
import java.util.List;

import javax.ejb.Local;

import util.SimpleFilter;
import v.excepciones.EliminarException;
import v.excepciones.GuardarException;
import v.modelo.Pago;

@Local
public interface PagoEaoLocal {

	Pago agregar(Pago pago) throws GuardarException;

	void modificar(Pago pago) throws GuardarException;

	void eliminar(Pago pago) throws EliminarException;

	Pago getById(Long id);

	int getTotalPagos();

	int getTotalPagosFactura(Integer numeroFactura);

	List<Pago> listar(List<SimpleFilter> filters, int start, int limit);

	int getTotalPagosFilters(List<SimpleFilter> plainFilters);
	

}
