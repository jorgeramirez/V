package v.client.panels;

import v.client.grids.NuevaCompraDetallesGrid;

import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.layout.AnchorData;
import com.extjs.gxt.ui.client.widget.layout.AnchorLayout;
import com.google.gwt.user.client.Element;

/**
 * Define el Panel para Registro de Compras
 * 
 * @author Jorge Ramírez <jorgeramirez1990@gmail.com>
 **/
public class NuevaCompraPanel extends ContentPanel {
	private NuevaCompraDetallesGrid grid;
	private ProveedorSelectorPanel providerSelector;
	private Button saveButton; // boton para guardar la compra
	
	public NuevaCompraPanel() {
		this.setHeading("Nueva Compra");
		this.setFrame(true);

	}
	
	public void onRender(Element parent, int pos) {
		super.onRender(parent, pos);
		
		grid = new NuevaCompraDetallesGrid();
		providerSelector = new ProveedorSelectorPanel();

		
		//boton guardar compra
		saveButton = new Button("Guardar Compra");
		saveButton.setIconStyle("icon-save");
		providerSelector.getTopToolBar().add(saveButton);
		
		this.setLayout(new AnchorLayout());
		
		AnchorData data = new AnchorData("100% 25%");
		this.add(providerSelector, data);
		
		data = new AnchorData("100% 75%");
		this.add(grid, data);
	}

	public NuevaCompraDetallesGrid getGrid() {
		return grid;
	}

	public void setGrid(NuevaCompraDetallesGrid grid) {
		this.grid = grid;
	}

	public ProveedorSelectorPanel getProviderSelector() {
		return providerSelector;
	}

	public void setProviderSelector(ProveedorSelectorPanel panel) {
		this.providerSelector = panel;
	}

	public Button getSaveButton() {
		return saveButton;
	}

	public void setSaveButton(Button saveButton) {
		this.saveButton = saveButton;
	}
}
