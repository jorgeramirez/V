package v.facade;
import java.util.HashMap;
import java.util.List;

import javax.ejb.Local;

import v.excepciones.EliminarException;
import v.excepciones.GuardarException;
import v.modelo.FacturaCompra;
import v.modelo.Producto;
import v.modelo.Proveedor;

@Local
public interface CompradorFacadeLocal {

	Producto agregarProducto(Producto producto) throws GuardarException;

	void modificarProducto(Producto producto) throws GuardarException;

	void eliminarProducto(Producto producto) throws EliminarException;

	List<Producto> listarProductos(HashMap<String, Object> filters, int start,
			int limit);

	void agregarProveedor(Proveedor proveedor) throws GuardarException;

	void modificarProveedor(Proveedor proveedor) throws GuardarException;

	void eliminarProveedor(Proveedor proveedor) throws EliminarException;

	List<Proveedor> listarProveedores(HashMap<String, Object> filters,
			int start, int limit);

	void registrarCompra(FacturaCompra factura) throws GuardarException;

	Producto findProductoByCodigo(String codigo);

	int getTotalProductos();

}
