package v.client;

import com.google.gwt.core.client.GWT;

public class AppConstants {
	
	public static enum Filtros {
		NUMERIC_FILTER,  // valores double
		INTEGER_FILTER,  // valores integer
		DATE_FILTER,
		STRING_FILTER,
		BOOLEAN_FILTER
	};
	
	public static final String ADMINISTRADOR_SERVICE = GWT.getModuleBaseURL() + "administrador";
	public static final String COMPRADOR_SERVICE = GWT.getModuleBaseURL() + "comprador";
	public static final String LOGIN_SERVICE = GWT.getModuleBaseURL() + "login";
	
	// labels utilizados en el Tree de navegacion.
	public static final String ABM_LABEL = "ABM";
	public static final String VENTAS_LABEL = "Ventas";
	public static final String COMPRAS_LABEL = "Compras";
	public static final String SISTEMA_LABEL = "Sistema";
	public static final String USUARIOS_LABEL = "Usuarios";
	public static final String CLIENTE_LABEL = "Cliente";
	public static final String VENDEDOR_LABEL = "Vendedor";
	public static final String PROVEEDOR_LABEL = "Proveedor";
	public static final String CAJA_LABEL = "Caja";
	public static final String PRODUCTO_LABEL = "Producto";
	public static final String REGISTRAR_VENTA_LABEL = "Registrar Venta";
	public static final String REGISTRAR_COMPRA_LABEL = "Registrar Compra";
	public static final String OPERACIONES_CAJA_LABEL = "Operaciones Caja";
	public static final String REGISTRAR_PAGO_LABEL = "Registrar Pago";
	public static final String CIERRE_CAJA_LABEL = "Cierre de Caja";
	public static final String LISTAR_VENTAS_LABEL = "Listar Ventas";
	public static final String LISTAR_COMPRAS_LABEL = "Listar Compras";
	public static final String LOGOUT_LABEL = "Salir";
	
	public static final int PAGE_SIZE = 50;
	
	//nombre de los roles que maneja el sistema
	public static final String ADMINISTRADOR_ROL = "administrador";
	public static final String CAJERO_ROL = "cajero";
	public static final String VENDEDOR_ROL = "vendedor";
	public static final String COMPRADOR_ROL = "comprador";

}
