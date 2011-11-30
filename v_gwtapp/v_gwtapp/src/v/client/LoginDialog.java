package v.client;

import v.client.rpc.LoginServiceAsync;
import v.modelo.Usuario;

import com.extjs.gxt.ui.client.GXT;
import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.ComponentEvent;
import com.extjs.gxt.ui.client.event.KeyListener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.util.IconHelper;
import com.extjs.gxt.ui.client.widget.Dialog;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.extjs.gxt.ui.client.widget.Status;
import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.form.TextField;
import com.extjs.gxt.ui.client.widget.layout.FormLayout;
import com.extjs.gxt.ui.client.widget.toolbar.FillToolItem;
import com.google.gwt.user.client.Element;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.RootPanel;


public class LoginDialog extends Dialog {
	protected TextField<String> userName;
	protected TextField<String> password;
	protected Button reset;
	protected Button login;
	protected Status status;

	public LoginDialog() {
		FormLayout layout = new FormLayout();
		layout.setLabelWidth(90);
		layout.setDefaultWidth(155);
		setLayout(layout);

		setButtonAlign(HorizontalAlignment.LEFT);
		setButtons("");
		setIcon(IconHelper.createStyle("user"));
		setHeading("V Login");
		setModal(true);
		setBodyBorder(true);
		setBodyStyle("padding: 8px;background: none");
		setWidth(300);
		setResizable(false);

		KeyListener keyListener = new KeyListener() {
			public void componentKeyUp(ComponentEvent event) {
				validate();
			}

		};

		userName = new TextField<String>();
		userName.setMinLength(4);
		userName.setFieldLabel("Username");
		userName.addKeyListener(keyListener);
		add(userName);

		password = new TextField<String>();
		password.setPassword(true);
		password.setFieldLabel("Password");
		password.addKeyListener(keyListener);
		add(password);

		setFocusWidget(userName);
		this.setClosable(false);

	}
	
	public void onRender(Element target, int pos) {
		super.onRender(target, pos);
		GXT.hideLoadingPanel("loading");
	}

	@Override
	protected void createButtons() {
		super.createButtons();
		status = new Status();
		status.setBusy("please wait...");
		status.hide();
		status.setAutoWidth(true);
		getButtonBar().add(status);

		getButtonBar().add(new FillToolItem());

		reset = new Button("Reset");
		reset.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				userName.reset();
				password.reset();
				validate();
				userName.focus();
			}

		});

		login = new Button("Login");
		login.disable();
		login.setIconStyle("icon-login");
		login.addSelectionListener(new SelectionListener<ButtonEvent>() {
			public void componentSelected(ButtonEvent ce) {
				onSubmit();
			}
		});

		addButton(reset);
		addButton(login);


	}

	protected void onSubmit() {
		getButtonBar().disable();
		final Dialog dialog = this;
		LoginServiceAsync loginService = Registry.get(AppConstants.LOGIN_SERVICE);
		loginService.login(userName.getValue(), password.getValue(), new AsyncCallback<Usuario>() {
			
			@Override
			public void onSuccess(Usuario usuario) {
				if (usuario != null){
					dialog.hide();
					AppViewport viewport = new AppViewport(usuario.getRol());
					RootPanel.get().add(viewport);
				}
				else {
					MessageBox.alert("Error", "Username y password incorrectos", null);
				}
			}
			
			@Override
			public void onFailure(Throwable caught) {
				MessageBox.alert("Error en el Servidor", caught.getMessage(), null);
				
			}
		});
	}

	protected boolean hasValue(TextField<String> field) {
		return field.getValue() != null && field.getValue().length() > 0;
	}

	protected void validate() {
		login.setEnabled(hasValue(userName) && hasValue(password)
				&& password.getValue().length() > 1);
	}

}
