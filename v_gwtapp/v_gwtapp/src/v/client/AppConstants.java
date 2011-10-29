package v.client;

import com.google.gwt.core.client.GWT;

public class AppConstants {
	
	public static enum Filtros {
		NUMERIC_FILTER,
		DATE_FILTER,
		STRING_FILTER,
		BOOLEAN_FILTER
	};
	
	public static final String ADMINISTRADOR_SERVICE = GWT.getModuleBaseURL() + "administrador";

}
