package v.client.dialogs;

import v.client.grids.ComprasDetallesGrid;
import v.modelo.FacturaCompra;

import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.user.client.Element;

/**
 * Define un {@link Dialog} que contiene un {@link Grid} de
 * detalles para la compra pasada como parámetro
 * 
 * @author Jorge Ramírez <jorgeramirez1990@gmail.com>
 **/
public class ListarCompraDetallesDialog extends Dialog {
	private FacturaCompra compra;
	private ComprasDetallesGrid grid;
	
	public ListarCompraDetallesDialog(FacturaCompra compra) {
		this.compra = compra;
		this.setBodyBorder(false);
		this.setHeading("Detalles para Compra Nro. " + compra.getNumeroFactura());
		this.setLayout(new FitLayout());
		this.setHideOnButtonClick(true);
		this.closeText = "Cerrar";
		this.setButtons(Dialog.CLOSE);
		this.setSize(760, 450);
		this.setModal(true);		
	}

	public FacturaCompra getFactura() {
		return compra;
	}

	public void setFactura(FacturaCompra factura) {
		this.compra = factura;
	}
	
	public void onRender(Element parent, int pos) {
		super.onRender(parent, pos);
		this.setLayout(new FitLayout());
		grid = new ComprasDetallesGrid(this.compra, null, false, true);
		add(grid);
	}
}
