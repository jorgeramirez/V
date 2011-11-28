package v.ws;

public class PagoWs {

	Long idCajero;
	Long IdFactura;
	Double monto;
	
	public PagoWs(Long idCajero, Long idFactura, Double monto) {
		super();
		this.idCajero = idCajero;
		IdFactura = idFactura;
		this.monto = monto;
	}

	public Long getIdCajero() {
		return idCajero;
	}

	public void setIdCajero(Long idCajero) {
		this.idCajero = idCajero;
	}

	public Long getIdFactura() {
		return IdFactura;
	}

	public void setIdFactura(Long idFactura) {
		IdFactura = idFactura;
	}

	public Double getMonto() {
		return monto;
	}

	public void setMonto(Double monto) {
		this.monto = monto;
	}
	
	
}
