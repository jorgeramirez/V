package v.modelo;

import javax.persistence.Entity;
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

	@Transient
	private static final long serialVersionUID = 1L;

	public FacturaCompra() {
		super();
	}
   
}
