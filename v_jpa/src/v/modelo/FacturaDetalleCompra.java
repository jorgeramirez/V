package v.modelo;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import v.modelo.FacturaDetalle;

/**
 * Entity implementation class for Entity: FacturaDetalleCompra
 *
 */
@Entity
@Table(name="factura_detalle_compra")
public class FacturaDetalleCompra extends FacturaDetalle {

	@ManyToOne(optional=false, fetch=FetchType.EAGER)
	@JoinColumn(name="id_factura_compra", referencedColumnName="id")
	protected FacturaCompra cabecera;
	
	@ManyToOne(optional=false, fetch=FetchType.EAGER)
	@JoinColumn(name="id_producto", referencedColumnName="id")
	protected Producto producto;
	
	@Transient
	private static final long serialVersionUID = 1L;

	public FacturaDetalleCompra() {
		super();
	}

	public FacturaCompra getCabecera() {
		return cabecera;
	}

	public void setCabecera(FacturaCompra cabecera) {
		this.cabecera = cabecera;
	}

	public Producto getProducto() {
		return producto;
	}

	public void setProducto(Producto producto) {
		this.producto = producto;
	}
   
}
