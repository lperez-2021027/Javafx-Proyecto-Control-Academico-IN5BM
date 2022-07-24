package org.in5bm.jsaldana_lperez.controllers;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.in5bm.jsaldana_lperez.system.Principal;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import org.in5bm.jsaldana_lperez.reports.GenerarReporte;
import org.in5bm.jsaldana_lperez.controllers.LoginController;
import org.in5bm.jsaldana_lperez.models.Roles;
import org.in5bm.jsaldana_lperez.models.Usuarios;

/**
 *
 * @author José Roberto Saldaña Arrazola
 * @author Luis Carlos Pérez
 * @date 26-abr-2022
 * @time 8:26:32
 *
 * Código técnico: IN5BM
 */
public class MenuPrincipalController implements Initializable {

    private final String PAQUETE_IMAGE = "org/in5bm/jsaldana_lperez/resources/images/";

    private Principal escenarioPrincipal;

    LoginController log = new LoginController();
    
    private int rol;

    public int getRol() {
        return rol;
    }

    public void setRol(int rol) {
        this.rol = rol;
    }

    @FXML
    private Button btnAlumnos;

    @FXML
    private Button btnCarreras;

    @FXML
    private Button btnInstructores;

    @FXML
    private Button btnSalones;

    @FXML
    private Button btnHorarios;

    //Usuarios usu = new Usuarios();
    LoginController login = new LoginController();
    /*String desc = login.descripcion;
    String nomb = login.datos(1);;
    int rol = login.rolType;*/
    
    //String hola = usu.getNombre();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        /*comprobarRol();
        hola();
        System.out.println(login.datos(1));
        System.out.println(nomb);*/
        System.out.println(getRol());
        comprobarRol();

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Sesión iniciada");
        alert.setHeaderText(null);
        alert.setContentText("Bienvenido ");
        alert.show();
    }

    private void comprobarRol() {
        if (getRol() == 2) {
            btnAlumnos.setDisable(true);
            btnCarreras.setDisable(true);
            btnInstructores.setDisable(true);
            btnSalones.setDisable(true);
            btnHorarios.setDisable(true);
        }
    }

    @FXML
    public void clicAlumnos(ActionEvent event) {
        escenarioPrincipal.mostrarEscenaAlumnos();
    }

    @FXML
    public void clicSalones(ActionEvent event) {
        escenarioPrincipal.mostrarEscenaSalones();
    }

    @FXML
    private void clicCarreras(ActionEvent event) {
        escenarioPrincipal.mostrarEscenaCarrerasTecnicas();
    }

    @FXML
    private void clicCursos(ActionEvent event) {
        escenarioPrincipal.mostrarEscenaCursos();
    }

    @FXML
    private void clicAsignacionesAlumnos(ActionEvent event) {
        escenarioPrincipal.mostrarEscenaAsignacionesAlumnos();
    }

    @FXML
    private void clicHorarios(ActionEvent event) {
        escenarioPrincipal.mostrarEscenaHorarios();
    }

    @FXML
    private void clicInstructores(ActionEvent event) {
        escenarioPrincipal.mostrarEscenaInstructores();
    }

    @FXML
    private void clicAcercaDe() {
        escenarioPrincipal.mostrarEscenaAcercaDe();
    }

    @FXML
    private void clicCerrarSesion() {
        escenarioPrincipal.mostrarLogin();
    }

    @FXML
    private void reportAlumnos() {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("IMAGE_ENTIDAD", PAQUETE_IMAGE + "estudiante.png");
        GenerarReporte.getInstance().mostrarReporte("ReportAlumnos.jasper", parametros, "Reporte Alumnos");
    }

    @FXML
    void reportInstructores() {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("IMAGE_ENTIDAD", PAQUETE_IMAGE + "Instructores.png");
        GenerarReporte.getInstance().mostrarReporte("ReportInstructores.jasper", parametros, "Reporte Instructores");
    }

    @FXML
    void reportCursos() {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("IMAGE_ENTIDAD", PAQUETE_IMAGE + "Curso.png");
        GenerarReporte.getInstance().mostrarReporte("ReportCursos.jasper", parametros, "Reporte Cursos");
    }

    @FXML
    void reportCursosById() {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("IMAGE_ENTIDAD", PAQUETE_IMAGE + "Curso.png");
        parametros.put("idCurso", 1);
        GenerarReporte.getInstance().mostrarReporte("ReportCursosById.jasper", parametros, "Reporte Curso");
    }

    @FXML
    private void reportSalones() {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("IMAGE_ENTIDAD", PAQUETE_IMAGE + "Salones.png");
        GenerarReporte.getInstance().mostrarReporte("ReportSalones.jasper", parametros, "Reporte Salones");
    }

    @FXML
    private void reportCarreras() {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("IMAGE_ENTIDAD", PAQUETE_IMAGE + "Carrera_tecnica.png");
        GenerarReporte.getInstance().mostrarReporte("ReportCarreras.jasper", parametros, "Reporte Carreras Técnicas");
    }

    @FXML
    private void reportHorarios() {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("IMAGE_ENTIDAD", PAQUETE_IMAGE + "Horarios.png");
        GenerarReporte.getInstance().mostrarReporte("ReportHorarios.jasper", parametros, "Reporte Horarios");
    }

    @FXML
    private void reportAsignaciones() {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("IMAGE_ENTIDAD", PAQUETE_IMAGE + "Asignaciones.png");
        GenerarReporte.getInstance().mostrarReporte("ReportAsignaciones.jasper", parametros, "Reporte Asignaciones");
    }

    @FXML
    private void reportAsignacionesById() {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("IMAGE_ENTIDAD", PAQUETE_IMAGE + "Asignaciones.png");
        parametros.put("idAsignacion", 1);
        GenerarReporte.getInstance().mostrarReporte("ReportAsignacionAlumnoById.jasper", parametros, "Reporte Asignación Alumno");
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
}
