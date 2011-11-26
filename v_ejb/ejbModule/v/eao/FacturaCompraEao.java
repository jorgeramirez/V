package v.eao;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import v.excepciones.EliminarException;
import v.excepciones.GuardarException;
import v.modelo.FacturaCompra;

/**
 * Session Bean implementation class FacturaCompraEao
 */
@Stateless
public class FacturaCompraEao implements FacturaCompraEaoLocal {

	@PersistenceContext(unitName="v_jpa")
	private EntityManager em;
	
    public FacturaCompraEao() {
        // TODO Auto-generated constructor stub
    }
    
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public FacturaCompra agregar(FacturaCompra facturaCompra) throws GuardarException {
		try {
			em.persist(facturaCompra);
			em.flush();
			return em.merge(facturaCompra);
		}catch (PersistenceException pe) {
			throw new GuardarException(pe.getMessage());
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void modificar(FacturaCompra facturaCompra) throws GuardarException {
		try {
			em.merge(facturaCompra);
		}catch (PersistenceException pe) {
			throw new GuardarException(pe.getMessage());
		}
		
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void eliminar(FacturaCompra facturaCompra) throws EliminarException {
		try {
			em.remove(em.merge(facturaCompra));
		}catch(PersistenceException pe) {
			throw new EliminarException(pe.getMessage());
		}
	}

}
