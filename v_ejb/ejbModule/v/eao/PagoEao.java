package v.eao;

import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import util.SimpleFilter;
import v.excepciones.EliminarException;
import v.excepciones.GuardarException;
import v.modelo.Pago;

/**
 * Session Bean implementation class PagoEao
 */
@Stateless
public class PagoEao implements PagoEaoLocal {

	@PersistenceContext(unitName="v_jpa")
	private EntityManager em;
	
    public PagoEao() {
        // TODO Auto-generated constructor stub
    }

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Pago agregar(Pago pago) throws GuardarException {
		try {
			em.persist(pago);
			em.flush();
			return em.merge(pago);
		}catch (PersistenceException pe) {
			throw new GuardarException(pe.getMessage());
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void modificar(Pago pago) throws GuardarException {
		try {
			em.merge(pago);
		}catch (PersistenceException pe) {
			throw new GuardarException(pe.getMessage());
		}
		
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public void eliminar(Pago pago) throws EliminarException {
		try {
			em.remove(em.merge(pago));
		}catch(Exception pe) {
			throw new EliminarException(pe.getMessage());
		}
	}
	
	@Override
	public Pago getById(Long id){
		return em.find(Pago.class, id);
	}

	@Override
	public int getTotalPagos() {
		Query q = em.createNamedQuery("Pago.count");
		return Integer.parseInt(q.getSingleResult().toString());
	}

	@Override
	public int getTotalPagosFactura(Integer numeroFactura) {
		Query q = em.createNamedQuery("Pago.getTotalPagosFactura");
		q.setParameter("id", numeroFactura);
		return Integer.parseInt(q.getSingleResult().toString());
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Pago> listar(List<SimpleFilter> filters, int start, int limit) {
		String q = "select p from Pago p ";
		int i = 1, size = filters.size();
		if(!filters.isEmpty()){
			q += "where ";
			for(SimpleFilter sf: filters){
				q += "p." + sf;
				if(size > 1 && i < size){
					q += " and ";
				}
				++i;
			}
		}
		Query query = em.createQuery(q, Pago.class);
		query.setFirstResult(start);
		query.setMaxResults(limit);
		return query.getResultList();
	}

}