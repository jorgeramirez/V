package v.modelo;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import v.modelo.Persona;
import v.modelo.FacturaVenta;

/**
 * Entity implementation class for Entity: Cliente
 *
 */
@Entity
@Table(name="cliente")
public class Cliente extends Persona {
	
	@OneToMany(mappedBy="cliente")
	private List<FacturaVenta> ventas;
	
	@Transient
	private static final long serialVersionUID = 1L;

	public List<FacturaVenta> getVentas() {
		return ventas;
	}

	public void setVentas(List<FacturaVenta> ventas) {
		this.ventas = ventas;
	}
}
