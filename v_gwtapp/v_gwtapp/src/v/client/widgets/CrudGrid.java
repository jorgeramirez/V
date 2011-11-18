package v.client.widgets;

import java.util.HashMap;

import v.client.AppConstants;
import v.client.AppConstants.Filtros;

import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.store.Store;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.grid.CheckBoxSelectionModel;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;

/**
 * Clase que Define un {@link Grid} con capacidad de realizar CRUD sobre
 * los elementos que almacena en su {@link Store}
 * 
 * @author Jorge Ramírez <jorgeramirez1990@gmail.com> 
 **/

public class CrudGrid<M> extends CustomGrid<M> {

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
		Button addButton = new Button("Agregar");
		addButton.setIconStyle("icon-add");
		tb.add(addButton);
		Button delButton = new Button("Eliminar");
		delButton.setIconStyle("icon-delete");
		tb.add(delButton);
		return tb;
	}	
	
}
