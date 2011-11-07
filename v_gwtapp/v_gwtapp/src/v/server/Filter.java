package v.server;

import java.util.HashMap;
import java.util.List;

import com.extjs.gxt.ui.client.data.FilterConfig;


/**
 * Procesa los filtros enviados por la interfaz y los convierte a un hash 
 * de la forma Campo => Valor
 **/
public class Filter {
	
	public static HashMap<String, Object> processFilters(List<FilterConfig> filters) {
		HashMap<String, Object> pf = new HashMap<String, Object>();
		if(filters != null){
			for (FilterConfig f : filters) {
				Object val = f.getValue();
				String field = f.getField();
				pf.put(field, val);
		    }
		}
		return pf;
	}
	
}
