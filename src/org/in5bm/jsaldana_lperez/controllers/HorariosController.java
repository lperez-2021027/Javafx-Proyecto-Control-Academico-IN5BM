package org.in5bm.jsaldana_lperez.controllers;

import com.jfoenix.controls.JFXTimePicker;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import java.sql.Time;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.in5bm.jsaldana_lperez.db.Conexion;
import org.in5bm.jsaldana_lperez.models.Horarios;
import org.in5bm.jsaldana_lperez.reports.GenerarReporte;
import org.in5bm.jsaldana_lperez.system.Principal;

/**
 *
 * @author José Roberto Saldaña Arrazola
 * @author Luis Carlos Pérez
 * @date 26-abr-2022
 * @time 8:26:32
 *
 * Código técnico: IN5BM
 */

public class HorariosController implements Initializable {

    private final String PAQUETE_IMAGE = "org/in5bm/jsaldana_lperez/resources/images/";

    private enum Operacion {
        NINGUNO, GUARDAR, MODIFICAR
    }

    private Operacion operacion = Operacion.NINGUNO;

    private Principal escenarioPrincipal;

    @FXML
    private TextField txtId;
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
    private TableView<Horarios> tblHorarios;
    @FXML
    private TableColumn<Horarios, Integer> colId;
    @FXML
    private TableColumn<Horarios, Time> colHoraInicio;
    @FXML
    private TableColumn<Horarios, Time> colHoraFInal;
    @FXML
    private TableColumn<Horarios, Boolean> colLunes;
    @FXML
    private TableColumn<Horarios, Boolean> colMartes;
    @FXML
    private TableColumn<Horarios, Boolean> colMiercoles;
    @FXML
    private TableColumn<Horarios, Boolean> colJueves;
    @FXML
    private TableColumn<Horarios, Boolean> colViernes;
    @FXML
    private CheckBox ckbLunes;
    @FXML
    private CheckBox ckbMartes;
    @FXML
    private CheckBox ckbMiercoles;
    @FXML
    private CheckBox ckbJueves;
    @FXML
    private CheckBox ckbViernes;
    @FXML
    private JFXTimePicker tpkHoraInicio;
    @FXML
    private JFXTimePicker tpkHoraFinal;
    @FXML
    private Label lblRegistros;

