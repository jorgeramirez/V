package v.eao;
import java.util.List;

import javax.ejb.Local;

import util.SimpleFilter;
import v.excepciones.EliminarException;
import v.excepciones.GuardarException;
import v.modelo.Usuario;

@Local
public interface UsuarioEaoLocal {

	Usuario agregar(Usuario usuario) throws GuardarException;
	void modificar(Usuario usuario) throws GuardarException;
	void eliminar(Usuario usuario) throws EliminarException;
	List<Usuario> listar(List<SimpleFilter> filters, int start, int limit);
	int getCount();
	Usuario findByUsername(String username);
	public String cifrarPassword(String textoplano) throws IllegalStateException;
	Usuario findById(Long id);
	
}
