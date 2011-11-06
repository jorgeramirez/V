package v.eao;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import v.excepciones.EliminarException;
import v.excepciones.GuardarException;
import v.modelo.Pago;

/**
 * Session Bean implementation class PagoEao
 */
@Stateless
public class PagoEao implements PagoEaoLocal {

	@PersistenceContext(unitName="v_jpa")
	private EntityManager em;
	
    public PagoEao() {
        // TODO Auto-generated constructor stub
    }

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Pago agregar(Pago pago) throws GuardarException {
		try {
			em.persist(pago);
			em.flush();
			return em.merge(pago);
		}catch (PersistenceException pe) {
			throw new GuardarException(pe.getMessage());
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void modificar(Pago pago) throws GuardarException {
		try {
			em.merge(pago);
		}catch (PersistenceException pe) {
			throw new GuardarException(pe.getMessage());
		}
		
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void eliminar(Pago pago) throws EliminarException {
		try {
			em.remove(em.merge(pago));
		}catch(PersistenceException pe) {
			throw new EliminarException(pe.getMessage());
		}
	}

}
