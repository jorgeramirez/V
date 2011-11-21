package v.facade;
import java.util.HashMap;
import java.util.List;

import javax.ejb.Local;

import v.modelo.Cliente;

@Local
public interface VendedorFacadeLocal {

	List<Cliente> listarClientes(HashMap<String, Object> filters, int start, int limit);

}
