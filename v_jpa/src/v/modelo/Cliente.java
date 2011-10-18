package v.modelo;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import v.modelo.Persona;

/**
 * Entity implementation class for Entity: Cliente
 *
 */
@Entity
@Table(name="cliente")
public class Cliente extends Persona {
	
	@Transient
	private static final long serialVersionUID = 1L;
}
