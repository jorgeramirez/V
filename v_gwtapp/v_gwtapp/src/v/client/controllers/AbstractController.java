package v.client.controllers;

/**
 * Clase base para los Controladores.
 **/
public abstract class AbstractController {
	
	private String id;
	
	public AbstractController(String id) {
		this.id = id;
	}
	
	/**
	 * Metodo que se encarga de inicializar el controlador.
	 * Setea las vistas,etc.
	 **/
	public abstract void init();

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
