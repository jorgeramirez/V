package v.eao;
import java.util.List;

import javax.ejb.Local;

import util.SimpleFilter;
import v.excepciones.EliminarException;
import v.excepciones.GuardarException;
import v.modelo.Proveedor;

@Local
public interface ProveedorEaoLocal {

	public Proveedor agregar(Proveedor proveedor) throws GuardarException;

	void modificar(Proveedor proveedor) throws GuardarException;

	void eliminar(Proveedor proveedor) throws EliminarException;

	List<Proveedor> listar(List<SimpleFilter> filters, int start, int limit);

	Proveedor getById(Long id);

	int getCount();

	Proveedor findByRuc(String ruc);

	int getTotalProveedoresFilters(List<SimpleFilter> plainFilters);

}
