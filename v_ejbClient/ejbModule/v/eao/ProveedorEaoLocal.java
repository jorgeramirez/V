package v.eao;
import javax.ejb.Local;

import v.excepciones.EliminarException;
import v.excepciones.GuardarException;
import v.modelo.Proveedor;

@Local
public interface ProveedorEaoLocal {

	public Proveedor agregar(Proveedor proveedor) throws GuardarException;

	void modificar(Proveedor proveedor) throws GuardarException;

	void eliminar(Proveedor proveedor) throws EliminarException;

}
