package v.modelo;

import java.io.Serializable;
import java.lang.Long;
import java.lang.String;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

/**
 * Entity implementation class for Entity: 
 *
 */
@MappedSuperclass
public class Persona implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="cedula", unique=true, nullable=false, length=10)
	private String cedula;
	
	@Column(name="nombre", nullable=false, length=50)
	private String nombre;
	
	@Column(name="apellido", nullable=false, length=50)
	private String apellido;

	@Column(name="direccion", length=70, nullable=false)
	private String direccion;
	
	@Column(name="telefono", length=20)
	private String telefono;

	@Transient
	private static final long serialVersionUID = 1L;

	public Persona() {
		super();
	}
	
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getCedula() {
		return this.cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}
	
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}   
	public String getApellido() {
		return this.apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public String getDireccion() {
		return this.direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}
	
	public String getTelefono() {
		return this.telefono;
	}

	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object){
        if (!(object instanceof Persona)){
            return false;
        }
        Persona other = (Persona) object;
        if ((this.id == null && other.id != null) ||
                (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.nombre + " [" + this.cedula + "]";
    }
}
