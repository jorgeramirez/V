package v.eao;
import java.util.List;

import javax.ejb.Local;

import v.excepciones.EliminarException;
import v.excepciones.GuardarException;
import v.modelo.Usuario;

@Local
public interface UsuarioEaoLocal {

	public Usuario agregar(Usuario usuario) throws GuardarException;
	public void modificar(Usuario usuario) throws GuardarException;
	public void eliminar(Usuario usuario) throws EliminarException;
	public List<Usuario> listar();	
	
}
