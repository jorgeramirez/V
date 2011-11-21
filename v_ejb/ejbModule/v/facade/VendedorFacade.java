package v.facade;

import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import v.eao.CajaEaoLocal;
import v.eao.ClienteEaoLocal;
import v.eao.UsuarioEaoLocal;
import v.modelo.Cliente;

/**
 * Session Bean implementation class VendedorFacade
 */
@Stateless
public class VendedorFacade implements VendedorFacadeLocal {
	@EJB
	UsuarioEaoLocal usuarioEao;
	
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
		//TODO
		
		return null;
	}

 


    

}
