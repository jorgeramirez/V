package v.modelo;

import java.io.Serializable;
import java.lang.Long;
import java.lang.String;
import java.util.List;

import javax.persistence.Entity;
//import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import javax.persistence.Transient;

/**
 * Entity implementation class for Entity: Proveedor
 *
 */
@Entity
@Table(name="proveedor")
@NamedQueries({
	@NamedQuery(name="Proveedor.count", query="select count(p) from Proveedor p"),
	@NamedQuery(name="Proveedor.findByRuc", query="select p from Proveedor p where p.ruc like :ruc")
})
public class Proveedor implements Serializable {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id")
	private Long id;
	
	@Column(name="ruc", length=15, unique=true, nullable=false)
	private String ruc;
	
	@Column(name="nombre", length=50, nullable=false)
	private String nombre;
	
	@Column(name="direccion", length=70, nullable=false)
	private String direccion;
	
	@Column(name="telefono", length=20)
	private String telefono;
	
	/*@ManyToMany(mappedBy="proveedores")
	private List<Producto> productos;*/

	@OneToMany(mappedBy="proveedor")
	private List<FacturaCompra> compras;
	
	@Transient
	private static final long serialVersionUID = 1L;

	public Proveedor() {
		super();
	}
	
	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getRuc() {
		return this.ruc;
	}

	public void setRuc(String ruc) {
		this.ruc = ruc;
	}
	
	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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

    /*public List<Producto> getProductos() {
		return productos;
	}

	public void setProductos(List<Producto> productos) {
		this.productos = productos;
	}*/

	public List<FacturaCompra> getCompras() {
		return compras;
	}

	public void setCompras(List<FacturaCompra> compras) {
		this.compras = compras;
	}

	@Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }	

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Proveedor)){
            return false;
        }
        Proveedor other = (Proveedor) object;
        if ((this.id == null && other.id != null) ||
                (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return this.nombre + " [" + this.ruc + "]";
    }	
}
