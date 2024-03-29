package v.client;

import v.client.util.NavigationIconProvider;
import v.client.widgets.NavigationTree;
import v.shared.model.Funcionalidad;
import v.shared.model.Roles;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style.LayoutRegion;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.store.TreeStore;
import com.extjs.gxt.ui.client.util.Margins;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.HtmlContainer;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.Viewport;
import com.extjs.gxt.ui.client.widget.layout.BorderLayout;
import com.extjs.gxt.ui.client.widget.layout.BorderLayoutData;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.google.gwt.user.client.Element;

public class AppViewport extends Viewport {
	public static final String CENTER_REGION = "centerRegion";
	public static final String NORTH_PANEL = "northPanel";
	public static final String WEST_PANEL = "westPanel";
	private Roles userRol; //determina las funcionalidades a visualizar.

	public AppViewport(Roles userRol) {
		super();
		this.userRol = userRol;
	}
	
	public AppViewport(String rol) {
		if(rol.equals(AppConstants.ADMINISTRADOR_ROL)){
			this.userRol = Roles.ADMINISTRADOR;
		}else if (rol.equals(AppConstants.CAJERO_ROL)){
			this.userRol = Roles.CAJERO;
		}else if (rol.equals(AppConstants.VENDEDOR_ROL)){
			this.userRol = Roles.VENDEDOR;
		}else if (rol.equals(AppConstants.COMPRADOR_ROL)){
			this.userRol = Roles.COMPRADOR;
		}		
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
		navTree.setIconProvider(new NavigationIconProvider());
		
		ContentPanel west = new ContentPanel();
		west.add(navTree);
		this.add(west, data);
		Registry.register(WEST_PANEL, west);
		
	}
	
	private void createCenter() {
		BorderLayoutData centerData = new BorderLayoutData(LayoutRegion.CENTER);
		centerData.setCollapsible(false);
		LayoutContainer center = new LayoutContainer();
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
		break;
		case CAJERO:
			ret = getCajeroTreeModel();
		break;
		case VENDEDOR:
			ret = getVendedorTreeModel();
		break;
		case COMPRADOR:
			ret = getCompradorTreeModel();
		}
		return ret;
	}
	
	private static Funcionalidad getAdminTreeModel() {
		Funcionalidad[] funcs = new Funcionalidad[] {
		    new Funcionalidad(AppConstants.ABM_LABEL,
		    	new Funcionalidad[] {
		    		new Funcionalidad(AppConstants.USUARIOS_LABEL),
		        	new Funcionalidad(AppConstants.CLIENTE_LABEL),
		        	new Funcionalidad(AppConstants.PROVEEDOR_LABEL),
		        	new Funcionalidad(AppConstants.CAJA_LABEL),
		        	new Funcionalidad(AppConstants.PRODUCTO_LABEL)
		    	}
		    ),
	        new Funcionalidad(AppConstants.VENTAS_LABEL,
	        	new Funcionalidad[] {
	        		new Funcionalidad(AppConstants.REGISTRAR_VENTA_LABEL),
	        		new Funcionalidad(AppConstants.LISTAR_VENTAS_LABEL)
	        	}
	        ),
	        new Funcionalidad(AppConstants.COMPRAS_LABEL,
		       	new Funcionalidad[] {
		       		new Funcionalidad(AppConstants.REGISTRAR_COMPRA_LABEL),
		       		new Funcionalidad(AppConstants.LISTAR_COMPRAS_LABEL)
		    	}
	        ),
	        new Funcionalidad(AppConstants.OPERACIONES_CAJA_LABEL,
		       	new Funcionalidad[] {
		       		new Funcionalidad(AppConstants.COBRAR_FACTURA_LABEL),
		       		new Funcionalidad(AppConstants.LISTAR_FACTURAS_LABEL),
		       		new Funcionalidad(AppConstants.CIERRE_CAJA_LABEL)
	        	}
	        ),
	        new Funcionalidad(AppConstants.SISTEMA_LABEL,
		       	new Funcionalidad[] {
		       		new Funcionalidad(AppConstants.LOGOUT_LABEL)
		        }
	        )
		};

		Funcionalidad root = new Funcionalidad("root");
		for (int i = 0; i < funcs.length; i++) {
			root.add((Funcionalidad) funcs[i]);
		}
		return root;		
	}
	
	private static Funcionalidad getCajeroTreeModel() {
		Funcionalidad[] funcs = new Funcionalidad[] {
	        new Funcionalidad(AppConstants.OPERACIONES_CAJA_LABEL,
		       	new Funcionalidad[] {
		       		new Funcionalidad(AppConstants.COBRAR_FACTURA_LABEL),
		       		new Funcionalidad(AppConstants.LISTAR_FACTURAS_LABEL),
		       		new Funcionalidad(AppConstants.CIERRE_CAJA_LABEL)
	        	}
	        ),
	        new Funcionalidad(AppConstants.SISTEMA_LABEL,
		       	new Funcionalidad[] {
		       		new Funcionalidad(AppConstants.LOGOUT_LABEL)
		        }
	        )
		};

		Funcionalidad root = new Funcionalidad("root");
		for (int i = 0; i < funcs.length; i++) {
			root.add((Funcionalidad) funcs[i]);
		}
		return root;		
	}
	
	private static Funcionalidad getVendedorTreeModel() {
		Funcionalidad[] funcs = new Funcionalidad[] {
		    new Funcionalidad(AppConstants.ABM_LABEL,
		    	new Funcionalidad[] {
		        	new Funcionalidad(AppConstants.CLIENTE_LABEL)
		    	}
		    ),
	        new Funcionalidad(AppConstants.VENTAS_LABEL,
	        	new Funcionalidad[] {
	        		new Funcionalidad(AppConstants.REGISTRAR_VENTA_LABEL),
	        		new Funcionalidad(AppConstants.LISTAR_VENTAS_LABEL)
	        	}
	        ),
	        new Funcionalidad(AppConstants.SISTEMA_LABEL,
		       	new Funcionalidad[] {
		       		new Funcionalidad(AppConstants.LOGOUT_LABEL)
		        }
	        )
		};
		
		Funcionalidad root = new Funcionalidad("root");
		for (int i = 0; i < funcs.length; i++) {
			root.add((Funcionalidad) funcs[i]);
		}
		return root;		
	}
	
	private static Funcionalidad getCompradorTreeModel() {
		Funcionalidad[] funcs = new Funcionalidad[] {
		    new Funcionalidad(AppConstants.ABM_LABEL,
		    	new Funcionalidad[] {
		        	new Funcionalidad(AppConstants.PROVEEDOR_LABEL),
		        	new Funcionalidad(AppConstants.PRODUCTO_LABEL)
		    	}
		    ),
	        new Funcionalidad(AppConstants.COMPRAS_LABEL,
		       	new Funcionalidad[] {
		       		new Funcionalidad(AppConstants.REGISTRAR_COMPRA_LABEL),
		       		new Funcionalidad(AppConstants.LISTAR_COMPRAS_LABEL)
		    	}
	        ),
	        new Funcionalidad(AppConstants.SISTEMA_LABEL,
		       	new Funcionalidad[] {
		       		new Funcionalidad(AppConstants.LOGOUT_LABEL)
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
