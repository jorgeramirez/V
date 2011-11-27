package v.facade;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import util.SimpleFilter;
import v.eao.CajaEaoLocal;
import v.eao.FacturaVentaEaoLocal;
import v.eao.PagoEaoLocal;
import v.excepciones.GuardarException;
import v.modelo.FacturaVenta;
import v.modelo.Pago;

/**
 * Session Bean implementation class CajeroFacade
 */
@Stateless
public class CajeroFacade implements CajeroFacadeLocal {

	@EJB
	CajaEaoLocal cajaEao;
	
	@EJB
	PagoEaoLocal pagoEao;
	
	@EJB
	FacturaVentaEaoLocal ventaEao;

    public CajeroFacade() {
    
    }
    
    @Override
    public List<Pago> cerrarCaja(Long idCaja) throws GuardarException{
    	
    	List<Pago> pagosDelDia = cajaEao.pagosNoCerrados(idCaja);
    	
    	for (Pago pagoCierre : pagosDelDia){
    		Pago pago = pagoEao.getById(pagoCierre.getId());
    		pago.setEstado("cerrado");
    		pagoEao.modificar(pago);
    	}
    	
    	return pagosDelDia;
    }

	@Override
	public int getTotalFacturasPendientes() {
		return ventaEao.getTotalPendientes();
	}

	@Override
	public List<FacturaVenta> listarFacturasPendientes(
			List<SimpleFilter> plainFilters, int start, int limit) {
		return ventaEao.listar(plainFilters, start, limit);
	}
    
}
