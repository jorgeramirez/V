package v.eao;

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

import util.SimpleFilter;
import v.excepciones.EliminarException;
import v.excepciones.GuardarException;
import v.modelo.Cliente;

/**
 * Session Bean implementation class ClienteEao
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ClienteEao implements ClienteEaoLocal {

	@PersistenceContext(unitName="v_jpa")
	private EntityManager em;
	
    public ClienteEao() {

    }
    
    @Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Cliente agregar(Cliente cliente) throws GuardarException {
		try {
			em.persist(cliente);
			em.flush();
			return em.merge(cliente);
		}catch (PersistenceException pe) {
			throw new GuardarException(pe.getMessage());
		}
	}
    
    @Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void modificar(Cliente cliente) throws GuardarException {
		try {
			em.merge(cliente);
		}catch (PersistenceException pe) {
			throw new GuardarException(pe.getMessage());
		}
		
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void eliminar(Cliente cliente) throws EliminarException {
		try {
			em.remove(em.merge(cliente));
		}catch(Exception pe) {
			throw new EliminarException(pe.getMessage());
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<Cliente> listar(List<SimpleFilter> filters, int start, int limit) {
		String q = "select c from Cliente c ";
		int i = 1, size = filters.size();
		if(!filters.isEmpty()){
			q += "where ";
			for(SimpleFilter sf: filters){
				q += "c." + sf;
				if(size > 1 && i < size){
					q += " and ";
				}
				++i;
			}
		}
		Query query = em.createQuery(q, Cliente.class);
		query.setFirstResult(start);
		query.setMaxResults(limit);
		return query.getResultList();
	}
	
	@Override
	public int getCount() {
		Query q = em.createNamedQuery("Cliente.count");
		return Integer.parseInt(q.getSingleResult().toString());
	}

	@Override
	public int getTotalClientesFilters(List<SimpleFilter> filters) {
		String q = "select count(c) from Cliente c ";
		int i = 1, size = filters.size();
		if(!filters.isEmpty()){
			q += "where ";
			for(SimpleFilter sf: filters){
				q += "c." + sf;
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