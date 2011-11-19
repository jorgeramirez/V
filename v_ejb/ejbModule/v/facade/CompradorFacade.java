package v.facade;

import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import v.eao.ProductoEaoLocal;
import v.eao.ProveedorEaoLocal;
import v.excepciones.EliminarException;
import v.excepciones.GuardarException;
import v.modelo.FacturaCompra;
import v.modelo.Producto;
import v.modelo.Proveedor;


@Stateless
public class CompradorFacade implements ComprasFacadeLocal {

	@EJB
	ProductoEaoLocal productoEao;
	
	@EJB
	ProveedorEaoLocal proveedorEao;
	
    public CompradorFacade() {
        // TODO Auto-generated constructor stub
    }

	@Override
	public void agregarProducto(Producto producto) throws GuardarException {
		productoEao.agregar(producto);
	}
	
	@Override
	public void modificarProducto(Producto producto) throws GuardarException {
		productoEao.modificar(producto);
	}
	
	@Override
	public void eliminarProducto(Producto producto) throws EliminarException {
		productoEao.eliminar(producto);
	}
	
	@Override
	public List<Producto> listarProductos(HashMap<String, Object> filters, int start, int limit) {
		return productoEao.listar(filters, start, limit);
	}
	
	@Override
	public void agregarProveedor(Proveedor proveedor) throws GuardarException {
		proveedorEao.agregar(proveedor);
	}
	
	@Override
	public void modificarProveedor(Proveedor proveedor) throws GuardarException {
		proveedorEao.modificar(proveedor);
	}
	
	@Override
	public void eliminarProveedor(Proveedor proveedor) throws EliminarException {
		proveedorEao.eliminar(proveedor);
	}
	
	@Override
	public List<Proveedor> listarProveedores(HashMap<String, Object> filters, int start, int limit) {
		return proveedorEao.listar(filters, start, limit);
	}
	
	public void registrarCompra(FacturaCompra factura) {
		
	}
}
