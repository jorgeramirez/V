package v.facade;

import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import v.eao.CajaEaoLocal;
import v.eao.ClienteEaoLocal;
import v.excepciones.EliminarException;
import v.excepciones.GuardarException;
import v.modelo.Cliente;

/**
 * Session Bean implementation class VendedorFacade
 */
@Stateless
public class VendedorFacade implements VendedorFacadeLocal {	
	@EJB
	ClienteEaoLocal clienteEao;
	
	@EJB
	CajaEaoLocal cajaEao;
	
    /**
     * Default constructor. 
     */
    public VendedorFacade() {

    }
    
	@Override
	//@RolesAllowed("administrador")
	public List<Cliente> listarClientes(HashMap<String, Object> filters, int start, int limit) {
		return clienteEao.listar(filters, start, limit);
	}

	@Override
	public Cliente agregarCliente(Cliente c) throws GuardarException {
		return clienteEao.agregar(c);
	}

	@Override
	public void modificarCliente(Cliente c) throws GuardarException {
		clienteEao.modificar(c);
	}

	@Override
	public void eliminarCliente(Cliente c) throws EliminarException {
		clienteEao.eliminar(c);
	}
	
	@Override
	public int getTotalClientes() {
		return clienteEao.getCount();
	}
}
