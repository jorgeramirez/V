package v.facade;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import util.SimpleFilter;
import v.eao.CajaEaoLocal;
import v.eao.ClienteEaoLocal;
import v.eao.FacturaDetalleVentaEaoLocal;
import v.eao.FacturaVentaEaoLocal;
import v.eao.UsuarioEaoLocal;
import v.excepciones.EliminarException;
import v.excepciones.GuardarException;
import v.modelo.Cliente;
import v.modelo.FacturaDetalleVenta;
import v.modelo.FacturaVenta;

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
	public boolean agregarVenta(FacturaVenta v) throws GuardarException {
		FacturaVenta fv = null;
		fv = facturaVentaEao.agregar(v);
		if (fv == null) {
			return false;
		} else {
			return true;
		}		
		
	}

	@Override
	public int getTotalDetallesVenta(Integer numeroFactura) {
		return ventaDetalleEao.getTotalDetallesVenta(numeroFactura);
	}

	@Override
	public List<FacturaDetalleVenta> listarVentasDetalles(List<SimpleFilter> filters, int start, int limit) {
		return ventaDetalleEao.listarVentasDetalles(filters, start, limit);
	}
}
