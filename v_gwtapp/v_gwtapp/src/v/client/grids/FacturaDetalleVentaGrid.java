package v.client.grids;

import java.util.ArrayList;
import java.util.List;

import v.modelo.FacturaDetalleVenta;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.Style.SelectionMode;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.ColumnModelEvent;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.Store;
import com.extjs.gxt.ui.client.store.StoreEvent;
import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.grid.CellEditor;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.EditorGrid;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.grid.RowNumberer;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.i18n.client.NumberFormat;
import com.google.gwt.user.client.Element;

public class FacturaDetalleVentaGrid extends ContentPanel {


	private EditorGrid<BeanModel> gridDetalle;

	private ListStore<BeanModel> store;
	private ColumnModel cm;
	private String title = "Detalles de Venta";
	private Button add;
	private Button del;
	
	public FacturaDetalleVentaGrid(){

		this.setHeading(this.title);  
		this.setFrame(true);   
		this.setWidth(700);  
		this.setLayout(new FitLayout());
		
/*		ToolBar tb = new ToolBar();
		add = new Button();
		del = new Button();
		tb.add(add);
		tb.add(del);
		this.setTopComponent(tb);*/

	}
	
	
	protected FacturaDetalleVenta crearDetalle() { 
		//aca hay que crear un Facutura detalle a partir de los seleccionado en el grid de productos
		FacturaDetalleVenta fdv = new FacturaDetalleVenta();


		return fdv;  
	}  



	protected void doAutoHeight() {  
		if (gridDetalle.isViewReady()) {  
			gridDetalle.getView().getScroller().setStyleAttribute("overflowY", "hidden");  
			this.setHeight((gridDetalle.getView().getBody().isScrollableX() ? 19 : 0) + gridDetalle.el().getFrameWidth("tb")  
					+ gridDetalle.getView().getHeader().getHeight() + this.getFrameHeight()  
					+ gridDetalle.getView().getBody().firstChild().getHeight());  
		}  
	}  

