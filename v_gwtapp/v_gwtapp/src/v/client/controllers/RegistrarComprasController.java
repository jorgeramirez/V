package v.client.controllers;

import java.util.List;

import v.client.AppConstants;
import v.client.AppViewport;
import v.client.panels.NuevaCompraPanel;
import v.client.rpc.CompradorServiceAsync;
import v.modelo.FacturaCompra;
import v.modelo.FacturaDetalleCompra;
import v.modelo.Proveedor;

import com.extjs.gxt.ui.client.Registry;
import com.extjs.gxt.ui.client.event.BaseEvent;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.Listener;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.widget.LayoutContainer;
import com.extjs.gxt.ui.client.widget.MessageBox;
import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * Controlador de Registro de Compra
 * 
 * @author Jorge Ramírez <jorgeramirez1990@gmail.com> 
 **/
public class RegistrarComprasController extends AbstractController {
	private final CompradorServiceAsync service = (CompradorServiceAsync)Registry.get(AppConstants.COMPRADOR_SERVICE);
	private NuevaCompraPanel panel;
	
	public RegistrarComprasController() {
		super(AppConstants.REGISTRAR_COMPRA_LABEL);
	}

	@Override
	public void init() {
		panel = new NuevaCompraPanel();
		
		bindHandlers();

		LayoutContainer cp = (LayoutContainer)Registry.get(AppViewport.CENTER_REGION);
		cp.add(panel);
		cp.layout();		
	}

	/**
	 * Asocia los handlers para {@link NuevaCompraPanel} definidos
	 * en este controller
	 **/
	private void bindHandlers() {
		
		panel.addListener(Events.Render, new Listener<BaseEvent>() {

			@Override
			public void handleEvent(BaseEvent be) {
				
				// handlers para boton seleccionar proveedor
				panel.getProviderSelector().getSelectButton().addSelectionListener(new SelectionListener<ButtonEvent>() {
					
					@Override
					public void componentSelected(ButtonEvent ce) {
						panel.getProviderSelector().showDialog();
					}
				});
				
				//handlers para agregar producto
				panel.getGrid().getAddProductButton().addSelectionListener(new SelectionListener<ButtonEvent>() {

					@Override
					public void componentSelected(ButtonEvent ce) {
						panel.getGrid().showProductsDialog();
					}
				});
				
				//handlers para boton Guardar
				panel.getSaveButton().addSelectionListener(new SelectionListener<ButtonEvent>() {
					
					@Override
					public void componentSelected(ButtonEvent ce) {
						onSaveClicked();
					}
				});
			}
		});
	}
	
	/**
	 * Método que guarda la compra creada.
	 **/
	private void onSaveClicked() {
		List<FacturaDetalleCompra> details = panel.getGrid().getDetalles();
		Proveedor provider = panel.getProviderSelector().getProvider();
		if(details == null || provider == null){
			MessageBox.alert("Advertencia", "Debe establecer todos los datos de la compra para poder guardarla", null);
			return;
		}
		FacturaCompra compra = new FacturaCompra();
		compra.setDetalles(details);
		compra.setProveedor(provider);
		Double total = 0.0;
		for(FacturaDetalleCompra d: details){
			total += d.getPrecio() * d.getCantidad();
		}
		compra.setTotal(total);
		
		service.registrarCompra(compra, new AsyncCallback<Boolean>() {

			@Override
			public void onFailure(Throwable caught) {
				MessageBox.alert("Error en el Servidor", caught.getMessage(), null);
			}

			@Override
			public void onSuccess(Boolean ok) {
				if(ok){
					MessageBox.info("Operación Exitosa", "La compra se ha guardado correctamente", null);
					panel.getProviderSelector().getBindings().unbind();
					panel.getGrid().getGrid().getStore().removeAll();
				}else{
					MessageBox.alert("Error", "La compra no pudo guardarse, por favor intente de otra vez", null);
				}
			}
		});
	}
}
