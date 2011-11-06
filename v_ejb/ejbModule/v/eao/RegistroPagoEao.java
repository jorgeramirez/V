package v.eao;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import v.excepciones.EliminarException;
import v.excepciones.GuardarException;
import v.modelo.RegistroPago;

/**
 * Session Bean implementation class RegistroPagoEao
 */
@Stateless
public class RegistroPagoEao implements RegistroPagoEaoLocal {

	@PersistenceContext(unitName="v_jpa")
	private EntityManager em;
	
    public RegistroPagoEao() {
        // TODO Auto-generated constructor stub
    }

    @Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public RegistroPago agregar(RegistroPago registroPago) throws GuardarException {
		try {
			em.persist(registroPago);
			em.flush();
			return em.merge(registroPago);
		}catch (PersistenceException pe) {
			throw new GuardarException(pe.getMessage());
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void modificar(RegistroPago registroPago) throws GuardarException {
		try {
			em.merge(registroPago);
		}catch (PersistenceException pe) {
			throw new GuardarException(pe.getMessage());
		}
		
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void eliminar(RegistroPago registroPago) throws EliminarException {
		try {
			em.remove(em.merge(registroPago));
		}catch(PersistenceException pe) {
			throw new EliminarException(pe.getMessage());
		}
	}
}
