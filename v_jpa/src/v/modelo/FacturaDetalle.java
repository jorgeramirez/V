package v.modelo;

import java.io.Serializable;
import java.lang.Double;
import java.lang.Integer;
import java.lang.Long;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;


/**
 * Entity implementation class for Entity: FacturaDetalle
 *
 */
@MappedSuperclass
public class FacturaDetalle implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="cantidad", nullable=false)
	private Integer cantidad;
	
	@Column(name="precio", nullable=false)
	private Double precio;
	
	public Double getPrecio() {
		return precio;
	}

	public void setPrecio(Double precio) {
		this.precio = precio;
	}

	@Transient
	private static final long serialVersionUID = 1L;

	public FacturaDetalle() {
		super();
	}  
	
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Integer getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof FacturaDetalle)){
            return false;
        }
        FacturaDetalle other = (FacturaDetalle) object;
        if ((this.id == null && other.id != null) ||
                (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
}
