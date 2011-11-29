package v.eao;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

import sun.misc.BASE64Encoder;
import util.SimpleFilter;
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
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void eliminar(Usuario usuario) throws EliminarException {
		try {
			em.remove(em.merge(usuario));
		}catch(Exception pe) {
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
	public List<Usuario> listar(List<SimpleFilter> filters, int start, int limit) {
		String q = "select u from Usuario u ";
		int i = 1, size = filters.size();
		if(!filters.isEmpty()){
			q += "where ";
			for(SimpleFilter sf: filters){
				q += "u." + sf;
				if(size > 1 && i < size){
					q += " and ";
				}
				++i;
			}
		}
		Query query = em.createQuery(q, Usuario.class);
		query.setFirstResult(start);
		query.setMaxResults(limit);
		return query.getResultList();
	}

	@Override
	public Usuario findByUsername(String username) {
		Usuario u = null;
		Query query = em.createNamedQuery("Usuario.findByUsername", Usuario.class);
		query.setParameter("username", username);
		try{
			u = (Usuario) query.getSingleResult();
		}catch (NoResultException nre) {
			// ignored
		}
		return u;
	}
	
	@Override
	public Usuario findById(Long id){
		return em.find(Usuario.class, id);
	}
	
	public String cifrarPassword(String textoplano) throws IllegalStateException {

		MessageDigest md = null;

		try {
			md = MessageDigest.getInstance("SHA"); // Instancia de generador SHA-1
		}
		catch(NoSuchAlgorithmException e) {
			throw new IllegalStateException(e.getMessage());
		}

		try {
			md.update(textoplano.getBytes("UTF-8")); // Generacion de resumen de mensaje
		}
		catch(UnsupportedEncodingException e) {
			throw new IllegalStateException(e.getMessage());
		}

		byte raw[] = md.digest(); // Obtencion del resumen de mensaje
		String hash = (new BASE64Encoder()).encode(raw); // Traduccion a BASE64
		return hash;
	}

	@Override
	public int getTotalUsuariosFilters(List<SimpleFilter> filters) {
		String q = "select count(u) from Usuario u ";
		int i = 1, size = filters.size();
		if(!filters.isEmpty()){
			q += "where ";
			for(SimpleFilter sf: filters){
				q += "u." + sf;
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