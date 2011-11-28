package v.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;


/**
 * Entity implementation class for Entity: Pago
 *
 */
@Entity
@Table(name="pago")
@NamedQueries({
	@NamedQuery(name="Pago.findNoCerrados", query="select p from Pago p where p.usuario.id = :id and p.estado like 'no cerrado'")
})
public class Pago implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="monto", nullable=false)
	private Double monto;
	
	@Column(name="estado", nullable=false, length=20)
	private String estado;
	
	@Temporal(value=TemporalType.TIMESTAMP)
	@Column(name="fecha", nullable=false)
	private Date fecha;	
	
	@ManyToOne(optional=false, fetch=FetchType.EAGER, cascade=CascadeType.REFRESH)
	@JoinColumn(name="id_factura_venta", referencedColumnName="id")
	private FacturaVenta factura;
	
	@ManyToOne(optional=false, fetch=FetchType.EAGER, cascade=CascadeType.REFRESH)
	@JoinColumn(name="id_usuario", referencedColumnName="id")
	private Usuario usuario;	
	
	@ManyToOne(optional=false, fetch=FetchType.EAGER, cascade=CascadeType.REFRESH)
	@JoinColumn(name="id_caja", referencedColumnName="id")
	private Caja caja;  //la caja donde se registro el pago.
	
	@Transient
	private static final long serialVersionUID = 1L;

	public Pago() {
		super();
	}
	
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Double getMonto() {
		return this.monto;
	}

	public void setMonto(Double monto) {
		this.monto = monto;
	}
	
	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
    public FacturaVenta getFactura() {
		return factura;
	}

	public void setFactura(FacturaVenta factura) {
		this.factura = factura;
	}

	public Caja getCaja() {
		return caja;
	}

	public void setCaja(Caja caja) {
		this.caja = caja;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Pago)){
            return false;
        }
        Pago other = (Pago) object;
        if ((this.id == null && other.id != null) ||
                (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }
   
}
