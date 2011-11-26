package v.server;

import java.util.ArrayList;
import java.util.List;

import util.SimpleFilter;

import com.extjs.gxt.ui.client.data.FilterConfig;


/**
 * Procesa los filtros enviados por la interfaz y los convierte a una 
 * lista de {@link SimpleFilter}
 **/
public class Filter {
	
	public static List<SimpleFilter> processFilters(List<FilterConfig> filters) {
		List<SimpleFilter> pf = new ArrayList<SimpleFilter>();
		Object val;
		String field;
		String comparison = null;
		if(filters != null){
			for (FilterConfig f : filters) {
				val = f.getValue();
				field = f.getField();
				if(f.getType().equals("string")){
					comparison = "like";
				}else if(f.getType().equals("numeric")){
					comparison = f.getComparison();
				}
				pf.add(new SimpleFilter(field, val ,comparison));
		    }
		}
		return pf;
	}
	
}
