package v.client.dialogs;

import v.client.grids.PagosGrid;
import v.modelo.FacturaVenta;

import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.user.client.Element;

public class ListarPagosDialog extends Dialog {
	private FacturaVenta factura;
	private PagosGrid grid;
	
	public ListarPagosDialog(FacturaVenta factura) {
		this.factura = factura;
		this.setBodyBorder(false);
		if(factura != null){
			this.setHeading("Lista de Pagos para Factura Nro. " + factura.getNumeroFactura());
		}
		this.setLayout(new FitLayout());
		this.setHideOnButtonClick(true);
		this.closeText = "Cerrar";
		this.setButtons(Dialog.CLOSE);
		this.setSize(700, 450);
		this.setModal(true);		
	}

	public FacturaVenta getFactura() {
		return factura;
	}

	public void setFactura(FacturaVenta factura) {
		this.factura = factura;
	}
	
	public void onRender(Element parent, int pos) {
		super.onRender(parent, pos);
		this.setLayout(new FitLayout());
		grid = new PagosGrid(this.factura);
		add(grid);
	}	
}
