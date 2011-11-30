package v.client.forms;

import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.PropertyEditor;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.Validator;

import v.client.widgets.EditorForm;
import v.modelo.Producto;

/**
 * Define Formulario para modificacion de datos
 * de Detalle de Compra
 * 
 * @author Jorge Ramírez <jorgeramirez1990@gmail.com>
 **/
public class CompraDetalleEditorForm extends EditorForm {

	public CompraDetalleEditorForm(String title, boolean create, int width,
			int height) {
		super(title, create, width, height);
	}

	@Override
	public void build(boolean create) {
		
		// codigo
		final TextField<BeanModel> codigo = new TextField<BeanModel>();
		codigo.setEnabled(false);
		codigo.setName("producto");
		codigo.setEnabled(false);
		codigo.setFieldLabel("Código");
		codigo.setPropertyEditor(new PropertyEditor<BeanModel>() {

			@Override
			public String getStringValue(BeanModel value) {
				String nf = null;
				if(value != null){
					nf = ((Producto) value.getBean()).getCodigo();
				}
				return nf;
			}

			@Override
			public BeanModel convertStringValue(String value) {
				return codigo.getValue();
			}
		
		});
		fields.add(codigo);
		
		// nombre
		final TextField<BeanModel> nombre = new TextField<BeanModel>();
		nombre.setName("producto");
		nombre.setFieldLabel("Nombre");
		nombre.setEnabled(false);
		nombre.setReadOnly(true);
		nombre.setPropertyEditor(new PropertyEditor<BeanModel>() {

			@Override
			public String getStringValue(BeanModel value) {
				String nf = null;
				if(value != null){
					nf = ((Producto) value.getBean()).getNombre();
				}
				return nf;
			}

			@Override
			public BeanModel convertStringValue(String value) {
				return nombre.getValue();
			}
		
		});

		fields.add(nombre);
		
		// precio de compra
		NumberField precio = new NumberField();
		precio.setName("precio");
		precio.setFieldLabel("Precio");
		precio.setValidator(new Validator() {
			
			@Override
			public String validate(Field<?> field, String value) {
				return Double.parseDouble(value) > 0 ? null: "El precio debe ser mayor a cero";
			}
		});
		fields.add(precio);

		
		// cantidad
		NumberField cantidad = new NumberField();
		cantidad.setName("cantidad");
		cantidad.setFieldLabel("Cantidad");
		cantidad.setPropertyEditorType(Integer.class);
		cantidad.setValidator(new Validator() {
			
			@Override
			public String validate(Field<?> field, String value) {
				return Integer.parseInt(value) > 0 ? null: "La cantidad debe ser mayor a cero";
			}
		});		
		fields.add(cantidad);
		
	}

}
