package v.client;

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

}
