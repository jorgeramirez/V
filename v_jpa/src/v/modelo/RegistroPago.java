package v.modelo;

import java.io.Serializable;
import java.lang.Boolean;
import java.lang.Long;
import java.lang.String;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;


/**
 * Entity implementation class for Entity: RegistroPago
 *
 */
@Entity
@Table(name="registro_pago")
public class RegistroPago implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="id_pago")
	private Long idPago;
	
	@Temporal(value=TemporalType.TIMESTAMP)
	@Column(name="fecha", nullable=false)
	private Date fecha;
	
	@Column(name="realizado", nullable=false)
	private Boolean realizado;
	
	@Column(name="mensaje_error", length=70)
	private String mensajeError;
	
	@Transient
	private static final long serialVersionUID = 1L;

	public RegistroPago() {
		super();
	}  
	
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}  
	
	public Long getIdPago() {
		return idPago;
	}

	public void setIdPago(Long idPago) {
		this.idPago = idPago;
	}

	public Date getFecha() {
		return this.fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}   
	
	public Boolean getRealizado() {
		return this.realizado;
	}

	public void setRealizado(Boolean realizado) {
		this.realizado = realizado;
	}  
	
	public String getMensajeError() {
		return this.mensajeError;
	}

	public void setMensajeError(String mensajeError) {
		this.mensajeError = mensajeError;
	}

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof RegistroPago)){
            return false;
        }
        RegistroPago other = (RegistroPago) object;
        if ((this.id == null && other.id != null) ||
                (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
}
