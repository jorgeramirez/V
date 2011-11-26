package v.client.util;

import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.grid.filters.NumericFilter;
import com.extjs.gxt.ui.client.widget.grid.filters.RangeMenu;

/**
 * Este Filter te permite especificar el tipo de {@link Number} utilizado a la hora
 * de convertir de {@link String} a {@link Number}. Default a {@link Double}
 **/

public class XNumericFilter extends NumericFilter {

    private Class<?> type;
    
    public XNumericFilter(String dataIndex) {
        super(dataIndex);

        fixPropertyEditorType();
    }

    /**
     * Especifica el tipo de {@link Number} utilizado a la hora de convertir
     * de {@link String} a {@link Number}. Default a {@link Double}
     * 
     * @param type
     *            el tipo de {@link Number} (Short, Integer, Long, Float, Double).
     */
    public void setPropertyEditorType(Class<?> type) {
        this.type = type;
    }

    private void fixPropertyEditorType() {
        final RangeMenu rangeMenu = getRangeMenu();
        rangeMenu.addListener(Events.Render, new Listener<BaseEvent>() {

        	@Override
            public void handleEvent(BaseEvent be) {
                getNumberFieldLt(rangeMenu).setPropertyEditorType(type);
                getNumberFieldGt(rangeMenu).setPropertyEditorType(type);
                getNumberFieldEq(rangeMenu).setPropertyEditorType(type);
            }
        });
    }

    private native NumberField getNumberFieldLt(RangeMenu rangeMenu) /*-{
        return rangeMenu.@com.extjs.gxt.ui.client.widget.grid.filters.RangeMenu::lt;
    }-*/;

    private native NumberField getNumberFieldGt(RangeMenu rangeMenu) /*-{
        return rangeMenu.@com.extjs.gxt.ui.client.widget.grid.filters.RangeMenu::gt;
    }-*/;

    private native NumberField getNumberFieldEq(RangeMenu rangeMenu) /*-{
        return rangeMenu.@com.extjs.gxt.ui.client.widget.grid.filters.RangeMenu::eq;
    }-*/;

    private native RangeMenu getRangeMenu() /*-{
        return this.@com.extjs.gxt.ui.client.widget.grid.filters.NumericFilter::rangeMenu;
    }-*/;
}