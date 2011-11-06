package v.modelo;

import java.lang.String;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;


/**
 * Entity implementation class for Entity: Usuario
 *
 */
@Entity
@Table(name="usuario")
@NamedQueries({
		@NamedQuery(name="Usuario.findAll", query="select u from Usuario u"),
		@NamedQuery(name="Usuario.count", query="select count(u) from Usuario u")
})
public class Usuario extends Persona {

	@Column(name="username", unique=true, nullable=false, length=25)
	private String username;
	
	@Column(name="password", nullable=false, length=64)
	private String password;

	@Column(name="email", nullable=false, unique=true, length=100)
	private String email;
	
	@Column(name="rol", length=20, nullable=false)
	private String rol; //administrador, cajero, vendedor o comprador
	
	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name="id_caja", referencedColumnName="id")
	private Caja caja; //para usuarios con rol de cajero
	
	@OneToMany(mappedBy="comprador")
	private List<FacturaCompra> compras; //compras registradas por usuario comprador
	
	@OneToMany(mappedBy="vendedor")
	private List<FacturaVenta> ventas; //ventas registradas por usuario vendedor
	
	@Transient
	private static final long serialVersionUID = 1L;

	public Usuario() {
		super();
	}
	
	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Caja getCaja() {
		return caja;
	}

	public void setCaja(Caja caja) {
		this.caja = caja;
	}

	public String getRol() {
		return rol;
	}

	public void setRol(String rol) {
		this.rol = rol;
	}

	public List<FacturaCompra> getCompras() {
		return compras;
	}

	public void setCompras(List<FacturaCompra> compras) {
		this.compras = compras;
	}

	public List<FacturaVenta> getVentas() {
		return ventas;
	}

	public void setVentas(List<FacturaVenta> ventas) {
		this.ventas = ventas;
	}
}
