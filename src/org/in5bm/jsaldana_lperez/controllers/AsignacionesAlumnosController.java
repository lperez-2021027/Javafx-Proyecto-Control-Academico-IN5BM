
package org.in5bm.jsaldana_lperez.controllers;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTimePicker;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import java.time.LocalDate;
import javafx.collections.ObservableList;
import javafx.scene.image.Image;
import org.in5bm.jsaldana_lperez.system.Principal;
import org.in5bm.jsaldana_lperez.models.AsignacionesAlumnos;
import org.in5bm.jsaldana_lperez.models.Alumnos;
import org.in5bm.jsaldana_lperez.models.Cursos;

/**
 *
 * @author Luis Carlos Pérez
 * @date 6/06/2022
 * @time 17:39:08
 * 
 *Código técnico: IN5BM
 *
 */
public class AsignacionesAlumnosController implements Initializable{
    
    private Principal escenarioPrincipal;
    
    private final String PAQUETE_IMAGE = "org/in5bm/jsaldana_lperez/resources/images/";
    
    private enum Operacion {
        NINGUNO, GUARDAR, MODIFICAR
    }
    
    private Operacion operacion = Operacion.NINGUNO;
    
    private ObservableList<Alumnos> listaObservableAlumnos;
    private ObservableList<Cursos> listaObservableCursos;
    private ObservableList<AsignacionesAlumnos> listaObservableAsignaciones;

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
    private TextField txtId;
    @FXML
    private JFXTimePicker dpkFechaAsignacion;
    @FXML
    private ComboBox<Cursos> cmbCurso;
    @FXML
    private ComboBox<Alumnos> cmbAlumno;
    @FXML
    private TableView<AsignacionesAlumnos> tblAsignacionesAlumnos;
    @FXML
    private TableColumn<AsignacionesAlumnos, Integer> colId; // Modelo de datos, tipo de dato
    @FXML
    private TableColumn<AsignacionesAlumnos, String> colCarne;
    @FXML
    private TableColumn<Alumnos, String> colNombreAlumno;
    @FXML
    private TableColumn<AsignacionesAlumnos, Integer> colCursoId;
    @FXML
    private TableColumn<Cursos, String> colNombreCurso;
    @FXML
    private TableColumn<AsignacionesAlumnos, LocalDate> colFechaAsignacion;
    
    @Override
    public void initialize (URL location, ResourceBundle resources) {
    }

    private void deshabilitarCampos() {
        txtId.setEditable(false);
        cmbCurso.setEditable(false);
        cmbAlumno.setEditable(false);
        dpkFechaAsignacion.setEditable(false);
        
        txtId.setDisable(true);
        cmbCurso.setDisable(true);
        cmbAlumno.setDisable(true);
        dpkFechaAsignacion.setDisable(true);
    }
    
    private void habilitarCampos() {
        txtId.setEditable(false);
        cmbCurso.setEditable(true);
        cmbAlumno.setEditable(true);
        dpkFechaAsignacion.setEditable(true);
        
        txtId.setDisable(true);
        cmbCurso.setDisable(false);
        cmbAlumno.setDisable(false);
        dpkFechaAsignacion.setDisable(false);
    }
    
    private void limpiarCampos() {
        txtId.setText("");
        cmbCurso.valueProperty().set(null);
        cmbAlumno.valueProperty().set(null);
        
    }
    
    public void cargarDatos() {
        
    }
    
    public boolean existeElementoSeleccionado() {
        return (tblAsignacionesAlumnos.getSelectionModel().getSelectedItem() != null);
    }
    
    @FXML
    private void seleccionarElemento() {
        
    }
    
    @FXML
    private void clicNuevo(MouseEvent event) {
        switch (operacion) {
            case NINGUNO:
                habilitarCampos();
                tblAsignacionesAlumnos.setDisable(true);
                txtId.setEditable(true);
                txtId.setDisable(false);
                limpiarCampos();
                btnNuevo.setText("Guardar");
                btnModificar.setText("Cancelar");
                imgNuevo.setImage(new Image(PAQUETE_IMAGE + "Guardar.png"));
                imgModificar.setImage(new Image(PAQUETE_IMAGE + "Cancelar.png"));
                btnEliminar.setVisible(false);
                btnReporte.setVisible(false);
                imgReporte.setVisible(false);
                imgEliminar.setVisible(false);
                btnEliminar.setDisable(true);
                btnReporte.setDisable(true);
                imgReporte.setDisable(true);
                imgEliminar.setDisable(true);
                imgReporte.setDisable(true);
                operacion = AsignacionesAlumnosController.Operacion.GUARDAR;
                break;
            case GUARDAR:
                if (agregarAsignacionAlumno()) {
                    cargarDatos();
                    limpiarCampos();
                    deshabilitarCampos();
                    tblAsignacionesAlumnos.setDisable(false);
                    btnModificar.setText("Modificar");
                    imgModificar.setImage(new Image(PAQUETE_IMAGE + "Editar.png"));
                    btnNuevo.setText("nuevo");
                    imgNuevo.setImage(new Image(PAQUETE_IMAGE + "Agregar.png"));
                    btnEliminar.setVisible(true);
                    btnReporte.setVisible(true);
                    imgReporte.setVisible(true);
                    imgEliminar.setVisible(true);
                    btnEliminar.setDisable(false);
                    btnReporte.setDisable(false);
                    imgReporte.setDisable(false);
                    imgEliminar.setDisable(false);
                    imgReporte.setDisable(false);
                    operacion = AsignacionesAlumnosController.Operacion.NINGUNO;
                }
                break;
        }
    }
    
    private boolean agregarAsignacionAlumno() {
        return false;
    }

    @FXML
    private void clicNuevo(ActionEvent event) {
    }

    @FXML
    private void clicModificar(MouseEvent event) {
    }

    @FXML
    private void clicModificar(ActionEvent event) {
    }

    @FXML
    private void clicEliminar(MouseEvent event) {
    }

    @FXML
    private void clicEliminar(ActionEvent event) {
    }

    @FXML
    private void clicReporte(MouseEvent event) {
    }

    @FXML
    private void clicReporte(ActionEvent event) {
    }

    @FXML
    private void clicRegresar(MouseEvent event) {
        escenarioPrincipal.mostrarEscenaPrincipal();
    }
    
    public Principal getEscenarioPrincipal () {
        return escenarioPrincipal;
    }
    
    public void setEscenarioPrincipal (Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

}
