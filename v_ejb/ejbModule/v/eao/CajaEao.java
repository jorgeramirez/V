package v.eao;

import java.util.HashMap;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import v.excepciones.EliminarException;
import v.excepciones.GuardarException;
import v.modelo.Caja;
import v.modelo.Pago;

/**
 * Session Bean implementation class CajaEao
 */
@Stateless
public class CajaEao implements CajaEaoLocal {

	@PersistenceContext(unitName="v_jpa")
	private EntityManager em;
	
    public CajaEao() {
        // TODO Auto-generated constructor stub
    }
    
    @Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public Caja agregar(Caja caja) throws GuardarException {
		try {
			em.persist(caja);
			em.flush();
			return em.merge(caja);
		}catch (PersistenceException pe) {
			throw new GuardarException(pe.getMessage());
		}
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void modificar(Caja caja) throws GuardarException {
		try {
			em.merge(caja);
		}catch (PersistenceException pe) {
			throw new GuardarException(pe.getMessage());
		}
		
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public void eliminar(Caja caja) throws EliminarException {
		try {
			em.remove(em.merge(caja));
		}catch(PersistenceException pe) {
			throw new EliminarException(pe.getMessage());
		}
	}

	@Override
	public List<Caja> listar() {
		return em.createNamedQuery("Caja.findAll", Caja.class).getResultList();
	}
	
	
	@SuppressWarnings("null")
	@Override
	public List<Pago> pagosNoCerrados(Long idCaja){
		
		Caja caja = em.find(Caja.class,idCaja);
		
		List<Pago> listaDeCierre = null;
		
		for (Pago pago : caja.getPagos()){
			if (pago.getEstado() != "cerrado") {
				listaDeCierre.add(pago);				
			}
		}
		
		return listaDeCierre;
	}

	@Override
	public Caja findByNumeroCaja(Integer nroCaja) {
		return em.find(Caja.class, nroCaja);
	}

	@Override
	public int getCount() {
		Query q = em.createNamedQuery("Caja.count");
		return Integer.parseInt(q.getSingleResult().toString());
	}

	@SuppressWarnings({ "unchecked" })
	@Override
	public List<Caja> listar(HashMap<String, Object> filters, int start, int limit) {
		String q = "select c from Caja c ";
		Object val;
		int i = 1, size = filters.size();
		boolean use_and = size > 1;		
		if(!filters.isEmpty()){
			q += "where ";
			for(String key: filters.keySet()) {
				val = filters.get(key);
				if(key.equals("numeroCaja")){
					q += "c." + key + " = " + val.toString();
				}
				if(use_and && i < size){
					q += " and ";
				}
				++i;				
			}
		}
		Query query = em.createQuery(q, Caja.class);
		query.setFirstResult(start);
		query.setMaxResults(limit);
		return query.getResultList();
	}

}
