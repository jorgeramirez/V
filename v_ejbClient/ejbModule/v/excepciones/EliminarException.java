package v.excepciones;

import java.io.Serializable;

import javax.ejb.ApplicationException;

@ApplicationException(rollback=true)
public class EliminarException extends Exception implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public EliminarException(String msg) {
		super(msg);
	}

}
