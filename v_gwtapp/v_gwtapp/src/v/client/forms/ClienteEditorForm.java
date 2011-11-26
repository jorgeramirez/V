package v.client.forms;

import v.client.VType;
import v.client.VTypeValidator;
import v.client.widgets.EditorForm;

import com.extjs.gxt.ui.client.widget.form.TextField;

public class ClienteEditorForm extends EditorForm {
	public ClienteEditorForm(String title, boolean create, int width, int height) {
		super(title, create, width, height);
	}

	@Override
	public void build(boolean create) {
		//final VendedorServiceAsync service = Registry.get(AppConstants.VENDEDOR_SERVICE);
		
		// nombre field
		TextField<String> text = new TextField<String>();
		text.setMaxLength(50);
		text.setValidator(new VTypeValidator(VType.ALPHABET));
		text.setFieldLabel("Nombre");
		text.setName("nombre");
		text.setAllowBlank(false);
		fields.add(text);
		
		// apellido field
		text = new TextField<String>();
		text.setMaxLength(50);
		text.setValidator(new VTypeValidator(VType.ALPHABET));
		text.setFieldLabel("Apellido");
		text.setName("apellido");
		text.setAllowBlank(false);
		fields.add(text);

		// cedula field
		text = new TextField<String>();
		text.setAllowBlank(false);
		text.setMaxLength(10);
		text.setValidator(new VTypeValidator(VType.NUMERIC));
		text.setFieldLabel("Cédula");
		text.setName("cedula");
		fields.add(text);

		// direccion field
		text = new TextField<String>();
		text.setMaxLength(70);		
		text.setValidator(new VTypeValidator(VType.ALPHANUMERIC));
		text.setFieldLabel("Dirección");
		text.setName("direccion");
		text.setAllowBlank(false);
		fields.add(text);

		// telefono field
		text = new TextField<String>();
		text.setMaxLength(20);
		text.setValidator(new VTypeValidator(VType.ALPHANUMERIC));
		text.setFieldLabel("Teléfono");
		text.setName("telefono");
		fields.add(text);
	
	}

}
