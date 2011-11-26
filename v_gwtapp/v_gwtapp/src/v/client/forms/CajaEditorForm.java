package v.client.forms;

import v.client.widgets.EditorForm;

import com.extjs.gxt.ui.client.widget.form.TextArea;
import com.extjs.gxt.ui.client.widget.form.TextField;

/**
 * Define Formulario para Alta/Modificación de Cajas
 * 
 * @author Jorge Ramírez <jorgeramirez1990@gmail.com>
 **/
public class CajaEditorForm extends EditorForm {

	public CajaEditorForm(String title, boolean create, int width, int height) {
		super(title, create, width, height);
	}

	@Override
	public void build(boolean create) {		
		// username field
		TextArea descripcion = new TextArea();
		descripcion.setAllowBlank(false);
		descripcion.setMaxLength(120);
		descripcion.setFieldLabel("Descripcion");
		descripcion.setName("descripcion");
		fields.add(descripcion);
		
		TextField<Integer> nroCaja = new TextField<Integer>();
		nroCaja.setEnabled(false);
		nroCaja.setVisible(!create);
		nroCaja.setName("numeroCaja");
		nroCaja.setFieldLabel("Nro de Caja");
		fields.add(nroCaja);
	}

}
