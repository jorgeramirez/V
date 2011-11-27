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
import v.client.rpc.CajeroService;
import v.facade.CajeroFacadeLocal;
import v.modelo.Cliente;
import v.modelo.FacturaVenta;
import v.modelo.Pago;
import v.modelo.Usuario;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.FilterConfig;
import com.extjs.gxt.ui.client.data.FilterPagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class CajeroServiceImpl extends RemoteServiceServlet implements CajeroService {
	@EJB
	CajeroFacadeLocal cajeroFacade;
	
	@Override
	public PagingLoadResult<FacturaVenta> listarFacturasPendientes(FilterPagingLoadConfig config) {
		int count = cajeroFacade.getTotalFacturasPendientes();
		List<FilterConfig> filters = config.getFilterConfigs();
		int start = config.getOffset();
		int limit = AppConstants.PAGE_SIZE;
		List<SimpleFilter> plainFilters = Filter.processFilters(filters);
		plainFilters.add(new SimpleFilter("estado", AppConstants.FACTURA_PENDIENTE_PAGO, "like"));
		List<FacturaVenta> sales = cajeroFacade.listarFacturasPendientes(plainFilters, start, limit);
		Converter<FacturaVenta> fvc = new Converter<FacturaVenta>();
		Converter<Usuario> uc = new Converter<Usuario>();
		Converter<Cliente> cc = new Converter<Cliente>();
		sales = fvc.convertObjects(sales);
		for(FacturaVenta fv: sales){
			fv.setVendedor(uc.convertObject(fv.getVendedor()));
			fv.setCliente(cc.convertObject(fv.getCliente()));
		}
		return new BasePagingLoadResult<FacturaVenta>(sales, config.getOffset(), count);
	}

	@Override
	public Pago registrarPago(Pago pago) {
		// TODO Auto-generated method stub
		return null;
	}
}
