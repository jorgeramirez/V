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

import java.util.HashMap;
import java.util.List;

import javax.ejb.EJB;

import v.client.AppConstants;
import v.client.rpc.AdministradorService;
import v.facade.AdministradorFacadeLocal;

import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.FilterConfig;
import com.extjs.gxt.ui.client.data.FilterPagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

import v.modelo.Usuario;

@SuppressWarnings("serial")
public class AdministradorServiceImpl extends RemoteServiceServlet implements AdministradorService {

	@EJB
	AdministradorFacadeLocal administradorFacade;
	
	@Override
	public PagingLoadResult<Usuario> listarUsuarios(FilterPagingLoadConfig config) {
		int count = administradorFacade.getTotalUsuarios();
		List<FilterConfig> filters = config.getFilterConfigs();
		int start = config.getOffset();
		int limit = AppConstants.PAGE_SIZE;
		
		HashMap<String, Object> plainFilters = new HashMap<String, Object>();
		if(filters != null){
			for (FilterConfig f : filters) {
				Object val = f.getValue();
				String field = f.getField();
				plainFilters.put(field, val);
		    }
		}
		
		List<Usuario> users = administradorFacade.listarUsuarios(plainFilters, start, limit);
		for(Usuario u: users){
			u.setCompras(null);
			u.setVentas(null);
		}
		
		return new BasePagingLoadResult<Usuario>(users, config.getOffset(), count);
	}
	
}
