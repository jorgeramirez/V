package v.client.widgets;

import v.client.Dispatcher;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.TreePanelEvent;
import com.extjs.gxt.ui.client.store.TreeStore;
import com.extjs.gxt.ui.client.widget.treepanel.TreePanel;
import com.google.gwt.user.client.Element;

public class NavigationTree extends TreePanel<ModelData> {

	public NavigationTree(TreeStore<ModelData> store) {
		super(store);
	}
	
	public void onRender(Element target, int index) {
		super.onRender(target, index);
		this.expandAll();

		this.addListener(Events.OnClick, new Listener<TreePanelEvent<ModelData>>() {
			@Override
			public void handleEvent(TreePanelEvent<ModelData> te){
				Dispatcher d = (Dispatcher)Registry.get("dispatcher");
				d.distpatch(te.getItem().toString());
			}
		});
	}

}
