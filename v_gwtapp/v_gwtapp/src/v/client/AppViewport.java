package v.client;

import v.shared.model.Funcionalidad;
import v.shared.model.Roles;
import v.client.widgets.NavigationTree;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.ModelIconProvider;
import com.extjs.gxt.ui.client.store.TreeStore;
import com.extjs.gxt.ui.client.util.IconHelper;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.HtmlContainer;
import com.extjs.gxt.ui.client.widget.Viewport;
import com.extjs.gxt.ui.client.widget.custom.ThemeSelector;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.ui.AbstractImagePrototype;

public class AppViewport extends Viewport {
	public static final String CENTER_REGION = "centerRegion";
	public static final String NORTH_PANEL = "northPanel";
	public static final String WEST_PANEL = "westPanel";
	private Roles userRol; //determina las funcionalidades a visualizar.

	public AppViewport(Roles userRol) {
		super();
		this.userRol = userRol;
	}
	
	public void onRender(Element parent, int pos) {
		super.onRender(parent, pos);
		final BorderLayout borderLayout = new BorderLayout();
		this.setLayout(borderLayout);
		this.createWest();
		this.createNorth();
		this.createCenter();
	}
	
	private void createNorth() {
	    StringBuffer sb = new StringBuffer();
	    sb.append("<div id='v-theme'></div><div id=v-title>V</div>");

	    HtmlContainer northPanel = new HtmlContainer(sb.toString());
	    northPanel.setStateful(false);
	    northPanel.setId("v-header");
	    northPanel.addStyleName("x-small-editor");

	    ThemeSelector selector = new ThemeSelector();
	    selector.setWidth(125);
	    northPanel.add(selector, "#v-theme");

	    BorderLayoutData data = new BorderLayoutData(LayoutRegion.NORTH, 33);
	    data.setMargins(new Margins());
	    this.add(northPanel, data);
	    Registry.register(NORTH_PANEL, northPanel);
	}
	
	private void createWest() {
		BorderLayoutData data = new BorderLayoutData(LayoutRegion.WEST,200, 150, 300);
		data.setCollapsible(true);
		data.setSplit(true);

		Funcionalidad funcs = getTreeModel(this.userRol);
		TreeStore<ModelData> store = new TreeStore<ModelData>();
		store.add(funcs.getChildren(), true);
		
		final NavigationTree navTree = new NavigationTree(store);
		navTree.setDisplayProperty("nombre");
		navTree.setIconProvider(new ModelIconProvider<ModelData>() {
			
			@Override
			public AbstractImagePrototype getIcon(ModelData model) {
				String n = model.toString();
				if("Usuarios".equals(n)){
					return IconHelper.createPath("images/user.png");
				}
				return null;
			}
		});
		
		ContentPanel west = new ContentPanel();
		west.add(navTree);
		this.add(west, data);
		Registry.register(WEST_PANEL, west);
		
	}
	
	private void createCenter() {
		BorderLayoutData centerData = new BorderLayoutData(LayoutRegion.CENTER);
		centerData.setCollapsible(false);
		ContentPanel center = new ContentPanel();
		final FitLayout fitLayout = new FitLayout();
		center.setLayout(fitLayout);
		this.add(center, centerData);
		Registry.register(AppViewport.CENTER_REGION, center);		
	}

	public Roles getUserRol() {
		return userRol;
	}

	public void setUserRol(Roles userRol) {
		this.userRol = userRol;
	}

	
	private static Funcionalidad getTreeModel(Roles userRol) {
		Funcionalidad ret = null;
		switch(userRol){
		case ADMINISTRADOR:
			ret = getAdminTreeModel();
		}
		return ret;
	}
	
	private static Funcionalidad getAdminTreeModel() {
		Funcionalidad[] funcs = new Funcionalidad[] {
		    new Funcionalidad("ABM",
		    	new Funcionalidad[] {
		    		new Funcionalidad("Usuarios"),
		        	new Funcionalidad("Cliente"),
		        	new Funcionalidad("Vendedor"),
		        	new Funcionalidad("Proveedor"),
		        	new Funcionalidad("Caja")
		    	}
		    ),
	        new Funcionalidad("Ventas",
	        	new Funcionalidad[] {
	        		new Funcionalidad("Registrar Venta"),
	        		new Funcionalidad("Listar")
	        	}
	        ),
	        new Funcionalidad("Compras",
		       	new Funcionalidad[] {
		       		new Funcionalidad("Registrar Compra"),
		       		new Funcionalidad("Listar")
		    	}
	        ),
	        new Funcionalidad("Caja",
		       	new Funcionalidad[] {
		       		new Funcionalidad("Registrar Pago"),
		       		new Funcionalidad("Cierre de Caja")
	        	}
	        ),
	        new Funcionalidad("Sistema",
		       	new Funcionalidad[] {
		       		new Funcionalidad("Logout")
		        }
	        )
		};

		Funcionalidad root = new Funcionalidad("root");
		for (int i = 0; i < funcs.length; i++) {
			root.add((Funcionalidad) funcs[i]);
		}
		return root;		
	}
}