package reportes;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.j2ee.servlets.BaseHttpServlet;


/**
 * @author Nahuel
 */
public class FillServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * prepara la plantilla para ser exportada a xhtml o pdf
	 */
	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		ServletContext context = this.getServletConfig().getServletContext();

        response.setContentType("text/html");
		PrintWriter out = response.getWriter();
        
		try {
            //obtener los parámetros de la url
			String rp = request.getParameter("reporte");
            String compilar = request.getParameter("compilar");
            String parametro = request.getParameter("id");
            String tipo = request.getParameter("tipo");
            
            String reportFileName = context.getRealPath("/reportes/" + rp + ".jrxml");
            String rootDir = context.getRealPath("");
            
            //compilar el archivo de reporte si se recibe como parámetro de la url compilar=True
            if (compilar != null) {
                if (compilar.compareTo("True") == 0) { 
                    JasperCompileManager.compileReportToFile(reportFileName);
                }
            }
            
            reportFileName = context.getRealPath("/reportes/" + rp + ".jasper");
            
			File reportFile = new File(reportFileName);
			if (!reportFile.exists())
                
				throw new JRRuntimeException("No se encontr� el archivo " + rp + ".jasper");

			Map<String, Object> parameters = new HashMap<String, Object>();
            
            //Hacer la conexión a la base de datos
            String db = "jdbc:postgresql://localhost:5432/vdb";
            
            Connection cnx = null;
            try {
                cnx = DriverManager.getConnection(db, "admin", "1234");
            } catch (SQLException s) {
                out.println("<html>");
                out.println("<span class=\"bnew\">Error en la conexi�n a la base de datos: </span>");
                out.println("<pre>");

                s.printStackTrace(out);

                out.println("</pre>");
                out.println("</body>");
                out.println("</html>");
            }
            
            //colocar los parámetros para llenar el reporte
          
            parameters.put("id", new BigDecimal(parametro));
            parameters.put("ROOT_DIR", rootDir);
						
			JasperPrint jasperPrint = JasperFillManager.fillReport(
                                                                    reportFileName, 
                                                                    parameters, 
                                                                    cnx
                                                                    );
            //se pone en la sesión para que se pueda generar el pdf
            request.getSession().setAttribute(BaseHttpServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE, jasperPrint);
            
            //redireccionar a la página del informe según el tipo
            String url = "http://localhost:8080/v_reports/reportes/";
            response.sendRedirect(url + tipo + "?reporte=" + rp + "&id=" + parametro);
            
		} catch (JRException e) {
			out.println("<html>");
			out.println("<span class=\"bnew\">JasperReports, se encontró el siguiente error: </span>");
			out.println("<pre>");

			e.printStackTrace(out);

			out.println("</pre>");
			out.println("</body>");
			out.println("</html>");
		}
	}
}