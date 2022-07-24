
package org.in5bm.jsaldana_lperez.reports;

import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import net.sf.jasperreports.engine.JasperReport; 
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.view.JasperViewer;

import org.in5bm.jsaldana_lperez.db.Conexion;

/**
 *
 * @author Luis Carlos Pérez
 * @date 27/06/2022
 * @time 18:13:16
 * 
 *Código técnico: IN5BM
 *
 */
public class GenerarReporte {
    
    private final String PAQUETE_IMAGE = "org/in5bm/jsaldana_lperez/resources/images/";
    
    private static GenerarReporte instance;
    
    private GenerarReporte(){
    }
    
    public static GenerarReporte getInstance() {
        if (instance == null) {
            instance = new GenerarReporte();
        }
        return instance;
    }
    
    public void mostrarReporte(String nombreReporte, Map<String, Object> parametros, String titulo) {
        try {
            
            parametros.put("IMAGE_LOGO", PAQUETE_IMAGE + "logoPrincipal.png");
            parametros.put("IMAGE_FOOTER", PAQUETE_IMAGE + "LogoPieDePagina.png");
            
            URL urlFile = new URL(getClass().getResource(nombreReporte).toString());
            //InputStream inputStream = GenerarReporte.class.getResourceAsStream(nombreReporte);
            JasperReport jasperReport = (JasperReport) JRLoader.loadObject(urlFile);
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parametros, Conexion.getInstance().getConexion());
            JasperViewer jasperViewer = new JasperViewer(jasperPrint, false); // El false es para que la ventana del reporte sea la única en cerrarse
            jasperViewer.setTitle(titulo);
            jasperViewer.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
