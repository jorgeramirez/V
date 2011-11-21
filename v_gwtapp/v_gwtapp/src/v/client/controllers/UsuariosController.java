package v.client.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import v.client.AppConstants;
import v.client.AppViewport;
import v.client.Util;
import v.client.VType;
import v.client.VTypeValidator;
import v.client.rpc.AdministradorServiceAsync;
import v.client.widgets.CrudGrid;
import v.client.widgets.EditorForm;
import v.modelo.Caja;
import v.modelo.Usuario;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.binding.FieldBinding;
import com.extjs.gxt.ui.client.data.BaseListLoader;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.BeanModelReader;
import com.extjs.gxt.ui.client.data.FilterPagingLoadConfig;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.GridEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.form.SimpleComboValue;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.form.Validator;
import com.extjs.gxt.ui.client.widget.grid.CheckBoxSelectionModel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Controlador de ABM de Usuarios
 * 
 * @author Jorge Ramírez <jorgeramirez1990@gmail.com> 
 **/
public class UsuariosController extends AbstractController {
	private CrudGrid<Usuario> grid;
	private final AdministradorServiceAsync service = Registry.get(AppConstants.ADMINISTRADOR_SERVICE);

	public UsuariosController() {
		super(AppConstants.USUARIOS_LABEL);
	}

	@Override
	public void init() {

		RpcProxy<PagingLoadResult<Usuario>> proxy = new RpcProxy<PagingLoadResult<Usuario>>() {
			@Override
			public void load(Object loadConfig, AsyncCallback<PagingLoadResult<Usuario>> callback) {
				service.listarUsuarios((FilterPagingLoadConfig)loadConfig, callback);
			}
		};		

		// Creamos los ColumnConfigs
		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();

		// Selection Model para el grid
		CheckBoxSelectionModel<BeanModel> cbsm = new CheckBoxSelectionModel<BeanModel>();
		columns.add(cbsm.getColumn());

		// username
		ColumnConfig column = new ColumnConfig("username", "Username", 100);
		columns.add(column);

		// rol
		column = new ColumnConfig("rol", "Rol", 100);
		columns.add(column);

		// cedula
		column = new ColumnConfig("cedula", "Cédula", 100);
		columns.add(column);

		// nro. de caja field.
		ColumnConfig nroCajaColumn = new ColumnConfig("nroCaja", "Nro. de Caja", 100);
		nroCajaColumn.setRenderer(new GridCellRenderer<BeanModel>() {

			@Override
			public Object render(BeanModel model, String property,
					ColumnData config, int rowIndex, int colIndex,
					ListStore<BeanModel> store, Grid<BeanModel> grid) {
				Usuario u = (Usuario)model.getBean();
				if(u.getRol().equals(AppConstants.CAJERO_ROL)){
					return u.getCaja().getNumeroCaja();
				}
				return null;
			}
		});
		columns.add(nroCajaColumn);

		// nombre
		column = new ColumnConfig("nombre", "Nombre", 100);
		columns.add(column);

		// apellido
		column = new ColumnConfig("apellido", "Apellido", 100);
		columns.add(column);

		// direccion
		column = new ColumnConfig("direccion", "Dirección", 200);
		columns.add(column);

		// telefono
		column = new ColumnConfig("telefono", "Teléfono", 100);
		columns.add(column);

		// email
		column = new ColumnConfig("email", "Email", 200);
		columns.add(column);

		ColumnModel cm = new ColumnModel(columns);

		// establecemos los filtros
		HashMap<String, AppConstants.Filtros> fc = new HashMap<String, AppConstants.Filtros>();
		fc.put("username", AppConstants.Filtros.STRING_FILTER);
		fc.put("cedula", AppConstants.Filtros.STRING_FILTER);
		fc.put("rol", AppConstants.Filtros.STRING_FILTER);

		// creamos el CrudGrid para Usuarios
		grid = new CrudGrid<Usuario>("ABM Usuarios", cm, fc, proxy, cbsm);
		bindHandlers();

		LayoutContainer cp = (LayoutContainer)Registry.get(AppViewport.CENTER_REGION);
		cp.add(grid);
		cp.layout();

	}

	/**
	 * Método que se encarga de construir el {@link EditorForm} para {@link Usuario}
	 **/
	private EditorForm buildEditorForm(boolean create) {
		List<Field<?>> fields = new ArrayList<Field<?>>();
		List<FieldBinding> bindings = new ArrayList<FieldBinding>();


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

		return new EditorForm("Usuario", fields, bindings, 500, 370);
	}

