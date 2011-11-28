package v.ws;

import java.util.List;

import javax.ejb.Stateless;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;


@WebService()
@Stateless
public class PagoWebService implements PagoWebServiceRemote {
  
    public PagoWebService() {
    }
    
    @WebMethod(operationName = "registrarPagos")
    public String registrarPagos(@WebParam(name = "pagos") List<PagoWs> pagos){
    	return registrarPagos(pagos);
    }
}
