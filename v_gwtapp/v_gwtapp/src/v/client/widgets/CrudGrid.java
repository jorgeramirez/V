package v.client.widgets;

import java.util.HashMap;

import v.client.AppConstants;
import v.client.AppConstants.Filtros;

import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.event.SelectionChangedEvent;
import com.extjs.gxt.ui.client.event.SelectionChangedListener;
import com.extjs.gxt.ui.client.store.Store;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.grid.CheckBoxSelectionModel;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.user.client.Element;

/**
 * Clase que Define un {@link Grid} con capacidad de realizar CRUD sobre
 * los elementos que almacena en su {@link Store}
 * 
 * @author Jorge Ramírez <jorgeramirez1990@gmail.com> 
 **/

public class CrudGrid<M> extends CustomGrid<M> {
	private Button addButton;
	private Button deleteButton;

	public CrudGrid(String title, ColumnModel cm, HashMap<String, AppConstants.Filtros> filtersConfig, 
			  		RpcProxy<PagingLoadResult<M>> proxy) {
		super(title, cm, filtersConfig, proxy);
	}

	public CrudGrid(String title, ColumnModel cm, RpcProxy<PagingLoadResult<M>> proxy) {
		super(title, cm, proxy);
	}
	
	public CrudGrid(String title, ColumnModel cm, HashMap<String, Filtros> filtersConfig,
			RpcProxy<PagingLoadResult<M>> proxy,
			CheckBoxSelectionModel<BeanModel> cbsm) {
		super(title, cm, filtersConfig, proxy, cbsm);
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
