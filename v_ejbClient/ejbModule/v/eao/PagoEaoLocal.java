package v.eao;
import javax.ejb.Local;

import v.excepciones.EliminarException;
import v.excepciones.GuardarException;
import v.modelo.Pago;

@Local
public interface PagoEaoLocal {

	Pago agregar(Pago pago) throws GuardarException;

	void modificar(Pago pago) throws GuardarException;

	void eliminar(Pago pago) throws EliminarException;
	

}
