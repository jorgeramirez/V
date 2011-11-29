package v.client.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import v.client.AppConstants;
import v.client.AppViewport;
import v.client.VType;
import v.client.VTypeValidator;
import v.client.grids.FacturaDetalleVentaGrid;
import v.client.grids.VentasClienteGrid;
import v.client.rpc.LoginServiceAsync;
import v.client.rpc.VendedorServiceAsync;
import v.modelo.Cliente;
import v.modelo.FacturaDetalleVenta;
import v.modelo.FacturaVenta;
import v.modelo.Usuario;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.binding.FormBinding;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.FormPanel.LabelAlign;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.AnchorData;
import com.extjs.gxt.ui.client.widget.layout.AnchorLayout;
import com.extjs.gxt.ui.client.widget.layout.ColumnData;
import com.extjs.gxt.ui.client.widget.layout.ColumnLayout;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class VentasController extends AbstractController {
	private FacturaDetalleVentaGrid gridDetalle;
	private VentasClienteGrid gridCliente;

	private FormBinding formBindings; 
	private ContentPanel panelVertical;
	private final VendedorServiceAsync service = Registry.get(AppConstants.VENDEDOR_SERVICE);

	final private FacturaVenta venta = new FacturaVenta();
	
	public VentasController() {
		super(AppConstants.REGISTRAR_VENTA_LABEL);
	}

	@Override
	public void init() {

		// creamos el grid de selección de cliente
		gridCliente = new VentasClienteGrid();

		
		//form que contiene los datos del cliente seleccionado
		FormPanel panelCliente = crearFormCliente();  
		formBindings = new FormBinding(panelCliente, true);  

		bindHandlers();
		
		
		
		//el grid de detalles de los producutos seleccionados
		gridDetalle = new FacturaDetalleVentaGrid(venta);


		/*panelVertical = new VerticalPanel();
		panelVertical.setSpacing(20);
		

		panelVertical.add(gridCliente);
		panelVertical.add(panelCliente);
		panelVertical.add(gridDetalle);*/
		
		
		panelVertical = new ContentPanel();
		panelVertical.setTitle("Venta");
		panelVertical.setLayout(new AnchorLayout());
		
		AnchorData data = new AnchorData();
		data.setAnchorSpec("100% 25%");
		panelVertical.add(panelCliente, data);
		
		data = new AnchorData();
		data.setAnchorSpec("100% 75%");
		panelVertical.add(gridDetalle, data);
		
		panelVertical.setTopComponent(gridCliente);
		
	    Button guardar = new Button("Guardar Venta", new SelectionListener<ButtonEvent>() {  

			@Override  
			public void componentSelected(ButtonEvent ce) {
				
				BeanModel cliente = gridCliente.obtenerCliente();
				
				if (cliente == null) {
					MessageBox.alert("Cliente", "Debe seleccionar un Cliente", null);
					return;
				}
				
				venta.setCliente((Cliente) cliente.getBean());
				List<BeanModel> detalles = gridDetalle.guardarDetalles();
				
				if (detalles.isEmpty()) {
					MessageBox.alert("Detalles", "La factura debe tener al menos un producto", null);
					return;
				}
				
				List<FacturaDetalleVenta> fdv = new ArrayList<FacturaDetalleVenta>();
				Double total = 0.0;
				for (BeanModel f : detalles) {
					FacturaDetalleVenta fd = (FacturaDetalleVenta)f.getBean();
					fd.setPrecio(fd.getProducto().getPrecioVenta());
					fdv.add(fd);
					total += fd.getPrecio() * fd.getCantidad();
					
				}
				
				venta.setDetalles(fdv);
				venta.setTotal(total);
				venta.setSaldo(total);
				venta.setFecha(new Date());
				venta.setEstado(AppConstants.FACTURA_PENDIENTE_PAGO);

				
				final LoginServiceAsync loginService = (LoginServiceAsync)Registry.get(AppConstants.LOGIN_SERVICE);
				loginService.getSessionAttribute("usuario", new AsyncCallback<Usuario>() {

					@Override
					public void onFailure(Throwable caught) {
						MessageBox.alert("Error en el Servidor", caught.getMessage(), null);
					}

					@Override
					public void onSuccess(Usuario vendedor) {
						venta.setVendedor(vendedor);
						service.agregarVenta(venta,  new AsyncCallback<Boolean>() {

							@Override
							public void onFailure(Throwable caught) {
								MessageBox.alert("Error", caught.getMessage(), null);
							}

							@Override
							public void onSuccess(Boolean b) {
								if (b) {
									//mostrar el reporte de la factura
								} else {
								//no se puedo guardar la venta
								}
							}
						});						
					}
				});				
				
				
			}  
		});  
		
	    guardar.setIconStyle("icon-save");
	    ToolBar bottom = new ToolBar();
	    bottom.add(guardar);
	    panelVertical.setBottomComponent(bottom);
	    panelVertical.setButtonAlign(HorizontalAlignment.CENTER);
	    
		LayoutContainer lc = (LayoutContainer)Registry.get(AppViewport.CENTER_REGION);

		lc.add(panelVertical);
		lc.layout(true);

	}

	protected void completarVenta(List<BeanModel> models) {
		List<FacturaDetalleVenta> fdvs = new ArrayList<FacturaDetalleVenta>();
		for(BeanModel f: models){
			fdvs.add((FacturaDetalleVenta)f.getBean());
		}
		
	}
	
	private FormPanel crearFormCliente() {

		FormPanel panel = new FormPanel();  
		panel.setHeaderVisible(false);  

		TextField<String> text;

		LayoutContainer main = new LayoutContainer();  
		main.setLayout(new ColumnLayout());  

		LayoutContainer left = new LayoutContainer();  
		left.setStyleAttribute("paddingRight", "10px");
		//left.setWidth(300);
		FitLayout layout;
		layout = new FitLayout(); 
  
		//layout.setLabelAlign(LabelAlign.TOP);  
		left.setLayout(layout); 

		// cedula field
		text = new TextField<String>();
		text.setAllowBlank(false);
		text.setFieldLabel("Cédula");
		text.setName("cedula");
		text.setEnabled(false);
		text.setWidth(50);
		left.add(text);

		// nombre field
		text = new TextField<String>();
		text.setFieldLabel("Nombre");
		text.setName("nombre");
		text.setEnabled(false);
		text.setAllowBlank(false);
		text.setWidth(200);
		left.add(text);


		// telefono field
		text = new TextField<String>();
		text.setMaxLength(20);
		text.setFieldLabel("Teléfono");
		text.setName("telefono");
		text.setEnabled(false);
		text.setWidth(50);
		left.add(text);


		LayoutContainer right = new LayoutContainer();  
		right.setStyleAttribute("paddingLeft", "10px");
		right.setWidth(300);
		layout = new FitLayout();  
		//layout.setLabelAlign(LabelAlign.TOP);  
		right.setLayout(layout);  

		// direccion field
		text = new TextField<String>();		
		text.setFieldLabel("Dirección");
		text.setName("direccion");
		text.setAllowBlank(false);
		text.setEnabled(false);
		text.setWidth(100);
		right.add(text);

		// apellido field
		text = new TextField<String>();
		text.setFieldLabel("Apellido");
		text.setName("apellido");
		text.setAllowBlank(false);
		text.setEnabled(false);		
	
		text.setWidth(100);
		right.add(text);

		main.add(left, new ColumnData(.5));  
		main.add(right, new ColumnData(.5));  

		panel.add(main, new FormData("100%")); 

		return panel;  
	}

	private void bindHandlers() {
		gridCliente.getWindow().addListener(Events.Render, new Listener<BaseEvent>() {

			@Override
			public void handleEvent(BaseEvent be) {
				//hacer el bind del form cliente con la fila seleccionada
				//gridCliente.getGrid().getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
				  
				gridCliente.getGrid().getSelectionModel().addListener(Events.SelectionChange,  
						new Listener<SelectionChangedEvent<BeanModel>>() {  
					public void handleEvent(SelectionChangedEvent<BeanModel> be) {  
						if (be.getSelection().size() > 0) {  
							formBindings.bind((ModelData) be.getSelection().get(0));  
						} else {  
							formBindings.unbind();  
						}  
					}  
				}); 
			}
		});
		

	}
}

