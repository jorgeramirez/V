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

import javax.ejb.EJB;
import javax.servlet.http.HttpSession;

import v.client.AppConstants;
import v.client.rpc.LoginService;
import v.facade.AdministradorFacadeLocal;
import v.modelo.Caja;
import v.modelo.Usuario;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;

@SuppressWarnings("serial")
public class LoginServiceImpl extends RemoteServiceServlet implements LoginService {
	
	@EJB
	AdministradorFacadeLocal administradorFacade;
	
	@Override
	public Usuario login(String usuario, String password){
		
		Usuario u = administradorFacade.findByUsername(usuario);
		boolean aceptado = false;

		Converter<Usuario> cu = new Converter<Usuario>();
		Converter<Caja> cc = new Converter<Caja>();
		
		if (u != null){
			u = cu.convertObject(u);
		

			if(u.getRol().equals(AppConstants.CAJERO_ROL)){
				u.setCaja(cc.convertObject(u.getCaja()));
			}
			
			if (password.equals(u.getPassword())){
				aceptado = true;
			}
			
			if (aceptado) {
	            HttpSession session = getThreadLocalRequest().getSession(true);
	            session.setAttribute("usuario", u);
	/*            session.setAttribute("id", u.getId());
	            session.setAttribute("rol", u.getRol());*/
	            return u;
			}
		}
		
		return null;
		
	}
	
    @Override
    public void logout() {
            HttpSession session = getThreadLocalRequest().getSession();
            session.removeAttribute("usuario");
            session.removeAttribute("id");
            session.removeAttribute("rol");
            session.invalidate();
    }
    
    @Override
    public Usuario getSessionAttribute(String attribute){
            HttpSession session = getThreadLocalRequest().getSession();
            return (Usuario) session.getAttribute(attribute);
    }
    
    @Override
    public Usuario alreadyLoggedIn() {
    		HttpSession session = getThreadLocalRequest().getSession();
            return (Usuario) session.getAttribute("usuario");
    }
	
}
