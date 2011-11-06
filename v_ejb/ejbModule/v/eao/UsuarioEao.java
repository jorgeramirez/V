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
import v.modelo.Usuario;

/**
 * Session Bean implementation class UsuarioEao
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class UsuarioEao implements UsuarioEaoLocal {

	@PersistenceContext(unitName="v_jpa")
	private EntityManager em;
	
	public UsuarioEao() {
		
	}
	
	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Usuario agregar(Usuario usuario) throws GuardarException {
		try {
			em.persist(usuario);
			em.flush();
			return em.merge(usuario);
		}catch (PersistenceException pe) {
			throw new GuardarException(pe.getMessage());
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void modificar(Usuario usuario) throws GuardarException {
		try {
			em.merge(usuario);
		}catch (PersistenceException pe) {
			throw new GuardarException(pe.getMessage());
		}
		
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void eliminar(Usuario usuario) throws EliminarException {
		try {
			em.remove(em.merge(usuario));
		}catch(PersistenceException pe) {
			throw new EliminarException(pe.getMessage());
		}
	}

	@Override
	public int getCount() {
		Query q = em.createNamedQuery("Usuario.count");
		return Integer.parseInt(q.getSingleResult().toString());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Usuario> listar(HashMap<String, Object> filters, int start, int limit) {
		String q = "select u from Usuario u ";
		Object val;
		if(!filters.isEmpty()){
			q += "where ";
			for(String key: filters.keySet()) {
				val = filters.get(key);
				if(val instanceof String){
					val = (String)val;
					q += "u." + key + " like '" + val + "'";
				}
			}
		}
		Query query = em.createQuery(q, Usuario.class);
		query.setFirstResult(start);
		query.setMaxResults(limit);
		return query.getResultList();
	}

}
