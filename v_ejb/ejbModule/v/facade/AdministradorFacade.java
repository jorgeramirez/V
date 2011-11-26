package v.facade;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import util.SimpleFilter;
import v.client.AppConstants;
import v.eao.CajaEaoLocal;
import v.eao.UsuarioEaoLocal;
import v.excepciones.EliminarException;
import v.excepciones.GuardarException;
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
	public List<Usuario> listarUsuarios(List<SimpleFilter> filters, int start, int limit) {
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

	@Override
	public Usuario agregarUsuario(Usuario u) throws GuardarException {
		if(u.getRol().equals(AppConstants.CAJERO_ROL)){
			u.setCaja(cajaEao.findByNumeroCaja(u.getCaja().getNumeroCaja()));
		}
		return usuarioEao.agregar(u);
	}

	@Override
	public void modificarUsuario(Usuario u) throws GuardarException {
		if(u.getRol().equals(AppConstants.CAJERO_ROL)){
			u.setCaja(cajaEao.findByNumeroCaja(u.getCaja().getNumeroCaja()));
		}
		usuarioEao.modificar(u);
	}

	@Override
	public Usuario findByUsername(String username) {
		return usuarioEao.findByUsername(username);
	}

	@Override
	public void eliminarUsuario(Usuario u) throws EliminarException {
		String rol = u.getRol();
		if(rol.equals(AppConstants.CAJERO_ROL)){
			u.setCaja(cajaEao.findByNumeroCaja(u.getCaja().getNumeroCaja()));
		}else if(rol.equals(AppConstants.VENDEDOR_ROL)){
			u.setVentas(usuarioEao.findByUsername(u.getUsername()).getVentas());
		}else if(rol.equals(AppConstants.COMPRADOR_ROL)){
			u.setCompras(usuarioEao.findByUsername(u.getUsername()).getCompras());
		}
		usuarioEao.eliminar(u);
	}

	@Override
	public int getTotalCajas() {
		return cajaEao.getCount();
	}

	@Override
	public List<Caja> listarCajas(List<SimpleFilter> filters, int start, int limit) {
		return cajaEao.listar(filters, start, limit);
	}

	@Override
	public Caja agregarCaja(Caja c) throws GuardarException {
		return cajaEao.agregar(c);		
	}

	@Override
	public void modificarCaja(Caja c) throws GuardarException {
		c.setCajeros(cajaEao.findByNumeroCaja(c.getNumeroCaja()).getCajeros());
		c.setPagos(cajaEao.findByNumeroCaja(c.getNumeroCaja()).getPagos());
		cajaEao.modificar(c);
	}

	@Override
	public void eliminarCaja(Caja c) throws EliminarException {
		c.setCajeros(cajaEao.findByNumeroCaja(c.getNumeroCaja()).getCajeros());
		c.setPagos(cajaEao.findByNumeroCaja(c.getNumeroCaja()).getPagos());
		cajaEao.eliminar(c);		
	}	
	
}
