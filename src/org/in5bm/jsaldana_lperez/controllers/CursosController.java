package org.in5bm.jsaldana_lperez.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.in5bm.jsaldana_lperez.system.Principal;
import org.in5bm.jsaldana_lperez.models.CarrerasTecnicas;
import org.in5bm.jsaldana_lperez.models.Horarios;
import org.in5bm.jsaldana_lperez.models.Instructores;
import org.in5bm.jsaldana_lperez.models.Salones;
import org.in5bm.jsaldana_lperez.models.Cursos;

/**
 *
 * @author José Roberto Saldaña Arrazola
 * @author Luis Carlos Pérez
 * @date 26-abr-2022
 * @time 8:26:32
 *
 * Código técnico: IN5BM
 */

public class CursosController implements Initializable {
    
    private Principal escenarioPrincipal;
    
    @FXML
    private ImageView imgNuevo;
    @FXML
    private Button btnNuevo;
    @FXML
    private ImageView imgModificar;
    @FXML
    private Button btnModificar;
    @FXML
    private ImageView imgEliminar;
    @FXML
    private Button btnEliminar;
    @FXML
    private ImageView imgReporte;
    @FXML
    private Button btnReporte;
    @FXML
    private TextField txtNombreCurso;
    @FXML
    private Spinner<Integer> spnCiclo;
    @FXML
    private Spinner<Integer> spnCupoMaximo;
    @FXML
    private Spinner<Integer> spnCupoMinimo;
    @FXML
    private ComboBox<CarrerasTecnicas> cmbCarreraTecnica;
    @FXML
    private ComboBox<Horarios> cmbHorario;
    @FXML
    private ComboBox<Salones> cmbSalon;
    @FXML
    private ComboBox<Instructores> cmbInstructor;
    @FXML
    private TextField txtNombreCurso1;
    @FXML
    private TableView<Cursos> tblCursos;
    @FXML
    private TableColumn<Cursos, Integer> colId;
    @FXML
    private TableColumn<Cursos, String> colNombreCurso;
    @FXML
    private TableColumn<Cursos, Integer> colCiclo;
    @FXML
    private TableColumn<Cursos, Integer> colMaximo;
    @FXML
    private TableColumn<Cursos, Integer> colMinimo;
    @FXML
    private TableColumn<Cursos, Integer> colInstructor;
    @FXML
    private TableColumn<Cursos, String> colCarreraTecnica;
    @FXML
    private TableColumn<Cursos, Integer> colHorario;
    @FXML
    private TableColumn<Cursos, String> colSalon;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    private void habilitarCampos() {
        
    }

    private void limpiarCampos() {
        
    }

    @FXML
    private void clicNuevo() {
        
    }
    
    @FXML
    private void clicModificar() {
        
    }

    @FXML
    private void clicEliminar() {
        
    }

    @FXML
    private void clicReporte() {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setHeaderText(null);
        alerta.setTitle("Informacion");
        alerta.setContentText("Función solo disponible en la versión pro.");
        Stage stageAlert = (Stage) alerta.getDialogPane().getScene().getWindow();
        stageAlert.getIcons().add(new Image("org/in5bm/jsaldana_lperez/resources/images/informacion.png"));
        alerta.show();
    }
    
    @FXML
    public void clicRegresar(MouseEvent event){
        escenarioPrincipal.mostrarEscenaPrincipal();
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
}