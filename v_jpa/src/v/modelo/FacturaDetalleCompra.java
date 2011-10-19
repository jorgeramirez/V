package v.modelo;

import javax.persistence.Entity;
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

	@Transient
	private static final long serialVersionUID = 1L;

	public FacturaDetalleCompra() {
		super();
	}
   
}
