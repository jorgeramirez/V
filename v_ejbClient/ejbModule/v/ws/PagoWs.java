package v.ws;

public class PagoWs {
	Long idPago;
	Long idCajero;
	Integer idFactura;
	Double monto;
	
	public PagoWs(){
		
	}
	
	public PagoWs(Long idCajero, Integer idFactura, Double monto) {
		this.idCajero = idCajero;
		this.idFactura = idFactura;
		this.monto = monto;
	}

	public Long getIdCajero() {
		return idCajero;
	}

	public void setIdCajero(Long idCajero) {
		this.idCajero = idCajero;
	}

	public Integer getIdFactura() {
		return this.idFactura;
	}

	public void setIdFactura(Integer idFactura) {
		this.idFactura = idFactura;
	}

	public Double getMonto() {
		return monto;
	}

	public void setMonto(Double monto) {
		this.monto = monto;
	}

	public Long getIdPago() {
		return idPago;
	}

	public void setIdPago(Long idPago) {
		this.idPago = idPago;
	}
}

