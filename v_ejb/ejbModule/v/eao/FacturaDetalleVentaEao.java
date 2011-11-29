package v.eao;

import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import util.SimpleFilter;
import v.modelo.FacturaDetalleVenta;

/**
 * Session Bean implementation class FacturaDetalleVentaEao
 */
@Stateless
public class FacturaDetalleVentaEao implements FacturaDetalleVentaEaoLocal {
	
	@PersistenceContext(unitName="v_jpa")
	EntityManager em;
	
    /**
     * Default constructor. 
     */
    public FacturaDetalleVentaEao() {
    }

	@SuppressWarnings("unchecked")
	@Override
	public List<FacturaDetalleVenta> listarVentasDetalles(
			List<SimpleFilter> filters, int start, int limit) {
		String q = "select f from FacturaDetalleVenta f ";
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
		Query query = em.createQuery(q, FacturaDetalleVenta.class);
		query.setFirstResult(start);
		query.setMaxResults(limit);
		return query.getResultList();
	}

	@Override
	public int getTotalDetallesVenta(Integer numeroFactura) {
		Query q = em.createNamedQuery("FacturaDetalleVenta.countDetallesVenta");
		q.setParameter("id", numeroFactura);
		return Integer.parseInt(q.getSingleResult().toString());
	}

}
