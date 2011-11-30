package v.client.dialogs;

import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.form.Field;
import com.extjs.gxt.ui.client.widget.form.FormPanel;
import com.extjs.gxt.ui.client.widget.form.FormPanel.LabelAlign;
import com.extjs.gxt.ui.client.widget.form.NumberField;
import com.extjs.gxt.ui.client.widget.form.Validator;
import com.extjs.gxt.ui.client.widget.layout.FitLayout;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.google.gwt.user.client.Element;

/**
 * Define un {@link Dialog} para eliminación de un elemento
 * del store pasado como parámetro en el constructor.
 * 
 * @author Jorge Ramírez <jorgeramirez1990@gmail.com>
 **/
public class RemoveFromStoreDialog extends Dialog {
	private ListStore<?> store;
	private FormPanel form;
	private NumberField index; 
	
	public RemoveFromStoreDialog(ListStore<?> store, String title){
		this.store = store;
		this.setBodyBorder(false);
		this.setHeading(title);
		this.setLayout(new FitLayout());
		this.setHideOnButtonClick(false);
		this.okText = "Remover";
		this.cancelText = "Cancelar";
		this.setButtons(Dialog.OKCANCEL);
		this.setSize(425, 130);
		this.setModal(true);		
	}
	
	public void onRender(Element parent, int pos) {
		super.onRender(parent, pos);
		form = buildFormPanel();
		
		this.add(form);
		
		final Dialog me = this;
		// handler para botón Remover.
		getButtonById(Dialog.OK).addSelectionListener(new SelectionListener<ButtonEvent>() {
			
			@Override
			public void componentSelected(ButtonEvent ce) {
				if(form.isValid()){
					store.remove(getIndex() - 1);
					me.hide();
				}
			}
		});
		
		// handler para el botón cancelar
		getButtonById(Dialog.CANCEL).addSelectionListener(new SelectionListener<ButtonEvent>() {
			
			@Override
			public void componentSelected(ButtonEvent ce) {
				me.hide();
			}
		});		
	}
	
	public FormPanel buildFormPanel() {
		// creamos el form panel
		FormPanel form = new FormPanel();
		form.setHeaderVisible(false);
		form.setFrame(true);
		
		// field para el índice a eliminar del store
		index = new NumberField();
		index.setPropertyEditorType(Integer.class);
		index.setFieldLabel("Número del Detalle");
		index.setValidator(new Validator() {
			
			@Override
			public String validate(Field<?> field, String value) {
				int pos = Integer.parseInt(value);
				String errorMsg = "El número de elemento a eliminar debe estar entre 1 y " + store.getCount(); 
				return  pos > 0 && pos <= store.getCount() ? null: errorMsg; 
			}
		});
		index.setAllowBlank(false);
		FormLayout formlayout = new FormLayout(LabelAlign.LEFT);
		formlayout.setLabelWidth(150);
		LayoutContainer layout = new LayoutContainer(formlayout);
		layout.add(index);
		form.add(layout);

		return form;
	}
	
	public int getIndex() {
		return (Integer)index.getValue();
	}

}