    private ObservableList<Horarios> listaHorarios;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }

    public void cargarDatos() {
        tblHorarios.setItems(getHorarios());
        colId.setCellValueFactory(new PropertyValueFactory("id"));
        colHoraFInal.setCellValueFactory(new PropertyValueFactory("horarioInicio"));
        colHoraInicio.setCellValueFactory(new PropertyValueFactory("horarioFinal"));
        colLunes.setCellValueFactory(new PropertyValueFactory("lunes"));
        colMartes.setCellValueFactory(new PropertyValueFactory("martes"));
        colMiercoles.setCellValueFactory(new PropertyValueFactory("miercoles"));
        colJueves.setCellValueFactory(new PropertyValueFactory("jueves"));
        colViernes.setCellValueFactory(new PropertyValueFactory("viernes"));
        contarRegistros();
    }
    
    public void contarRegistros() {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_horarios_count()}");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                lblRegistros.setText("Total de registros: " + Integer.toString(rs.getInt(1)));
            }
        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar consultar el total de la lista horarios");
            System.out.println("Message: " + e.getMessage());
            System.out.println("Error code: " + e.getErrorCode());
            System.out.println("SQLState: " + e.getSQLState());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean existeElementoSeleccionado() {
        return (tblHorarios.getSelectionModel().getSelectedItem() != null);
    }

    @FXML
    public void seleccionarElemento() {
        if (existeElementoSeleccionado()) {
            txtId.setText(String.valueOf(((Horarios) tblHorarios.getSelectionModel().getSelectedItem()).getId()));
            tpkHoraFinal.setValue(((Horarios) tblHorarios.getSelectionModel().getSelectedItem()).getHorarioFinal());
            tpkHoraInicio.setValue(((Horarios) tblHorarios.getSelectionModel().getSelectedItem()).getHorarioInicio());
            ckbLunes.setSelected(((Horarios) tblHorarios.getSelectionModel().getSelectedItem()).getLunes());
            ckbMartes.setSelected(((Horarios) tblHorarios.getSelectionModel().getSelectedItem()).getMartes());
            ckbMiercoles.setSelected(((Horarios) tblHorarios.getSelectionModel().getSelectedItem()).getMiercoles());
            ckbJueves.setSelected(((Horarios) tblHorarios.getSelectionModel().getSelectedItem()).getJueves());
            ckbViernes.setSelected(((Horarios) tblHorarios.getSelectionModel().getSelectedItem()).getViernes());
        }
    }

    public boolean eliminarHorarios() {
        if (existeElementoSeleccionado()) {
            Horarios horarios = (Horarios) tblHorarios.getSelectionModel().getSelectedItem();

            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Control Académico Kinal");
            confirm.setHeaderText(null);
            confirm.setContentText("Esta apunto de eliminar el registro con los siguientes datos: "
                    + "\n" + horarios.getId()
                    + "\nEsta seguro?");

            Optional<ButtonType> result = confirm.showAndWait();

            if (result.get().equals(ButtonType.OK)) {

                PreparedStatement pstmt = null;
                try {
                    pstmt = Conexion.getInstance().getConexion().prepareCall("{Call sp_horarios_delete(?)}");
                    pstmt.setInt(1, horarios.getId());
                    System.out.println(pstmt.toString());
                    pstmt.execute();
                    return true;
                } catch (SQLException e) {
                    System.err.println("\nSe produjo un error al intentar eliminar el alumno siguiente registro: " + horarios.toString());
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (pstmt != null) {
                            pstmt.close();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else {
                tblHorarios.getSelectionModel().clearSelection();
            }
        }
        return false;
    }

    public ObservableList getHorarios() {

        ArrayList<Horarios> arrayListaHorarios = new ArrayList<>();

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{Call sp_horarios_read()}");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Horarios horarios = new Horarios();
                horarios.setId(rs.getInt(1));
                horarios.setHorarioFinal(rs.getTime(2).toLocalTime());
                horarios.setHorarioInicio(rs.getTime(3).toLocalTime());
                horarios.setLunes(rs.getBoolean(4));
                horarios.setMartes(rs.getBoolean(5));
                horarios.setMiercoles(rs.getBoolean(6));
                horarios.setJueves(rs.getBoolean(7));
                horarios.setViernes(rs.getBoolean(8));

                arrayListaHorarios.add(horarios);

                System.out.println(horarios.toString());
            }
        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar conmsultar la lista de horarios");
            System.out.println("Message: " + e.getMessage());
            System.out.println("Error code: " + e.getErrorCode());
            System.out.println("SQLState: " + e.getSQLState());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return FXCollections.observableArrayList(arrayListaHorarios);
    }

    private void deshabilitarCampos() {
        txtId.setDisable(true);
        tpkHoraFinal.setDisable(true);
        tpkHoraInicio.setDisable(true);
        ckbLunes.setDisable(true);
        ckbMartes.setDisable(true);
        ckbMiercoles.setDisable(true);
        ckbJueves.setDisable(true);
        ckbViernes.setDisable(true);
    }

    private void habilitarCampos() {
        tpkHoraFinal.setDisable(false);
        tpkHoraInicio.setDisable(false);
        ckbLunes.setDisable(false);
        ckbMartes.setDisable(false);
        ckbMiercoles.setDisable(false);
        ckbJueves.setDisable(false);
        ckbViernes.setDisable(false);
    }

    private void limpiarCampos() {
        txtId.setText("");
        tpkHoraFinal.getEditor().clear();
        tpkHoraInicio.getEditor().clear();
        ckbLunes.setSelected(false);
        ckbMartes.setSelected(false);
        ckbMiercoles.setSelected(false);
        ckbJueves.setSelected(false);
        ckbViernes.setSelected(false);
    }

    @FXML
    private void clicNuevo() {
        switch (operacion) {
            case NINGUNO:
                habilitarCampos();
                tblHorarios.setDisable(true);
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
                operacion = Operacion.GUARDAR;
                break;
            case GUARDAR:
                if (agregarHorario()) {
                    cargarDatos();
                    limpiarCampos();
                    deshabilitarCampos();
                    tblHorarios.setDisable(false);
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
                    operacion = Operacion.NINGUNO;
                }
                break;
        }
    }

    private boolean agregarHorario() {
        if (!(tpkHoraFinal.getEditor().getText().equals("") || tpkHoraInicio.getEditor().getText().equals(""))) {
            
            Horarios horarios = new Horarios();
            horarios.setHorarioFinal(tpkHoraInicio.getValue());
            horarios.setHorarioInicio(tpkHoraFinal.getValue());
            horarios.setLunes(ckbLunes.isSelected());
            horarios.setMartes(ckbMartes.isSelected());
            horarios.setMiercoles(ckbMiercoles.isSelected());
            horarios.setJueves(ckbJueves.isSelected());
            horarios.setViernes(ckbViernes.isSelected());

            PreparedStatement pstmt = null;

            try {
                pstmt = Conexion.getInstance().getConexion().prepareCall("Call sp_horarios_create(?, ?, ?, ?, ?, ?, ?)");

                System.out.println(pstmt.toString());

                pstmt.setTime(1, Time.valueOf(horarios.getHorarioFinal()));
                pstmt.setTime(2, Time.valueOf(horarios.getHorarioInicio()));
                pstmt.setBoolean(3, horarios.getLunes());
                pstmt.setBoolean(4, horarios.getMartes());
                pstmt.setBoolean(5, horarios.getMiercoles());
                pstmt.setBoolean(6, horarios.getJueves());
                pstmt.setBoolean(7, horarios.getViernes());
                pstmt.execute();
                cargarDatos();
                return true;

            } catch (SQLException e) {
                System.err.println("Se produjo un error al intentar insertar el siguiente registro: " + horarios.toString());
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (pstmt != null) {
                        pstmt.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Control academico");
            alert.setHeaderText(null);
            alert.setContentText("Antes de continuar rellene todos los campos");
            alert.show();
        }
        return false;
    }

    @FXML
    private void clicModificar() {

        switch (operacion) {
            case NINGUNO:

                if (existeElementoSeleccionado()) {
                    habilitarCampos();
                    btnNuevo.setDisable(true);
                    imgNuevo.setVisible(false);
                    btnNuevo.setVisible(false);
                    btnReporte.setVisible(false);
                    imgReporte.setVisible(false);

                    btnEliminar.setText("Cancelar");
                    imgEliminar.setImage(new Image(PAQUETE_IMAGE + "Cancelar.png"));
                    btnModificar.setText("Guardar");
                    imgModificar.setImage(new Image(PAQUETE_IMAGE + "Guardar.png"));
                    btnReporte.setVisible(false);
                    btnReporte.setDisable(true);
                    operacion = Operacion.MODIFICAR;
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Control academico");
                    alert.setHeaderText(null);
                    alert.setContentText("Antes de continuar selecciona un registro");
                    alert.show();
                }
                break;

            case GUARDAR:
                tblHorarios.setDisable(false);
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
                limpiarCampos();
                deshabilitarCampos();
                tblHorarios.getSelectionModel().clearSelection();
                operacion = Operacion.NINGUNO;
                break;
            case MODIFICAR:
                if (existeElementoSeleccionado()) {
                    if (actualizarHorario()) {
                        cargarDatos();
                        limpiarCampos();
                        deshabilitarCampos();

                        tblHorarios.setDisable(false);
                        tblHorarios.getSelectionModel().clearSelection();
                        btnModificar.setText("Modificar");
                        imgModificar.setImage(new Image(PAQUETE_IMAGE + "Editar.png"));
                        btnEliminar.setText("Eliminar");
                        imgEliminar.setImage(new Image(PAQUETE_IMAGE + "Eliminar.png"));
                        btnNuevo.setVisible(true);
                        imgNuevo.setVisible(true);
                        btnEliminar.setVisible(true);
                        btnReporte.setVisible(true);
                        imgReporte.setVisible(true);
                        imgEliminar.setVisible(true);
                        btnEliminar.setDisable(false);
                        imgNuevo.setDisable(false);
                        btnNuevo.setDisable(false);
                        btnReporte.setDisable(false);
                        imgReporte.setDisable(false);
                        imgEliminar.setDisable(false);
                        imgReporte.setDisable(false);
                        operacion = Operacion.NINGUNO;
                    }
                    break;
                }
        }
    }

    private boolean actualizarHorario() {
        if (!(tpkHoraFinal.getEditor().getText().equals("") || tpkHoraInicio.getEditor().getText().equals(""))) {
            
            Horarios horarios = new Horarios();
            horarios.setId(Integer.valueOf(txtId.getText()));
            horarios.setHorarioFinal(tpkHoraFinal.getValue());
            horarios.setHorarioInicio(tpkHoraInicio.getValue());
            horarios.setLunes(ckbLunes.isSelected());
            horarios.setMartes(ckbMartes.isSelected());
            horarios.setMiercoles(ckbMiercoles.isSelected());
            horarios.setJueves(ckbJueves.isSelected());
            horarios.setViernes(ckbViernes.isSelected());

            PreparedStatement pstmt = null;

            try {
                pstmt = Conexion.getInstance().getConexion().prepareCall("{Call sp_horarios_update(?, ?, ?, ?, ?, ?, ?, ?)}");
                pstmt.setInt(1, horarios.getId());
                pstmt.setTime(2, Time.valueOf(horarios.getHorarioFinal()));
                pstmt.setTime(3, Time.valueOf(horarios.getHorarioInicio()));
                pstmt.setBoolean(4, horarios.getLunes());
                pstmt.setBoolean(5, horarios.getMartes());
                pstmt.setBoolean(6, horarios.getMiercoles());
                pstmt.setBoolean(7, horarios.getJueves());
                pstmt.setBoolean(8, horarios.getViernes());

                System.out.println(pstmt);

                pstmt.execute();

                return true;
            } catch (SQLException e) {
                System.out.println("Se produjo un error al intentar actualizar el siguiente registro: " + horarios.toString());
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (pstmt != null) {
                        pstmt.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Control academico");
            alert.setHeaderText(null);
            alert.setContentText("Antes de continuar rellene todos los campos");
            alert.show();
        }
        return false;
    }

    @FXML
    private void clicEliminar() {
        switch (operacion) {
            case MODIFICAR:
                tblHorarios.getSelectionModel().clearSelection();
                btnEliminar.setText("Eliminar");
                imgEliminar.setImage(new Image(PAQUETE_IMAGE + "Eliminar.png"));
                btnModificar.setText("Modificar");
                imgModificar.setImage(new Image(PAQUETE_IMAGE + "Editar.png"));
                btnReporte.setVisible(true);
                imgReporte.setVisible(true);
                imgNuevo.setVisible(true);
                btnNuevo.setVisible(true);
                btnNuevo.setDisable(false);
                imgNuevo.setDisable(false);
                btnReporte.setDisable(false);
                imgReporte.setDisable(false);
                limpiarCampos();
                deshabilitarCampos();
                operacion = Operacion.NINGUNO;
                break;
            case NINGUNO:
                if (existeElementoSeleccionado()) {

                    if (eliminarHorarios()) {
                        cargarDatos();
                        limpiarCampos();
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Control academico");
                        alert.setHeaderText(null);
                        alert.setContentText("Registro eliminado exitosamente");
                        alert.show();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Control academico");
                    alert.setHeaderText(null);
                    alert.setContentText("Antes de continuar selecciona un registro");
                    alert.show();
                }
                break;
        }
    }

    @FXML
    private void clicReporte() {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("IMAGE_ENTIDAD", PAQUETE_IMAGE + "Horarios.png");
        GenerarReporte.getInstance().mostrarReporte("ReportHorarios.jasper", parametros, "Reporte Horarios");
    }

    @FXML
    public void clicRegresar(MouseEvent event) {
        escenarioPrincipal.mostrarEscenaPrincipal();
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
}
