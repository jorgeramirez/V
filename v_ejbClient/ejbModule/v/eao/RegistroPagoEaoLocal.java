package v.eao;
import javax.ejb.Local;

import v.excepciones.EliminarException;
import v.excepciones.GuardarException;
import v.modelo.RegistroPago;

@Local
public interface RegistroPagoEaoLocal {

	RegistroPago agregar(RegistroPago registroPago) throws GuardarException;
	void modificar(RegistroPago registroPago) throws GuardarException;
	void eliminar(RegistroPago registroPago) throws EliminarException;

}
