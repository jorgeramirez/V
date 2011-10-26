package v.excepciones;

import java.io.Serializable;

import javax.ejb.ApplicationException;

@ApplicationException(rollback=true)
public class GuardarException extends Exception implements Serializable {

	private static final long serialVersionUID = 1L;

	public GuardarException(String msg) {
		super(msg);
	}
	
}
