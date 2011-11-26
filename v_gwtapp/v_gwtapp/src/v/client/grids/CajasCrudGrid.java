package v.client.grids;

import java.util.Map;

import v.client.AppConstants.Filtros;
import v.client.widgets.CrudGrid;
import v.modelo.Caja;

import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;

public class CajasCrudGrid extends CrudGrid<Caja> {

	public CajasCrudGrid(String title) {
		super(title);
	}

	@Override
	public RpcProxy<PagingLoadResult<Caja>> buildProxy() {
		CajasGrid c = new CajasGrid("", true, true);
		return c.buildProxy();
	}

	@Override
	public Map<String, Filtros> buildFiltersConfig() {
		CajasGrid c = new CajasGrid("", true, true);
		return c.buildFiltersConfig();
	}

	@Override
	public ColumnModel buildColumnModel() {
		CajasGrid c = new CajasGrid("", true, true);
		return c.buildColumnModel();
	}

}
