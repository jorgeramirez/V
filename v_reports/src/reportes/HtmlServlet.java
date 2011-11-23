
package reportes;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRXhtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;


/**
 * @author Nahuel
 */
public class HtmlServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Devuelve una página xhtml con el reporte que se llenó con el fillservlet
	 */
	public void service(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		ServletContext context = this.getServletConfig().getServletContext();

        response.setContentType("text/html");
		PrintWriter out = response.getWriter();
        
		try {
            //obtener los parámetros de la url
			String rp = request.getParameter("reporte");
            String parametro = request.getParameter("id");          
            String reportFileName = context.getRealPath("/reportes/" + rp + ".jasper");
            String rootDir = context.getRealPath("");
            
			File reportFile = new File(reportFileName);
            
			if (!reportFile.exists())
				throw new JRRuntimeException("No se encontró el archivo " + rp + ".jasper");

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
            //debug
            System.out.print(rootDir);
            
            JasperReport jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
            
			JasperPrint jasperPrint = JasperFillManager.fillReport(
																	jasperReport, 
                                                                    parameters, 
                                                                    cnx
                                                                    );
                                                                    
        

			
					
			JRXhtmlExporter exporter = new JRXhtmlExporter();
			
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
			exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI, "./" + rp + ".html_files/");
			exporter.exportReport();
            
		} catch (JRException e) {
			out.println("<html>");
			out.println("<head>");
			out.println("<title>JasperReports - Web Application Sample</title>");
			out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"../stylesheet.css\" title=\"Style\">");
			out.println("</head>");
			
			out.println("<body bgcolor=\"white\">");

			out.println("<span class=\"bnew\">JasperReports, se encontró el siguiente error: </span>");
			out.println("<pre>");

			e.printStackTrace(out);

			out.println("</pre>");

			out.println("</body>");
			out.println("</html>");
		}
	}


}
