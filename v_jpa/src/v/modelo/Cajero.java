package v.modelo;

import java.lang.String;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.persistence.Table;
import v.modelo.Persona;

/**
 * Entity implementation class for Entity: Cajero
 *
 */
@Entity
@Table(name="cajero")
public class Cajero extends Persona {

	@Column(name="email", nullable=false, unique=true, length=100)
	private String email;

	@Transient
	private static final long serialVersionUID = 1L;

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
   
}
