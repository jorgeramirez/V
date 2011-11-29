package v.eao;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import util.SimpleFilter;
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
		}catch (Exception pe) {
			throw new GuardarException(pe.getMessage());
		}
		
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void eliminar(FacturaDetalleCompra facturaDetalleCompra) throws EliminarException {
		try {
			em.remove(em.merge(facturaDetalleCompra));
		}catch(PersistenceException pe) {
			throw new EliminarException(pe.getMessage());
		}
	}

	@Override
	public int getTotalDetallesCompra(Integer numeroFactura) {
		Query q = em.createNamedQuery("FacturaDetalleCompra.countDetallesCompra");
		q.setParameter("id", numeroFactura);
		return Integer.parseInt(q.getSingleResult().toString());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FacturaDetalleCompra> listarComprasDetalles(
			List<SimpleFilter> filters, int start, int limit) {
		String q = "select f from FacturaDetalleCompra f ";
		int i = 1, size = filters.size();
		if(!filters.isEmpty()){
			q += "where ";
			for(SimpleFilter sf: filters){
				q += "f." + sf;
				if(size > 1 && i < size){
					q += " and ";
				}
				++i;
			}
		}
		Query query = em.createQuery(q, FacturaDetalleCompra.class);
		query.setFirstResult(start);
		query.setMaxResults(limit);
		return query.getResultList();
	}

	
}
