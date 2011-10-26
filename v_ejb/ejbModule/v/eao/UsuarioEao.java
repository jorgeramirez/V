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

import v.excepciones.EliminarException;
import v.excepciones.GuardarException;
import v.modelo.Usuario;

/**
 * Session Bean implementation class UsuarioEao
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class UsuarioEao implements UsuarioEaoLocal {

	@PersistenceContext(name="v_jpa")
	private EntityManager em;
	
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
	public List<Usuario> listar() {
		return em.createNamedQuery("Usuario.findAll", Usuario.class).getResultList();
	}

}
