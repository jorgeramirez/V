package v.facade;

import javax.ejb.Local;

import v.excepciones.GuardarException;

@Local
public interface CajeroFacadeLocal {

	void cierredeCaja(Long idCaja) throws GuardarException;

}
