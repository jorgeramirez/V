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

import v.modelo.FacturaVenta;
import v.modelo.Pago;

import com.extjs.gxt.ui.client.data.FilterPagingLoadConfig;
import com.extjs.gxt.ui.client.data.PagingLoadResult;
import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

@RemoteServiceRelativePath("CajeroService")
public interface CajeroService extends RemoteService {
	
	PagingLoadResult<FacturaVenta> listarFacturasPendientes(FilterPagingLoadConfig config);
	String registrarPago(Pago pago);
	String cierreCaja(Long idCajero);
	PagingLoadResult<Pago> listarPagos(FilterPagingLoadConfig config, FacturaVenta factura);
	PagingLoadResult<FacturaVenta> listarFacturas(FilterPagingLoadConfig config);
}
