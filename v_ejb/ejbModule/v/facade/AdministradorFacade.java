package v.facade;

import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import v.eao.UsuarioEaoLocal;
import v.modelo.Usuario;

/**
 * Session Bean implementation class AdministradorFacade
 */
//@DeclareRoles("administrador")
@Stateless
public class AdministradorFacade implements AdministradorFacadeLocal {

	@EJB
	UsuarioEaoLocal usuarioEao;
	
	public AdministradorFacade () {
		
	}
	
	@Override
	//@RolesAllowed("administrador")
	public List<Usuario> listarUsuarios(HashMap<String, Object> filters, int start, int limit) {
		return usuarioEao.listar(filters, start, limit);
	}

	@Override
	public int getTotalUsuarios() {
		return usuarioEao.getCount();
	}

}
