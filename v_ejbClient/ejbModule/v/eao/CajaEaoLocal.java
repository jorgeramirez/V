package v.eao;
import java.util.HashMap;
import java.util.List;

import javax.ejb.Local;

import v.excepciones.EliminarException;
import v.excepciones.GuardarException;
import v.modelo.Caja;

@Local
public interface CajaEaoLocal {

	Caja agregar(Caja caja) throws GuardarException;

	void modificar(Caja caja) throws GuardarException;

	void eliminar(Caja caja) throws EliminarException;
	
	List<Caja> listar();
	
	Caja findByNumeroCaja(Integer numeroCaja);

	int getCount();

	List<Caja> listar(HashMap<String, Object> filters, int start, int limit);

}
