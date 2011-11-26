package v.client.grids;

import java.util.Map;

import v.client.AppConstants.Filtros;
import v.client.widgets.CrudGrid;
import v.modelo.Usuario;

import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;


/**
 * Define un {@link CrudGrid} para Usuarios
 * 
 * @author Jorge Ramírez <jorgeramirez1990@gmail.com>
 **/
public class UsuariosCrudGrid extends CrudGrid<Usuario> {

	public UsuariosCrudGrid(String title) {
		super(title);
	}

	@Override
	public RpcProxy<PagingLoadResult<Usuario>> buildProxy() {
		UsuariosGrid u = new UsuariosGrid("", true, true);
		return u.buildProxy();
	}

	@Override
	public Map<String, Filtros> buildFiltersConfig() {
		UsuariosGrid u = new UsuariosGrid("", true, true);
		return u.buildFiltersConfig();
	}

	@Override
	public ColumnModel buildColumnModel() {
		UsuariosGrid u = new UsuariosGrid("", true, true);
		return u.buildColumnModel();
	}

}
