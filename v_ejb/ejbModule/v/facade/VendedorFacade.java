package v.facade;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import util.SimpleFilter;
import v.eao.CajaEaoLocal;
import v.eao.ClienteEaoLocal;
import v.eao.FacturaDetalleVentaEaoLocal;
import v.eao.FacturaVentaEaoLocal;
import v.eao.ProductoEaoLocal;
import v.eao.UsuarioEaoLocal;
import v.excepciones.EliminarException;
import v.excepciones.GuardarException;
import v.modelo.Cliente;
import v.modelo.FacturaDetalleCompra;
import v.modelo.FacturaDetalleVenta;
import v.modelo.FacturaVenta;
import v.modelo.Producto;
import v.modelo.Proveedor;
import v.modelo.Usuario;

/**
 * Session Bean implementation class VendedorFacade
 */
@Stateless
public class VendedorFacade implements VendedorFacadeLocal {	
	@EJB
	ClienteEaoLocal clienteEao;
	
	@EJB
	CajaEaoLocal cajaEao;
	
	@EJB
	FacturaVentaEaoLocal facturaVentaEao;
	
	@EJB
	UsuarioEaoLocal usuarioEao;
	
	@EJB
	ProductoEaoLocal productoEao;
	
	@EJB
	FacturaDetalleVentaEaoLocal ventaDetalleEao;
	
    /**
     * Default constructor. 
     */
    public VendedorFacade() {

    }
    
	@Override
	//@RolesAllowed("administrador")
	public List<Cliente> listarClientes(List<SimpleFilter> filters, int start, int limit) {
		return clienteEao.listar(filters, start, limit);
	}

	@Override
	public Cliente agregarCliente(Cliente c) throws GuardarException {
		return clienteEao.agregar(c);
	}

	@Override
	public void modificarCliente(Cliente c) throws GuardarException {
		clienteEao.modificar(c);
	}

	@Override
	public void eliminarCliente(Cliente c) throws EliminarException {
		clienteEao.eliminar(c);
	}
	
	@Override
	public int getTotalClientes() {
		return clienteEao.getCount();
	}

	@Override
	public FacturaVenta agregarVenta(FacturaVenta factura) throws GuardarException {
		Cliente cliente = clienteEao.getById(factura.getCliente().getId());
		Usuario vendedor = usuarioEao.findById(factura.getVendedor().getId());
		
		for (FacturaDetalleVenta detalle : factura.getDetalles()) {
			
			Producto producto = productoEao.getById(detalle.getProducto().getId());
			
			int nuevaCantidad = producto.getCantidad() - detalle.getCantidad();
			
			if (nuevaCantidad < 0 ) {
				throw new GuardarException("Cantidad Incorrecta");
			}
			
			producto.setCantidad(nuevaCantidad);
				
			detalle.setProducto(producto);
			detalle.setCabecera(factura);
		}
		
		factura.setFecha(new Date());
		factura.setCliente(cliente);
		factura.setVendedor(vendedor);
		
		return facturaVentaEao.agregar(factura);		
		
	}

	@Override
	public int getTotalDetallesVenta(Integer numeroFactura) {
		return ventaDetalleEao.getTotalDetallesVenta(numeroFactura);
	}

	@Override
	public List<FacturaDetalleVenta> listarVentasDetalles(List<SimpleFilter> filters, int start, int limit) {
		return ventaDetalleEao.listarVentasDetalles(filters, start, limit);
	}

	@Override
	public int getTotalClientesFilters(List<SimpleFilter> plainFilters) {
		return clienteEao.getTotalClientesFilters(plainFilters);
	}

	@Override
	public int getTotalDetallesVentaFilters(List<SimpleFilter> plainFilters) {
		return ventaDetalleEao.getTotalDetallesVentaFilters(plainFilters);
	}
}
