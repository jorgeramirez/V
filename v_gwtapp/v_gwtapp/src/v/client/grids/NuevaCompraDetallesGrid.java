package v.client.grids;

import java.util.ArrayList;
import java.util.List;

import v.client.Util;
import v.client.dialogs.ListarProductosDialog;
import v.client.dialogs.RemoveFromStoreDialog;
import v.client.forms.CompraDetalleEditorForm;
import v.modelo.FacturaDetalleCompra;
import v.modelo.Producto;
import v.shared.model.FacturaDetalleCompraBeanModel;
import v.shared.model.ProductoBeanModel;

import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.grid.RowNumberer;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.user.client.Element;

/**
 * Define un {@link Grid} para Listado de Detalles 
 * para la nueva Compra
 * 
 * @author Jorge Ramírez <jorgeramirez1990@gmail.com>
 **/
public class NuevaCompraDetallesGrid extends ContentPanel {
	private Grid<BeanModel> grid;
	private ToolBar topToolBar;
	private ListStore<BeanModel> store;
	private String title = "Detalles Compra";
	private Button addProductButton;
	private Button removeButton;
	private Button removeAllButton;
	private ListarProductosDialog dialog;
	private RowNumberer rowNumber;
	
	public NuevaCompraDetallesGrid() {
		this.setHeading(this.title);	
		this.setupToolBars();
	}
	
	/**
	 * Inicializa el Top y Bottom ToolBar.
	 **/
	public void setupToolBars() {
		topToolBar = new ToolBar();
		
		//boton agregar producto
		addProductButton = new Button("Agregar Producto");
		addProductButton.setIconStyle("icon-add");
		topToolBar.add(addProductButton);
		
		//boton remover
		removeButton = new Button("Eliminar");
		removeButton.setIconStyle("icon-delete");
		topToolBar.add(removeButton);
		
		//boton remover todos
		removeAllButton = new Button("Limpiar", new SelectionListener<ButtonEvent>() {  

			@Override  
			public void componentSelected(ButtonEvent ce) {  
				store.removeAll();
			}  
		});
		removeAllButton.setIconStyle("clear_icon");		
		topToolBar.add(removeAllButton);
		
		dialog = new ListarProductosDialog();
		
		this.setTopComponent(topToolBar);
	}
	
	public void onRender(Element parent, int pos) {
		super.onRender(parent, pos);
		
		this.setLayout(new FitLayout());
		
		// creamos el grid
		ColumnModel cm = buildColumnModel();
		store = new ListStore<BeanModel>();
		grid = new Grid<BeanModel>(store, cm);
		grid.setBorders(false);
		grid.setStripeRows(true);
		grid.setColumnLines(true);
		grid.getView().setForceFit(true); 
		grid.addPlugin(rowNumber);
		
		removeButton.addSelectionListener(new SelectionListener<ButtonEvent>() {

			@Override
			public void componentSelected(ButtonEvent ce) {
				if(store.getCount() > 0){
					RemoveFromStoreDialog remove = new RemoveFromStoreDialog(store, "Remover Detalle de Compra");
					remove.show();
				}else{
					MessageBox.alert("Advertencia", "No existen detalles", null);
				}
			}
		});
		
		removeAllButton.addSelectionListener(new SelectionListener<ButtonEvent>() {
			
			@Override
			public void componentSelected(ButtonEvent ce) {
				grid.getStore().removeAll();	
			}
		});
		
		grid.addListener(Events.Render, new Listener<BaseEvent>() {

			@Override
			public void handleEvent(BaseEvent be) {
				//Controlamos enabled/disabled del botón clear del ToolBar
				grid.getSelectionModel().addSelectionChangedListener(new SelectionChangedListener<BeanModel>() {

					@Override
					public void selectionChanged(SelectionChangedEvent<BeanModel> se) {
						removeButton.setEnabled(se.getSelection().size() > 0);
					}
				});
				
			}
			
		});
		
		this.add(grid);

	}

