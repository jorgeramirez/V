package v.eao;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import v.excepciones.EliminarException;
import v.excepciones.GuardarException;
import v.modelo.Cliente;

/**
 * Session Bean implementation class ClienteEao
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ClienteEao implements ClienteEaoLocal {

	@PersistenceContext(unitName="v_jpa")
	private EntityManager em;
	
    public ClienteEao() {

    }
    
    @Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Cliente agregar(Cliente cliente) throws GuardarException {
		try {
			em.persist(cliente);
			em.flush();
			return em.merge(cliente);
		}catch (PersistenceException pe) {
			throw new GuardarException(pe.getMessage());
		}
	}
    
    @Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void modificar(Cliente cliente) throws GuardarException {
		try {
			em.merge(cliente);
		}catch (PersistenceException pe) {
			throw new GuardarException(pe.getMessage());
		}
		
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void eliminar(Cliente cliente) throws EliminarException {
		try {
			em.remove(em.merge(cliente));
		}catch(Exception pe) {
			throw new EliminarException(pe.getMessage());
		}
	}
}
