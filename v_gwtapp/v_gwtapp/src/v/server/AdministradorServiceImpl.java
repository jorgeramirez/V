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
import v.client.rpc.AdministradorService;
import v.excepciones.EliminarException;
import v.excepciones.GuardarException;
import v.facade.AdministradorFacadeLocal;
import v.modelo.Caja;
import v.modelo.Usuario;

import com.extjs.gxt.ui.client.data.BaseListLoadResult;
import com.extjs.gxt.ui.client.data.BasePagingLoadResult;
import com.extjs.gxt.ui.client.data.FilterConfig;
import com.extjs.gxt.ui.client.data.FilterPagingLoadConfig;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

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
		List<SimpleFilter> plainFilters = Filter.processFilters(filters);
		List<Usuario> users = administradorFacade.listarUsuarios(plainFilters, start, limit);
		Converter<Usuario> cu = new Converter<Usuario>();
		Converter<Caja> cc = new Converter<Caja>();
		users = cu.convertObjects(users);
		for(Usuario u: users){
			if(u.getRol().equals(AppConstants.CAJERO_ROL)){
				u.setCaja(cc.convertObject(u.getCaja()));
			}
		}
		return new BasePagingLoadResult<Usuario>(users, config.getOffset(), count);
	}

	@Override
	public List<Integer> listarNrosCaja() {
		return administradorFacade.listarNrosCaja();
	}

	@Override
	public ListLoadResult<Caja> listarCajas() {
		Converter<Caja> cc = new Converter<Caja>();
		return new BaseListLoadResult<Caja>(cc.convertObjects(administradorFacade.listarCajas()));
	}

	@Override
	public Usuario agregarUsuario(Usuario u) {
		Usuario added = null;
		try {
			added = administradorFacade.agregarUsuario(u);
			Converter<Usuario> uc = new Converter<Usuario>();
			added = uc.convertObject(added);
			if(added.getRol().equals(AppConstants.CAJERO_ROL)){
				Converter<Caja> cc = new Converter<Caja>();
				added.setCaja(cc.convertObject(added.getCaja()));
			}			
		} catch (GuardarException e) {
			e.printStackTrace();
		}
		return added;
	}

	@Override
	public void modificarUsuario(Usuario u) {
		try {
			administradorFacade.modificarUsuario(u);
		} catch (GuardarException e) {
			e.printStackTrace();
		}		
	}

	/**
	 * Verifica si el username existe en la BD.
	 **/
	@Override
	public boolean existeUsername(String username) {
		return administradorFacade.findByUsername(username) != null;
	}

	@Override
	public boolean eliminarUsuarios(List<Usuario> users) {
		boolean ok = true;
		for(Usuario u: users){
			try {
				administradorFacade.eliminarUsuario(u);
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
	public PagingLoadResult<Caja> listarCajas(FilterPagingLoadConfig loadConfig) {
		int count = administradorFacade.getTotalCajas();
		List<FilterConfig> filters = loadConfig.getFilterConfigs();
		int start = loadConfig.getOffset();
		int limit = AppConstants.PAGE_SIZE;
		List<SimpleFilter> plainFilters = Filter.processFilters(filters);
		List<Caja> cashBoxes = administradorFacade.listarCajas(plainFilters, start, limit);
		Converter<Caja> cc = new Converter<Caja>();
		cashBoxes = cc.convertObjects(cashBoxes);
		return new BasePagingLoadResult<Caja>(cashBoxes, loadConfig.getOffset(), count);		
	}

	@Override
	public Caja agregarCaja(Caja c) {
		Caja added = null;
		try {
			added = administradorFacade.agregarCaja(c);
			Converter<Caja> cc = new Converter<Caja>();
			added = cc.convertObject(added);
		} catch (GuardarException e) {
			e.printStackTrace();
		}
		return added;		
	}

	@Override
	public void modificarCaja(Caja c) {
		try {
			administradorFacade.modificarCaja(c);
		} catch (GuardarException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean eliminarCajas(List<Caja> cashBoxes) {
		boolean ok = true;
		for(Caja c: cashBoxes){
			try {
				administradorFacade.eliminarCaja(c);
			}catch (EliminarException e){
				e.printStackTrace();
				ok = false;
			}catch(EJBTransactionRolledbackException e){
				ok = false;
			}
		}
		return ok;
	}
}