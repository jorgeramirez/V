/*
 * Copyright 2011 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package v.server;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;

import util.SimpleFilter;
import v.client.AppConstants;
import v.client.rpc.CompradorService;
import v.excepciones.EliminarException;
import v.excepciones.GuardarException;
import v.facade.CompradorFacadeLocal;
import v.modelo.FacturaCompra;
import v.modelo.FacturaDetalleCompra;
import v.modelo.Producto;
import v.modelo.Proveedor;
import v.modelo.Usuario;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.FilterConfig;
import com.extjs.gxt.ui.client.data.FilterPagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class CompradorServiceImpl extends RemoteServiceServlet implements CompradorService {
	@EJB
	CompradorFacadeLocal compradorFacade;

	@Override
	public boolean existeCodigoProducto(String codigo) {
		return compradorFacade.findProductoByCodigo(codigo) != null;
	}

	@Override
	public PagingLoadResult<Producto> listarProductos(FilterPagingLoadConfig loadConfig) {
		int count = compradorFacade.getTotalProductos();
		List<FilterConfig> filters = loadConfig.getFilterConfigs();
		int start = loadConfig.getOffset();
		int limit = AppConstants.PAGE_SIZE;
		List<SimpleFilter> plainFilters = Filter.processFilters(filters);
		List<Producto> products = compradorFacade.listarProductos(plainFilters, start, limit);
		Converter<Producto> pc = new Converter<Producto>();
		products = pc.convertObjects(products);
		return new BasePagingLoadResult<Producto>(products, loadConfig.getOffset(), count);
	}

	@Override
	public Producto agregarProducto(Producto p) {
		Producto added = null;
		try {
			added = compradorFacade.agregarProducto(p);
			Converter<Producto> pc = new Converter<Producto>();
			added = pc.convertObject(added);
		} catch (GuardarException e) {
			e.printStackTrace();
		}
		return added;			
	}

	@Override
	public boolean modificarProducto(Producto p) {
		boolean ok = true;
		try {
			compradorFacade.modificarProducto(p);
		} catch (GuardarException e) {
			e.printStackTrace();
			ok = false;
		}
		return ok;
	}

	@Override
	public boolean eliminarProductos(List<Producto> products) {
		boolean ok = true;
		for(Producto p: products){
			try {
				compradorFacade.eliminarProducto(p);
			}catch (EliminarException e){
				e.printStackTrace();
				ok = false;
			}catch(EJBTransactionRolledbackException e){
				ok = false;
			}
		}
		return ok;
	}

	@Override
	public PagingLoadResult<Proveedor> listarProveedores(FilterPagingLoadConfig loadConfig) {
		int count = compradorFacade.getTotalProveedores();
		List<FilterConfig> filters = loadConfig.getFilterConfigs();
		int start = loadConfig.getOffset();
		int limit = AppConstants.PAGE_SIZE;
		List<SimpleFilter> plainFilters = Filter.processFilters(filters);
		List<Proveedor> providers = compradorFacade.listarProveedores(plainFilters, start, limit);
		Converter<Proveedor> pc = new Converter<Proveedor>();
		providers = pc.convertObjects(providers);
		return new BasePagingLoadResult<Proveedor>(providers, loadConfig.getOffset(), count);		
	}

	@Override
	public boolean existeRucProveedor(String ruc) {
		return compradorFacade.findProductoByRuc(ruc) != null;
	}

	@Override
	public Proveedor agregarProveedor(Proveedor provider) {
		Proveedor added = null;
		try {
			added = compradorFacade.agregarProveedor(provider);
			Converter<Proveedor> pc = new Converter<Proveedor>();
			added = pc.convertObject(added);
		} catch (GuardarException e) {
			e.printStackTrace();
		}
		return added;
	}

	@Override
	public boolean modificarProveedor(Proveedor provider) {
		boolean ok = true;
		try {
			compradorFacade.modificarProveedor(provider);
		} catch (GuardarException e) {
			e.printStackTrace();
			ok = false;
		}
		return ok;
	}

	@Override
	public boolean eliminarProveedores(List<Proveedor> providers) {
		boolean ok = true;
		for(Proveedor p: providers){
			try {
				compradorFacade.eliminarProveedor(p);
			}catch (EliminarException e){
				e.printStackTrace();
				ok = false;
			}catch(EJBTransactionRolledbackException e){
				ok = false;
			}
		}
		return ok;
	}

	@Override
	public PagingLoadResult<Producto> listarProductosConExistencia(
			FilterPagingLoadConfig loadConfig) {
		int count = compradorFacade.getTotalProductos();
		List<FilterConfig> filters = loadConfig.getFilterConfigs();
		int start = loadConfig.getOffset();
		int limit = AppConstants.PAGE_SIZE;
		List<SimpleFilter> plainFilters = Filter.processFilters(filters);
		plainFilters.add(new SimpleFilter("cantidad", 0, ">"));
		List<Producto> products = compradorFacade.listarProductos(plainFilters, start, limit);
		Converter<Producto> pc = new Converter<Producto>();
		products = pc.convertObjects(products);
		return new BasePagingLoadResult<Producto>(products, loadConfig.getOffset(), count);
	}

	@Override
	public PagingLoadResult<FacturaCompra> listarCompras(FilterPagingLoadConfig loadConfig) {
		int count = compradorFacade.getTotalCompras();
		List<FilterConfig> filters = loadConfig.getFilterConfigs();
		int start = loadConfig.getOffset();
		int limit = AppConstants.PAGE_SIZE;
		List<SimpleFilter> plainFilters = Filter.processFilters(filters);
		List<FacturaCompra> purchases = compradorFacade.listarCompras(plainFilters, start, limit);
		Converter<FacturaCompra> fc = new Converter<FacturaCompra>();
		purchases = fc.convertObjects(purchases);
		
		Converter<Usuario> uc = new Converter<Usuario>();
		Converter<Proveedor> pc = new Converter<Proveedor>();
		
		for(FacturaCompra c: purchases){
			c.setProveedor(pc.convertObject(c.getProveedor()));
			c.setComprador(uc.convertObject(c.getComprador()));
		}
		
		return new BasePagingLoadResult<FacturaCompra>(purchases, loadConfig.getOffset(), count);
	}

	@Override
	public PagingLoadResult<FacturaDetalleCompra> listarComprasDetalles(
			FilterPagingLoadConfig config, FacturaCompra compra) {
		int count = compradorFacade.getTotalDetallesCompra(compra.getNumeroFactura());
		List<FilterConfig> filters = config.getFilterConfigs();
		int start = config.getOffset();
		int limit = AppConstants.PAGE_SIZE;
		List<SimpleFilter> plainFilters = Filter.processFilters(filters);
		plainFilters.add(new SimpleFilter("cabecera.numeroFactura", compra.getNumeroFactura(), "="));
		List<FacturaDetalleCompra> detalles = compradorFacade.listarComprasDetalles(plainFilters, start, limit);
		
		Converter<FacturaDetalleCompra> fdcc = new Converter<FacturaDetalleCompra>();
		detalles = fdcc.convertObjects(detalles);
		
		Converter<Usuario> uc = new Converter<Usuario>();
		Converter<Proveedor> pc = new Converter<Proveedor>();
		Converter<FacturaCompra> fcc = new Converter<FacturaCompra>();
		
		
		for(FacturaDetalleCompra fdc: detalles){
			fdc.setCabecera(fcc.convertObject(fdc.getCabecera()));
			fdc.getCabecera().setProveedor(pc.convertObject(fdc.getCabecera().getProveedor()));
			fdc.getCabecera().setComprador(uc.convertObject(fdc.getCabecera().getComprador()));
		}
		
		return new BasePagingLoadResult<FacturaDetalleCompra>(detalles, config.getOffset(), count);	
	}
}