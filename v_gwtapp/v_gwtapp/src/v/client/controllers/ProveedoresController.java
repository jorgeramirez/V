package v.client.controllers;

import java.util.ArrayList;
import java.util.List;

import v.client.AppConstants;
import v.client.AppViewport;
import v.client.Util;
import v.client.forms.ProveedorEditorForm;
import v.client.grids.ProveedoresCrudGrid;
import v.client.rpc.CompradorServiceAsync;
import v.client.widgets.CrudGrid;
import v.client.widgets.EditorForm;
import v.modelo.Proveedor;

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
 * Controlador de ABM de Proveedores
 * 
 * @author Jorge Ramírez <jorgeramirez1990@gmail.com> 
 **/
public class ProveedoresController extends AbstractController {
	private CrudGrid<Proveedor> grid;
	private final CompradorServiceAsync service = Registry.get(AppConstants.COMPRADOR_SERVICE);
	
	public ProveedoresController() {
		super(AppConstants.PROVEEDOR_LABEL);
	}

	@Override
	public void init() {

		// creamos el CrudGrid para Proveedores
		grid = new ProveedoresCrudGrid("ABM Proveedores");
		bindHandlers();

		LayoutContainer cp = (LayoutContainer)Registry.get(AppViewport.CENTER_REGION);
		cp.add(grid);
		cp.layout();

	}

	/**
	 * Método que se encarga de construir el {@link ProveedorEditorForm}
	 **/
	private EditorForm buildEditorForm(boolean create) {
		return new ProveedorEditorForm("Proveedor", create, 500, 280);
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
	 * definidos en el {@link ProductosController}
	 **/
	private void bindButtonsHandlers(final EditorForm form, final boolean create){
		form.getButtonById(Dialog.OK).addSelectionListener(new SelectionListener<ButtonEvent>() {

			@Override
			public void componentSelected(ButtonEvent ce) {
				if(form.getForm().isValid()){
					BeanModel p = (BeanModel)form.getFormBindings().getModel();
					if(create){
						saveProvider(p);
					}else{
						updateProvider(p);
					}
				}else{ // si hay campos invalidos mostramos de vuelta el form
					form.show();
				}
			}
		});		
	}

	/**
	 * Guarda el proveedor creado
	 **/
	private void saveProvider(BeanModel p){
		Proveedor provider = (Proveedor)p.getBean();
		service.agregarProveedor(provider, new AsyncCallback<Proveedor>() {

			@Override
			public void onFailure(Throwable caught) {
				MessageBox.alert("Error en el Servidor", caught.getMessage(), null);
			}

			@Override
			public void onSuccess(Proveedor provider) {
				if(provider == null){
					MessageBox.alert("Error", "No se pudo crear el proveedor", null);
				}else{
					grid.getGrid().getStore().getLoader().load();
				}
			}
		});
	}

	/**
	 * Actualiza el proveedor
	 **/
	private void updateProvider(final BeanModel p){
		Proveedor provider = (Proveedor)p.getBean();
		service.modificarProveedor(provider, new AsyncCallback<Boolean>() {

			@Override
			public void onFailure(Throwable caught) {
				MessageBox.alert("Error en el Servidor", caught.getMessage(), null);
			}

			@Override
			public void onSuccess(Boolean ok) {
				if(ok){
					grid.getGrid().getStore().getLoader().load();
				}else{
					MessageBox.alert("Error", "No se pudo modificar el proveedor", null);
				}
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
				form.getFormBindings().bind(Util.createBeanModel(new Proveedor()));
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
		List<Proveedor> providers = new ArrayList<Proveedor>();
		for(BeanModel s: selected){
			providers.add((Proveedor)s.getBean());
		}
		service.eliminarProveedores(providers, new AsyncCallback<Boolean>() {

			@Override
			public void onFailure(Throwable caught) {
				MessageBox.alert("Error en el Servidor", caught.getMessage(), null);
				
			}

			@Override
			public void onSuccess(Boolean ok) {
				if(ok){
					MessageBox.info("OK", "Proveedores eliminados correctamente", null);
				}else{
					MessageBox.alert("Error", "Algunos proveedores no pudieron eliminarse", null);
				}
				grid.getGrid().getStore().getLoader().load();
			}
		
		});
	}
}