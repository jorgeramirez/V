package v.facade;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import util.SimpleFilter;
import v.eao.FacturaCompraEaoLocal;
import v.eao.FacturaDetalleCompraEaoLocal;
import v.eao.ProductoEaoLocal;
import v.eao.ProveedorEaoLocal;
import v.excepciones.EliminarException;
import v.excepciones.GuardarException;
import v.modelo.FacturaCompra;
import v.modelo.FacturaDetalleCompra;
import v.modelo.Producto;
import v.modelo.Proveedor;


@Stateless
public class CompradorFacade implements CompradorFacadeLocal {

	@EJB
	ProductoEaoLocal productoEao;
	
	@EJB
	ProveedorEaoLocal proveedorEao;
	
	@EJB
	FacturaCompraEaoLocal facturaEao;
	
	@EJB
	FacturaDetalleCompraEaoLocal detalleEao;
	
    public CompradorFacade() {
    	
    }

	@Override
	public Producto agregarProducto(Producto producto) throws GuardarException {
		return productoEao.agregar(producto);
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
	public List<Producto> listarProductos(List<SimpleFilter> filters, int start, int limit) {
		return productoEao.listar(filters, start, limit);
	}
	
	@Override
	public Proveedor agregarProveedor(Proveedor proveedor) throws GuardarException {
		return proveedorEao.agregar(proveedor);
	}
	
	@Override
	public void modificarProveedor(Proveedor proveedor) throws GuardarException {
		proveedor.setCompras(proveedorEao.getById(proveedor.getId()).getCompras());
		proveedorEao.modificar(proveedor);
	}
	
	@Override
	public void eliminarProveedor(Proveedor proveedor) throws EliminarException {
		proveedor.setCompras(proveedorEao.getById(proveedor.getId()).getCompras());
		proveedorEao.eliminar(proveedor);
	}
	
	@Override
	public List<Proveedor> listarProveedores(List<SimpleFilter> filters, int start, int limit) {
		return proveedorEao.listar(filters, start, limit);
	}
	
	@Override
	public void registrarCompra(FacturaCompra factura) throws GuardarException {
		
		Proveedor proveedor = proveedorEao.getById(factura.getProveedor().getId());
		
		for (FacturaDetalleCompra detalle : factura.getDetalles()) {
			
			Producto producto = productoEao.getById(detalle.getProducto().getId());
			
			//costo actual * cantidad actual + costo nuevo * cantidad compra) / 
			//           (cantidad actual + cantidad compra).
			Double nuevoCosto = ((producto.getCosto() * producto.getCantidad()) 
					+ ( detalle.getPrecio() * detalle.getCantidad() )) 
					/ ( producto.getCantidad() + detalle.getCantidad() );
			
			int nuevaCantidad = producto.getCantidad() + detalle.getCantidad();
			
			producto.setCosto(nuevoCosto);
			producto.setCantidad(nuevaCantidad);
			
			productoEao.modificar(producto);
				
			detalle.setProducto(producto);			
		}
		
		factura.setProveedor(proveedor);
		
		facturaEao.agregar(factura);
	}

	@Override
	public Producto findProductoByCodigo(String codigo) {
		return productoEao.findByCodigo(codigo);
	}

	@Override
	public int getTotalProductos() {
		return productoEao.getCount();
	}

	@Override
	public int getTotalProveedores() {
		return proveedorEao.getCount();
	}

	@Override
	public Object findProductoByRuc(String ruc) {
		return proveedorEao.findByRuc(ruc);
	}

	@Override
	public int getTotalCompras() {
		return facturaEao.getCount();
	}

	@Override
	public List<FacturaCompra> listarCompras(List<SimpleFilter> filters, int start, int limit) {
		return facturaEao.listarCompras(filters, start, limit);
	}

	@Override
	public int getTotalDetallesCompra(Integer numeroFactura) {
		return detalleEao.getTotalDetallesCompra(numeroFactura);
	}

	@Override
	public List<FacturaDetalleCompra> listarComprasDetalles(
			List<SimpleFilter> plainFilters, int start, int limit) {
		return detalleEao.listarComprasDetalles(plainFilters, start, limit);
	}

	@Override
	public int getTotalProductosFilters(List<SimpleFilter> plainFilters) {
		return productoEao.getTotalProductosFilters(plainFilters);
	}

	@Override
	public int getTotalProveedoresFilters(List<SimpleFilter> plainFilters) {
		return proveedorEao.getTotalProveedoresFilters(plainFilters);
	}

	@Override
	public int getTotalComprasFilters(List<SimpleFilter> plainFilters) {
		return facturaEao.getTotalComprasFilters(plainFilters);
	}

	@Override
	public int getTotalDetallesCompraFilters(List<SimpleFilter> plainFilters) {
		return detalleEao.getTotalDetallesCompraFilters(plainFilters);
	}
}
