package v.client.widgets;

import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.store.TreeStore;
import com.extjs.gxt.ui.client.widget.treepanel.TreePanel;
import com.google.gwt.user.client.Element;

public class NavigationTree extends TreePanel<ModelData> {

	public NavigationTree(TreeStore<ModelData> store) {
		super(store);
	}
	
	public void onRender(Element target, int index) {
		super.onRender(target, index);
	}

}
