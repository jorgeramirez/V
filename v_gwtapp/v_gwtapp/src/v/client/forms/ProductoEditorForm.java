package v.client.forms;

import v.client.AppConstants;
import v.client.rpc.CompradorServiceAsync;
import v.client.widgets.EditorForm;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.Validator;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Define Formulario para Alta/Modificación de Productos
 * 
 * @author Jorge Ramírez <jorgeramirez1990@gmail.com>
 **/
public class ProductoEditorForm extends EditorForm {
	final CompradorServiceAsync service = (CompradorServiceAsync)Registry.get(AppConstants.COMPRADOR_SERVICE);

	public ProductoEditorForm(String title, boolean create, int width, int height) {
		super(title, create, width, height);
	}

	@Override
	public void build(boolean create) {
		
		// codigo
		final TextField<String> codigo = new TextField<String>();
		codigo.setEnabled(create);
		codigo.setName("codigo");
		codigo.setAllowBlank(false);
		codigo.setMaxLength(15);
		codigo.setFieldLabel("Código");
		fields.add(codigo);
		
		// nombre
		TextField<String> nombre = new TextField<String>();
		nombre.setName("nombre");
		nombre.setFieldLabel("Nombre");
		nombre.setAllowBlank(false);
		nombre.setMaxLength(50);
		fields.add(nombre);
		
		// costo
		NumberField costo = new NumberField();
		costo.setName("costo");
		costo.setFieldLabel("Costo");
		costo.setEnabled(false);
		costo.setPropertyEditorType(Double.class);
		fields.add(costo);
		
		// cantidad
		NumberField cantidad = new NumberField();
		cantidad.setName("cantidad");
		cantidad.setFieldLabel("Cantidad");
		cantidad.setEnabled(false);
		cantidad.setPropertyEditorType(Integer.class);
		fields.add(cantidad);
		
		// porcentaje ganancia
		NumberField porcentaje = new NumberField();
		porcentaje.setName("porcentajeGanancia");
		porcentaje.setFieldLabel("Porcentaje Ganancia");
		porcentaje.setPropertyEditorType(Double.class);
		porcentaje.setAllowBlank(false);
		porcentaje.setValidator(new Validator() {
			
			@Override
			public String validate(Field<?> field, String value) {
				Double percent = Double.parseDouble(value);
				return percent > 0 && percent <= 1 ? null: "El porcentaje de ganancia debe ser un número mayor a cero y menor o igual a uno";
			}
		});
		fields.add(porcentaje);
		
		if(create){
			costo.setValue(0.0);
			cantidad.setValue(0);
			porcentaje.setValue(0.0);

			codigo.setValidator(new Validator() {  // Validator para codigo de producto

				@Override
				public String validate(Field<?> field, final String value) {
					service.existeCodigoProducto(value, new AsyncCallback<Boolean>(){

						@Override
						public void onFailure(Throwable caught) {
							// ignored
						}

						@Override
						public void onSuccess(Boolean existe) {
							if(existe){
								codigo.markInvalid("El código " + value + " ya existe");
							}
						}

					});
					return null;
				}
			});
			
		}
		
	}

}
