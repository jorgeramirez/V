package v.modelo;

import java.lang.Double;
import java.lang.String;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import v.modelo.Factura;

/**
 * Entity implementation class for Entity: FacturaVenta
 *
 */
@Entity
@Table(name="factura_venta")
public class FacturaVenta extends Factura {

	@Column(name="estado", nullable=false, length=40)
	private String estado;
	
	@Column(name="saldo", nullable=false)
	private Double saldo;
	
	@Transient
	private static final long serialVersionUID = 1L;

	public FacturaVenta() {
		super();
	}   
	public String getEstado() {
		return this.estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}   
	public Double getSaldo() {
		return this.saldo;
	}

	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}
   
}
