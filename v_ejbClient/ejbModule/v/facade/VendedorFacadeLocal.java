package v.facade;
import java.util.List;

import javax.ejb.Local;

import util.SimpleFilter;
import v.excepciones.EliminarException;
import v.excepciones.GuardarException;
import v.modelo.Cliente;

@Local
public interface VendedorFacadeLocal {

	List<Cliente> listarClientes(List<SimpleFilter> filters, int start, int limit);

	Cliente agregarCliente(Cliente c) throws GuardarException;

	void modificarCliente(Cliente c) throws GuardarException;

	void eliminarCliente(Cliente c) throws EliminarException;

	int getTotalClientes();

}
