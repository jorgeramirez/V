package v.modelo;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import v.modelo.FacturaDetalle;

/**
 * Entity implementation class for Entity: FacturaDetalleVenta
 *
 */
@Entity
@Table(name="factura_detalle_venta")
public class FacturaDetalleVenta extends FacturaDetalle {

	@ManyToOne(optional=false, fetch=FetchType.EAGER)
	@JoinColumn(name="id_factura_venta", referencedColumnName="id")
	protected FacturaVenta cabecera;

	@ManyToOne(optional=false, fetch=FetchType.EAGER)
	@JoinColumn(name="id_producto", referencedColumnName="id")
	protected Producto producto;	
	
	@Transient
	private static final long serialVersionUID = 1L;

	public FacturaDetalleVenta() {
		super();
	}

	public FacturaVenta getCabecera() {
		return cabecera;
	}

	public void setCabecera(FacturaVenta cabecera) {
		this.cabecera = cabecera;
	}   
}
