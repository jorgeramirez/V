package v.eao;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;

import v.excepciones.EliminarException;
import v.excepciones.GuardarException;
import v.modelo.FacturaDetalleCompra;

/**
 * Session Bean implementation class FacturaDetalleCompraEao
 */
@Stateless
public class FacturaDetalleCompraEao implements FacturaDetalleCompraEaoLocal {

	@PersistenceContext(unitName="v_jpa")
	private EntityManager em;
	
    public FacturaDetalleCompraEao() {
        // TODO Auto-generated constructor stub
    }

    @Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public FacturaDetalleCompra agregar(FacturaDetalleCompra facturaDetalleCompra) throws GuardarException {
		try {
			em.persist(facturaDetalleCompra);
			em.flush();
			return em.merge(facturaDetalleCompra);
		}catch (PersistenceException pe) {
			throw new GuardarException(pe.getMessage());
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void modificar(FacturaDetalleCompra facturaDetalleCompra) throws GuardarException {
		try {
			em.merge(facturaDetalleCompra);
		}catch (PersistenceException pe) {
			throw new GuardarException(pe.getMessage());
		}
		
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void eliminar(FacturaDetalleCompra facturaDetalleCompra) throws EliminarException {
		try {
			em.remove(em.merge(facturaDetalleCompra));
		}catch(PersistenceException pe) {
			throw new EliminarException(pe.getMessage());
		}
	}
}
