package v.facade;
import java.util.HashMap;
import java.util.List;

import javax.ejb.Local;

import v.excepciones.EliminarException;
import v.excepciones.GuardarException;
import v.modelo.Cliente;

@Local
public interface VendedorFacadeLocal {

	List<Cliente> listarClientes(HashMap<String, Object> filters, int start, int limit);

	Cliente agregarCliente(Cliente c) throws GuardarException;

	void modificarCliente(Cliente c) throws GuardarException;

	void eliminarCliente(Cliente c) throws EliminarException;

	int getTotalClientes();

}
