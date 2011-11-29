package v.facade;
import java.util.List;

import javax.ejb.Local;

import util.SimpleFilter;
import v.excepciones.EliminarException;
import v.excepciones.GuardarException;
import v.modelo.FacturaCompra;
import v.modelo.FacturaDetalleCompra;
import v.modelo.Producto;
import v.modelo.Proveedor;

@Local
public interface CompradorFacadeLocal {

	Producto agregarProducto(Producto producto) throws GuardarException;

	void modificarProducto(Producto producto) throws GuardarException;

	void eliminarProducto(Producto producto) throws EliminarException;

	List<Producto> listarProductos(List<SimpleFilter> filters, int start,
			int limit);

	Proveedor agregarProveedor(Proveedor proveedor) throws GuardarException;

	void modificarProveedor(Proveedor proveedor) throws GuardarException;

	void eliminarProveedor(Proveedor proveedor) throws EliminarException;

	List<Proveedor> listarProveedores(List<SimpleFilter> filters,
			int start, int limit);

	void registrarCompra(FacturaCompra factura) throws GuardarException;

	Producto findProductoByCodigo(String codigo);

	int getTotalProductos();

	int getTotalProveedores();

	Object findProductoByRuc(String ruc);

	int getTotalCompras();

	List<FacturaCompra> listarCompras(List<SimpleFilter> filters, int start, int limit);

	int getTotalDetallesCompra(Integer numeroFactura);

	List<FacturaDetalleCompra> listarComprasDetalles(
			List<SimpleFilter> plainFilters, int start, int limit);

}
