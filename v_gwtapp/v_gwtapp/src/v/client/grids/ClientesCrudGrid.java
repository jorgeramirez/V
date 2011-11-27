package v.client.grids;

import java.util.Map;

import v.client.AppConstants.Filtros;
import v.client.widgets.CrudGrid;
import v.modelo.Cliente;

import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;

public class ClientesCrudGrid extends CrudGrid<Cliente> {
	public ClientesCrudGrid(String title) {
		super(title);
	}

	@Override
	public RpcProxy<PagingLoadResult<Cliente>> buildProxy() {
		ClientesGrid c = new ClientesGrid("", true, true);
		return c.buildProxy();
	}

	@Override
	public Map<String, Filtros> buildFiltersConfig() {
		ClientesGrid c = new ClientesGrid("", true, true);
		return c.buildFiltersConfig();
	}

	@Override
	public ColumnModel buildColumnModel() {
		ClientesGrid c = new ClientesGrid("", true, true);
		return c.buildColumnModel();
	}
}
