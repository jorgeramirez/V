package v.shared.model;

import com.extjs.gxt.ui.client.data.BaseTreeModel;

public class Funcionalidad extends BaseTreeModel {

	private static final long serialVersionUID = 1L;
	private static long ID = 0;

	public Funcionalidad() {
	   set("id", ID++);
	}

	public Funcionalidad(String nombre) {
		set("id", ID++);
	    set("nombre", nombre);
	}

	  
	public Funcionalidad(String nombre, BaseTreeModel[] hijos) {
		this(nombre);
	    for (int i = 0; i < hijos.length; i++) {
	      add(hijos[i]);
	    }
	}

	public Integer getId() {
		return (Integer) get("id");
	}

	public String getNombre() {
		return (String) get("nombre");
	}
	
	public String toString() {
	    return getNombre();
	}
}
