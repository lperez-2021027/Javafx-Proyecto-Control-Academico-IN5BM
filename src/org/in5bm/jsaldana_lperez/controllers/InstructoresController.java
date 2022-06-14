package org.in5bm.jsaldana_lperez.controllers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import org.in5bm.jsaldana_lperez.db.Conexion;
import org.in5bm.jsaldana_lperez.models.Instructores;
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

public class InstructoresController implements Initializable {

    private final String PAQUETE_IMAGE = "org/in5bm/jsaldana_lperez/resources/images/";
    private final String TITULO_ALERT = "Control Académico Kinal";
    private final String TIPO_ALERT_WARNING = "warning";
    private final String TIPO_ALERT_INFORMATION = "information";

    private enum Operacion {
        NINGUNO, GUARDAR, MODIFICAR
    }

    private Operacion operacion = Operacion.NINGUNO;

    private ObservableList<Instructores> listaInstructores;

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
    private TextField txtId;
    @FXML
    private TextField txtNombre2;
    @FXML
    private TextField txtNombre1;
    @FXML
    private TextField txtNombre3;
    @FXML
    private TextField txtApellido1;
    @FXML
    private TextField txtApellido2;
    @FXML
    private TextField txtDireccion;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtTelefono;
    @FXML
    private DatePicker dtpkFechaNacimiento;
    @FXML
    private TableView<Instructores> tblInstructores;
    @FXML
    private TableColumn<Instructores, Integer> colId; // Modelo de datos, tipo de dato
    @FXML
    private TableColumn<Instructores, String> colNombre1;
    @FXML
    private TableColumn<Instructores, String> colNombre2;
    @FXML
    private TableColumn<Instructores, String> colNombre3;
    @FXML
    private TableColumn<Instructores, String> colApellido1;
    @FXML
    private TableColumn<Instructores, String> colApellido2;
    @FXML
    private TableColumn<Instructores, String> colDireccion;
    @FXML
    private TableColumn<Instructores, String> colEmail;
    @FXML
    private TableColumn<Instructores, String> colTelefono;
    @FXML
    private TableColumn<Instructores, LocalDate> colFechaNacimiento;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }

    public void cargarDatos() {
        tblInstructores.setItems(getInstructores());
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombre1.setCellValueFactory(new PropertyValueFactory<>("nombre1"));
        colNombre2.setCellValueFactory(new PropertyValueFactory<>("nombre2"));
        colNombre3.setCellValueFactory(new PropertyValueFactory<>("nombre3"));
        colApellido1.setCellValueFactory(new PropertyValueFactory<>("apellido1"));
        colApellido2.setCellValueFactory(new PropertyValueFactory<>("apellido2"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<>("direccion"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
        colFechaNacimiento.setCellValueFactory(new PropertyValueFactory<>("fechaNacimiento"));
    }

    public boolean existeElementoSeleccionado() {
        return tblInstructores.getSelectionModel().getSelectedItem() != null;
    }

    @FXML
    public void seleccionarElemento() {
        if (existeElementoSeleccionado()) {
            txtId.setText(Integer.toString(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getId()));
            txtNombre1.setText(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getNombre1());
            txtNombre2.setText(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getNombre2());
            txtNombre3.setText(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getNombre3());
            txtApellido1.setText(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getApellido1());
            txtApellido2.setText(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getApellido2());
            txtDireccion.setText(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getDireccion());
            txtEmail.setText(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getEmail());
            txtTelefono.setText(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getTelefono());
            dtpkFechaNacimiento.setValue(((Instructores) tblInstructores.getSelectionModel().getSelectedItem()).getFechaNacimiento());
        }
    }

    public boolean eliminarInstructor() {
        if (existeElementoSeleccionado()) {
            Instructores instructores = (Instructores) tblInstructores.getSelectionModel().getSelectedItem();
            System.out.println(instructores.toString());

            PreparedStatement pstmt = null;
            try {
                pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_instructores_delete(?)}");
                pstmt.setInt(1, instructores.getId());
                System.out.println(pstmt);

                pstmt.execute();

                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                System.err.println("Se pordujo un error al intentar eliminar el registro: " + instructores.toString());
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
        }
        return false;
    }

    public ObservableList getInstructores() {
        ArrayList<Instructores> lista = new ArrayList<>();

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_instructores_read()}");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Instructores instructor = new Instructores();
                instructor.setId(rs.getInt(1));
                instructor.setNombre1(rs.getString(2));
                instructor.setNombre2(rs.getString(3));
                instructor.setNombre3(rs.getString(4));
                instructor.setApellido1(rs.getString(5));
                instructor.setApellido2(rs.getString(6));
                instructor.setDireccion(rs.getString(7));
                instructor.setEmail(rs.getString(8));
                instructor.setTelefono(rs.getString(9));
                instructor.setFechaNacimiento(rs.getDate(10).toLocalDate());

                lista.add(instructor);

                System.out.println(instructor.toString());
            }

            listaInstructores = FXCollections.observableArrayList(lista);

        } catch (SQLException e) {
            System.err.println("Ocurrio un error al intentar consultar la lista de instructores.");
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
        return listaInstructores;
    }

    private void habilitarCampos() {
        txtId.setEditable(false);
        txtNombre1.setEditable(true);
        txtNombre2.setEditable(true);
        txtNombre3.setEditable(true);
        txtApellido1.setEditable(true);
        txtApellido2.setEditable(true);
        txtDireccion.setEditable(true);
        txtEmail.setEditable(true);
        txtTelefono.setEditable(true);
        dtpkFechaNacimiento.setEditable(true);

        txtId.setDisable(true);
        txtNombre1.setDisable(false);
        txtNombre2.setDisable(false);
        txtNombre3.setDisable(false);
        txtApellido1.setDisable(false);
        txtApellido2.setDisable(false);
        txtDireccion.setDisable(false);
        txtEmail.setDisable(false);
        txtTelefono.setDisable(false);
        dtpkFechaNacimiento.setDisable(false);
    }

    private void deshabilitarCampos() {
        txtId.setEditable(false);
        txtNombre1.setEditable(false);
        txtNombre2.setEditable(false);
        txtNombre3.setEditable(false);
        txtApellido1.setEditable(false);
        txtApellido2.setEditable(false);
        txtDireccion.setEditable(false);
        txtEmail.setEditable(false);
        txtTelefono.setEditable(false);
        dtpkFechaNacimiento.setEditable(false);

        txtId.setDisable(true);
        txtNombre1.setDisable(true);
        txtNombre2.setDisable(true);
        txtNombre3.setDisable(true);
        txtApellido1.setDisable(true);
        txtApellido2.setDisable(true);
        txtDireccion.setDisable(true);
        txtEmail.setDisable(true);
        txtTelefono.setDisable(true);
        dtpkFechaNacimiento.setDisable(true);
    }

    private void limpiarCampos() {
        txtId.setText("");
        txtNombre1.setText("");
        txtNombre2.setText("");
        txtNombre3.setText("");
        txtApellido1.setText("");
        txtApellido2.setText("");
        txtDireccion.setText("");
        txtEmail.setText("");
        txtTelefono.setText("");
        //dtpkFechaNacimiento.getEditor().clear();
        dtpkFechaNacimiento.setValue(null);
    }

    @FXML
    void clicNuevo() {
        switch (operacion) {
            case NINGUNO:
                if (existeElementoSeleccionado()) {
                    tblInstructores.getSelectionModel().clearSelection();
                }

                habilitarCampos();
                limpiarCampos();
                dtpkFechaNacimiento.setEditable(false);

                tblInstructores.setDisable(true);

                btnNuevo.setText("Guardar");
                btnModificar.setText("Cancelar");
                imgNuevo.setImage(new Image(PAQUETE_IMAGE + "Guardar.png"));
                imgModificar.setImage(new Image(PAQUETE_IMAGE + "Cancelar.png"));

                btnEliminar.setVisible(false);
                btnReporte.setVisible(false);
                btnEliminar.setDisable(true);
                btnReporte.setDisable(true);
                imgReporte.setVisible(false);
                imgReporte.setDisable(true);
                imgEliminar.setVisible(false);
                imgEliminar.setDisable(true);

                operacion = Operacion.GUARDAR;
                break;
            case GUARDAR:
                if (comprobacionCamposTxt()) {
                    if (agreagarInstructor()) {
                        cargarDatos();
                        deshabilitarCampos();
                        limpiarCampos();

                        tblInstructores.setDisable(false);

                        btnNuevo.setText("Nuevo");
                        btnModificar.setText("Modificar");
                        imgNuevo.setImage(new Image(PAQUETE_IMAGE + "Agregar.png"));
                        imgModificar.setImage(new Image(PAQUETE_IMAGE + "Editar.png"));

                        btnEliminar.setVisible(true);
                        btnEliminar.setDisable(false);
                        imgEliminar.setVisible(true);
                        btnReporte.setVisible(true);
                        btnReporte.setDisable(false);
                        imgReporte.setVisible(true);

                        operacion = Operacion.NINGUNO;
                    }
                }
                break;
        }
    }

    private boolean agreagarInstructor() {
        Instructores instructor = new Instructores();
        instructor.setNombre1(txtNombre1.getText());
        instructor.setNombre2(txtNombre2.getText());
        instructor.setNombre3(txtNombre3.getText());
        instructor.setApellido1(txtApellido1.getText());
        instructor.setApellido2(txtApellido2.getText());
        instructor.setDireccion(txtDireccion.getText());
        instructor.setEmail(txtEmail.getText());
        instructor.setTelefono(txtTelefono.getText());
        instructor.setFechaNacimiento(dtpkFechaNacimiento.getValue());

        PreparedStatement pstmt = null;

        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_instructores_create(?, ?, ?, ?, ?, ?, ?, ?, ?)}");
            pstmt.setString(1, instructor.getNombre1());
            pstmt.setString(2, instructor.getNombre2());
            pstmt.setString(3, instructor.getNombre3());
            pstmt.setString(4, instructor.getApellido1());
            pstmt.setString(5, instructor.getApellido2());
            pstmt.setString(6, instructor.getDireccion());
            pstmt.setString(7, instructor.getEmail());
            pstmt.setString(8, instructor.getTelefono());
            pstmt.setDate(9, Date.valueOf(instructor.getFechaNacimiento()));
            System.out.println(pstmt);
            pstmt.execute();

            listaInstructores.add(instructor);

            return true;
        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar agregar el registro : " + instructor.toString());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    private boolean comprobacionCamposTxt() {
        if (txtNombre1.getText().isEmpty()
                || txtApellido1.getText().isEmpty()
                || txtEmail.getText().isEmpty()
                || txtTelefono.getText().isEmpty()
                || dtpkFechaNacimiento.getValue() == null) {
            mostrarAlert(TIPO_ALERT_WARNING, "Verifique que los campos Primer Nombre, Primer Apellido, Email, Teléfono o Fecha Nacimiento contengan datos.");
        } else {
            if (txtNombre1.getText().charAt(0) == ' '
                    || txtApellido1.getText().charAt(0) == ' '
                    || txtEmail.getText().charAt(0) == ' '
                    || txtTelefono.getText().charAt(0) == ' ') {
                mostrarAlert(TIPO_ALERT_WARNING, "Verifique que los campos Primer Nombre, Primer Apellido, Email o Teléfono no contengan espacios al inicio.");
            } else if (!txtEmail.getText().contains("@") || !txtEmail.getText().contains(".com")) {
                mostrarAlert(TIPO_ALERT_WARNING, "Datos no validos en el campo Email.");
            } else {
                return true;
            }
        }
        return false;
    }

    private void mostrarAlert(String alertType, String alertContent) {
        switch (alertType) {
            case TIPO_ALERT_WARNING:
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setHeaderText(null);
                alert.setTitle(TITULO_ALERT);
                alert.setContentText(alertContent);
                alert.show();
                break;
            case TIPO_ALERT_INFORMATION:
                Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                alert2.setHeaderText(null);
                alert2.setTitle(TITULO_ALERT);
                alert2.setContentText(alertContent);
                Stage stageAlert2 = (Stage) alert2.getDialogPane().getScene().getWindow();
                stageAlert2.getIcons().add(new Image(PAQUETE_IMAGE + "informacion.png"));
                alert2.show();
                break;
        }
    }

    @FXML
    void clicModificar() {
        switch (operacion) {
            case NINGUNO:
                if (existeElementoSeleccionado()) {
                    habilitarCampos();
                    dtpkFechaNacimiento.setEditable(false);

                    btnNuevo.setVisible(false);
                    btnNuevo.setDisable(true);
                    imgNuevo.setVisible(false);
                    btnReporte.setVisible(false);
                    btnReporte.setDisable(true);
                    imgReporte.setVisible(false);

                    btnModificar.setText("Guardar");
                    btnEliminar.setText("Cancelar");
                    imgEliminar.setImage(new Image(PAQUETE_IMAGE + "Cancelar.png"));
                    imgModificar.setImage(new Image(PAQUETE_IMAGE + "Guardar.png"));

                    operacion = Operacion.MODIFICAR;
                } else {
                    mostrarAlert(TIPO_ALERT_WARNING, "Antes de continuar, seleccione un registro.");
                }
                break;
            case GUARDAR:
                deshabilitarCampos();
                limpiarCampos();

                tblInstructores.setDisable(false);

                btnNuevo.setText("Nuevo");
                btnModificar.setText("Modificar");
                imgNuevo.setImage(new Image(PAQUETE_IMAGE + "Agregar.png"));
                imgModificar.setImage(new Image(PAQUETE_IMAGE + "Editar.png"));

                btnEliminar.setVisible(true);
                btnEliminar.setDisable(false);
                imgEliminar.setVisible(true);
                btnReporte.setVisible(true);
                btnReporte.setDisable(false);
                imgReporte.setVisible(true);

                operacion = Operacion.NINGUNO;
                break;
            case MODIFICAR:
                if (actualizarInstrucor()) {
                    if (comprobacionCamposTxt()) {
                        cargarDatos();
                        limpiarCampos();
                        deshabilitarCampos();

                        btnEliminar.setText("Eliminar");
                        btnModificar.setText("Modificar");
                        imgEliminar.setImage(new Image(PAQUETE_IMAGE + "Eliminar.png"));
                        imgModificar.setImage(new Image(PAQUETE_IMAGE + "Editar.png"));

                        btnNuevo.setVisible(true);
                        btnNuevo.setDisable(false);
                        imgNuevo.setVisible(true);
                        btnReporte.setVisible(true);
                        btnReporte.setDisable(false);
                        imgReporte.setVisible(true);

                        operacion = Operacion.NINGUNO;
                    }
                }
                break;
        }
    }

    private boolean actualizarInstrucor() {
        Instructores instructor = new Instructores();
        instructor.setId(Integer.parseInt(txtId.getText()));
        instructor.setNombre1(txtNombre1.getText());
        instructor.setNombre2(txtNombre2.getText());
        instructor.setNombre3(txtNombre3.getText());
        instructor.setApellido1(txtApellido1.getText());
        instructor.setApellido2(txtApellido2.getText());
        instructor.setDireccion(txtDireccion.getText());
        instructor.setEmail(txtEmail.getText());
        instructor.setTelefono(txtTelefono.getText());
        instructor.setFechaNacimiento(dtpkFechaNacimiento.getValue());

        PreparedStatement pstmt = null;
        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_instructores_update(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)}");
            pstmt.setInt(1, instructor.getId());
            pstmt.setString(2, instructor.getNombre1());
            pstmt.setString(3, instructor.getNombre2());
            pstmt.setString(4, instructor.getNombre3());
            pstmt.setString(5, instructor.getApellido1());
            pstmt.setString(6, instructor.getApellido2());
            pstmt.setString(7, instructor.getDireccion());
            pstmt.setString(8, instructor.getEmail());
            pstmt.setString(9, instructor.getTelefono());
            pstmt.setDate(10, Date.valueOf(instructor.getFechaNacimiento()));
            System.out.println(pstmt);
            pstmt.execute();

            return true;
        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar actualizar el registro : " + instructor.toString());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pstmt != null) {
                try {
                    pstmt.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    @FXML
    void clicEliminar(ActionEvent event) {
        switch (operacion) {
            case MODIFICAR:
                tblInstructores.getSelectionModel().clearSelection();

                limpiarCampos();
                deshabilitarCampos();

                btnEliminar.setText("Eliminar");
                btnModificar.setText("Modificar");
                imgEliminar.setImage(new Image(PAQUETE_IMAGE + "Eliminar.png"));
                imgModificar.setImage(new Image(PAQUETE_IMAGE + "Editar.png"));

                btnNuevo.setVisible(true);
                btnNuevo.setDisable(false);
                imgNuevo.setVisible(true);
                btnReporte.setVisible(true);
                btnReporte.setDisable(false);
                imgReporte.setVisible(true);

                operacion = Operacion.NINGUNO;
                break;
            case NINGUNO:
                if (existeElementoSeleccionado()) {
                    Alert aler = new Alert(Alert.AlertType.CONFIRMATION);
                    aler.setTitle("Control Académico Kinal");
                    aler.setHeaderText(null);
                    aler.setContentText("¿Desea eliminar el registro seleccionado?");
                    Optional<ButtonType> result = aler.showAndWait();

                    if (result.get() == ButtonType.OK) {
                        if (eliminarInstructor()) {
                            limpiarCampos();
                            listaInstructores.remove(tblInstructores.getSelectionModel().getFocusedIndex());
                            cargarDatos();
                            mostrarAlert(TIPO_ALERT_INFORMATION, "Registro eliminado exitosamente");
                        }
                    } else {
                        tblInstructores.getSelectionModel().clearSelection();
                        limpiarCampos();
                    }
                } else {
                    mostrarAlert(TIPO_ALERT_WARNING, "Antes de continuar, seleccione un registro.");
                }
        }
    }

    @FXML
    void clicReporte(ActionEvent event) {

    }

    @FXML
    public void clicRegresar() {
        escenarioPrincipal.mostrarEscenaPrincipal();
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }

}
