package v.client;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import v.client.controllers.AbstractController;

import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.BeanModelFactory;
import com.extjs.gxt.ui.client.data.BeanModelLookup;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.grid.CellEditor;


/**
 * Clase que define métodos de utileria.
 * 
 * @author Jorge Ramírez <jorgeramirez1990@gmail.com>
 **/
public class Util {
	private static String reportBaseUrl = "http://localhost:8080/v_reports/reportes/";
	private static List<String> reportesCompilados = new ArrayList<String>();

	/**
	 * Genera un BeanModel a partir de un Java Bean.
	 * 
	 * @param bean: el Java Bean para crear el {@link BeanModel}
	 **/
	public static BeanModel createBeanModel(Object bean) {
		BeanModelFactory bmf = BeanModelLookup.get().getFactory(bean.getClass());
		return bmf.createModel(bean);
	}

	/**
	 * Crea un {@link CellEditor} para el {@link ComboBox} pasado
	 * como parámetro.
	 **/
	public static CellEditor createComboBoxCellEditor(
			final SimpleComboBox<Object> combo) {
		
		return new CellEditor(combo) {
			@Override  
			public Object preProcessValue(Object value) {  
				if (value == null) {  
					return value;  
				}  
				return combo.findModel((Object)value);  
			}  

			@Override  
			public Object postProcessValue(Object value) {  
				if (value == null) {  
					return value;  
				}
				return ((ModelData) value).get("value");
			}  
		}; 
	}
	
	/**
	 * Retorna la URL del reporte report en el formato tipo,
	 * parametrizado por param.
	 * La lista reportesCompilados contiene los reportes compilados, se revisa
	 * para ver si el reporte está compilado, si no está se procesa
	 * el archivo jrxml correspondiente
	 **/	
	public static String reporteUrl(String report, String tipo, String param) {
		String url = reportBaseUrl + "preparar?reporte=" + report + "&id=" + param + "&tipo=" + tipo;
		if (!reportesCompilados.contains(report)) {
			reportesCompilados.add(report);
			return url + "&compilar=True";
		} else {
			return url;
		}
	}

}
