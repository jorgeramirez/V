package v.client.widgets;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import v.client.AppConstants;
import v.client.AppViewport;
import v.client.AppConstants.Filtros;
import v.modelo.Usuario;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.data.BaseFilterPagingLoadConfig;
import com.extjs.gxt.ui.client.data.BaseListLoader;
import com.extjs.gxt.ui.client.data.BasePagingLoadConfig;
import com.extjs.gxt.ui.client.data.BasePagingLoader;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.BeanModelReader;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.ListLoader;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.PagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoader;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.GridEvent;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.store.Store;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.grid.CheckBoxSelectionModel;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.LiveGridView;
import com.extjs.gxt.ui.client.widget.grid.RowEditor;
import com.extjs.gxt.ui.client.widget.grid.EditorGrid.ClicksToEdit;
import com.extjs.gxt.ui.client.widget.grid.filters.BooleanFilter;
import com.extjs.gxt.ui.client.widget.grid.filters.DateFilter;
import com.extjs.gxt.ui.client.widget.grid.filters.Filter;
import com.extjs.gxt.ui.client.widget.grid.filters.GridFilters;
import com.extjs.gxt.ui.client.widget.grid.filters.NumericFilter;
import com.extjs.gxt.ui.client.widget.grid.filters.StringFilter;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.selection.SelectionModel;
import com.extjs.gxt.ui.client.widget.toolbar.FillToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.LiveToolItem;
import com.extjs.gxt.ui.client.widget.toolbar.PagingToolBar;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.rpc.AsyncCallback;



/**
 * {@link CustomGrid} es una clase que define un {@link Grid} con paginación
 * utilizando {@link PagingToolBar}
 * 
 * También permite realizar filtrado, utilizando el plugin {@link GridFilters}
 * 
 * 
 * @author Jorge Ramírez <jorgeramirez1990@gmail.com>
 **/
@SuppressWarnings({"unused"})
public class CustomGrid<M> extends ContentPanel {

	/**
	 * Guarda la configuración de filtros. El formato de la misma es
	 * 		Nombre de Campo => Tipo de Filtro
	 * 
	 * Los tipos de filtros se definen en {@link AppConstants.Filtros}
	 **/
	private HashMap<String, AppConstants.Filtros> filtersConfig;
	
	/**
	 * Define si se utilizará {@link GridFilters} en el Grid.
	 **/
	private Boolean hasFilters = false;
	
	/**
	 * Define si se utilizará {@link CheckBoxSelectionModel}
	 **/
	private Boolean useCheckBoxSm = false;

	
	/**
	 * Determina si se utiliza un {@link RowEditor}
	 **/
	private Boolean useRowEditor = false;
	
	private CheckBoxSelectionModel<BeanModel> cbsm;	
	private Grid<BeanModel> grid;
	private ToolBar topToolBar;
	private ListStore<BeanModel> store;
	private ColumnModel cm;
	private String title = "Grid Panel";
	private GridFilters filters;
	private BeanModelReader reader;
	private RpcProxy<PagingLoadResult<M>> proxy;
	private PagingLoader<PagingLoadResult<ModelData>> loader;
	
	/**
	 * {@link RowEditor} utilizado para realizar cambios en los elementos
	 * almacenados en el {@link Store}
	 **/
	private RowEditor<BeanModel> rowEditor;
	
	
	/**
	 * Realiza inicializaciones básicas.
	 **/
	private void basicSetups(String title, ColumnModel cm, RpcProxy<PagingLoadResult<M>> proxy) {
		this.cm = cm;
		this.title = title;
		this.proxy = proxy;
		this.setupToolBars();		
	}
	
	
	/**
	 * Constructor del {@link CustomGrid}. En este caso se necesita que el grid tenga
	 * capacidad de filtrado.
	 **/
	public CustomGrid(String title, ColumnModel cm, HashMap<String, AppConstants.Filtros> filtersConfig, 
					  RpcProxy<PagingLoadResult<M>> proxy) {
		super();
		this.filtersConfig = filtersConfig;
		this.hasFilters = true;
		this.basicSetups(title, cm, proxy);
		
	}
	
	/**
	 * Contructor del {@link CustomGrid}. En este caso se crea un grid simple.
	 **/
	public CustomGrid(String title, ColumnModel cm, RpcProxy<PagingLoadResult<M>> proxy) {
		super();
	}
	
	/**
	 * Constructor del {@link CustomGrid}. En este caso se necesita capacidad de filtrado y
	 * modificar el {@link SelectionModel} del grid por {@link CheckBoxSelectionModel}
	 **/
	public CustomGrid(String title, ColumnModel cm, HashMap<String, Filtros> filtersConfig,
			RpcProxy<PagingLoadResult<M>> proxy,
			CheckBoxSelectionModel<BeanModel> cbsm) {
		super();
		this.filtersConfig = filtersConfig;
		this.hasFilters = true;		
		this.useCheckBoxSm = true;
		this.cbsm = cbsm;
		this.basicSetups(title, cm, proxy);
	}	
	
