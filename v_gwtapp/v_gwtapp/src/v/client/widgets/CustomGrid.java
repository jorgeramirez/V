package v.client.widgets;

import java.util.HashMap;
import java.util.List;

import v.client.AppConstants;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.data.BaseListLoader;
import com.extjs.gxt.ui.client.data.BeanModel;
import com.extjs.gxt.ui.client.data.BeanModelReader;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.ListLoader;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.RpcProxy;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.grid.CheckBoxSelectionModel;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.grid.LiveGridView;
import com.extjs.gxt.ui.client.widget.grid.filters.BooleanFilter;
import com.extjs.gxt.ui.client.widget.grid.filters.DateFilter;
import com.extjs.gxt.ui.client.widget.grid.filters.GridFilters;
import com.extjs.gxt.ui.client.widget.grid.filters.NumericFilter;
import com.extjs.gxt.ui.client.widget.grid.filters.StringFilter;
import com.extjs.gxt.ui.client.widget.toolbar.ToolBar;
import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.rpc.AsyncCallback;

@SuppressWarnings({"unused"})
public class CustomGrid<M> extends LayoutContainer {

	private HashMap<String, AppConstants.Filtros> filtersConfig;
	private Boolean hasFilters = false;
	private Grid<BeanModel> grid;
	private ToolBar topToolBar;
	private ListStore<BeanModel> store;
	private ColumnModel cm;
	private ContentPanel cp;
	private String title = "Grid Panel";
	private GridFilters filters;
	private BeanModelReader reader;
	private RpcProxy<List<M>> proxy;
	private ListLoader<ListLoadResult<ModelData>> loader;
	private Class<?> serviceClass; // GWT service class
	private String serviceName; 
	private String methodName;
	
	
	public CustomGrid(String title, ColumnModel cm, HashMap<String, AppConstants.Filtros> filtersConfig, 
					  RpcProxy<List<M>> proxy) {
		super();
		this.filtersConfig = filtersConfig;
		this.hasFilters = true;
		this.cm = cm;
		this.title = title;
		this.proxy = proxy;
		
	}
	
	public CustomGrid(String title, ColumnModel cm, RpcProxy<List<M>> proxy) {
		super();
		this.cm = cm;
		this.title = title;
		this.proxy = proxy;
	}
	
	
	public GridFilters createFilters() {
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
	
	public void onRender(Element target, int index) {
		super.onRender(target, index);
		reader = new BeanModelReader();
		loader = new BaseListLoader<ListLoadResult<ModelData>>(proxy, reader);
		store = new ListStore<BeanModel>(loader);
		loader.load();
		
		cp = new ContentPanel();
		cp.setBodyBorder(true);
		cp.setHeading(this.title);
		grid = createGrid();
		if(hasFilters){
			filters = createFilters();
			grid.addPlugin(filters);
		}
		cp.add(grid);
		topToolBar = createToolBar();
		cp.setTopComponent(topToolBar);
		add(cp);
	}

	public Boolean getHasFilters() {
		return hasFilters;
	}

	public void setHasFilters(Boolean hasFilters) {
		this.hasFilters = hasFilters;
	}
	
	private ToolBar createToolBar() {
		ToolBar tb = new ToolBar();
		if(this.hasFilters){
			Button clearButton = new Button("Eliminar Filtros");
			clearButton.setIconStyle("clear_icon");
			tb.add(clearButton);
		}
		return tb;
	}
	
	private Grid<BeanModel> createGrid() {
		Grid<BeanModel> g = new Grid<BeanModel>(store, cm);
		g.setBorders(false);
		g.setStripeRows(true);
		g.setColumnLines(true);
		g.setSelectionModel(new CheckBoxSelectionModel<BeanModel>());
		g.setLoadMask(true);
		LiveGridView lv = new LiveGridView();
		lv.setEmptyText("No se encontraron filas en el servidor");
		lv.setRowHeight(32);
		g.setView(lv);
		return g;
	}
	
}
