package v.facade;
import java.util.List;

import javax.ejb.Local;

import v.excepciones.GuardarException;
import v.modelo.Pago;

@Local
public interface CajeroFacadeLocal {

	List<Pago> cerrarCaja(Long idCaja) throws GuardarException;

}
