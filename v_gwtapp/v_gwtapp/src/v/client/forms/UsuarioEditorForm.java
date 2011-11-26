package v.client.forms;

import v.client.AppConstants;
import v.client.VType;
import v.client.VTypeValidator;
import v.client.rpc.AdministradorServiceAsync;
import v.client.widgets.EditorForm;
import v.modelo.Caja;
import v.modelo.Usuario;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.binding.FieldBinding;
import com.extjs.gxt.ui.client.data.BaseListLoader;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.BeanModelReader;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.form.SimpleComboValue;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.Validator;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Define un Formulario para Alta/Modificación de {@link Usuario}
 * 
 * @author Jorge Ramírez <jorgeramirez1990@gmail.com>
 **/
public class UsuarioEditorForm extends EditorForm {

	public UsuarioEditorForm(String title, boolean create, int width, int height) {
		super(title, create, width, height);
	}

	@Override
	public void build(boolean create) {
		final AdministradorServiceAsync service = Registry.get(AppConstants.ADMINISTRADOR_SERVICE);
		
		// username field
		final TextField<String> username = new TextField<String>();
		username.setAllowBlank(false);
		username.setMaxLength(25);
		username.setFieldLabel("Username");
		username.setName("username");

		if(create){ // Comprobar que el nombre de usuario no existe todavía.
			username.setValidator(new Validator() {  // Validator para username

				@Override
				public String validate(Field<?> field, final String value) {
					service.existeUsername(value, new AsyncCallback<Boolean>(){

						@Override
						public void onFailure(Throwable caught) {
							// ignored
						}

						@Override
						public void onSuccess(Boolean existe) {
							if(existe){
								username.markInvalid("El username " + value + " ya existe");
							}
						}

					});
					return null;
				}
			});
		}else{
			username.setEnabled(false);
		}
		fields.add(username);

		// password field
		final TextField<String> password = new TextField<String>();
		password.setAllowBlank(false);
		password.setMaxLength(64);
		password.setPassword(true);
		password.setFieldLabel("Contraseña");
		password.setName("password");
		fields.add(password);

		// confirmar password field
		final TextField<String> confirm = new TextField<String>();
		confirm.setAllowBlank(!create);
		confirm.setMaxLength(64);
		confirm.setPassword(true);
		confirm.setFieldLabel("Confirmar");
		confirm.setName("confirmar");

		confirm.setValidator(new Validator() { // validator para confirmación de password

			@Override
			public String validate(Field<?> field, String value) {
				return value == password.getValue() ? null : "Confirmación de Password incorrecta";
			}
		});

		fields.add(confirm);

		// Creamos el combobox para Caja. El mismo trae los datos desde el server.

		RpcProxy<ListLoadResult<Caja>> cashBoxesProxy = new RpcProxy<ListLoadResult<Caja>>() {

			@Override
			protected void load(Object loadConfig, AsyncCallback<ListLoadResult<Caja>> callback) {
				service.listarCajas(callback);
			}

		};

		final BaseListLoader<ListLoadResult<Caja>> loader = new BaseListLoader<ListLoadResult<Caja>>(cashBoxesProxy, new BeanModelReader());  
		ListStore<BeanModel> cashBoxesStore = new ListStore<BeanModel>(loader);
		final ComboBox<BeanModel> cajaCombo = new ComboBox<BeanModel>();
		cajaCombo.setStore(cashBoxesStore);
		cajaCombo.setForceSelection(false);  
		cajaCombo.setTriggerAction(TriggerAction.ALL);
		cajaCombo.setEditable(false);
		cajaCombo.setVisible(false);
		cajaCombo.setFieldLabel("Caja");
		cajaCombo.setName("caja");
		cajaCombo.setDisplayField("numeroCaja");

		// binding para el nro de caja
		FieldBinding cajaBinding = new FieldBinding(cajaCombo, "caja");
		bindings.add(cajaBinding);

		// ComboBox para el campo Rol.
		final SimpleComboBox<String> rolCombo = new SimpleComboBox<String>();  
		rolCombo.setForceSelection(true);
		rolCombo.setAllowBlank(false);
		rolCombo.setTriggerAction(TriggerAction.ALL);  
		rolCombo.add(AppConstants.ADMINISTRADOR_ROL);  
		rolCombo.add(AppConstants.CAJERO_ROL);  
		rolCombo.add(AppConstants.VENDEDOR_ROL);  
		rolCombo.add(AppConstants.COMPRADOR_ROL);
		rolCombo.setEditable(false);
		rolCombo.setFieldLabel("Rol");
		rolCombo.setName("rol");

		if(!create){
			rolCombo.setEnabled(false);
		}

		// binding para el campo rol
		FieldBinding rolComboBinding = new FieldBinding(rolCombo, "rol") {
			@Override
			protected Object onConvertFieldValue(Object value) {
				return rolCombo.getSimpleValue();
			}

			@Override
			protected Object onConvertModelValue(Object value) {
				String name = (String)value;
				return rolCombo.findModel(name);
			}
		}; 
		bindings.add(rolComboBinding);

		// controla la visibilidad del combobox Nro de Caja.
		rolCombo.addSelectionChangedListener(new SelectionChangedListener<SimpleComboValue<String>>() {

			@Override
			public void selectionChanged(SelectionChangedEvent<SimpleComboValue<String>> se) {
				cajaCombo.setVisible(false);
				if(se.getSelectedItem().getValue().equals(AppConstants.CAJERO_ROL)){
					cajaCombo.setVisible(true);
				}
			}
		});

		// rol field
		fields.add(rolCombo);


		// cedula field
		TextField<String> text = new TextField<String>();
		text.setAllowBlank(false);
		text.setMaxLength(10);
		text.setValidator(new VTypeValidator(VType.NUMERIC));
		text.setFieldLabel("Cédula");
		text.setName("cedula");
		fields.add(text);


		// nro. de caja field.
		fields.add(cajaCombo);


		// nombre field
		text = new TextField<String>();
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


		// email field
		text = new TextField<String>();
		text.setMaxLength(100);
		text.setValidator(new VTypeValidator(VType.EMAIL));
		text.setFieldLabel("Email");
		text.setName("email");
		text.setAllowBlank(false);
		fields.add(text);		
	}

}
