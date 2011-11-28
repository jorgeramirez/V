package v.client.util;

import java.util.HashMap;
import java.util.Map;

import v.client.AppConstants;

import com.extjs.gxt.ui.client.data.ModelData;
import com.extjs.gxt.ui.client.data.ModelIconProvider;
import com.extjs.gxt.ui.client.util.IconHelper;
import com.google.gwt.user.client.ui.AbstractImagePrototype;

/**
 * Utilizado para asociar los iconos a los elementos
 * del NavigationTree
 * 
 * @author Jorge Ramírez <jorgeramirez1990@gmail.com>
 **/
public class NavigationIconProvider implements ModelIconProvider<ModelData> {
	private Map<String, String> iconsMap;
	
	public NavigationIconProvider() {
		iconsMap = new HashMap<String, String>();
		iconsMap.put(AppConstants.USUARIOS_LABEL, "images/user.png");
		iconsMap.put(AppConstants.CAJA_LABEL, "images/caja.png");
		iconsMap.put(AppConstants.PRODUCTO_LABEL, "images/producto.png");
		iconsMap.put(AppConstants.PROVEEDOR_LABEL, "images/proveedor.png");
		iconsMap.put(AppConstants.CLIENTE_LABEL, "images/cliente.png");
		iconsMap.put(AppConstants.REGISTRAR_VENTA_LABEL, "images/venta-1.png");
		iconsMap.put(AppConstants.REGISTRAR_COMPRA_LABEL, "images/compra.png");
		iconsMap.put(AppConstants.LISTAR_VENTAS_LABEL, "images/listar_ventas.png");
		iconsMap.put(AppConstants.LISTAR_COMPRAS_LABEL, "images/listar_compras.png");
		iconsMap.put(AppConstants.COBRAR_FACTURA_LABEL, "images/pago.png");
		iconsMap.put(AppConstants.CIERRE_CAJA_LABEL, "images/cierre_caja1.png");
		iconsMap.put(AppConstants.LOGOUT_LABEL, "images/logout.jpg");
	}
	
	@Override
	public AbstractImagePrototype getIcon(ModelData model) {
		String key = model.toString();
		if(iconsMap.containsKey(key)){
			return IconHelper.createPath(iconsMap.get(key));
		}
		return null;
	}

}

