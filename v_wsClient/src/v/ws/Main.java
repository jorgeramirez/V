package v.ws;

import java.util.ArrayList;
import java.util.List;

public class Main {

	public static void main(String[] args) {
		
        PagoWebServiceService service1 = new PagoWebServiceService();
        
        PagoWebService port1 = service1.getPagoWebServicePort();
        
        List<PagoWs> lista = new ArrayList<PagoWs>();

		PagoWs pago1 = new PagoWs();
		PagoWs pago2 = new PagoWs();
				
		pago1.setIdCajero((long)2);
		pago2.setIdCajero((long)2);
		
		pago1.setIdFactura(1);
		pago2.setIdFactura(2);
		
		pago1.setMonto((double)1000);
		pago2.setMonto((double)2000);
			
		lista.add(pago1);
		lista.add(pago2);
		//PagoWs [] plist = {pago1, pago2};
		
		List<PagoWs> res;
		res = port1.registrarPagos(lista);
		if (res.isEmpty()){
			System.out.println("Ningun pago guardado");
		} else {
			for (PagoWs re : res){
	    		System.out.println("xxx");
	    		System.out.print("idCajero: ");
	    		System.out.println(re.getIdCajero());
	    		System.out.print("idFactura: ");
	    		System.out.println(re.getIdFactura());
	    		System.out.print("Monto: ");
	    		System.out.println(re.getMonto());
	    		System.out.println("xxx");
			}
		}		
	}

}
