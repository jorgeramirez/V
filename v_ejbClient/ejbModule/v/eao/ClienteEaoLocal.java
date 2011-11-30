package v.eao;
import java.util.List;

import javax.ejb.Local;

import util.SimpleFilter;
import v.excepciones.EliminarException;
import v.excepciones.GuardarException;
import v.modelo.Cliente;

@Local
public interface ClienteEaoLocal {

	Cliente agregar(Cliente cliente) throws GuardarException;
	void modificar(Cliente cliente) throws GuardarException;
	void eliminar(Cliente cliente) throws EliminarException;
	List<Cliente> listar(List<SimpleFilter> filters, int start, int limit);
	int getCount();
	int getTotalClientesFilters(List<SimpleFilter> plainFilters);
	Cliente getById(Long id);
	
}
