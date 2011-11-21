package v.eao;
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
	
	Caja findById(Long id);

}
