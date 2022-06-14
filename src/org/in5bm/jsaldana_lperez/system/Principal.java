package org.in5bm.jsaldana_lperez.system;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.Initializable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.in5bm.jsaldana_lperez.controllers.AlumnosController;
import org.in5bm.jsaldana_lperez.controllers.CarrerasTecnicasController;
import org.in5bm.jsaldana_lperez.controllers.CursosController;
import org.in5bm.jsaldana_lperez.controllers.MenuPrincipalController;
import org.in5bm.jsaldana_lperez.controllers.SalonesController;
import org.in5bm.jsaldana_lperez.controllers.AsignacionesAlumnosController;
import org.in5bm.jsaldana_lperez.controllers.HorariosController;
import org.in5bm.jsaldana_lperez.controllers.InstructoresController;

/**
 *
 * @author José Roberto Saldaña Arrazola
 * @author Luis Carlos Pérez
 * @date 26-abr-2022
 * @time 8:26:32
 *
 * Código técnico: IN5BM
 */

public class Principal extends Application {

    private Stage escenarioPrincipal;
    private final String PAQUETE_IMAGE = "org/in5bm/jsaldana_lperez/resources/images/";
    private final String PAQUETE_VIEW = "../views/";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        this.escenarioPrincipal = primaryStage;
        this.escenarioPrincipal.getIcons().add(new Image(PAQUETE_IMAGE + "logo.png"));
        this.escenarioPrincipal.setResizable(false);
        this.escenarioPrincipal.centerOnScreen();
        this.escenarioPrincipal.setTitle("Control Academico Kinal");
        mostrarEscenaPrincipal();
    }

    public void mostrarEscenaPrincipal() {
        try {
            MenuPrincipalController menuController = (MenuPrincipalController) cambiarEscena("MenuPrincipalView.fxml", 1024, 600);
            menuController.setEscenarioPrincipal(this);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("\nSe produjo un error al intentar mostrar la vista del Menú Principal");
        }
    }

    public void mostrarEscenaAlumnos() {
        try {
            AlumnosController alumnosController = (AlumnosController) cambiarEscena("AlumnosView.fxml", 1024, 600);
            alumnosController.setEscenarioPrincipal(this);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("\nSe produjo un error al intentar mostrar la vista de alumnos");
        }
    }

    public void mostrarEscenaCarrerasTecnicas() {
        try {
            CarrerasTecnicasController carreras_tecnicas = (CarrerasTecnicasController) cambiarEscena("CarrerasTecnicasView.fxml", 1024, 600);
            carreras_tecnicas.setEscenarioPrincipal(this);
        } catch (Exception ex) {
            ex.printStackTrace();
            System.err.println("\nSe produjo un error al intentar mostrar la vista de carreras tecnicas");
        }
    }

    public void mostrarEscenaSalones() {
        try {
            SalonesController salonesController = (SalonesController) cambiarEscena("SalonesView.fxml", 1024, 600);
            salonesController.setEscenarioPrincipal(this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void mostrarEscenaCursos() {
        try {
            CursosController cursosController = (CursosController) cambiarEscena("CursosView.fxml", 1024, 600);
            cursosController.setEscenarioPrincipal(this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void mostrarEscenaAsignacionesAlumnos () {
        try {
            AsignacionesAlumnosController asigancionesAlumnos = (AsignacionesAlumnosController) cambiarEscena("AsignacionesAlumnosView.fxml", 1024,600);
            asigancionesAlumnos.setEscenarioPrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void mostrarEscenaHorarios() {
        try {
            HorariosController horariosController = (HorariosController) cambiarEscena("HorariosView.fxml", 1024, 600);
            horariosController.setEscenarioPrincipal(this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    public void mostrarEscenaInstructores() {
        try {
            InstructoresController instructoresController = (InstructoresController) cambiarEscena("InstructoresView.fxml", 1024, 600);
            instructoresController.setEscenarioPrincipal(this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /*public Initializable cambiarEscena(String vistaFxml, int ancho, int alto) throws IOException {
        Initializable resultado = null;
        FXMLLoader cargadorFXML = new FXMLLoader();
        cargadorFXML.setBuilderFactory(new JavaFXBuilderFactory());
        cargadorFXML.setLocation(Principal.class.getResource(PAQUETE_VIEW + vistaFxml));
        InputStream archivo = Principal.class.getResourceAsStream(PAQUETE_VIEW + vistaFxml);
        AnchorPane root = cargadorFXML.load(archivo);
        Scene nuevaEscena = new Scene(root, ancho, alto);
        this.escenarioPrincipal.setScene(nuevaEscena);
        this.escenarioPrincipal.sizeToScene();
        this.escenarioPrincipal.show();
        resultado = cargadorFXML.getController();
        return resultado;
    }*/

    public Initializable cambiarEscena(String vistaFxml, int ancho, int alto) throws IOException {
        System.out.println("Cambiando escena: " + PAQUETE_VIEW + vistaFxml);
        FXMLLoader cargadorFXML = new FXMLLoader(getClass().getResource(PAQUETE_VIEW + vistaFxml));
        Scene scene = new Scene((AnchorPane) cargadorFXML.load(), ancho, alto);
        this.escenarioPrincipal.setScene(scene);
        this.escenarioPrincipal.sizeToScene();
        this.escenarioPrincipal.show();
        return (Initializable) cargadorFXML.getController();
    }
}