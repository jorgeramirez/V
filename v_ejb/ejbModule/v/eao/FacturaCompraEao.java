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
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void eliminar(FacturaCompra facturaCompra) throws EliminarException {
		try {
			em.remove(em.merge(facturaCompra));
		}catch(Exception pe) {
			throw new EliminarException(pe.getMessage());
		}
	}

	@Override
	public int getCount() {
		Query q = em.createNamedQuery("FacturaCompra.count");
		return Integer.parseInt(q.getSingleResult().toString());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<FacturaCompra> listarCompras(List<SimpleFilter> filters, int start, int limit) {
		String q = "select f from FacturaCompra f ";
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
		Query query = em.createQuery(q, FacturaCompra.class);
		query.setFirstResult(start);
		query.setMaxResults(limit);
		return query.getResultList();
	}

}