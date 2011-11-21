package v.facade;
import java.util.HashMap;
import java.util.List;

import javax.ejb.Local;

import v.excepciones.GuardarException;
import v.modelo.Caja;
import v.modelo.Usuario;

@Local
public interface AdministradorFacadeLocal {
	public List<Usuario> listarUsuarios(HashMap<String, Object> filters, int start, int limit);
	public int getTotalUsuarios();
	public List<Integer> listarNrosCaja();
	public List<Caja> listarCajas();
	public Usuario agregarUsuario(Usuario u) throws GuardarException;
}
