package v.client.panels;

import v.client.dialogs.ListarProveedoresDialog;
import v.modelo.Proveedor;

import com.extjs.gxt.ui.client.binding.FormBinding;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.ColumnData;
import com.extjs.gxt.ui.client.widget.layout.ColumnLayout;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FormData;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.user.client.Element;

/**
 * Define un {@link ContentPanel} para seleccion de 
 * proveedores.
 * 
 * @author Jorge Ramírez <jorgeramirez1990@gmail.com>
 **/
public class ProveedorSelectorPanel extends ContentPanel {
	private ToolBar topToolBar;
	private ListarProveedoresDialog dialog; // lista de proveedores en un Dialog
	private FormPanel selectedPanel; // panel para el proveedor seleccionado
	private Button selectButton; // boton para seleccionar un proveedor. Muestra el dialog
	private FormBinding bindings;
	
	public ProveedorSelectorPanel() {
		
		// creamo el top toolbar
		topToolBar = new ToolBar();
		topToolBar.setSpacing(5);
		
		//boton seleccionar proveedor
		selectButton = new Button("Seleccionar Proveedor");
		selectButton.setIconStyle("icon-add");
		topToolBar.add(selectButton);
		this.setHeaderVisible(false);
		this.setTopComponent(topToolBar);
	}
	
	public void onRender(Element parent, int pos) {
		super.onRender(parent, pos);
		
		this.setLayout(new FitLayout());
		
		selectedPanel = buildFormPanel();
		bindings = new FormBinding(selectedPanel, true);
		
		this.add(selectedPanel);
	}
	
	public FormPanel buildFormPanel() {
		FormPanel panel = new FormPanel();
		panel.setFrame(true);
		panel.setHeaderVisible(false);
		
		LayoutContainer main = new LayoutContainer();  
		main.setLayout(new ColumnLayout());
		
		LayoutContainer left = new LayoutContainer();  
	    left.setStyleAttribute("paddingRight", "10px");  
	    FormLayout layout = new FormLayout();  
	    left.setLayout(layout);
	    
	    LayoutContainer right = new LayoutContainer();  
	    right.setStyleAttribute("paddingLeft", "10px");  
	    layout = new FormLayout();  
	    right.setLayout(layout);
	    
	    // ruc field
	    TextField<String> ruc = new TextField<String>();
	    ruc.setName("ruc");
	    ruc.setFieldLabel("RUC");
	    ruc.setEnabled(false);
	    
	    // nombre field
	    TextField<String> nombre = new TextField<String>();
	    nombre.setName("nombre");
	    nombre.setFieldLabel("Nombre");
	    nombre.setEnabled(false);
	    
	    // direccion field
	    TextField<String> direccion = new TextField<String>();
	    direccion.setName("direccion");
	    direccion.setFieldLabel("Dirección");
	    direccion.setEnabled(false);
	    
	    // telefono field
	    TextField<String> telefono = new TextField<String>();
	    telefono.setName("telefono");
	    telefono.setFieldLabel("Teléfono");
	    telefono.setEnabled(false);
	    
	    FormData formData = new FormData("100%");
	    left.add(ruc, formData);
	    left.add(nombre, formData);
	    
	    right.add(direccion, formData);
	    right.add(telefono, formData);	 
	    
	    main.add(left, new ColumnData(.5));  
	    main.add(right, new ColumnData(.5));  
	  
	    panel.add(main, new FormData("100%")); 
	    
	    return panel;
		  
	}

	public ListarProveedoresDialog getDialog() {
		return dialog;
	}

	public FormPanel getSelectedPanel() {
		return selectedPanel;
	}

	public Button getSelectButton() {
		return selectButton;
	}

	public FormBinding getBindings() {
		return bindings;
	}

	public void setDialog(ListarProveedoresDialog dialog) {
		this.dialog = dialog;
	}

	public void setSelectedPanel(FormPanel selectedPanel) {
		this.selectedPanel = selectedPanel;
	}

	public void setSelectButton(Button selectButton) {
		this.selectButton = selectButton;
	}

	public void setBindings(FormBinding bindings) {
		this.bindings = bindings;
	}
	
	public void showDialog() {
		dialog = new ListarProveedoresDialog();
		
		dialog.addListener(Events.Render, new Listener<BaseEvent>() {

			@Override
			public void handleEvent(BaseEvent be) {
				dialog.getGrid().getGrid().getSelectionModel().addListener(Events.SelectionChange,  
						new Listener<SelectionChangedEvent<BeanModel>>() {  
					public void handleEvent(SelectionChangedEvent<BeanModel> be) {  
						if (be.getSelection().size() > 0) {  
							bindings.bind((ModelData) be.getSelection().get(0));  
						} else {  
							bindings.unbind();  
						}  
					}  
				}); 				
			}
		});
		
		dialog.show();
	}
	
	public Proveedor getProvider() {
		BeanModel model = (BeanModel) bindings.getModel();
		if(model != null){
			return (Proveedor)model.getBean();
		}
		return null;
	}

	public ToolBar getTopToolBar() {
		return topToolBar;
	}

	public void setTopToolBar(ToolBar topToolBar) {
		this.topToolBar = topToolBar;
	}
}
