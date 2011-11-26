package v.client.grids;

import java.util.Map;

import v.client.AppConstants.Filtros;
import v.client.widgets.CrudGrid;
import v.modelo.Producto;

import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;

public class ProductosCrudGrid extends CrudGrid<Producto> {

	public ProductosCrudGrid(String title) {
		super(title);
	}

	@Override
	public RpcProxy<PagingLoadResult<Producto>> buildProxy() {
		ProductosGrid p = new ProductosGrid("", true, true);
		return p.buildProxy();
	}

	@Override
	public Map<String, Filtros> buildFiltersConfig() {
		ProductosGrid p = new ProductosGrid("", true, true);
		return p.buildFiltersConfig();
	}

	@Override
	public ColumnModel buildColumnModel() {
		ProductosGrid p = new ProductosGrid("", true, true);
		return p.buildColumnModel();
	}

}
