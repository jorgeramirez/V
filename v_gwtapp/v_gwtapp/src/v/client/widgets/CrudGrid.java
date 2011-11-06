package v.client.widgets;

import java.util.HashMap;
import java.util.List;

import v.client.AppConstants;

import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;

public class CrudGrid<M> extends CustomGrid<M> {

	public CrudGrid(String title, ColumnModel cm, HashMap<String, AppConstants.Filtros> filtersConfig, 
			  		RpcProxy<List<M>> proxy) {
		super(title, cm, filtersConfig, proxy);
	}

	public CrudGrid(String title, ColumnModel cm, RpcProxy<List<M>> proxy) {
		super(title, cm, proxy);
	}
	
	@Override
	protected ToolBar createToolBar() {
		ToolBar tb = super.createToolBar();
		Button addButton = new Button("Agregar");
		addButton.setIconStyle("icon-add");
		tb.add(addButton);
		Button delButton = new Button("Eliminar");
		delButton.setIconStyle("icon-delete");
		tb.add(delButton);
		return tb;
	}	
	
}
