package v.modelo;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import v.modelo.Factura;

/**
 * Entity implementation class for Entity: FacturaCompra
 *
 */
@Entity
@Table(name="factura_compra")
public class FacturaCompra extends Factura {

	@ManyToOne(optional=false, fetch=FetchType.EAGER, cascade=CascadeType.REFRESH)
	@JoinColumn(name="id_producto", referencedColumnName="id")
	private Proveedor proveedor;
	
	@OneToMany(mappedBy="cabecera", cascade=CascadeType.ALL)
	private List<FacturaDetalleCompra> detalles;

	@ManyToOne(optional=false, fetch=FetchType.EAGER)
	@JoinColumn(name="id_comprador", referencedColumnName="id")
	private Usuario comprador; //el usuario con rol comprador que registro la compra.
	
	@Transient
	private static final long serialVersionUID = 1L;

	public FacturaCompra() {
		super();
	}

	public Proveedor getProveedor() {
		return proveedor;
	}

	public void setProveedor(Proveedor proveedor) {
		this.proveedor = proveedor;
	}

	public List<FacturaDetalleCompra> getDetalles() {
		return detalles;
	}

	public void setDetalles(List<FacturaDetalleCompra> detalles) {
		this.detalles = detalles;
	}

	public Usuario getComprador() {
		return comprador;
	}

	public void setComprador(Usuario comprador) {
		this.comprador = comprador;
	}
   
}
