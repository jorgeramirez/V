package v.eao;

import java.util.HashMap;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import v.excepciones.EliminarException;
import v.excepciones.GuardarException;
import v.modelo.Proveedor;


/**
 * Session Bean implementation class ProveedorEao
 */
@Stateless
public class ProveedorEao implements ProveedorEaoLocal {

	@PersistenceContext(unitName="v_jpa")
	private EntityManager em;
	
    public ProveedorEao() {
        // TODO Auto-generated constructor stub
    }
    
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Proveedor agregar(Proveedor proveedor) throws GuardarException {
		try {
			em.persist(proveedor);
			em.flush();
			return em.merge(proveedor);
		}catch (PersistenceException pe) {
			throw new GuardarException(pe.getMessage());
		}
	}
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void modificar(Proveedor proveedor) throws GuardarException {
		try {
			em.merge(proveedor);
		}catch (PersistenceException pe) {
			throw new GuardarException(pe.getMessage());
		}
		
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void eliminar(Proveedor proveedor) throws EliminarException {
		try {
			em.remove(em.merge(proveedor));
		}catch(PersistenceException pe) {
			throw new EliminarException(pe.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Proveedor> listar(HashMap<String, Object> filters, int start, int limit) {
		String q = "select p from Proveedor p ";
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
		Query query = em.createQuery(q, Proveedor.class);
		query.setFirstResult(start);
		query.setMaxResults(limit);
		return query.getResultList();
	}
	
	@Override
	public Proveedor getById(Long id){
		return em.find(Proveedor.class, id);
	}

}
