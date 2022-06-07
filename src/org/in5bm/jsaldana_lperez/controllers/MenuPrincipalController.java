package org.in5bm.jsaldana_lperez.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import org.in5bm.jsaldana_lperez.system.Principal;
import javafx.event.ActionEvent;

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

    private Principal escenarioPrincipal;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    public void clicAlumnos(ActionEvent event) {
        escenarioPrincipal.mostrarEscenaAlumnos();
    }

    @FXML
    public void clicSalones(ActionEvent event){
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
    private void clicAsignacionesAlumnos (ActionEvent event) {
        escenarioPrincipal.mostrarEscenaAsignacionesAlumnos();
    }
    
    public Principal getEscenarioPrincipal(){
        return escenarioPrincipal;
    }
    
    public void setEscenarioPrincipal(Principal escenarioPrincipal){
        this.escenarioPrincipal = escenarioPrincipal;
    }
}