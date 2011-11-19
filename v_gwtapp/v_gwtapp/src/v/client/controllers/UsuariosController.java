package v.client.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.FilterPagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.form.ComboBox.TriggerAction;
import com.extjs.gxt.ui.client.widget.form.SimpleComboBox;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.grid.CellEditor;
import com.extjs.gxt.ui.client.widget.grid.CheckBoxSelectionModel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.grid.RowEditor;
import com.google.gwt.user.client.rpc.AsyncCallback;

import v.client.AppConstants;
import v.client.AppViewport;
import v.client.Util;
import v.client.VType;
import v.client.VTypeValidator;
import v.client.rpc.AdministradorServiceAsync;
import v.client.widgets.CrudGrid;
import v.client.widgets.CustomGrid;
import v.modelo.Usuario;

/**
 * Controlador de ABM de Usuarios
 * 
 * @author Jorge Ramírez <jorgeramirez1990@gmail.com> 
 **/
public class UsuariosController extends AbstractController {
	
	public UsuariosController() {
		super(AppConstants.USUARIOS_LABEL);
	}
	
	@Override
	public void init() {
		final AdministradorServiceAsync service = Registry.get(AppConstants.ADMINISTRADOR_SERVICE);
		RpcProxy<PagingLoadResult<Usuario>> proxy = new RpcProxy<PagingLoadResult<Usuario>>() {
			@Override
			public void load(Object loadConfig, AsyncCallback<PagingLoadResult<Usuario>> callback) {
				service.listarUsuarios((FilterPagingLoadConfig)loadConfig, callback);
			}
		};
		
		// ComboBox para el campo Rol.
		final SimpleComboBox<Object> combo = new SimpleComboBox<Object>();  
	    combo.setForceSelection(true);  
	    combo.setTriggerAction(TriggerAction.ALL);  
	    combo.add(AppConstants.ADMINISTRADOR_ROL);  
	    combo.add(AppConstants.CAJERO_ROL);  
	    combo.add(AppConstants.VENDEDOR_ROL);  
	    combo.add(AppConstants.COMPRADOR_ROL);
	    combo.setEditable(false);
	  
	    CellEditor rolEditor = Util.createComboBoxCellEditor(combo);
	    
	    // Creamos los ColumnConfigs
		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();

		// Selection Model para el grid
		CheckBoxSelectionModel<BeanModel> cbsm = new CheckBoxSelectionModel<BeanModel>();
		columns.add(cbsm.getColumn());
		
		// username field
		TextField<String> text = new TextField<String>();
		text.setAllowBlank(false);
		text.setMaxLength(25);
		ColumnConfig column = new ColumnConfig("username", "Username", 100);
		column.setEditor(new CellEditor(text));
		columns.add(column);
		
		// password field
		text = new TextField<String>();
		text.setAllowBlank(false);
		text.setMaxLength(64);
		text.setPassword(true);
		column = new ColumnConfig("password", "Password", 100);
		column.setEditor(new CellEditor(text));
		column.setRenderer(new GridCellRenderer<BeanModel>() {

			@Override
			public Object render(BeanModel model, String property,
					ColumnData config, int rowIndex, int colIndex,
					ListStore<BeanModel> store, Grid<BeanModel> grid) {
				return "**********";
			}
		});
		columns.add(column);		
		
		// rol field
		column = new ColumnConfig("rol", "Rol", 100);
		column.setEditor(rolEditor);
		columns.add(column);
		
		// cedula field
		text = new TextField<String>();
		text.setMaxLength(10);
		text.setValidator(new VTypeValidator(VType.NUMERIC));
		column = new ColumnConfig("cedula", "Cédula", 100);
		column.setEditor(new CellEditor(text));
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
		
		// Creamos el combobox para Nro. de Caja. El mismo trae los datos desde el server.
		
		final SimpleComboBox<Object> nroCajaCombo = new SimpleComboBox<Object>();
	    nroCajaCombo.setForceSelection(false);  
	    nroCajaCombo.setTriggerAction(TriggerAction.ALL);
		nroCajaCombo.setEditable(false);
	    
		service.listarNrosCaja(new AsyncCallback<List<Integer>>() {
			
			@Override
			public void onSuccess(List<Integer> result) {
				for(Integer nro: result){
					nroCajaCombo.add(nro);
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				// ignore
			}
		});
	    
		CellEditor nroCajaEditor = Util.createComboBoxCellEditor(nroCajaCombo);
		nroCajaColumn.setEditor(nroCajaEditor);
   
		columns.add(nroCajaColumn);
		
		// nombre field
		text = new TextField<String>();
		text.setMaxLength(50);
		text.setValidator(new VTypeValidator(VType.ALPHABET));
		column = new ColumnConfig("nombre", "Nombre", 100);
		column.setEditor(new CellEditor(text));
		columns.add(column);
		
		// apellido field
		text = new TextField<String>();
		text.setMaxLength(50);
		text.setValidator(new VTypeValidator(VType.ALPHABET));
		column = new ColumnConfig("apellido", "Apellido", 100);
		column.setEditor(new CellEditor(text));
		columns.add(column);

		// direccion field
		text = new TextField<String>();
		text.setMaxLength(70);		
		text.setValidator(new VTypeValidator(VType.ALPHABET));
		column = new ColumnConfig("direccion", "Dirección", 200);
		column.setEditor(new CellEditor(text));
		columns.add(column);

		// telefono field
		text = new TextField<String>();
		text.setMaxLength(20);
		text.setValidator(new VTypeValidator(VType.ALPHANUMERIC));
		column = new ColumnConfig("telefono", "Teléfono", 100);
		column.setEditor(new CellEditor(text));
		columns.add(column);
		
		// email field
		text = new TextField<String>();
		text.setMaxLength(100);
		text.setValidator(new VTypeValidator(VType.EMAIL));
		column = new ColumnConfig("email", "Email", 110);
		column.setEditor(new CellEditor(text));
		columns.add(column);
		
		ColumnModel cm = new ColumnModel(columns);
		
		// establecemos los filtros
		HashMap<String, AppConstants.Filtros> fc = new HashMap<String, AppConstants.Filtros>();
		fc.put("username", AppConstants.Filtros.STRING_FILTER);
		fc.put("cedula", AppConstants.Filtros.STRING_FILTER);
		fc.put("rol", AppConstants.Filtros.STRING_FILTER);
		
		CrudGrid<Usuario> usuariosGrid = new CrudGrid<Usuario>("ABM Usuarios", cm, fc, proxy, cbsm);
		usuariosGrid.setUseRowEditor(true); // indicamos que vamos usar el RowEditor
		
		
		bindHandlers(usuariosGrid);

		LayoutContainer cp = (LayoutContainer)Registry.get(AppViewport.CENTER_REGION);
		cp.add(usuariosGrid);
		cp.layout();

	}
	
	/**
	 * Asociamos a cada elemento de {@link CrudGrid} con su respectivo Handler.
	 **/
	private void bindHandlers(final CrudGrid<Usuario> grid) {
		//Asociamos a cada botón con su respectivo handler del controlador
		grid.getAddButton().addSelectionListener(new SelectionListener<ButtonEvent>() {
			
			@Override
			public void componentSelected(ButtonEvent ce) {
				onAddClicked(grid);
			}
		});

		grid.getDeleteButton().addSelectionListener(new SelectionListener<ButtonEvent>() {
			
			@Override
			public void componentSelected(ButtonEvent ce) {
				onDeleteClicked(grid);
			}
		});
		
		grid.getSaveChangesButton().addSelectionListener(new SelectionListener<ButtonEvent>() {

			@Override
			public void componentSelected(ButtonEvent ce) {
				onSaveChangesClicked(grid);
			}
		
		});
		
		grid.getDiscardChangesButton().addSelectionListener(new SelectionListener<ButtonEvent>() {

			@Override
			public void componentSelected(ButtonEvent ce) {
				onDiscardChangesClicked(grid);
			}
		});
	}

	/**
	 * Descarta las modificaciones realizadas.
	 **/
	private void onDiscardChangesClicked(CrudGrid<Usuario> grid) {
		grid.getSaveChangesButton().setEnabled(false);
		grid.getDiscardChangesButton().setEnabled(false);
		grid.getGrid().getStore().rejectChanges();
	}

	private void onSaveChangesClicked(CrudGrid<Usuario> grid) {
		// TODO
		grid.getSaveChangesButton().setEnabled(false);
		grid.getDiscardChangesButton().setEnabled(false);
	}

	/**
	 * Handler para el evento click del botón add del {@link CustomGrid}
	 **/
	private void onAddClicked(CrudGrid<Usuario> grid) {
		BeanModel user = Util.createBeanModel(new Usuario());
		user.set("username", "newUser");
		user.set("password", "pass");
		user.set("rol", "vendedor");
		user.set("cedula", "123456");
		user.set("nombre", "Nombre");
		user.set("apellido", "Apellido");
		user.set("direccion", "mi casa");
		user.set("telefono", "123456");
		user.set("email", "nn@gmail.com");
		RowEditor<BeanModel> re = grid.getRowEditor();
		re.stopEditing(false);
		ListStore<BeanModel> st = grid.getGrid().getStore();
		st.insert(user, 0);
		re.startEditing(st.indexOf(user), true);
	}

	/**
	 * Handler para el evento click del botón delete del {@link CustomGrid}
	 **/	
	private void onDeleteClicked(CrudGrid<Usuario> grid) {
		System.out.println("Delete Clicked");
	}

}
