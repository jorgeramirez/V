package v.eao;

import java.util.HashMap;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

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
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void eliminar(Producto producto) throws EliminarException {
		try {
			em.remove(em.merge(producto));
		}catch(PersistenceException pe) {
			throw new EliminarException(pe.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Producto> listar(HashMap<String, Object> filters, int start, int limit) {
		String q = "select p from Producto p ";
		Object val;
		if(!filters.isEmpty()){
			q += "where ";
			for(String key: filters.keySet()) {
				val = filters.get(key);
				if(val instanceof String){
					val = (String)val;
					q += "p." + key + " like '" + val + "'";
				}
			}
		}
		Query query = em.createQuery(q, Producto.class);
		query.setFirstResult(start);
		query.setMaxResults(limit);
		return query.getResultList();
	}
}
