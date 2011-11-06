package v.eao;
import java.util.List;

import javax.ejb.Local;

import v.excepciones.EliminarException;
import v.excepciones.GuardarException;
import v.modelo.FacturaVenta;

@Local
public interface FacturaVentaEaoLocal {
	public FacturaVenta agregar(FacturaVenta usuario) throws GuardarException;
	public void modificar(FacturaVenta usuario) throws GuardarException;
	public void eliminar(FacturaVenta usuario) throws EliminarException;
	public List<FacturaVenta> listar();	
}
