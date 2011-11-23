package v.client.grids;

import java.util.Map;

import v.client.AppConstants.Filtros;
import v.client.widgets.CrudGrid;
import v.modelo.Proveedor;

import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;

public class ProveedoresCrudGrid extends CrudGrid<Proveedor> {
	
	public ProveedoresCrudGrid(String title) {
		super(title);
	}

	@Override
	public RpcProxy<PagingLoadResult<Proveedor>> buildProxy() {
		ProveedoresGrid p = new ProveedoresGrid("", true, true);
		return p.buildProxy();
	}

	@Override
	public Map<String, Filtros> buildFiltersConfig() {
		ProveedoresGrid p = new ProveedoresGrid("", true, true);
		return p.buildFiltersConfig();
	}

	@Override
	public ColumnModel buildColumnModel() {
		ProveedoresGrid p = new ProveedoresGrid("", true, true);
		return p.buildColumnModel();
	}
}
