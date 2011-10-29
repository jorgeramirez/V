package v.facade;

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
	public List<Usuario> listarUsuarios() {
		return usuarioEao.listar();
	}

}
