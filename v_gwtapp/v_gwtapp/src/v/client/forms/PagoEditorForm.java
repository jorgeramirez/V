package v.client.forms;

import v.client.widgets.EditorForm;
import v.modelo.Caja;
import v.modelo.FacturaVenta;
import v.modelo.Usuario;

import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.PropertyEditor;
import com.extjs.gxt.ui.client.widget.form.TextField;

public class PagoEditorForm extends EditorForm {

	public PagoEditorForm(String title, boolean create, int width, int height) {
		super(title, create, width, height);
	}

	@Override
	public void build(boolean create) {

		NumberField monto = new NumberField();
		monto.setFieldLabel("Monto");
		monto.setName("monto");
		monto.setAllowBlank(false);
		fields.add(monto);
		
		final TextField<BeanModel> factura = new TextField<BeanModel>();
		factura.setFieldLabel("Número de Factura");
		factura.setName("factura");
		factura.setEnabled(false);

		factura.setReadOnly(true);
		factura.setPropertyEditor(new PropertyEditor<BeanModel>() {

			@Override
			public String getStringValue(BeanModel value) {
				String nf = null;
				if(value != null){
					nf = ((FacturaVenta) value.getBean()).getNumeroFactura().toString();
				}
				return nf;
			}

			@Override
			public BeanModel convertStringValue(String value) {
				return factura.getValue();
			}
		
		});
		fields.add(factura);
		
		final TextField<BeanModel> saldo = new TextField<BeanModel>();
		saldo.setFieldLabel("Saldo");
		saldo.setName("factura");
		saldo.setEnabled(false);

		saldo.setReadOnly(true);
		saldo.setPropertyEditor(new PropertyEditor<BeanModel>() {

			@Override
			public String getStringValue(BeanModel value) {
				String nf = null;
				if(value != null){
					nf = ((FacturaVenta) value.getBean()).getSaldo().toString();
				}
				return nf;
			}

			@Override
			public BeanModel convertStringValue(String value) {
				return saldo.getValue();
			}
		
		});
		fields.add(saldo);
		
		final TextField<BeanModel> user = new TextField<BeanModel>();
		user.setFieldLabel("Cajero");
		user.setName("usuario");
		user.setEnabled(false);
		user.setPropertyEditor(new PropertyEditor<BeanModel>() {
			
			@Override
			public String getStringValue(BeanModel value) {
				String un = null;
				if(value != null){
					un = ((Usuario) value.getBean()).getUsername();
				}
				return un;
			}
			
			@Override
			public BeanModel convertStringValue(String value) {
				return user.getValue();
			}
		});
		fields.add(user);
		
		final TextField<BeanModel> cashBox = new TextField<BeanModel>();
		cashBox.setFieldLabel("Número de Caja");
		cashBox.setName("caja");
		cashBox.setEnabled(false);
		cashBox.setPropertyEditor(new PropertyEditor<BeanModel>() {
			
			@Override
			public String getStringValue(BeanModel value) {
				String un = null;
				if(value != null){
					un = ((Caja) value.getBean()).getNumeroCaja().toString();
				}
				return un;
				
			}
			
			@Override
			public BeanModel convertStringValue(String value) {
				return cashBox.getValue();
			}
		});
		fields.add(cashBox);
				
	}

}
