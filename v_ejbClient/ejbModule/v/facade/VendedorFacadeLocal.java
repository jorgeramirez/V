package v.facade;
import java.util.List;

import javax.ejb.Local;

import util.SimpleFilter;
import v.excepciones.EliminarException;
import v.excepciones.GuardarException;
import v.modelo.Cliente;
import v.modelo.FacturaDetalleVenta;
import v.modelo.FacturaVenta;

@Local
public interface VendedorFacadeLocal {

	List<Cliente> listarClientes(List<SimpleFilter> filters, int start, int limit);

	Cliente agregarCliente(Cliente c) throws GuardarException;

	void modificarCliente(Cliente c) throws GuardarException;

	void eliminarCliente(Cliente c) throws EliminarException;

	int getTotalClientes();

	FacturaVenta agregarVenta(FacturaVenta v) throws GuardarException;

	int getTotalDetallesVenta(Integer numeroFactura);

	List<FacturaDetalleVenta> listarVentasDetalles(List<SimpleFilter> filters, int start, int limit);

	int getTotalClientesFilters(List<SimpleFilter> plainFilters);

	int getTotalDetallesVentaFilters(List<SimpleFilter> plainFilters);

}
