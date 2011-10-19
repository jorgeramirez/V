package v.modelo;

import javax.persistence.Entity;
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

	@Transient
	private static final long serialVersionUID = 1L;

	public FacturaDetalleVenta() {
		super();
	}   
}
