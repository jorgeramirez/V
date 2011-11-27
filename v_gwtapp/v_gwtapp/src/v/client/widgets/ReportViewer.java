package v.client.widgets;

import v.client.Util;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.Window;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FlowLayout;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Frame;


/**
 * Define un contenedor para mostrar los reportes generados.
 * 
 * 
 * @author Nahuel
 **/


public class ReportViewer extends LayoutContainer {
	private String id;
	private String reporte;
	private String titulo;
	
	public ReportViewer(String id, String reporte, String titulo) {
		this.id = id;
		this.reporte = reporte;
		this.titulo = titulo;
	}
	
	@Override  
	protected void onRender(Element parent, int pos) {  
		super.onRender(parent, pos);  
		setLayout(new FlowLayout(10));  

		final Window window = new Window(); 
		window.setSize(500, 300);  
		window.setPlain(true);
		window.setModal(true);  
		//window.setBlinkModal(true);  
		window.setHeading(this.titulo);  
		window.setLayout(new FitLayout()); 	  
		
		Frame frame = new Frame(Util.reporteUrl(this.reporte, "html", this.id));
		window.add(frame);

		window.addButton(new Button("PDF", new SelectionListener<ButtonEvent>() {  
			@Override  
			public void componentSelected(ButtonEvent ce) {
				window.setUrl(Util.reporteUrl(reporte, "pdf", id));
			}
		}));  

		window.show();  

	}  

}  