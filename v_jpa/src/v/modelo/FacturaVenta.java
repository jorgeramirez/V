package v.modelo;

import java.lang.Double;
import java.lang.String;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import v.modelo.Factura;
import v.modelo.Cliente;

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
	
	@ManyToOne(optional=false, fetch=FetchType.EAGER, cascade=CascadeType.REFRESH)
	@JoinColumn(name="id_cliente", referencedColumnName="id")
	private Cliente cliente;

	@OneToMany(mappedBy="cabecera", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private List<FacturaDetalleVenta> detalles;

	@OneToMany(mappedBy="factura", cascade=CascadeType.ALL, fetch=FetchType.EAGER)
	private List<Pago> pagos;	
	
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
	public Cliente getCliente() {
		return cliente;
	}
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}
	public List<FacturaDetalleVenta> getDetalles() {
		return detalles;
	}
	public void setDetalles(List<FacturaDetalleVenta> detalles) {
		this.detalles = detalles;
	}
	public List<Pago> getPagos() {
		return pagos;
	}
	public void setPagos(List<Pago> pagos) {
		this.pagos = pagos;
	}
   
}
