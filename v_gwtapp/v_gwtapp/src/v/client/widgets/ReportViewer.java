package v.client.widgets;

import v.client.Util;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.Frame;


/**
 * Define un contenedor para mostrar los reportes generados.
 * 
 * 
 * @author Nahuel
 **/


public class ReportViewer extends Dialog {
	private String id;
	private String reporte;
	private String titulo;
	private Frame frame;
	public static String ACEPTAR = Dialog.YES;
	public static String CANCELAR = Dialog.NO;
	public static String PDF = Dialog.CANCEL;
	
	public ReportViewer(String id, String reporte, String titulo) {
		this.id = id;
		this.reporte = reporte;
		this.titulo = titulo;
		this.setSize(500, 300);
		this.setBlinkModal(true);
		this.setPlain(true);
		this.setModal(true);
		this.yesText = "Aceptar";
		this.noText = "Cancelar";
		this.cancelText = "PDF";
		this.setHeading(this.titulo);
		this.setButtons(Dialog.YESNOCANCEL);
		this.setHideOnButtonClick(true);
	}
	
	@Override  
	protected void onRender(Element parent, int pos) {  
		super.onRender(parent, pos);  
		frame = new Frame(Util.reporteUrl(reporte, "html", id));
		this.setLayout(new FitLayout());
		this.add(frame);

		final Dialog me = this;
		
		this.getButtonById(PDF).addSelectionListener(new SelectionListener<ButtonEvent>() {
			@Override  
			public void componentSelected(ButtonEvent ce) {
				me.setUrl(Util.reporteUrl(reporte, "pdf", id));
			}
		});
	}  

}  