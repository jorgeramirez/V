package v.client.widgets;

import java.util.ArrayList;
import java.util.List;

import v.client.AppConstants;
import v.client.AppViewport;
import v.client.rpc.AdministradorServiceAsync;
import v.modelo.Usuario;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.TreePanelEvent;
import com.extjs.gxt.ui.client.store.TreeStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.treepanel.TreePanel;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.rpc.AsyncCallback;

public class NavigationTree extends TreePanel<ModelData> {

	public NavigationTree(TreeStore<ModelData> store) {
		super(store);
	}
	
	public void onRender(Element target, int index) {
		super.onRender(target, index);
		//this.onComponentEvent(ce)
		this.addListener(Events.OnClick, new Listener<TreePanelEvent<ModelData>>() {
			@Override
			public void handleEvent(TreePanelEvent<ModelData> te){
				if(te.getItem().toString().equals("Usuarios")){
					final AdministradorServiceAsync service = Registry.get(AppConstants.ADMINISTRADOR_SERVICE);
					RpcProxy<List<Usuario>> proxy = new RpcProxy<List<Usuario>>() {
						@Override
						public void load(Object loadConfig, AsyncCallback<List<Usuario>> callback) {
							service.listarUsuarios(callback);
						}
					};
					
					List<ColumnConfig> columns = new ArrayList<ColumnConfig>();
					columns.add(new ColumnConfig("username", "Username", 200));
					columns.add(new ColumnConfig("nombre", "Nombre", 200));
					columns.add(new ColumnConfig("apellido", "Apellido", 200));
					columns.add(new ColumnConfig("cedula", "Cédula", 200));
					columns.add(new ColumnConfig("rol", "Rol", 200));
					ColumnModel cm = new ColumnModel(columns);
					CustomGrid<Usuario> usuariosGrid = new CustomGrid<Usuario>("ABM Usuarios", cm, proxy);
					ContentPanel cp = (ContentPanel)Registry.get(AppViewport.CENTER_REGION);
					cp.add(usuariosGrid);
				};
			}
		});
	}

}
