package v.eao;
import javax.ejb.Local;

import v.excepciones.EliminarException;
import v.excepciones.GuardarException;
import v.modelo.FacturaDetalleCompra;

@Local
public interface FacturaDetalleCompraEaoLocal {

	FacturaDetalleCompra agregar(FacturaDetalleCompra facturaDetalleCompra)
			throws GuardarException;

	void modificar(FacturaDetalleCompra facturaDetalleCompra)
			throws GuardarException;

	void eliminar(FacturaDetalleCompra facturaDetalleCompra)
			throws EliminarException;

}
