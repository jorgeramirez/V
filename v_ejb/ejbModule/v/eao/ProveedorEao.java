package v.eao;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import util.SimpleFilter;
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
	public List<Proveedor> listar(List<SimpleFilter> filters, int start, int limit) {
		String q = "select p from Proveedor p ";
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
		Query query = em.createQuery(q, Proveedor.class);
		query.setFirstResult(start);
		query.setMaxResults(limit);
		return query.getResultList();
	}
	
	@Override
	public Proveedor getById(Long id){
		return em.find(Proveedor.class, id);
	}

	@Override
	public int getCount() {
		Query q = em.createNamedQuery("Proveedor.count");
		return Integer.parseInt(q.getSingleResult().toString());		
	}

	@Override
	public Proveedor findByRuc(String ruc) {
		Proveedor p = null;
		Query query = em.createNamedQuery("Proveedor.findByRuc", Proveedor.class);
		query.setParameter("ruc", ruc);
		try{
			p = (Proveedor) query.getSingleResult();
		}catch (NoResultException nre) {
			// ignored
		}
		return p;
	}

}
