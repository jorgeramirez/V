package v.eao;
import java.util.HashMap;
import java.util.List;

import javax.ejb.Local;

import v.excepciones.EliminarException;
import v.excepciones.GuardarException;
import v.modelo.Cliente;

@Local
public interface ClienteEaoLocal {

	public Cliente agregar(Cliente cliente) throws GuardarException;
	public void modificar(Cliente cliente) throws GuardarException;
	public	void eliminar(Cliente cliente) throws EliminarException;
	List<Cliente> listar(HashMap<String, Object> filters, int start, int limit);
	int getCount();
	
}
