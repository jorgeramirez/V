package v.eao;
import javax.ejb.Local;

import v.excepciones.EliminarException;
import v.excepciones.GuardarException;
import v.modelo.FacturaCompra;

@Local
public interface FacturaCompraEaoLocal {

	FacturaCompra agregar(FacturaCompra facturaCompra) throws GuardarException;

	void modificar(FacturaCompra facturaCompra) throws GuardarException;

	void eliminar(FacturaCompra facturaCompra) throws EliminarException;

}
