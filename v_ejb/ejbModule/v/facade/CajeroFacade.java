package v.facade;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import util.SimpleFilter;
import v.eao.CajaEaoLocal;
import v.eao.FacturaVentaEaoLocal;
import v.eao.PagoEaoLocal;
import v.eao.UsuarioEaoLocal;
import v.excepciones.GuardarException;
import v.modelo.Caja;
import v.modelo.FacturaVenta;
import v.modelo.Pago;
import v.modelo.Usuario;

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
	
	@EJB
	UsuarioEaoLocal usuarioEao;

    public CajeroFacade() {
    
    }
    
    @Override
    public void cierredeCaja(Long idCajero) throws GuardarException{
    	
    	List<Pago> pagosDelDia = cajaEao.pagosNoCerrados(idCajero);
    	
    	for (Pago pagoCierre : pagosDelDia){
    		Pago pago = pagoEao.getById(pagoCierre.getId());
    		pago.setEstado("cerrado");
    		pagoEao.modificar(pago);
    	}
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

	@Override
	public boolean registrarPago(Pago pago) throws GuardarException {
		FacturaVenta factura = 	ventaEao.findById(pago.getFactura().getNumeroFactura());
		Usuario cajero = usuarioEao.findByUsername(pago.getUsuario().getUsername());
		Caja caja = cajero.getCaja();
		
		if(pago.getMonto() > factura.getSaldo()){
			throw new GuardarException("El monto a pagar excede el saldo");
		}
		
		pago.setFactura(factura);
		pago.setUsuario(cajero);
		pago.setCaja(caja);
		pago.setFecha(new Date());
		factura.setSaldo(factura.getSaldo() - pago.getMonto());
		if(factura.getSaldo() == 0.0){
			factura.setEstado("pagada");
		}
		return pagoEao.agregar(pago) != null;
	}
    
}
