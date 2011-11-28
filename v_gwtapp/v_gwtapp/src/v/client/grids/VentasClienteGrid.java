package v.client.grids;

import com.extjs.gxt.ui.client.Style.SelectionMode;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.event.WindowEvent;
import com.extjs.gxt.ui.client.event.WindowListener;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.user.client.Element;

public class VentasClienteGrid extends ToolBar {
	private ClientesGrid gridCliente;
	//private ToolBar tb;
	//private ToolBar topToolBar;
	private Button addCliente; 
	private final Window window = new Window(); 
	
	public VentasClienteGrid(){
		//tb = new ToolBar();
		//this.setTopComponent(tb);
	}
	
	
	public Window getWindow() {
		return window;
	}


	protected void onRender(Element parent, int pos) {  
		super.onRender(parent, pos);   

		gridCliente = new ClientesGrid("Seleccione un Cliente", false, true);

		gridCliente.addListener(Events.Render, new Listener<BaseEvent>() {

			@Override
			public void handleEvent(BaseEvent be) {
				gridCliente.getGrid().getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

			}

		});


		//final Window window = new Window();  
		window.setSize(500, 300);  
		window.setPlain(true);  
		window.setModal(true);  
		window.setBlinkModal(true);  
		window.setHeading("Clientes");  
		window.setLayout(new FitLayout());  
		
		window.addWindowListener(new WindowListener() {  
			@Override  
			public void windowHide(WindowEvent we) {  
				Button open = we.getWindow().getData("clientes");  
				open.focus();  
			}  
		});  

		window.add(gridCliente);
		window.addButton(new Button("Ok", new SelectionListener<ButtonEvent>() {  
			@Override  
			public void componentSelected(ButtonEvent ce) {  
				window.hide();  
			}  
		}));  

		//window.setFocusWidget(window.getButtonBar().getItem(0));  
		

		addCliente = new Button("Seleccionar Cliente", new SelectionListener<ButtonEvent>() {  
			@Override  
			public void componentSelected(ButtonEvent ce) {  
				window.show();  
			}  
		}); 

		addCliente.setIconStyle("icon-add");
		//tb.add(addCliente);
		add(addCliente);
		
		window.setData("clientes", addCliente);  
		//this.setTopComponent(tb);
	}

	public Grid<BeanModel> getGrid() {
		return gridCliente.getGrid();
	}
	
	public BeanModel obtenerCliente() {
		return gridCliente.getGrid().getSelectionModel().getSelectedItem();	
	}
}
