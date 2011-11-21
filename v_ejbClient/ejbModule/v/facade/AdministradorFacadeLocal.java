package v.facade;
import java.util.HashMap;
import java.util.List;

import javax.ejb.Local;

import v.excepciones.GuardarException;
import v.modelo.Caja;
import v.modelo.Usuario;

@Local
public interface AdministradorFacadeLocal {
	List<Usuario> listarUsuarios(HashMap<String, Object> filters, int start, int limit);
	int getTotalUsuarios();
	List<Integer> listarNrosCaja();
	List<Caja> listarCajas();
	Usuario agregarUsuario(Usuario u) throws GuardarException;
	void modificarUsuario(Usuario u) throws GuardarException;
	Usuario findByUsername(String username);
}
