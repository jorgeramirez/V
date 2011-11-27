package v.modelo;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
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
@NamedQueries({
	@NamedQuery(name="Cliente.findAll", query="select c from Cliente c"),
	@NamedQuery(name="Cliente.count", query="select count(c) from Cliente c"),
})
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
