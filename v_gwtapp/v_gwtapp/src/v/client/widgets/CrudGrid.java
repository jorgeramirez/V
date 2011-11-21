package v.client.widgets;

import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.store.Store;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.user.client.Element;

/**
 * Clase que Define un {@link Grid} con capacidad de realizar CRUD sobre
 * los elementos que almacena en su {@link Store}
 * 
 * El mismo es una clase abstracta. Subclases deben implementar
 * los metodos abstractos definidos en {@link CustomGrid}
 * 
 * @author Jorge Ramírez <jorgeramirez1990@gmail.com> 
 **/

public abstract class CrudGrid<M> extends CustomGrid<M> {
	private Button addButton;
	private Button deleteButton;

	public CrudGrid(String title){
		super(title, true, true);
	}

	@Override
	protected ToolBar createTopToolBar() {
		ToolBar tb = super.createTopToolBar();
		addButton = new Button("Agregar");
		addButton.setIconStyle("icon-add");
		tb.add(addButton);
		
		deleteButton = new Button("Eliminar");
		deleteButton.setIconStyle("icon-delete");
		deleteButton.setEnabled(false);
		tb.add(deleteButton);
		
		return tb;
	}
	
	public void onRender(Element target, int index) {
		super.onRender(target, index);
		
		//Controlamos enabled/disabled del botón delete del ToolBar
		this.getGrid().getSelectionModel().addSelectionChangedListener(new SelectionChangedListener<BeanModel>() {

			@Override
			public void selectionChanged(SelectionChangedEvent<BeanModel> se) {
				deleteButton.setEnabled(se.getSelection().size() > 0);
			}
		});
		
	}

	public Button getAddButton() {
		return addButton;
	}

	public void setAddButton(Button addButton) {
		this.addButton = addButton;
	}

	public Button getDeleteButton() {
		return deleteButton;
	}

	public void setDeleteButton(Button deleteButton) {
		this.deleteButton = deleteButton;
	}
	
}
