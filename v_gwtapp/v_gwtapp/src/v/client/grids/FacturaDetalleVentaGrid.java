package v.client.grids;

import java.util.ArrayList;
import java.util.List;

import v.client.Util;
import v.modelo.FacturaDetalleVenta;
import v.modelo.FacturaVenta;
import v.modelo.Producto;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.Style.SelectionMode;
import com.extjs.gxt.ui.client.binding.FieldBinding;
import com.extjs.gxt.ui.client.binding.FormBinding;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.FilterPagingLoadConfig;
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
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.PropertyEditor;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnData;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.GridCellRenderer;
import com.extjs.gxt.ui.client.widget.grid.RowNumberer;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class FacturaDetalleVentaGrid extends ContentPanel {

	private Grid<BeanModel> gridDetalle;
	private ProductosGrid gridProductos;
	private ListStore<BeanModel> store;
	private ColumnModel cm;
	private String title = "Detalles de Venta";
	private Button add;
	private Button del;
	private final Window window = new Window();
	private ToolBar topToolBar;
	private ToolBar bottomToolBar;
	private Button clear;
	private int selected;

	
	public Window getWindow() {
		return window;
	}


	public FacturaDetalleVentaGrid(){
		this.setHeading(this.title);  
		this.setFrame(true);
		
		

		RowNumberer r = new RowNumberer(); 
		List<ColumnConfig> configs = new ArrayList<ColumnConfig>();  
		configs.add(r);
		
		ColumnConfig column; 

//		NumberField nf;
//		CellEditor ce;
		// código producto field
		column = new ColumnConfig("producto.codigo", "Código", 50);

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
		column = new ColumnConfig("producto.nombre", "Nombre Producto", 100);
		//tf = new TextField<String>();
		//ce = new CellEditor(tf);

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
		column = new ColumnConfig("precio", "Precio", 80);
		column.setAlignment(HorizontalAlignment.RIGHT);
//		nf = new NumberField();
//		nf.setPropertyEditorType(Double.class);
//		ce = new CellEditor(nf);
//		//ce.setEnabled(false);
//		column.setEditor(ce);
		column.setRenderer(new GridCellRenderer<BeanModel>() {

			@Override
			public Object render(BeanModel model, String property,
					ColumnData config, int rowIndex, int colIndex,
					ListStore<BeanModel> store, Grid<BeanModel> grid) {
				FacturaDetalleVenta fd = (FacturaDetalleVenta)model.getBean();
				return fd.getProducto().getPrecioVenta();

			}
		});
		configs.add(column);
		
		column = new ColumnConfig("cantidad", "Cantidad", 50);     
		column.setAlignment(HorizontalAlignment.RIGHT);  
//		//column.setNumberFormat(NumberFormat.getCurrencyFormat());  //cambiar para guarani, si hay
//		nf = new NumberField();
//		nf.setPropertyEditorType(Integer.class);
//		ce = new CellEditor(nf);
//		column.setEditor(ce);
		
/*		column.addListener(Events.OnBlur, new Listener<BaseEvent>() {

			@SuppressWarnings("unchecked")
			@Override
			public void handleEvent(BaseEvent be) {
				GridEvent<BeanModel> gw = (GridEvent<BeanModel>)be;
				BeanModel fd = gw.getModel();
			
				FacturaDetalleVenta fdv = (FacturaDetalleVenta) fd.getBean();
				if (fdv.getProducto().getCantidad() < fdv.getCantidad()) {
					MessageBox.alert("Cantidad de Producto", "Sólo existen " +  fdv.getProducto().getCantidad().toString()
							+ "unidades", null);
					gridDetalle.startEditing(gw.getRowIndex(), 3);
				} else {
					fd.set("subtotal", (Integer) fd.get("cantidad") * (Double) fd.get("precio"));
					fd.set("cantidad", fd.get("cantidad"));
					store.commitChanges();
				}
				
			}
			
		});*/
		
		column.setRenderer(new GridCellRenderer<BeanModel>() {

			@Override
			public Object render(BeanModel model, String property,
					ColumnData config, int rowIndex, int colIndex,
					ListStore<BeanModel> store, Grid<BeanModel> grid) {
				FacturaDetalleVenta fd = (FacturaDetalleVenta)model.getBean();

				return fd.getCantidad();

			}
		});
		configs.add(column);

		// subtotal
		column = new ColumnConfig("subtotal", "Subtotales", 80);
		column.setAlignment(HorizontalAlignment.RIGHT);
//		nf = new NumberField();
//		nf.setPropertyEditorType(Double.class);
//		ce = new CellEditor(nf);
//		//ce.setEnabled(false);
//		column.setEditor(ce);
		
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
		
		cm = new ColumnModel(configs); 
		
	    store = new ListStore<BeanModel>();
		gridDetalle = new Grid<BeanModel>(store, cm);
		gridDetalle.setAutoExpandColumn("producto.nombre");
		gridDetalle.addPlugin(r);
		
		
		//gridDetalle.disableTextSelection(true);
		gridDetalle.setTrackMouseOver(true);
		
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
		

		window.addListener(Events.Render, new Listener<BaseEvent>() {

			@Override
			public void handleEvent(BaseEvent be) {
				gridProductos.getGrid().getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
				
				gridProductos.getGrid().addListener(Events.RowDoubleClick, new Listener<BaseEvent>() {

					@SuppressWarnings("unchecked")
					@Override
					public void handleEvent(BaseEvent be) {
						GridEvent<BeanModel> bm = (GridEvent<BeanModel>) be;
						BeanModel d = crearDetalle(gridProductos.getGrid().getSelectionModel().getSelectedItem());
						//gridDetalle.getStore().add(d);
						seleccionDetalle(d);
						//store.add(d);
						//window.hide();
					}
				});
				
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
		//window.setHeading("Productos");  
		window.setLayout(new FitLayout());  
/*		window.addWindowListener(new WindowListener() {  
			@Override  
			public void windowHide(WindowEvent we) {  
				Button open = we.getWindow().getData("productos");  
				open.focus();  
			}  
		}); */ 

		window.add(gridProductos);
		window.addButton(new Button("Ok", new SelectionListener<ButtonEvent>() {  
			@Override  
			public void componentSelected(ButtonEvent ce) {
				BeanModel d = crearDetalle(gridProductos.getGrid().getSelectionModel().getSelectedItem());
				seleccionDetalle(d);
				//store.add(d);
				
//				if (gridDetalle.getStore().getModels().isEmpty()){
//					store.insert(d, 0);
//
//				} else {
//					for (ColumnConfig c : gridDetalle.getColumnModel().getColumns()) {
//						c.getEditor().setEnabled(true);
//					}
//					store.insert(d, 0);
//					for (ColumnConfig c : gridDetalle.getColumnModel().getColumns()) {
//						c.getEditor().setEnabled(false);
//					}		
//				}
//				gridDetalle.startEditing(0, 4);
				//window.hide();  
			}  
		}));  


		//window.setFocusWidget(window.getButtonBar().getItem(0));  

		add = new Button("Agregar Producto", new SelectionListener<ButtonEvent>() {  
			@Override  
			public void componentSelected(ButtonEvent ce) {
				window.show();  
			}  
		});
		
		add.setIconStyle("icon-add");

		//ToolBar topToolBar= new ToolBar();  

		window.setData("productos", add);  

		topToolBar.add(add);
		del = new Button("Remover Seleccionado", new SelectionListener<ButtonEvent>() {  
			@Override  
			public void componentSelected(ButtonEvent ce) {
				//arreglar para que borre el seleccionado
				//gridDetalle.getActiveEditor();
				//gridDetalle.startEditing(0, 4);

				//MessageBox.alert("esto es una prueba", "preba", null);
				gridDetalle.getStore().remove(gridDetalle.getSelectionModel().getSelectedItem());  

			}
		});
		
		del.setIconStyle("icon-delete");

		topToolBar.add(del);
		//this.setTopComponent(topToolBar);  

	}


	protected BeanModel crearDetalle(BeanModel p) { 
		//aca hay que crear un Facutura detalle a partir de los seleccionado en el grid de productos
		FacturaDetalleVenta fdv = new FacturaDetalleVenta();
		fdv.setCantidad(1);
		fdv.setPrecio(((Producto)p.getBean()).getPrecioVenta());
		//fdv.setCabecera(fv);
		fdv.setProducto((Producto) p.getBean());

		return Util.createBeanModel(fdv);  
	}  

	/*protected void doAutoHeight() {  
		if (gridDetalle.isViewReady()) {  
			gridDetalle.getView().getScroller().setStyleAttribute("overflowY", "hidden");  
			this.setHeight((gridDetalle.getView().getBody().isScrollableX() ? 19 : 0) + gridDetalle.el().getFrameWidth("tb")  
					+ gridDetalle.getView().getHeader().getHeight() + this.getFrameHeight()  
					+ gridDetalle.getView().getBody().firstChild().getHeight());  
		}  
	} */ 

	@Override  
	protected void onRender(Element parent, int index) {  
		super.onRender(parent, index);  

		selectorProductos();
		
		gridDetalle.setBorders(true);  
		gridDetalle.setStripeRows(true);
		gridDetalle.setColumnLines(true);
		gridDetalle.getSelectionModel().setSelectionMode(SelectionMode.SIMPLE);
		//plugin de numeración
		//gridDetalle.addPlugin(r);
		gridDetalle.getView().setForceFit(true);

		gridDetalle.addListener(Events.Render, new Listener<BaseEvent>() {

			@Override
			public void handleEvent(BaseEvent be) {
				//Controlamos enabled/disabled del botón delete del ToolBar
				gridDetalle.getSelectionModel().addSelectionChangedListener(new SelectionChangedListener<BeanModel>() {

					@Override
					public void selectionChanged(SelectionChangedEvent<BeanModel> se) {
						clear.setEnabled(se.getSelection().size() > 0);
					}
				});
				
			}
			
		});
		
/*		gridDetalle.addListener(Events.RowDoubleClick, new Listener<BaseEvent>() {

			@SuppressWarnings("unchecked")
			@Override
			public void handleEvent(BaseEvent b) {
				GridEvent<BeanModel> bm = (GridEvent<BeanModel>) b;
				//bm.getGrid().get
				//store.remove(bm.getModel());
				selected = store.indexOf(bm.getModel());
				seleccionDetalle(bm.getModel());
			}
		});	*/	
		
		this.add(gridDetalle);  
		
		this.setButtonAlign(HorizontalAlignment.CENTER); 
		clear = new Button("Limpiar", new SelectionListener<ButtonEvent>() {  

			@Override  
			public void componentSelected(ButtonEvent ce) {  
				store.removeAll();
			}  
		});
		
	
		clear.setIconStyle("clear_icon");
				
		bottomToolBar.add(clear);
		
		
		
		/*  
		 esto comente, porque hace que el scroller del content panel no funcione.
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
		});*/  
		
		
	}
	
	public List<BeanModel> guardarDetalles() {
		//retorna la lista de detalles para guardar la factura
		//store.commitChanges();  
		return store.getModels();
	}
	


	private void seleccionDetalle(final BeanModel ge){ 
		final Dialog dialog = new Dialog();
		final FormPanel form = new FormPanel();
		final FormBinding formBindings = new FormBinding(form);

		dialog.setBodyBorder(false);
		dialog.setHeading("Detalle Venta");
		dialog.setLayout(new FitLayout());
		dialog.setHideOnButtonClick(false);
		dialog.okText = "Guardar";
		dialog.cancelText = "Cancelar";
		dialog.setButtons(Dialog.OKCANCEL);
		dialog.setModal(true);
		dialog.setSize(400, 250);
		dialog.setClosable(false);
		
		List<FieldBinding> bindings;
		List<Field<?>> fields;
		fields = new ArrayList<Field<?>>();
		bindings = new ArrayList<FieldBinding>();
		
		// codigo
		final TextField<BeanModel> codigo = new TextField<BeanModel>();
		codigo.setEnabled(false);
		codigo.setName("producto");
		codigo.setEnabled(false);
		codigo.setFieldLabel("Código");
		codigo.setPropertyEditor(new PropertyEditor<BeanModel>() {

			@Override
			public String getStringValue(BeanModel value) {
				String nf = null;
				if(value != null){
					nf = ((Producto) value.getBean()).getCodigo();
				}
				return nf;
			}

			@Override
			public BeanModel convertStringValue(String value) {
				return codigo.getValue();
			}
		
		});
		fields.add(codigo);
		
		// nombre
		final TextField<BeanModel> nombre = new TextField<BeanModel>();
		nombre.setName("producto");
		nombre.setFieldLabel("Nombre");
		nombre.setEnabled(false);
		nombre.setReadOnly(true);
		nombre.setPropertyEditor(new PropertyEditor<BeanModel>() {

			@Override
			public String getStringValue(BeanModel value) {
				String nf = null;
				if(value != null){
					nf = ((Producto) value.getBean()).getNombre();
				}
				return nf;
			}

			@Override
			public BeanModel convertStringValue(String value) {
				return nombre.getValue();
			}
		
		});

		fields.add(nombre);
		
		// precio
		NumberField precio = new NumberField();
		precio.setName("precio");
		precio.setFieldLabel("Precio");
		precio.setEnabled(false);

		precio.setReadOnly(true);
		fields.add(precio);

		
		// cantidad
		NumberField cantidad = new NumberField();
		cantidad.setName("cantidad");
		cantidad.setFieldLabel("Cantidad");
		cantidad.setPropertyEditorType(Integer.class);
		fields.add(cantidad);

			
		
		FormData formData = new FormData("-20");
		form.setHeaderVisible(false);
		form.setFrame(true);
		for(Field<?> f: fields){
			form.add(f, formData);
		}
		
		
		for(FieldBinding fb: bindings){
			formBindings.addFieldBinding(fb);
		}
		
		formBindings.autoBind();
		//formBindings.bind(ge.getModel());
		BeanModel detalle = ge;
		BeanModel s = store.findModel("producto", ge.get("producto"));
		selected = -1;
		
		if (s != null) {
			int c = s.get("cantidad");
			detalle.set("cantidad", c);	
			selected = store.indexOf(s);
		}
			
		
/*		BeanModel detalle = store.findModel("producto", ge);
		
		if ( detalle != null) {
			int c = detalle.get("cantidad");
			detalle = crearDetalle(ge);
			detalle.set("cantidad", c);
			
		} else {
			detalle = crearDetalle(ge);
		}*/
			

		formBindings.bind(detalle);
		
			
		dialog.addListener(Events.Render, new Listener<BaseEvent>() {
			
			@Override
			public void handleEvent(BaseEvent be) {
				//formBindings.setStore((Store) gridDetalle.getStore());
				
				dialog.getButtonById(Dialog.OK).addSelectionListener(new SelectionListener<ButtonEvent>() {

					@Override
					public void componentSelected(ButtonEvent ce) {
						//store.commitChanges();
						//store.remove(selected);
						
						BeanModel agregar = (BeanModel) formBindings.getModel();
						Producto prodAgregar = (Producto) ((FacturaDetalleVenta)agregar.getBean()).getProducto();
						if (selected >= 0) {
							List<BeanModel> nuevoStore = new ArrayList<BeanModel>();
							for (BeanModel b : store.getModels()) {
								Producto p = (Producto) ((FacturaDetalleVenta)b.getBean()).getProducto();
								//if (((Producto) b.get("producto")).getCodigo() != ((Producto) agregar.get("producto")).getCodigo()) {
								if(p.getCodigo().compareTo(prodAgregar.getCodigo()) != 0){
									nuevoStore.add(b);
									System.out.println(b.get("cantidad"));
								}
							}
							
							nuevoStore.add(agregar);
							store.removeAll();
							
							for (BeanModel b : nuevoStore) {
								store.add(b);
							}
							
							
						} else {

							store.add(agregar);
						}
						
						//formBindings.unbind();
						dialog.hide();
						window.hide();
						

					}
				});	
				dialog.getButtonById(Dialog.CANCEL).addSelectionListener(new SelectionListener<ButtonEvent>() {

					@Override
					public void componentSelected(ButtonEvent ce) {
						//store.rejectChanges();
						dialog.hide();
						

					}
				});
				
			}
		});
		
		dialog.add(form);
		dialog.show();
	}


}  

