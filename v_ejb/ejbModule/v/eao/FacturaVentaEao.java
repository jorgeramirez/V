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
import v.modelo.FacturaDetalleVenta;
import v.modelo.FacturaVenta;
import v.modelo.Producto;

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
			
			
			//actualizar el stock
			Producto p;
			int nuevaCantidad;
			for (FacturaDetalleVenta fdv : facturaVenta.getDetalles()) {
				p = fdv.getProducto();
				nuevaCantidad = p.getCantidad() - fdv.getCantidad();
				if (nuevaCantidad < 0) {
					throw new GuardarException("Cantidad mayor que existencia");
				}
				p.setCantidad(nuevaCantidad);
				em.merge(p);
			}
			
			return em.merge(facturaVenta);
		} catch (PersistenceException pe) {
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

	@SuppressWarnings("unchecked")
	@Override
	public List<FacturaVenta> listar(List<SimpleFilter> filters, int start, int limit) {
		String q = "select f from FacturaVenta f ";
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
		Query query = em.createQuery(q, FacturaVenta.class);
		query.setFirstResult(start);
		query.setMaxResults(limit);
		return query.getResultList();
	}

	@Override
	public int getTotalPendientes() {
		Query q = em.createNamedQuery("FacturaVenta.countPendientes");
		return Integer.parseInt(q.getSingleResult().toString());
	}
	
	@Override
	public FacturaVenta findById(Integer id) {
		return em.find(FacturaVenta.class, id);
	}	
}
