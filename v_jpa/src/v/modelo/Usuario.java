package v.modelo;

import java.lang.String;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * Entity implementation class for Entity: Usuario
 *
 */
@Entity
@Table(name="usuario")
public class Usuario extends Persona {

	@Column(name="username", unique=true, nullable=false, length=25)
	private String username;
	
	@Column(name="password", nullable=false, length=64)
	private String password;

	@Column(name="email", nullable=false, unique=true, length=100)
	private String email;
	
	@Transient
	private static final long serialVersionUID = 1L;

	public Usuario() {
		super();
	}
	
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
}
