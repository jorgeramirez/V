package v.facade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import v.eao.CajaEaoLocal;
import v.eao.UsuarioEaoLocal;
import v.modelo.Caja;
import v.modelo.Usuario;

/**
 * Session Bean implementation class AdministradorFacade
 */
//@DeclareRoles("administrador")
@Stateless
public class AdministradorFacade implements AdministradorFacadeLocal {

	@EJB
	UsuarioEaoLocal usuarioEao;
	
	@EJB
	CajaEaoLocal cajaEao;
	
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

	@Override
	public List<Integer> listarNrosCaja() {
		List<Integer> nrosCaja = new ArrayList<Integer>();
		List<Caja> cajas = cajaEao.listar();
		for(Caja c: cajas){
			nrosCaja.add(c.getNumeroCaja());
		}
		return nrosCaja;
	}

	@Override
	public List<Caja> listarCajas() {
		return cajaEao.listar();
	}

}
