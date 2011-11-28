package v.eao;
import java.util.List;

import javax.ejb.Local;

import util.SimpleFilter;
import v.excepciones.EliminarException;
import v.excepciones.GuardarException;
import v.modelo.Caja;
import v.modelo.Pago;

@Local
public interface CajaEaoLocal {

	Caja agregar(Caja caja) throws GuardarException;

	void modificar(Caja caja) throws GuardarException;

	void eliminar(Caja caja) throws EliminarException;
	
	List<Caja> listar();
	
	Caja findByNumeroCaja(Integer numeroCaja);

	int getCount();

	List<Caja> listar(List<SimpleFilter> filters, int start, int limit);

	List<Pago> pagosNoCerrados(Long idCajero);

}
