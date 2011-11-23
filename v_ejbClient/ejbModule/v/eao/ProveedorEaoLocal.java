package v.eao;
import java.util.HashMap;
import java.util.List;

import javax.ejb.Local;

import v.excepciones.EliminarException;
import v.excepciones.GuardarException;
import v.modelo.Proveedor;

@Local
public interface ProveedorEaoLocal {

	public Proveedor agregar(Proveedor proveedor) throws GuardarException;

	void modificar(Proveedor proveedor) throws GuardarException;

	void eliminar(Proveedor proveedor) throws EliminarException;

	List<Proveedor> listar(HashMap<String, Object> filters, int start, int limit);

	Proveedor getById(Long id);

	public int getCount();

	public Proveedor findByRuc(String ruc);

}
