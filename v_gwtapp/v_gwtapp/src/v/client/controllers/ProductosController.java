package v.client.controllers;

import java.util.ArrayList;
import java.util.List;

import v.client.AppConstants;
import v.client.AppViewport;
import v.client.Util;
import v.client.forms.ProductoEditorForm;
import v.client.grids.ProductosCrudGrid;
import v.client.rpc.CompradorServiceAsync;
import v.client.widgets.CrudGrid;
import v.client.widgets.EditorForm;
import v.modelo.Producto;

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
 * Controlador de ABM de Productos
 * 
 * @author Jorge Ramírez <jorgeramirez1990@gmail.com> 
 **/
public class ProductosController extends AbstractController {
	private CrudGrid<Producto> grid;
	private final CompradorServiceAsync service = Registry.get(AppConstants.COMPRADOR_SERVICE);

	public ProductosController() {
		super(AppConstants.PRODUCTO_LABEL);
	}

	@Override
	public void init() {

		// creamos el CrudGrid para Usuarios
		grid = new ProductosCrudGrid("ABM Productos");
		bindHandlers();

		LayoutContainer cp = (LayoutContainer)Registry.get(AppViewport.CENTER_REGION);
		cp.add(grid);
		cp.layout();

	}

	/**
	 * Método que se encarga de construir el {@link ProductoEditorForm}
	 **/
	private EditorForm buildEditorForm(boolean create) {
		return new ProductoEditorForm("Producto", create, 500, 280);
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
						saveProduct(p);
					}else{
						updateProduct(p);
					}
				}else{ // si hay campos invalidos mostramos de vuelta el form
					form.show();
				}
			}
		});		
	}

	/**
	 * Guarda el producto creado
	 **/
	private void saveProduct(BeanModel p){
		Producto product = (Producto)p.getBean();
		service.agregarProducto(product, new AsyncCallback<Producto>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onSuccess(Producto product) {
				if(product == null){
					MessageBox.alert("Error", "No se pudo crear el producto", null);
				}else{
					grid.getGrid().getStore().getLoader().load();
				}
			}
		});
	}

	/**
	 * Actualiza el producto
	 **/
	private void updateProduct(final BeanModel p){
		Producto product = (Producto)p.getBean();
		service.modificarProducto(product, new AsyncCallback<Boolean>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
			}

			@Override
			public void onSuccess(Boolean ok) {
				if(ok){
					grid.getGrid().getStore().getLoader().load();
				}else{
					MessageBox.alert("Error", "No se pudo modificar el producto", null);
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
				Producto p = new Producto();
				p.setPorcentajeGanancia(0.0);
				p.setCosto(0.0);
				p.setCantidad(0);
				form.getFormBindings().bind(Util.createBeanModel(p));
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
		List<Producto> products = new ArrayList<Producto>();
		for(BeanModel s: selected){
			products.add((Producto)s.getBean());
		}
		service.eliminarProductos(products, new AsyncCallback<Boolean>() {

			@Override
			public void onFailure(Throwable caught) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onSuccess(Boolean ok) {
				if(ok){
					MessageBox.info("OK", "Productos eliminados correctamente", null);
					grid.getGrid().getStore().getLoader().load();
				}else{
					MessageBox.alert("Error", "Algunos productos no pudieron eliminarse", null);
				}
			}
		
		});
	}

}