	public ColumnModel buildColumnModel() {
		// Creamos los ColumnConfigs
		List<ColumnConfig> columns = new ArrayList<ColumnConfig>();

		rowNumber = new RowNumberer();
		columns.add(rowNumber);
		
		// producto
		ColumnConfig column = new ColumnConfig("producto.codigo", "Código de Producto", 100);
		column.setRenderer(new GridCellRenderer<BeanModel>() {

			@Override
			public Object render(BeanModel model, String property,
					ColumnData config, int rowIndex, int colIndex,
					ListStore<BeanModel> store, Grid<BeanModel> grid) {
				FacturaDetalleCompra fd = (FacturaDetalleCompra)model.getBean();
				return fd.getProducto().getCodigo();
			}
		});

		columns.add(column);

		// nombre de producto
		column = new ColumnConfig("producto.nombre", "Nombre de Producto", 100);
		column.setRenderer(new GridCellRenderer<BeanModel>() {

			@Override
			public Object render(BeanModel model, String property,
					ColumnData config, int rowIndex, int colIndex,
					ListStore<BeanModel> store, Grid<BeanModel> grid) {
				FacturaDetalleCompra fd = (FacturaDetalleCompra)model.getBean();

				return fd.getProducto().getNombre();
			}
		});

		columns.add(column);

		// precio
		column = new ColumnConfig("precio", "Precio", 100);
		column.setRenderer(new GridCellRenderer<BeanModel>() {

			@Override
			public Object render(BeanModel model, String property,
					ColumnData config, int rowIndex, int colIndex,
					ListStore<BeanModel> store, Grid<BeanModel> grid) {
				FacturaDetalleCompra fd = (FacturaDetalleCompra)model.getBean();
				return fd.getPrecio();

			}
		});
		columns.add(column);
		
		// cantidad
		column = new ColumnConfig("cantidad", "Cantidad", 50);     
		columns.add(column);

		// subtotal
		column = new ColumnConfig("subtotal", "Subtotales", 100);
		column.setRenderer(new GridCellRenderer<BeanModel>() {

			@Override
			public Object render(BeanModel model, String property,
					ColumnData config, int rowIndex, int colIndex,
					ListStore<BeanModel> store, Grid<BeanModel> grid) {
				FacturaDetalleCompra fd = (FacturaDetalleCompra)model.getBean();
				return fd.getPrecio() * fd.getCantidad();

			}
		});
		columns.add(column);

		return new ColumnModel(columns);
	}

	public Grid<BeanModel> getGrid() {
		return grid;
	}

	public Button getAddProductButton() {
		return addProductButton;
	}

	public void setGrid(Grid<BeanModel> grid) {
		this.grid = grid;
	}

	public void setAddProductButton(Button addProductButton) {
		this.addProductButton = addProductButton;
	}
	
	/**
	 * Muestra el dialog para seleccionar un producto
	 **/
	public void showProductsDialog() {

		dialog.addListener(Events.Render, new Listener<BaseEvent>() {

			@Override
			public void handleEvent(BaseEvent be) {
				dialog.getButtonById(ListarProductosDialog.CONFIRMAR).addSelectionListener(new SelectionListener<ButtonEvent>() {
					
					@Override
					public void componentSelected(ButtonEvent ce) {
						
						final BeanModel detail = buildDetailBeanModel(dialog.getGrid().getGrid().getSelectionModel().getSelectedItem());
						final CompraDetalleEditorForm editor = new CompraDetalleEditorForm("Editar Detalle", true, 500, 350);
						
						editor.addListener(Events.Render, new Listener<BaseEvent>() {

							@Override
							public void handleEvent(BaseEvent be) {
								editor.getFormBindings().bind(detail);

								editor.getButtonById(Dialog.OK).addSelectionListener(new SelectionListener<ButtonEvent>() {

									@Override
									public void componentSelected(ButtonEvent ce) {
										updateStore((BeanModel)editor.getFormBindings().getModel());
										dialog.hide();
									}
								});
							}
						});
						editor.show();
					}
				});					
			}
		});		
		
		dialog.show();
	}
	
	/**
	 * Crea un {@link FacturaDetalleCompraBeanModel} a partir del {@link ProductoBeanModel} 
	 **/
	private BeanModel buildDetailBeanModel(BeanModel productBeanModel){
		Producto product = (Producto)productBeanModel.getBean();
		FacturaDetalleCompra detail = new FacturaDetalleCompra();
		detail.setProducto(product);
		detail.setCantidad(1);
		detail.setPrecio(product.getCosto());
		return Util.createBeanModel(detail);
	}
	
	/**
	 * Actualiza el store insertando el {@link BeanModel} que representa un detalle
	 * En caso de existir ya un detalle con dicho producto, se reemplaza.
	 **/
	private void updateStore(BeanModel newDetail){
		List<BeanModel> dataSet = new ArrayList<BeanModel>();
		Producto newProduct = ((FacturaDetalleCompra) newDetail.getBean()).getProducto();
		for(BeanModel bean: grid.getStore().getModels()){ //recorremos el store de detalles
			Producto p = ((FacturaDetalleCompra) bean.getBean()).getProducto();
			if(p.getCodigo().compareTo(newProduct.getCodigo()) != 0){
				dataSet.add(bean);
			}
		}
		dataSet.add(newDetail);
		grid.getStore().removeAll();
		grid.getStore().add(dataSet);		
	}
	
	/**
	 * Obtiene los detalles de compra almacenados en el grid
	 **/
	public List<FacturaDetalleCompra> getDetalles() {
		if(grid.getStore().getCount() == 0){
			return null;
		}
		List<FacturaDetalleCompra> details = new ArrayList<FacturaDetalleCompra>();
		for(BeanModel bean: grid.getStore().getModels()){
			details.add((FacturaDetalleCompra)bean.getBean());
		}
		return details;
	}
}
