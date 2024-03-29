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
package v.client.rpc;

import java.util.List;

import com.extjs.gxt.ui.client.data.FilterPagingLoadConfig;
import com.extjs.gxt.ui.client.data.ListLoadResult;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import v.modelo.Caja;
import v.modelo.Usuario;

@RemoteServiceRelativePath("AdministradorService")
public interface AdministradorService extends RemoteService {
	PagingLoadResult<Usuario> listarUsuarios(FilterPagingLoadConfig config);
	List<Integer> listarNrosCaja();
	ListLoadResult<Caja> listarCajas();
	Usuario agregarUsuario(Usuario u);
	boolean modificarUsuario(Usuario u);
	boolean existeUsername(String username);
	boolean eliminarUsuarios(List<Usuario> users);
	PagingLoadResult<Caja> listarCajas(FilterPagingLoadConfig loadConfig);
	Caja agregarCaja(Caja cashBox);
	boolean modificarCaja(Caja cashBox);
	boolean eliminarCajas(List<Caja> cashBoxes);
}
