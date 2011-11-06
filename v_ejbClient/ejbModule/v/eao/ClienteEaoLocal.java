package v.eao;
import javax.ejb.Local;

import v.excepciones.EliminarException;
import v.excepciones.GuardarException;
import v.modelo.Cliente;

@Local
public interface ClienteEaoLocal {

	public Cliente agregar(Cliente cliente) throws GuardarException;
	public void modificar(Cliente cliente) throws GuardarException;
	public	void eliminar(Cliente cliente) throws EliminarException;

}
