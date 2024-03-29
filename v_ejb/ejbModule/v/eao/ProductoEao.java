package v.eao;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import util.SimpleFilter;
import v.excepciones.EliminarException;
import v.excepciones.GuardarException;
import v.modelo.Producto;


/**
 * Session Bean implementation class ProductoEao
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ProductoEao implements ProductoEaoLocal {

	@PersistenceContext(unitName="v_jpa")
	private EntityManager em;
	
    public ProductoEao() {
        // TODO Auto-generated constructor stub
    }
    
    @Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Producto agregar(Producto producto) throws GuardarException {
		try {
			em.persist(producto);
			em.flush();
			return em.merge(producto);
		}catch (PersistenceException pe) {
			throw new GuardarException(pe.getMessage());
		}
	}
    
    @Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void modificar(Producto producto) throws GuardarException {
		try {
			em.merge(producto);
		}catch (PersistenceException pe) {
			throw new GuardarException(pe.getMessage());
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void eliminar(Producto producto) throws EliminarException {
		try {
			em.remove(em.merge(producto));
		}catch(Exception pe) {
			throw new EliminarException(pe.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Producto> listar(List<SimpleFilter> filters, int start, int limit) {
		String q = "select p from Producto p ";
		int i = 1, size = filters.size();
		if(!filters.isEmpty()){
			q += "where ";
			for(SimpleFilter sf: filters){
				q += "p." + sf;
				if(size > 1 && i < size){
					q += " and ";
				}
				++i;
			}
		}
		Query query = em.createQuery(q, Producto.class);
		query.setFirstResult(start);
		query.setMaxResults(limit);
		return query.getResultList();
	}
	
	@Override
	public Producto getById(Long id) {
		return em.find(Producto.class, id);
	}

	@Override
	public Producto findByCodigo(String codigo) {
		Producto p = null;
		Query query = em.createNamedQuery("Producto.findByCodigo", Producto.class);
		query.setParameter("codigo", codigo);
		try{
			p = (Producto) query.getSingleResult();
		}catch (NoResultException nre) {
			// ignored
		}
		return p;
	}

	@Override
	public int getCount() {
		Query q = em.createNamedQuery("Producto.count");
		return Integer.parseInt(q.getSingleResult().toString());
	}

	@Override
	public int getTotalProductosFilters(List<SimpleFilter> filters) {
		String q = "select count(p) from Producto p ";
		int i = 1, size = filters.size();
		if(!filters.isEmpty()){
			q += "where ";
			for(SimpleFilter sf: filters){
				q += "p." + sf;
				if(size > 1 && i < size){
					q += " and ";
				}
				++i;
			}
		}
		Query query = em.createQuery(q);
		return Integer.parseInt(query.getSingleResult().toString());
	}	
}