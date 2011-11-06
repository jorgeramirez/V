package v.eao;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import v.excepciones.EliminarException;
import v.excepciones.GuardarException;
import v.modelo.Caja;

/**
 * Session Bean implementation class CajaEao
 */
@Stateless
public class CajaEao implements CajaEaoLocal {

	@PersistenceContext(unitName="v_jpa")
	private EntityManager em;
	
    public CajaEao() {
        // TODO Auto-generated constructor stub
    }
    
    @Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Caja agregar(Caja caja) throws GuardarException {
		try {
			em.persist(caja);
			em.flush();
			return em.merge(caja);
		}catch (PersistenceException pe) {
			throw new GuardarException(pe.getMessage());
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void modificar(Caja caja) throws GuardarException {
		try {
			em.merge(caja);
		}catch (PersistenceException pe) {
			throw new GuardarException(pe.getMessage());
		}
		
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void eliminar(Caja caja) throws EliminarException {
		try {
			em.remove(em.merge(caja));
		}catch(PersistenceException pe) {
			throw new EliminarException(pe.getMessage());
		}
	}

}