	/**
	 * Asocia a cada evento con su respectivo handler definido
	 * en el controlador.
	 **/
	private void bindHandlers() {
		//Asociamos a cada botón con su respectivo handler del controlador
		grid.getAddButton().addSelectionListener(new SelectionListener<ButtonEvent>() {

			@Override
			public void componentSelected(ButtonEvent ce) {
				onAddClicked();
			}
		});

		grid.getDeleteButton().addSelectionListener(new SelectionListener<ButtonEvent>() {

			@Override
			public void componentSelected(ButtonEvent ce) {
				onDeleteClicked();
			}
		});

		/** 
		 * Agregamos el listener para el evento RowDoubleClick.
		 **/
		grid.addListener(Events.Render, new Listener<BaseEvent>() {

			@Override
			public void handleEvent(BaseEvent be) {
				grid.getGrid().addListener(Events.RowDoubleClick, new Listener<BaseEvent>() {

					@SuppressWarnings("unchecked")
					@Override
					public void handleEvent(BaseEvent be) {
						onRowDoubleClicked((GridEvent<BeanModel>)be);
					}
				});
			}
		});
	}

	/**
	 * Handler para el {@link Events.RowDoubleClick} del {@link Grid}
	 * 
	 * @param GridEvent<BeanModel> ge: el evento del Grid.
	 **/
	private void onRowDoubleClicked(final GridEvent<BeanModel> ge){ 
		final EditorForm form = buildEditorForm(false);

		/**
		 * Luego del rendering hacemos el binding del Modelo seleccionado
		 * Tambien hacemos el bind de los handlers para los botones
		 * del Form
		 **/
		form.addListener(Events.Render, new Listener<BaseEvent>() {

			@Override
			public void handleEvent(BaseEvent be) {
				form.getFormBindings().bind(ge.getModel());
				bindButtonsHandlers(form, false);
			}
		});
		form.show();	
	}

	/**
	 * Asocia los botones del {@link EditorForm} con sus respectivos handlers
	 * definidos en el {@link UsuariosController}
	 **/
	private void bindButtonsHandlers(final EditorForm form, final boolean create){
		form.getButtonById(Dialog.OK).addSelectionListener(new SelectionListener<ButtonEvent>() {

			@Override
			public void componentSelected(ButtonEvent ce) {
				if(form.getForm().isValid()){
					BeanModel u = (BeanModel)form.getFormBindings().getModel();
					if(create){
						saveUser(u);
					}else{
						updateUser(u);
					}
				}else{ // si hay campos invalidos mostramos de vuelta el form
					form.show();
				}
			}
		});		
	}

	/**
	 * Guarda el usuario creado
	 **/
	private void saveUser(BeanModel u){
		Usuario user = (Usuario)u.getBean();
		service.agregarUsuario(user, new AsyncCallback<Usuario>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onSuccess(Usuario user) {
				grid.getGrid().getStore().getLoader().load();
			}
		});
	}

	/**
	 * Actualiza el usuario
	 **/
	private void updateUser(final BeanModel u){
		Usuario user = (Usuario)u.getBean();
		service.modificarUsuario(user, new AsyncCallback<Void>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onSuccess(Void result) {
				grid.getGrid().getStore().getLoader().load();
			}
		});
	}

	/**
	 * Handler para el evento click del botón add del {@link CrudGrid}
	 **/
	private void onAddClicked() {
		final EditorForm form = buildEditorForm(true);
		/**
		 * Hacemos el bind de los handlers para los botones
		 * del Form
		 **/		
		form.addListener(Events.Render, new Listener<BaseEvent>() {

			@Override
			public void handleEvent(BaseEvent be) {
				form.getFormBindings().bind(Util.createBeanModel(new Usuario()));
				bindButtonsHandlers(form, true);
			}
		});
		form.show();
	}

	/**
	 * Handler para el evento click del botón delete del {@link CrudGrid}
	 **/	
	private void onDeleteClicked() {
		List<BeanModel> selected = grid.getGrid().getSelectionModel().getSelectedItems();
		List<Usuario> users = new ArrayList<Usuario>();
		for(BeanModel s: selected){
			users.add((Usuario)s.getBean());
		}
		service.eliminarUsuarios(users, new AsyncCallback<Boolean>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(Boolean ok) {
				if(ok){
					MessageBox.info("OK", "Usuarios eliminados correctamente", null);
					grid.getGrid().getStore().getLoader().load();
				}else{
					MessageBox.alert("Error", "Algunos usuarios no pudieron eliminarse", null);
				}
			}
		
		});
	}

}