	/**
	 * Crea los filtros
	 **/
	protected GridFilters createFilters() {
		GridFilters filters = new GridFilters();
		filters.setLocal(false);
		for(String f: filtersConfig.keySet()){
			switch(filtersConfig.get(f)){
				case NUMERIC_FILTER:
					filters.addFilter(new NumericFilter(f));
					break;
				case DATE_FILTER:
					filters.addFilter(new DateFilter(f));
					break;
				case STRING_FILTER:
					filters.addFilter(new StringFilter(f));
					break;
				case BOOLEAN_FILTER:
					filters.addFilter(new BooleanFilter(f));
					break;					
			}
		}
		return filters;
	}

	public HashMap<String, AppConstants.Filtros> getFiltersConfig() {
		return filtersConfig;
	}

	public void setFiltersConfig(HashMap<String, AppConstants.Filtros> filtersConfig) {
		this.filtersConfig = filtersConfig;
	}
	
	/**
	 * Realiza el rendering del Componente.
	 **/
	public void onRender(Element target, int index) {
		super.onRender(target, index);
		reader = new BeanModelReader();
		loader = new BasePagingLoader<PagingLoadResult<ModelData>>(proxy, reader) {
			@Override
			protected Object newLoadConfig() {
				BasePagingLoadConfig config = new BaseFilterPagingLoadConfig();
				return config;
			}
		};
		store = new ListStore<BeanModel>(loader);
		
		this.setLayout(new FitLayout());
		this.setHeading(this.title);
		grid = createGrid();
		setupGridPlugins();
		this.add(grid);
		final PagingToolBar toolBar = new PagingToolBar(AppConstants.PAGE_SIZE);  
	    toolBar.bind(loader);
	    
	    // seteamos el loader
	    grid.setStateId("customgrid");  
	    grid.setStateful(true);	    
	    grid.addListener(Events.Attach, new Listener<GridEvent<BeanModel>>() {  
	    	public void handleEvent(GridEvent<BeanModel> be) {  
	    		BasePagingLoadConfig config = new BaseFilterPagingLoadConfig();  
	    		config.setOffset(0);  
	    		config.setLimit(AppConstants.PAGE_SIZE);  

	    		Map<String, Object> state = grid.getState();  
	    		if (state.containsKey("offset")) {  
	    			int offset = (Integer)state.get("offset");  
	    			int limit = (Integer)state.get("limit");  
	    			config.setOffset(offset);  
	    			config.setLimit(limit);  
	    		}  
	    		loader.load(config);
	    	}  
	    });
	    ((ToolBar) this.getBottomComponent()).add(toolBar);
	}
	
	/**
	 * Inicializa los plugins que sean necesarios para el {@link Grid}.
	 * Este método es llamado luego de que se haya creado el {@link Grid}
	 **/
	public void setupGridPlugins() {
		if(hasFilters){  // Plugin para Filtros
			filters = createFilters();
			grid.addPlugin(filters);
		}
		if(useCheckBoxSm) {  // Plugin para utilizar el SelectionModel tipo CheckBox.
			grid.addPlugin(this.cbsm);
			grid.setSelectionModel(this.cbsm);
		}
		if(useRowEditor){
			rowEditor = new RowEditor<BeanModel>();
			rowEditor.setClicksToEdit(ClicksToEdit.TWO);
			grid.addPlugin(rowEditor);
		}
	}
	
	/**
	 * Inicializa el Top y Bottom ToolBar.
	 **/
	public void setupToolBars() {
		topToolBar = this.createTopToolBar();
		this.setTopComponent(topToolBar);
		
		//creamos bottom toolbar
		ToolBar tb = new ToolBar();
		tb.add(new FillToolItem());
		this.setBottomComponent(tb);		
	}

	public Boolean getHasFilters() {
		return hasFilters;
	}

	public void setHasFilters(Boolean hasFilters) {
		this.hasFilters = hasFilters;
	}
	
	/**
	 * Crea el Top {@link ToolBar}.
	 **/
	protected ToolBar createTopToolBar() {
		ToolBar tb = new ToolBar();
		if(this.hasFilters){
			Button clearButton = new Button("Eliminar Filtros");
			clearButton.setIconStyle("clear_icon");
			clearButton.addListener(Events.OnClick, new Listener<ButtonEvent>() {
				@Override
				public void handleEvent(ButtonEvent be) {
					filters.clearFilters();
				}
			});
			tb.add(clearButton);
		}
		return tb;
	}
	
	/**
	 * Crea el {@link Grid}
	 **/
	protected Grid<BeanModel> createGrid() {
		Grid<BeanModel> g = new Grid<BeanModel>(store, cm);
		g.setBorders(false);
		g.setStripeRows(true);
		g.setColumnLines(true);
		g.setLoadMask(true);
		return g;
	}

	public Grid<BeanModel> getGrid() {
		return grid;
	}

	public void setGrid(Grid<BeanModel> grid) {
		this.grid = grid;
	}


	public Boolean getUseRowEditor() {
		return useRowEditor;
	}


	public void setUseRowEditor(Boolean useRowEditor) {
		this.useRowEditor = useRowEditor;
	}


	public RowEditor<BeanModel> getRowEditor() {
		return rowEditor;
	}


	public void setRowEditor(RowEditor<BeanModel> re) {
		this.rowEditor = re;
	}
	
}
