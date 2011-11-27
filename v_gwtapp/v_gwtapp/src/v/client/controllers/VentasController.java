package v.client.controllers;

import v.client.AppConstants;
import v.client.AppViewport;
import v.client.VType;
import v.client.VTypeValidator;
import v.client.grids.FacturaDetalleVentaGrid;
import v.client.grids.VentasClienteGrid;
import v.client.rpc.VendedorServiceAsync;
import v.modelo.FacturaVenta;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style.SelectionMode;
import com.extjs.gxt.ui.client.binding.FormBinding;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.VerticalPanel;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.FormPanel.LabelAlign;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.ColumnData;
import com.extjs.gxt.ui.client.widget.layout.ColumnLayout;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.extjs.gxt.ui.client.widget.layout.TableLayout;

public class VentasController extends AbstractController {
	//private ContentPanel cp;

	private FacturaDetalleVentaGrid gridDetalle;
	private VentasClienteGrid gridCliente;

	private FormBinding formBindings; 
	private VerticalPanel panelVertical;
	private final VendedorServiceAsync service = Registry.get(AppConstants.VENDEDOR_SERVICE);

	public VentasController() {
		super(AppConstants.REGISTRAR_VENTA_LABEL);
	}

	@Override
	public void init() {
		//cp = new ContentPanel();

		// creamos el grid de selección de cliente
		gridCliente = new VentasClienteGrid();

		
		//form que contiene los datos del cliente seleccionado
		FormPanel panelCliente = crearFormCliente();  
		formBindings = new FormBinding(panelCliente, true);  

		bindHandlers();
		
		FacturaVenta v = new FacturaVenta();
		
		//el grid de detalles de los producutos seleccionados
		gridDetalle = new FacturaDetalleVentaGrid(v);


		panelVertical = new VerticalPanel();  
		panelVertical.setSpacing(20);
		

		panelVertical.add(gridCliente);
		panelVertical.add(panelCliente);
		panelVertical.add(gridDetalle);

		LayoutContainer lc = (LayoutContainer)Registry.get(AppViewport.CENTER_REGION);

		lc.add(panelVertical);
		lc.layout();

	}

	private FormPanel crearFormCliente() {

		FormPanel panel = new FormPanel();  
		panel.setHeaderVisible(false);  

		TextField<String> text;

		LayoutContainer main = new LayoutContainer();  
		main.setLayout(new ColumnLayout());  

		LayoutContainer left = new LayoutContainer();  
		left.setStyleAttribute("paddingRight", "10px");  
		FormLayout layout = new FormLayout();  
		layout.setLabelAlign(LabelAlign.TOP);  
		left.setLayout(layout); 

		// cedula field
		text = new TextField<String>();
		text.setAllowBlank(false);
		text.setMaxLength(10);
		text.setValidator(new VTypeValidator(VType.NUMERIC));
		text.setFieldLabel("Cédula");
		text.setName("cedula");
		text.setEnabled(false);
		left.add(text);

		// nombre field
		text = new TextField<String>();
		text.setMaxLength(50);
		text.setValidator(new VTypeValidator(VType.ALPHABET));
		text.setFieldLabel("Nombre");
		text.setName("nombre");
		text.setEnabled(false);
		text.setAllowBlank(false);
		left.add(text);


		// telefono field
		text = new TextField<String>();
		text.setMaxLength(20);
		text.setValidator(new VTypeValidator(VType.ALPHANUMERIC));
		text.setFieldLabel("Teléfono");
		text.setName("telefono");
		text.setEnabled(false);
		left.add(text);


		LayoutContainer right = new LayoutContainer();  
		right.setStyleAttribute("paddingLeft", "10px");  
		layout = new FormLayout();  
		layout.setLabelAlign(LabelAlign.TOP);  
		right.setLayout(layout);  

		// direccion field
		text = new TextField<String>();
		text.setMaxLength(70);		
		text.setValidator(new VTypeValidator(VType.ALPHANUMERIC));
		text.setFieldLabel("Dirección");
		text.setName("direccion");
		text.setAllowBlank(false);
		text.setEnabled(false);
		right.add(text);

		// apellido field
		text = new TextField<String>();
		text.setMaxLength(50);
		text.setValidator(new VTypeValidator(VType.ALPHABET));
		text.setFieldLabel("Apellido");
		text.setName("apellido");
		text.setAllowBlank(false);
		text.setEnabled(false);
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

