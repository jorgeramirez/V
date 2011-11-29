package v.client.controllers;

import java.util.ArrayList;
import java.util.List;

import v.client.AppConstants;
import v.client.AppViewport;
import v.client.Util;
import v.client.forms.ClienteEditorForm;
import v.client.grids.ClientesCrudGrid;
import v.client.rpc.VendedorServiceAsync;
import v.client.widgets.CrudGrid;
import v.client.widgets.EditorForm;
import v.modelo.Cliente;

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

public class ClientesController extends AbstractController {

	private CrudGrid<Cliente> grid;
	private final VendedorServiceAsync service = Registry.get(AppConstants.VENDEDOR_SERVICE);
	
	public ClientesController() {
		super(AppConstants.CLIENTE_LABEL);
	}
	
	@Override
	public void init() {
	
		// creamos el CrudGrid para Clientes
		grid = new ClientesCrudGrid("ABM Clientes");
		bindHandlers();
	
		LayoutContainer cp = (LayoutContainer)Registry.get(AppViewport.CENTER_REGION);
		cp.add(grid);
		cp.layout();
	
	}
	
	/**
	 * Método que se encarga de construir el {@link UsarioEditorForm}
	 **/
	private EditorForm buildEditorForm(boolean create) {
		return new ClienteEditorForm("Cliente", create, 500, 370);
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
						saveClient(u);
					}else{
						updateClient(u);
					}
				}else{ // si hay campos invalidos mostramos de vuelta el form
					form.show();
				}
			}
		});		
	}
	
	/**
	 * Guarda el cliente creado
	 **/
	private void saveClient(BeanModel bm){
		Cliente cliente = (Cliente)bm.getBean();
		service.agregarCliente(cliente, new AsyncCallback<Cliente>() {
	
			@Override
			public void onFailure(Throwable caught) {
				MessageBox.alert("Error en el Servidor", caught.getMessage(), null);
			}
	
			@Override
			public void onSuccess(Cliente cliente) {
				if(cliente == null){
					MessageBox.alert("Error", "No se pudo crear el cliente", null);
				}else{
					grid.getGrid().getStore().getLoader().load();
				}
			}
		});
	}
	
	/**
	 * Actualiza el cliente
	 **/
	private void updateClient(final BeanModel bm){
		Cliente cliente = (Cliente)bm.getBean();
		service.modificarCliente(cliente, new AsyncCallback<Boolean>() {
	
			@Override
			public void onFailure(Throwable caught) {
				MessageBox.alert("Error en el Servidor", caught.getMessage(), null);
			}
	
			@Override
			public void onSuccess(Boolean ok) {
				if(!ok){
					MessageBox.alert("Error", "No se pudo modificar el cliente", null);
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
				form.getFormBindings().bind(Util.createBeanModel(new Cliente()));
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
		List<Cliente> clientes = new ArrayList<Cliente>();
		for(BeanModel s: selected){
			clientes.add((Cliente)s.getBean());
		}
		service.eliminarClientes(clientes, new AsyncCallback<Boolean>() {
	
			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}
	
			@Override
			public void onSuccess(Boolean ok) {
				if(ok){
					MessageBox.info("OK", "Clientes eliminados correctamente", null);
					grid.getGrid().getStore().getLoader().load();
				}else{
					MessageBox.alert("Error", "Algunos clientes no pudieron eliminarse", null);
				}
			}
		
		});
	}
}
