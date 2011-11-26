package v.client.controllers;

import java.util.ArrayList;
import java.util.List;

import v.client.AppConstants;
import v.client.AppViewport;
import v.client.Util;
import v.client.forms.CajaEditorForm;
import v.client.grids.CajasCrudGrid;
import v.client.rpc.AdministradorServiceAsync;
import v.client.widgets.CrudGrid;
import v.client.widgets.EditorForm;
import v.modelo.Caja;

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
 * Controlador para ABM de Cajas
 * 
 * @author Jorge Ramírez <jorgeramirez1990@gmail.com>
 **/
public class CajasController extends AbstractController {
	private CrudGrid<Caja> grid;
	private final AdministradorServiceAsync service = Registry.get(AppConstants.ADMINISTRADOR_SERVICE);
	
	public CajasController() {
		super(AppConstants.CAJA_LABEL);
	}

	@Override
	public void init() {
		// creamos el CrudGrid para Usuarios
		grid = new CajasCrudGrid("ABM Cajas");
		bindHandlers();

		LayoutContainer cp = (LayoutContainer)Registry.get(AppViewport.CENTER_REGION);
		cp.add(grid);
		cp.layout();
	}

	/**
	 * Método que se encarga de construir el {@link CajaEditorForm}
	 **/
	private EditorForm buildEditorForm(boolean create) {
		return new CajaEditorForm("Caja", create, 400, 250);
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
	 * definidos en el {@link CajasController}
	 **/
	private void bindButtonsHandlers(final EditorForm form, final boolean create){
		form.getButtonById(Dialog.OK).addSelectionListener(new SelectionListener<ButtonEvent>() {

			@Override
			public void componentSelected(ButtonEvent ce) {
				if(form.getForm().isValid()){
					BeanModel c = (BeanModel)form.getFormBindings().getModel();
					if(create){
						saveCaja(c);
					}else{
						updateCaja(c);
					}
				}else{ // si hay campos invalidos mostramos de vuelta el form
					form.show();
				}
			}
		});		
	}

	/**
	 * Guarda la caja creada
	 **/
	private void saveCaja(BeanModel c){
		Caja cashBox = (Caja)c.getBean();
		service.agregarCaja(cashBox, new AsyncCallback<Caja>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onSuccess(Caja cashBox) {
				grid.getGrid().getStore().getLoader().load();
			}
		});
	}

	/**
	 * Actualiza la caja
	 **/
	private void updateCaja(final BeanModel c){
		Caja cashBox = (Caja)c.getBean();
		service.modificarCaja(cashBox, new AsyncCallback<Void>() {

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
				form.getFormBindings().bind(Util.createBeanModel(new Caja()));
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
		List<Caja> cashBoxes = new ArrayList<Caja>();
		for(BeanModel s: selected){
			cashBoxes.add((Caja)s.getBean());
		}
		service.eliminarCajas(cashBoxes, new AsyncCallback<Boolean>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(Boolean ok) {
				if(ok){
					MessageBox.info("OK", "Cajas eliminadas correctamente", null);
					grid.getGrid().getStore().getLoader().load();
				}else{
					MessageBox.alert("Error", "Algunas cajas no pudieron eliminarse", null);
				}
			}
		
		});
	}

}
