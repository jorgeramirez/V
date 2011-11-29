package v.eao;


import java.util.List;

import javax.ejb.Local;

import util.SimpleFilter;
import v.excepciones.EliminarException;
import v.excepciones.GuardarException;
import v.modelo.Producto;

@Local
public interface ProductoEaoLocal {

	Producto agregar(Producto producto) throws GuardarException;
	
	void modificar(Producto producto) throws GuardarException;
	
	void eliminar(Producto producto) throws EliminarException;
	
	List<Producto> listar(List<SimpleFilter> filters, int start, int limit);
	
	Producto getById(Long long1);
	
	Producto findByCodigo(String codigo);
	
	int getCount();
	
	int getTotalProductosFilters(List<SimpleFilter> plainFilters);
}