	@Override  
	protected void onRender(Element parent, int index) {  
		super.onRender(parent, index);  

		setLayout(new FlowLayout(10));  
		RowNumberer r = new RowNumberer(); 
		List<ColumnConfig> configs = new ArrayList<ColumnConfig>();  
		configs.add(r);

		ColumnConfig column; 

		// código producto field
		column = new ColumnConfig("codProd", "Código", 100);
		column.setRenderer(new GridCellRenderer<BeanModel>() {

			@Override
			public Object render(BeanModel model, String property,
					ColumnData config, int rowIndex, int colIndex,
					ListStore<BeanModel> store, Grid<BeanModel> grid) {
				FacturaDetalleVenta fd = (FacturaDetalleVenta)model.getBean();

				return fd.getProducto().getCodigo();
			}
		});

		configs.add(column);

		// nombre de producto
		column = new ColumnConfig("nombProd", "Nombre Producto", 100);
		column.setRenderer(new GridCellRenderer<BeanModel>() {

			@Override
			public Object render(BeanModel model, String property,
					ColumnData config, int rowIndex, int colIndex,
					ListStore<BeanModel> store, Grid<BeanModel> grid) {
				FacturaDetalleVenta fd = (FacturaDetalleVenta)model.getBean();

				return fd.getProducto().getNombre();
			}
		});

		configs.add(column);

		// precio
		column = new ColumnConfig("precio", "Precio", 100);
		column.setRenderer(new GridCellRenderer<BeanModel>() {

			@Override
			public Object render(BeanModel model, String property,
					ColumnData config, int rowIndex, int colIndex,
					ListStore<BeanModel> store, Grid<BeanModel> grid) {
				FacturaDetalleVenta fd = (FacturaDetalleVenta)model.getBean();

				return fd.getProducto().getPrecioVenta();

			}
		});

		column = new ColumnConfig("cantidad", "Cantidad", 100);     
		column.setAlignment(HorizontalAlignment.RIGHT);  
		column.setNumberFormat(NumberFormat.getCurrencyFormat());  //cambiar para guarani, si hay
		column.setEditor(new CellEditor(new NumberField()));  

		configs.add(column);

		// subtotal
		column = new ColumnConfig("subtotal", "Subtotales", 100);
		column.setRenderer(new GridCellRenderer<BeanModel>() {

			@Override
			public Object render(BeanModel model, String property,
					ColumnData config, int rowIndex, int colIndex,
					ListStore<BeanModel> store, Grid<BeanModel> grid) {
				FacturaDetalleVenta fd = (FacturaDetalleVenta)model.getBean();

				return fd.getProducto().getPrecioVenta() * fd.getCantidad();

			}
		});

		configs.add(column);

		store = new ListStore<BeanModel>();  

		cm = new ColumnModel(configs);  

		gridDetalle = new EditorGrid<BeanModel>(store, cm);  
		gridDetalle.setAutoExpandColumn("nombre");  
		gridDetalle.setBorders(true);  
		gridDetalle.setStripeRows(true);
		gridDetalle.setColumnLines(true);

		//plugin de numeración
		gridDetalle.addPlugin(r);
		gridDetalle.getView().setForceFit(true);

		gridDetalle.getSelectionModel().setSelectionMode(SelectionMode.SINGLE); 
		this.add(gridDetalle);  


		ToolBar topToolBar= new ToolBar();  
		add = new Button("Agregar Detalle");  
		add.addSelectionListener(new SelectionListener<ButtonEvent>() {  

			@Override  
			public void componentSelected(ButtonEvent ce) {  

				gridDetalle.stopEditing();  
				//mostrar el grid de selección de productos

				//pasar el foco a la columna cantidad del nuevo producto seleccionado

				//store.insert(createPlant(), 0);  
				gridDetalle.startEditing(0, 0);  

			}  

		});  


		topToolBar.add(add);
		del = new Button("Remover Seleccionado", new SelectionListener<ButtonEvent>() {  
			@Override  
			public void componentSelected(ButtonEvent ce) {
				//arreglar para que borre el seleccionado
				gridDetalle.getStore().remove(gridDetalle.getSelectionModel().getSelectedItem());  
				/*
				if (gridDetalle.getStore().getCount() == 0) {  
					ce.<Component> getComponent().disable();  
				}
				*/
			}
		});

		//Controlamos enabled/disabled del botón delete del ToolBar
		gridDetalle.getSelectionModel().addSelectionChangedListener(new SelectionChangedListener<BeanModel>() {

			@Override
			public void selectionChanged(SelectionChangedEvent<BeanModel> se) {
				del.setEnabled(se.getSelection().size() > 0);
			}
		});
		
		topToolBar.add(del);

		this.setTopComponent(topToolBar);  
		this.setButtonAlign(HorizontalAlignment.CENTER);  
		this.addButton(new Button("Limpiar", new SelectionListener<ButtonEvent>() {  

			@Override  
			public void componentSelected(ButtonEvent ce) {  
				store.rejectChanges();  
			}  
		}));  

		this.addButton(new Button("Guardar", new SelectionListener<ButtonEvent>() {  

			@Override  
			public void componentSelected(ButtonEvent ce) {  
				store.commitChanges();  
			}  
		}));  

		add(this);  
		//store.insert(createPlant(), 0);  


		gridDetalle.addListener(Events.ViewReady, new Listener<ComponentEvent>() {  
			public void handleEvent(ComponentEvent be) {  
				gridDetalle.getStore().addListener(Store.Add, new Listener<StoreEvent<BeanModel>>() {  
					public void handleEvent(StoreEvent<BeanModel> be) {  
						doAutoHeight();  
					}  
				});  
				doAutoHeight();  
			}  
		});

		gridDetalle.addListener(Events.ColumnResize, new Listener<ComponentEvent>() {  
			public void handleEvent(ComponentEvent be) {  
				doAutoHeight();  
			}  
		}); 

		gridDetalle.getColumnModel().addListener(Events.HiddenChange, new Listener<ColumnModelEvent>() {  
			public void handleEvent(ColumnModelEvent be) {  
				doAutoHeight();  
			}  
		});  
	}  
}  

