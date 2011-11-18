package v.client;

import java.util.HashMap;
import java.util.List;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.widget.LayoutContainer;

import v.client.controllers.AbstractController;

/**
 * Clase que se encarga de enrutar hacia los controladores.
 * 
 * 
 * @author Jorge Ramírez <jorgeramirez1990@gmail.com>
 **/

public class Dispatcher {
	
	HashMap<String, AbstractController> controllers;
	
	public Dispatcher(List<AbstractController> controllers) {
		this.controllers = new HashMap<String, AbstractController>();
		for(AbstractController c: controllers){
			this.controllers.put(c.getId(), c);
		}
	}
	
	/**
	 * Enruta hacia el controlador correspondiente.
	 **/
	public void distpatch(String idController) {
		if(controllers.containsKey(idController)){
			LayoutContainer cr = (LayoutContainer)Registry.get(AppViewport.CENTER_REGION);
			cr.removeAll();
			controllers.get(idController).init();
		}
	}
}
