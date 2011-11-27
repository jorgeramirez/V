package v.client.grids;

import java.util.ArrayList;
import java.util.List;

import v.client.Util;
import v.modelo.FacturaDetalleVenta;
import v.modelo.FacturaVenta;
import v.modelo.Producto;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.Style.SelectionMode;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.FilterPagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.ColumnModelEvent;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.event.WindowEvent;
import com.extjs.gxt.ui.client.event.WindowListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.Store;
import com.extjs.gxt.ui.client.store.StoreEvent;
import com.extjs.gxt.ui.client.widget.Component;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Window;
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
import com.google.gwt.user.client.rpc.AsyncCallback;

public class FacturaDetalleVentaGrid extends ContentPanel {

	private EditorGrid<BeanModel> gridDetalle;
	private ProductosGrid gridProductos;
	private ListStore<BeanModel> store;
	private ColumnModel cm;
	private String title = "Detalles de Venta";
	private Button add;
	private Button del;
	private FacturaVenta fv;
	private final Window window = new Window();
	private ToolBar topToolBar;
	private ToolBar bottomToolBar;

	public Window getWindow() {
		return window;
	}


	public FacturaDetalleVentaGrid(FacturaVenta v){
		this.fv = v;
		this.setHeading(this.title);  
		this.setFrame(true);
		
		//this.setWidth(700);  
		this.setLayout(new FitLayout());

		//creamos el grid de selección de productos
		gridProductos = new ProductosGrid() {
			@Override
			public RpcProxy<PagingLoadResult<Producto>> buildProxy() {
				return new RpcProxy<PagingLoadResult<Producto>>() {
					@Override
					public void load(Object loadConfig, AsyncCallback<PagingLoadResult<Producto>> callback) {
						service.listarProductosConExistencia((FilterPagingLoadConfig)loadConfig, callback);
					}
				};			
			}
		};
		
		//gridProductos.addListener(Events.Render, new Listener<BaseEvent>() {
		window.addListener(Events.Render, new Listener<BaseEvent>() {

			@Override
			public void handleEvent(BaseEvent be) {
				gridProductos.getGrid().getSelectionModel().setSelectionMode(SelectionMode.SINGLE); 
				
			}
			
		});
		
		topToolBar= new ToolBar();
		bottomToolBar = new ToolBar();
		this.setTopComponent(topToolBar);
		this.setBottomComponent(bottomToolBar);
	}


	protected void selectorProductos() {     

		//final Window window = new Window();  
		window.setSize(500, 300);  
		window.setPlain(true);  
		window.setModal(true);  
		window.setBlinkModal(true);  
		window.setHeading("Productos");  
		window.setLayout(new FitLayout());  
		window.addWindowListener(new WindowListener() {  
			@Override  
			public void windowHide(WindowEvent we) {  
				Button open = we.getWindow().getData("productos");  
				open.focus();  
			}  
		});  

		window.add(gridProductos);
		window.addButton(new Button("Ok", new SelectionListener<ButtonEvent>() {  
			@Override  
			public void componentSelected(ButtonEvent ce) {  

				store.insert(crearDetalle(gridProductos.getGrid().getSelectionModel().getSelectedItem()), 0); 
				gridDetalle.startEditing(0, 3);
				window.hide();  
			}  
		}));  

		window.setFocusWidget(window.getButtonBar().getItem(0));  



		add = new Button("Agregar Producto", new SelectionListener<ButtonEvent>() {  
			@Override  
			public void componentSelected(ButtonEvent ce) {  
				gridDetalle.stopEditing(); 
				window.show();  
			}  
		}); 


		//ToolBar topToolBar= new ToolBar();  

		window.setData("productos", add);  

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

		topToolBar.add(del);
		//this.setTopComponent(topToolBar);  

	}


	protected BeanModel crearDetalle(BeanModel p) { 
		//aca hay que crear un Facutura detalle a partir de los seleccionado en el grid de productos
		FacturaDetalleVenta fdv = new FacturaDetalleVenta();
		fdv.setCantidad(1);
		fdv.setCabecera(fv);
		fdv.setProducto((Producto) p.getBean());

		return Util.createBeanModel(fdv);  
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

		selectorProductos();

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
		//column.setNumberFormat(NumberFormat.getCurrencyFormat());  //cambiar para guarani, si hay
		NumberField nf = new NumberField();
		nf.setPropertyEditorType(Integer.class);
		column.setEditor(new CellEditor(nf));

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

		gridDetalle.addListener(Events.Render, new Listener<BaseEvent>() {

			@Override
			public void handleEvent(BaseEvent be) {
				gridDetalle.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
				//Controlamos enabled/disabled del botón delete del ToolBar
				gridDetalle.getSelectionModel().addSelectionChangedListener(new SelectionChangedListener<BeanModel>() {

					@Override
					public void selectionChanged(SelectionChangedEvent<BeanModel> se) {
						del.setEnabled(se.getSelection().size() > 0);
					}
				});
				
			}
			
		});
		
		this.add(gridDetalle);  
		
		this.setButtonAlign(HorizontalAlignment.CENTER);  
		bottomToolBar.add(new Button("Limpiar", new SelectionListener<ButtonEvent>() {  

			@Override  
			public void componentSelected(ButtonEvent ce) {  
				store.rejectChanges();  
			}  
		}));  

		bottomToolBar.add(new Button("Guardar", new SelectionListener<ButtonEvent>() {  

			@Override  
			public void componentSelected(ButtonEvent ce) {  
				store.commitChanges();  
			}  
		}));  

		//add(this);  

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

