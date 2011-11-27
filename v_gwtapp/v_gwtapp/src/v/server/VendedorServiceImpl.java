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

import util.SimpleFilter;
import v.client.AppConstants;
import v.client.rpc.VendedorService;
import v.excepciones.EliminarException;
import v.excepciones.GuardarException;
import v.facade.VendedorFacadeLocal;
import v.modelo.Cliente;
import v.modelo.FacturaVenta;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.FilterConfig;
import com.extjs.gxt.ui.client.data.FilterPagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class VendedorServiceImpl extends RemoteServiceServlet implements VendedorService {
	@EJB
	VendedorFacadeLocal vendedorFacade;
	
	public boolean agregarVenta(FacturaVenta v) {
		boolean added = false;
		try {
			added = vendedorFacade.agregarVenta(v);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return added;	
	}
	
	@Override
	public PagingLoadResult<Cliente> listarClientes(FilterPagingLoadConfig config) {
		int count = vendedorFacade.getTotalClientes();
		List<FilterConfig> filters = config.getFilterConfigs();
		int start = config.getOffset();
		int limit = AppConstants.PAGE_SIZE;
		List<SimpleFilter> plainFilters = Filter.processFilters(filters);
		List<Cliente> clientes = vendedorFacade.listarClientes(plainFilters, start, limit);
		Converter<Cliente> cu = new Converter<Cliente>();
		clientes = cu.convertObjects(clientes);
	
		return new BasePagingLoadResult<Cliente>(clientes, config.getOffset(), count);
	}


	@Override
	public Cliente agregarCliente(Cliente c) {
		Cliente added = null;
		try {
			added = vendedorFacade.agregarCliente(c);
			Converter<Cliente> uc = new Converter<Cliente>();
			added = uc.convertObject(added);
				
		} catch (GuardarException e) {
			e.printStackTrace();
		}
		return added;
	}

	@Override
	public void modificarCliente(Cliente c) {
		try {
			vendedorFacade.modificarCliente(c);
		} catch (GuardarException e) {
			e.printStackTrace();
		}	
	}


	@Override
	public boolean eliminarClientes(List<Cliente> clientes) {
		boolean ok = true;
		for(Cliente c: clientes){
			try {
				vendedorFacade.eliminarCliente(c);
			} catch (EliminarException e) {
				e.printStackTrace();
				ok = false;
				break;
			}
		}
		return ok;
	}


}
