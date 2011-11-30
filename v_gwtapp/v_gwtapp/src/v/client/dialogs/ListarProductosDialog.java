package v.client.dialogs;

import v.client.grids.ProductosGrid;

import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.user.client.Element;

/**
 * Define un {@link Dialog} que contiene un {@link Grid} de
 * productos
 * 
 * @author Jorge Ramírez <jorgeramirez1990@gmail.com>
 **/
public class ListarProductosDialog extends Dialog {
	public static final String CONFIRMAR = Dialog.OK;
	private ProductosGrid grid;
	
	public ListarProductosDialog() {
		this.setBodyBorder(false);
		this.setHeading("Lista de Productos");
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
		grid = new ProductosGrid(null, false, true);
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
				
			}
		
		});

	}

	public ProductosGrid getGrid() {
		return grid;
	}

	public void setGrid(ProductosGrid grid) {
		this.grid = grid;
	}
}
