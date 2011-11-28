package v.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;


/**
 * Entity implementation class for Entity: Factura
 *
 */
@MappedSuperclass
public class Factura implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Integer numeroFactura;
	
	@Temporal(value=TemporalType.DATE)
	@Column(name="fecha", nullable=false)
	private Date fecha;
	
	@Column(name="total", nullable=false)
	private Double total;
	
	@Transient
	private static final long serialVersionUID = 1L;

	public Factura() {
		super();
	}
	
	public Integer getNumeroFactura() {
		return this.numeroFactura;
	}

	public void setNumeroFactura(Integer numeroFactura) {
		this.numeroFactura = numeroFactura;
	}
	
	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}   
	
	public Double getTotal() {
		return this.total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numeroFactura != null ? numeroFactura.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Factura)){
            return false;
        }
        Factura other = (Factura) object;
        if ((this.numeroFactura == null && other.numeroFactura != null) ||
                (this.numeroFactura != null && !this.numeroFactura.equals(other.numeroFactura))) {
            return false;
        }
        return true;
    }
}
