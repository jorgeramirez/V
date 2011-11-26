package v.eao;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import v.excepciones.EliminarException;
import v.excepciones.GuardarException;
import v.modelo.FacturaVenta;

/**
 * Session Bean implementation class FacturaVentaEao
 */
@Stateless
public class FacturaVentaEao implements FacturaVentaEaoLocal {


	@PersistenceContext(unitName="v_jpa")
	private EntityManager em;
	
	public FacturaVentaEao() {
		
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public FacturaVenta agregar(FacturaVenta facturaVenta) throws GuardarException {
		try {
			em.persist(facturaVenta);
			em.flush();
			return em.merge(facturaVenta);
		}catch (PersistenceException pe) {
			throw new GuardarException(pe.getMessage());
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void modificar(FacturaVenta facturaVenta) throws GuardarException {
		try {
			em.merge(facturaVenta);
		}catch (PersistenceException pe) {
			throw new GuardarException(pe.getMessage());
		}
		
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void eliminar(FacturaVenta facturaVenta) throws EliminarException {
		try {
			em.remove(em.merge(facturaVenta));
		}catch(Exception pe) {
			throw new EliminarException(pe.getMessage());
		}
	}

	@Override
	public List<FacturaVenta> listar() {
		return em.createNamedQuery("FacturaVenta.findAll", FacturaVenta.class).getResultList();
	}

}
