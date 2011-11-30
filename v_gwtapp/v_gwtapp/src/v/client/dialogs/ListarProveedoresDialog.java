package v.client.dialogs;

import v.client.grids.ProveedoresGrid;

import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.user.client.Element;

/**
 * Define un {@link Dialog} que contiene un {@link Grid} de
 * proveedores
 * 
 * @author Jorge Ramírez <jorgeramirez1990@gmail.com>
 **/
public class ListarProveedoresDialog extends Dialog {
	private ProveedoresGrid grid;
	private static String CONFIRMAR = Dialog.OK;
	
	public ListarProveedoresDialog() {
		this.setBodyBorder(false);
		this.setHeading("Lista de Proveedores");
		this.setLayout(new FitLayout());
		this.setHideOnButtonClick(false);
		this.okText = "Confirmar";
		this.setButtons(Dialog.OK);
		this.setSize(700, 450);
		this.setModal(true);		
	}

	public void onRender(Element parent, int pos) {
		super.onRender(parent, pos);
		this.setLayout(new FitLayout());
		grid = new ProveedoresGrid(null, false, true);
		add(grid);
		
		final Dialog me = this;
		
		grid.addListener(Events.Render, new Listener<BaseEvent>() {

			@Override
			public void handleEvent(BaseEvent be) {
				grid.getGrid().getSelectionModel().addSelectionChangedListener(new SelectionChangedListener<BeanModel>() {
					
					@Override
					public void selectionChanged(SelectionChangedEvent<BeanModel> se) {
						me.getButtonById(CONFIRMAR).setEnabled(se.getSelection().size() > 0);
					}
				});
				
				
				me.getButtonById(CONFIRMAR).addSelectionListener(new SelectionListener<ButtonEvent>() {
					
					@Override
					public void componentSelected(ButtonEvent ce) {
						me.hide();
					}
				});
			}
		
		});

	}

	public ProveedoresGrid getGrid() {
		return grid;
	}

	public void setGrid(ProveedoresGrid grid) {
		this.grid = grid;
	}	
}
