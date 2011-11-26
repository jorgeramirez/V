package v.modelo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Entity implementation class for Entity: Producto
 *
 */
@Entity
@Table(name="producto")
@NamedQueries({
	@NamedQuery(name="Producto.findByCodigo", query="select p from Producto p where p.codigo like :codigo"),
	@NamedQuery(name="Producto.count", query="select count(p) from Producto p")
})
public class Producto implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="codigo", unique=true, nullable=false, length=15)
	private String codigo;
	
	@Column(name="nombre", length=50, nullable=false)
	private String nombre;
	
	@Column(name="costo")
	private Double costo;
	
	@Column(name="cantidad")
	private Integer cantidad;
	
	@Column(name="porcentaje_ganancia", nullable=false)
	private Double porcentajeGanancia;
	
	/*@ManyToMany
	@JoinTable(name="productos_proveedores", 
		joinColumns=
			@JoinColumn(name="id_producto", referencedColumnName="id"),
		inverseJoinColumns=
			@JoinColumn(name="id_proveedor", referencedColumnName="id"))	
	private List<Proveedor> proveedores;*/
	
	@Transient
	private static final long serialVersionUID = 1L;

	public Producto() {
		super();
	}
	
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getCodigo() {
		return this.codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
	
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public Double getCosto() {
		return this.costo;
	}

	public void setCosto(Double costo) {
		this.costo = costo;
	}
	
	public Integer getCantidad() {
		return this.cantidad;
	}

	public void setCantidad(Integer cantidad) {
		this.cantidad = cantidad;
	}
	
	public Double getPorcentajeGanancia() {
		return this.porcentajeGanancia;
	}

	public void setPorcentajeGanancia(Double porcentajeGanancia) {
		this.porcentajeGanancia = porcentajeGanancia;
	}

    /*public List<Proveedor> getProveedores() {
		return proveedores;
	}

	public void setProveedores(List<Proveedor> proveedores) {
		this.proveedores = proveedores;
	}*/

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }
	
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Producto)) {
            return false;
        }
        Producto other = (Producto) object;
        if ((this.id == null && other.id != null) ||
                (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.nombre + " [" + this.codigo + "]";
    }	
   
}
