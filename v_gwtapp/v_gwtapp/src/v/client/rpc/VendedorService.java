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

import v.modelo.Cliente;
import v.modelo.Producto;

import com.extjs.gxt.ui.client.data.FilterPagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("VendedorService")
public interface VendedorService extends RemoteService {
	//modificar
	boolean existeCodigoProducto(String codigo);
	PagingLoadResult<Producto> listarProductos(FilterPagingLoadConfig loadConfig);
	Producto agregarProducto(Producto p);
	boolean modificarProducto(Producto p);
	boolean eliminarProductos(List<Producto> products);
	
	PagingLoadResult<Cliente> listarClientes(FilterPagingLoadConfig config);
	Cliente agregarCliente(Cliente c);
	void modificarCliente(Cliente c);
	boolean eliminarClientes(List<Cliente> clientes);
}
