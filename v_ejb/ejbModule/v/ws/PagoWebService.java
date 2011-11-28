package v.ws;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import v.eao.FacturaVentaEaoLocal;
import v.eao.UsuarioEaoLocal;
import v.excepciones.GuardarException;
import v.facade.CajeroFacadeLocal;
import v.modelo.FacturaVenta;
import v.modelo.Pago;
import v.modelo.Usuario;

/**
 * Session Bean implementation class PagoWebService
 */
@WebService()
@Stateless
public class PagoWebService implements PagoWebServiceRemote {


    @EJB
    CajeroFacadeLocal cajeroFacade;
	
    @EJB
    UsuarioEaoLocal usuarioEao;
    
    @EJB
    FacturaVentaEaoLocal facturaVentaEao;
    
    public PagoWebService() {
    }
    
    @WebMethod(operationName = "registrarPago")
    public String registrarPago(@WebParam(name = "idCajero") Long idCajero,
            @WebParam(name = "idFactura") Long idFactura,
            @WebParam(name = "monto") Double monto) throws GuardarException{
    	
    	Usuario usuario = usuarioEao.findById(idCajero);
    	FacturaVenta facturaVenta = facturaVentaEao.findById(idFactura);
    	Pago pago = new Pago();
    	
    	pago.setUsuario(usuario);
    	pago.setFactura(facturaVenta);
    	pago.setMonto(monto);
    	
    	if (cajeroFacade.registrarPago(pago)){
    		return "Pago guardado exitosamente";
    	}
    	return "Error al guardar el pago";
    }

    @WebMethod(operationName = "registrarPagos")
    public String registrarPagos(@WebParam(name = "pagos") List<PagoWs> pagos){
    	return registrarPagos(pagos);
    }
}
