package v.modelo;

import java.io.Serializable;
import java.lang.Integer;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Entity implementation class for Entity: Caja
 *
 */
@Entity
@Table(name="caja")
@NamedQueries({
	@NamedQuery(name="Caja.findAll", query="select c from Caja c"),
	@NamedQuery(name="Caja.count", query="select count(c) from Caja c")
})
public class Caja implements Serializable {


	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)	
	@Column(name="id", nullable=false)
	private Integer numeroCaja;

	@Column(name="descripcion", nullable=false, length=120)
	private String descripcion;	
	
	@OneToMany(mappedBy="caja")
	private List<Usuario> cajeros;
	
	@OneToMany(mappedBy="caja")
	private List<Pago> pagos; //pagos registrados en la caja
	
	@Transient
	private static final long serialVersionUID = 1L;

	public Caja() {
		super();
	}
	
	public Integer getNumeroCaja() {
		return this.numeroCaja;
	}

	public void setNumeroCaja(Integer numeroCaja) {
		this.numeroCaja = numeroCaja;
	}

    public List<Usuario> getCajeros() {
		return cajeros;
	}

	public void setCajeros(List<Usuario> cajeros) {
		this.cajeros = cajeros;
	}

	public List<Pago> getPagos() {
		return pagos;
	}

	public void setPagos(List<Pago> pagos) {
		this.pagos = pagos;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (numeroCaja != null ? numeroCaja.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Caja)){
            return false;
        }
        Caja other = (Caja) object;
        if ((this.numeroCaja == null && other.numeroCaja != null) ||
                (this.numeroCaja != null && !this.numeroCaja.equals(other.numeroCaja))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Caja: " + this.numeroCaja.toString();
    }	
}
