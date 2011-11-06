package v.facade;
import java.util.HashMap;
import java.util.List;

import javax.ejb.Local;

import v.modelo.Usuario;

@Local
public interface AdministradorFacadeLocal {
	public List<Usuario> listarUsuarios(HashMap<String, Object> filters, int start, int limit);
	public int getTotalUsuarios();
}
