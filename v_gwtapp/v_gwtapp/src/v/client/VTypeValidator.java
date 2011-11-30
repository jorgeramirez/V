package v.client;

import v.client.VType;

import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.Validator;

/**
 * Clase utilizada para validar campos de formularios.
 **/

public class VTypeValidator implements Validator {

	private VType type;
	private String msg;

	public VTypeValidator(VType type){
		this.type = type;
		this.msg = null;
	}
	
	public VTypeValidator(VType type, String msg) {
		this.type = type;
		this.msg = msg;
	}

	@Override
	public String validate(Field<?> field, String value) {
		String res = null;
		if(!value.matches(type.regex)){
			res = value + " isn't a valid " + type.name;
			if(msg != null)	{
				res = msg;
			}
		}
		return res;
	}

}
