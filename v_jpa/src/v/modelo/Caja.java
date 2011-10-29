package v.modelo;

import java.io.Serializable;
import java.lang.Integer;
import java.lang.Long;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Entity implementation class for Entity: Caja
 *
 */
@Entity
@Table(name="caja")
public class Caja implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="id")
	private Long id;
	
	@Column(name="numero_caja", nullable=false)
	private Integer numeroCaja;
	
	@OneToMany(mappedBy="caja")
	private List<Usuario> cajeros;
	
	@OneToMany(mappedBy="caja")
	private List<Pago> pagos; //pagos registrados en la caja
	
	@Transient
	private static final long serialVersionUID = 1L;

	public Caja() {
		super();
	}
	
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
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

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Caja)){
            return false;
        }
        Caja other = (Caja) object;
        if ((this.id == null && other.id != null) ||
                (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Caja: " + this.numeroCaja.toString();
    }	
}
