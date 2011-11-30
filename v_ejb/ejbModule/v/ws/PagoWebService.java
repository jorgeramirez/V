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
    	
   		listaRetorno = cajeroFacade.registroPagosWebService(pagos);
	
		return listaRetorno;
    }
}
