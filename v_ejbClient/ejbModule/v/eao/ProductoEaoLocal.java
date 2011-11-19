package v.eao;


import java.util.HashMap;
import java.util.List;

import javax.ejb.Local;

import v.excepciones.EliminarException;
import v.excepciones.GuardarException;
import v.modelo.Producto;

@Local
public interface ProductoEaoLocal {

	public Producto agregar(Producto producto) throws GuardarException;
	public void modificar(Producto producto) throws GuardarException;
	public void eliminar(Producto producto) throws EliminarException;
	public List<Producto> listar(HashMap<String, Object> filters, int start, int limit);
	

}
