package v.ws;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;

import v.facade.CajeroFacadeLocal;


@WebService()
@Stateless
public class PagoWebService implements PagoWebServiceRemote {
  
    public PagoWebService() {
    }
    
    @EJB
    CajeroFacadeLocal cajeroFacade;
    
    @WebMethod(operationName = "registrarPagos")
    public List<PagoWs> registrarPagos(@WebParam(name = "pagos") List<PagoWs> pagos) {
    	
    	List<PagoWs> listaRetorno = new ArrayList<PagoWs>();
    	
    	for (PagoWs pago : pagos){
    		System.out.println("Pago Registrados:");
    		System.out.print("idPago: ");
    		System.out.println(pago.getIdPago());
    		System.out.print("idCajero: ");
    		System.out.println(pago.getIdCajero());
    		System.out.print("idFactura: ");
    		System.out.println(pago.getIdFactura());
    		System.out.print("Monto: ");
    		System.out.println(pago.getMonto());
    		System.out.println("----");
    	}

   		listaRetorno = cajeroFacade.registroPagosWebService(pagos);
	
		return listaRetorno;
    }
}
