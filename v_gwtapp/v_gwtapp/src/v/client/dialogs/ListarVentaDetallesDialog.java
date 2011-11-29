package v.client.dialogs;

import v.client.grids.VentasDetallesGrid;
import v.modelo.FacturaVenta;

import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.user.client.Element;

/**
 * Define un {@link Dialog} que contiene un {@link Grid} de
 * detalles para la venta pasada como parámetro
 * 
 * @author Jorge Ramírez <jorgeramirez1990@gmail.com>
 **/
public class ListarVentaDetallesDialog extends Dialog {
	private FacturaVenta factura;
	private VentasDetallesGrid grid;
	
	public ListarVentaDetallesDialog(FacturaVenta factura) {
		this.factura = factura;
		this.setBodyBorder(false);
		this.setHeading("Detalles para Factura Nro. " + factura.getNumeroFactura());
		this.setLayout(new FitLayout());
		this.setHideOnButtonClick(true);
		this.closeText = "Cerrar";
		this.setButtons(Dialog.CLOSE);
		this.setSize(760, 450);
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
		grid = new VentasDetallesGrid(this.factura, null, false, true);
		add(grid);
	}
}
