package v.client.widgets;

import java.util.List;

import com.extjs.gxt.ui.client.binding.FieldBinding;
import com.extjs.gxt.ui.client.binding.FormBinding;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.google.gwt.user.client.Element;


/**
 * Define un Formulario para Alta/Modificación de datos.
 * 
 * @author Jorge Ramírez <jorgeramirez1990@gmail.com>
 **/
public class EditorForm extends Dialog {
	private FormPanel form;
	private FormData formData;
	private List<Field<?>> fields;
	private FormBinding formBindings;
	private List<FieldBinding> bindings;
	
	public EditorForm(String title, List<Field<?>> fields, List<FieldBinding> bindings, int width, int height){
		this.fields = fields;
		formData = new FormData("-20");
		this.bindings = bindings;
		this.setBodyBorder(false);
		this.setHeading(title);
		this.setLayout(new FitLayout());
		this.setHideOnButtonClick(true);
		this.okText = "Guardar";
		this.cancelText = "Cancelar";
		this.setButtons(Dialog.OKCANCEL);
		this.setSize(width, height);
		this.setModal(true);
	}

	public void onRender(Element parent, int pos) {
		super.onRender(parent, pos);
		form = new FormPanel();
		form.setHeaderVisible(false);
		form.setFrame(true);
		for(Field<?> f: fields){
			form.add(f, formData);
		}
		formBindings = new FormBinding(form);
		for(FieldBinding fb: bindings){
			formBindings.addFieldBinding(fb);
		}
		formBindings.autoBind();
		add(form);
	}
	
	
	public FormPanel getForm() {
		return form;
	}

	public void setForm(FormPanel form) {
		this.form = form;
	}

	public List<Field<?>> getFields() {
		return fields;
	}

	public void setFields(List<Field<?>> fields) {
		this.fields = fields;
	}

	public FormBinding getFormBindings() {
		return formBindings;
	}

	public void setFormBindings(FormBinding formBindings) {
		this.formBindings = formBindings;
	}

	public List<FieldBinding> getBindings() {
		return bindings;
	}

	public void setBindings(List<FieldBinding> bindings) {
		this.bindings = bindings;
	}
	
}
