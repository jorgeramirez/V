package v.client.forms;

import v.client.AppConstants;
import v.client.VType;
import v.client.VTypeValidator;
import v.client.rpc.CompradorServiceAsync;
import v.client.widgets.EditorForm;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.Validator;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Define Formulario para Alta/Modificación de Proveedores
 * 
 * @author Jorge Ramírez <jorgeramirez1990@gmail.com>
 **/
public class ProveedorEditorForm extends EditorForm {

	final CompradorServiceAsync service = (CompradorServiceAsync)Registry.get(AppConstants.COMPRADOR_SERVICE);

	public ProveedorEditorForm(String title, boolean create, int width, int height) {
		super(title, create, width, height);
	}

	@Override
	public void build(boolean create) {
		
		// ruc
		final TextField<String> ruc = new TextField<String>();
		ruc.setName("ruc");
		ruc.setAllowBlank(false);
		ruc.setMaxLength(15);
		ruc.setFieldLabel("RUC");
		ruc.setEnabled(create);
			
		if(create){
			ruc.setValidator(new Validator() {  // Validator para ruc de proveedor
	
				@Override
				public String validate(Field<?> field, final String value) {
					service.existeRucProveedor(value, new AsyncCallback<Boolean>(){
	
						@Override
						public void onFailure(Throwable caught) {
							// ignored
						}
	
						@Override
						public void onSuccess(Boolean existe) {
							if(existe){
								ruc.markInvalid("El RUC " + value + " ya existe");
							}
						}
	
					});
					return null;
				}
			});
		}
		
		fields.add(ruc);
		
		// nombre
		TextField<String> nombre = new TextField<String>();
		nombre.setName("nombre");
		nombre.setFieldLabel("Nombre");
		nombre.setValidator(new VTypeValidator(VType.ALPHABET));
		nombre.setAllowBlank(false);
		nombre.setMaxLength(50);
		fields.add(nombre);
		
		// direccion
		TextField<String> dir = new TextField<String>();
		dir.setName("direccion");
		dir.setFieldLabel("Dirección");
		dir.setAllowBlank(false);
		dir.setValidator(new VTypeValidator(VType.ALPHANUMERIC));
		dir.setMaxLength(70);
		fields.add(dir);	

		// telefono
		TextField<String> telefono = new TextField<String>();
		telefono.setName("telefono");
		telefono.setFieldLabel("Teléfono");
		telefono.setAllowBlank(false);
		telefono.setValidator(new VTypeValidator(VType.ALPHANUMERIC));
		telefono.setMaxLength(20);
		fields.add(telefono);		
	}

}
