package v.client.controllers;

import java.util.ArrayList;
import java.util.List;

import v.client.AppConstants;
import v.client.AppViewport;
import v.client.Util;
import v.client.forms.UsuarioEditorForm;
import v.client.grids.UsuariosCrudGrid;
import v.client.rpc.AdministradorServiceAsync;
import v.client.widgets.CrudGrid;
import v.client.widgets.EditorForm;
import v.modelo.Usuario;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.GridEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.grid.Grid;
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

		// creamos el CrudGrid para Usuarios
		grid = new UsuariosCrudGrid("ABM Usuarios");
		bindHandlers();

		LayoutContainer cp = (LayoutContainer)Registry.get(AppViewport.CENTER_REGION);
		cp.add(grid);
		cp.layout();

	}

	/**
	 * Método que se encarga de construir el {@link UsarioEditorForm}
	 **/
	private EditorForm buildEditorForm(boolean create) {
		return new UsuarioEditorForm("Usuario", create, 500, 370);
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
				MessageBox.alert("Error en el Servidor", caught.getMessage(), null);
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
		service.modificarUsuario(user, new AsyncCallback<Boolean>() {

			@Override
			public void onFailure(Throwable caught) {
				MessageBox.alert("Error en el Servidor", caught.getMessage(), null);
			}

			@Override
			public void onSuccess(Boolean ok) {
				if(!ok){
					MessageBox.alert("Error", "No se pudo modificar el usuario", null);
				}
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
				MessageBox.alert("Error en el Servidor", caught.getMessage(), null);
				
			}

			@Override
			public void onSuccess(Boolean ok) {
				if(ok){
					MessageBox.info("OK", "Usuarios eliminados correctamente", null);
				}else{
					MessageBox.alert("Error", "Algunos usuarios no pudieron eliminarse", null);
				}
				grid.getGrid().getStore().getLoader().load();
			}
		
		});
	}

}