package org.in5bm.jsaldana_lperez.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.in5bm.jsaldana_lperez.system.Principal;

import org.in5bm.jsaldana_lperez.models.Alumnos;

import org.in5bm.jsaldana_lperez.db.Conexion;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ButtonType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

/**
 *
 * @author José Roberto Saldaña Arrazola
 * @author Luis Carlos Pérez
 * @date 26-abr-2022
 * @time 8:26:32
 *
 * Código técnico: IN5BM
 */
public class AlumnosController implements Initializable {

    private final String PAQUETE_IMAGE = "org/in5bm/jsaldana_lperez/resources/images/";

    private enum Operacion {
        NINGUNO, GUARDAR, MODIFICAR
    }

    private Operacion operacion = Operacion.NINGUNO;

    private Principal escenarioPrincipal;

    @FXML
    private TextField txtCarne;

    @FXML
    private TextField txtNombre1;

    @FXML
    private TextField txtNombre2;

    @FXML
    private TextField txtNombre3;

    @FXML
    private TextField txtApellido1;

    @FXML
    private TextField txtApellido2;

    @FXML
    private Button btnNuevo;

    @FXML
    private Button btnModificar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnReporte;

    @FXML
    private TableView tblAlumnos;

    @FXML
    private TableColumn colCarne;

    @FXML
    private TableColumn colNombre1;

    @FXML
    private TableColumn colNombre2;

    @FXML
    private TableColumn colNombre3;

    @FXML
    private TableColumn colApellido1;

    @FXML
    private TableColumn colApellido2;

    @FXML
    private ImageView imgNuevo;

    @FXML
    private ImageView imgModificar;

    @FXML
    private ImageView imgEliminar;

    @FXML
    private ImageView imgReporte;

    private ObservableList<Alumnos> listaAlumnos;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }

    public void cargarDatos() {
        tblAlumnos.setItems(getAlumnos());
        colCarne.setCellValueFactory(new PropertyValueFactory<Alumnos, String>("carne"));
        colNombre1.setCellValueFactory(new PropertyValueFactory<Alumnos, String>("nombre1"));
        colNombre2.setCellValueFactory(new PropertyValueFactory<Alumnos, String>("nombre2"));
        colNombre3.setCellValueFactory(new PropertyValueFactory<Alumnos, String>("nombre3"));
        colApellido1.setCellValueFactory(new PropertyValueFactory<Alumnos, String>("apellido1"));
        colApellido2.setCellValueFactory(new PropertyValueFactory<Alumnos, String>("apellido2"));
    }

    public boolean existeElementoSeleccionado() {
        return (tblAlumnos.getSelectionModel().getSelectedItem() != null);
    }

    @FXML
    public void seleccionarElemento() {
        if (existeElementoSeleccionado()) {
            txtCarne.setText(((Alumnos) tblAlumnos.getSelectionModel().getSelectedItem()).getCarne());
            txtNombre1.setText(((Alumnos) tblAlumnos.getSelectionModel().getSelectedItem()).getNombre1());
            txtNombre2.setText(((Alumnos) tblAlumnos.getSelectionModel().getSelectedItem()).getNombre2());
            txtNombre3.setText(((Alumnos) tblAlumnos.getSelectionModel().getSelectedItem()).getNombre3());
            txtApellido1.setText(((Alumnos) tblAlumnos.getSelectionModel().getSelectedItem()).getApellido1());
            txtApellido2.setText(((Alumnos) tblAlumnos.getSelectionModel().getSelectedItem()).getApellido2());
        }
    }

    public boolean eliminarAlumno() {
        if (existeElementoSeleccionado()) {
            Alumnos alumno = (Alumnos) tblAlumnos.getSelectionModel().getSelectedItem();

            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Control Académico Kinal");
            confirm.setHeaderText(null);
            confirm.setContentText("Esta apunto de eliminar el registro con los siguientes datos: "
                    + "\n" + alumno.getCarne() + " || " + alumno.getNombre2() + " || " + alumno.getApellido1()
                    + "\nEsta seguro?");

            Optional<ButtonType> result = confirm.showAndWait();

            if (result.get().equals(ButtonType.OK)) {

                PreparedStatement pstmt = null;
                try {
                    pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_alumnos_delete(?)}");
                    pstmt.setString(1, alumno.getCarne());
                    System.out.println(pstmt.toString());
                    pstmt.execute();
                    return true;
                } catch (SQLException e) {
                    System.err.println("\nSe produjo un error al intentar eliminar el alumno siguiente registro: " + alumno.toString());
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
                tblAlumnos.getSelectionModel().clearSelection();
                limpiarCampos();
            }
        }
        return false;
    }

    public ObservableList getAlumnos() {

        ArrayList<Alumnos> lista = new ArrayList<>();

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{Call sp_alumnos_read()}");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Alumnos alumno = new Alumnos();
                alumno.setCarne(rs.getString(1));
                alumno.setNombre1(rs.getString(2));
                alumno.setNombre2(rs.getString(3));
                alumno.setNombre3(rs.getString(4));
                alumno.setApellido1(rs.getString(5));
                alumno.setApellido2(rs.getString(6));

                lista.add(alumno);

                System.out.println(alumno.toString());
            }
        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar conmsultar la lista de alumnos");
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
        return FXCollections.observableArrayList(lista);
    }

    private void deshabilitarCampos() {
        txtCarne.setEditable(false);
        txtNombre1.setEditable(false);
        txtNombre2.setEditable(false);
        txtNombre3.setEditable(false);
        txtApellido1.setEditable(false);
        txtApellido2.setEditable(false);

        txtCarne.setDisable(true);
        txtNombre1.setDisable(true);
        txtNombre2.setDisable(true);
        txtNombre3.setDisable(true);
        txtApellido1.setDisable(true);
        txtApellido2.setDisable(true);
    }

    private void habilitarCampos() {
        txtCarne.setEditable(false);
        txtNombre1.setEditable(true);
        txtNombre2.setEditable(true);
        txtNombre3.setEditable(true);
        txtApellido1.setEditable(true);
        txtApellido2.setEditable(true);

        txtCarne.setDisable(true);
        txtNombre1.setDisable(false);
        txtNombre2.setDisable(false);
        txtNombre3.setDisable(false);
        txtApellido1.setDisable(false);
        txtApellido2.setDisable(false);
    }

    private void limpiarCampos() {
        txtCarne.setText("");
        txtNombre1.setText("");
        txtNombre2.setText("");
        txtNombre3.setText("");
        txtApellido1.setText("");
        txtApellido2.setText("");
    }

    @FXML
    private void clicNuevo() {
        switch (operacion) {
            case NINGUNO:
                habilitarCampos();
                tblAlumnos.setDisable(true);
                txtCarne.setEditable(true);
                txtCarne.setDisable(false);
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
                if (agregarAlumno()) {
                    cargarDatos();
                    limpiarCampos();
                    deshabilitarCampos();
                    tblAlumnos.setDisable(false);
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

    private boolean agregarAlumno() {
        if (!(txtNombre1.getText().equals("") || txtCarne.getText().equals("") || txtApellido1.getText().equals(""))) {
            if (!(txtCarne.getText().charAt(0) == ' ' || txtNombre1.getText().charAt(0) == ' ' || txtApellido1.getText().charAt(0) == ' ')) {
                if (!(txtCarne.getText().length() >= 8 || txtNombre1.getText().length() >= 16 || txtNombre2.getText().length() >= 16
                        || txtNombre3.getText().length() >= 16 || txtApellido1.getText().length() >= 16 || txtApellido2.getText().length() >= 16)) {

                    Alumnos alumno = new Alumnos();
                    alumno.setCarne(txtCarne.getText());
                    alumno.setNombre1(txtNombre1.getText());
                    alumno.setNombre2(txtNombre2.getText());
                    alumno.setNombre3(txtNombre3.getText());
                    alumno.setApellido1(txtApellido1.getText());
                    alumno.setApellido2(txtApellido2.getText());

                    PreparedStatement pstmt = null;

                    try {
                        pstmt = Conexion.getInstance().getConexion().prepareCall("Call sp_alumnos_create(?, ?, ?, ?, ?, ?)");

                        System.out.println(pstmt.toString());

                        pstmt.setString(1, alumno.getCarne());
                        pstmt.setString(2, alumno.getNombre1());
                        pstmt.setString(3, alumno.getNombre2());
                        pstmt.setString(4, alumno.getNombre3());
                        pstmt.setString(5, alumno.getApellido1());
                        pstmt.setString(6, alumno.getApellido2());
                        pstmt.execute();
                        cargarDatos();
                        return true;

                    } catch (SQLException e) {
                        System.err.println("Se produjo un error al intentar insertar el siguiente registro: " + alumno.toString());
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
                    alert.setContentText("Limite de caracteres excedido");
                    alert.show();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Control academico");
                alert.setHeaderText(null);
                alert.setContentText("Verifique que los campos no contengan un espacio al inicio");
                alert.show();
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
                tblAlumnos.setDisable(false);
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
                operacion = Operacion.NINGUNO;
                break;
            case MODIFICAR:
                if (existeElementoSeleccionado()) {
                    if (actualizarAlumno()) {
                        cargarDatos();
                        limpiarCampos();
                        deshabilitarCampos();

                        tblAlumnos.setDisable(false);
                        tblAlumnos.getSelectionModel().clearSelection();
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

    private boolean actualizarAlumno() {
        if (!(txtNombre1.getText().equals("") || txtCarne.getText().equals("") || txtApellido1.getText().equals(""))) {
            if (!(txtCarne.getText().charAt(0) == ' ' || txtNombre1.getText().charAt(0) == ' ' || txtApellido1.getText().charAt(0) == ' ')) {
                if (!(txtCarne.getText().length() >= 8 || txtNombre1.getText().length() >= 16 || txtNombre2.getText().length() >= 16
                        || txtNombre3.getText().length() >= 16 || txtApellido1.getText().length() >= 16 || txtApellido2.getText().length() >= 16)) {
                    Alumnos alumno = new Alumnos();
                    alumno.setCarne(txtCarne.getText());
                    alumno.setNombre1(txtNombre1.getText());
                    alumno.setNombre2(txtNombre2.getText());
                    alumno.setNombre3(txtNombre3.getText());
                    alumno.setApellido1(txtApellido1.getText());
                    alumno.setApellido2(txtApellido2.getText());

                    PreparedStatement pstmt = null;

                    try {
                        pstmt = Conexion.getInstance().getConexion().prepareCall("{Call sp_alumnos_update(?, ?, ?, ?, ?, ?)}");
                        pstmt.setString(1, alumno.getCarne());
                        pstmt.setString(2, alumno.getNombre1());
                        pstmt.setString(3, alumno.getNombre2());
                        pstmt.setString(4, alumno.getNombre3());
                        pstmt.setString(5, alumno.getApellido1());
                        pstmt.setString(6, alumno.getApellido2());

                        System.out.println(pstmt.toString());

                        pstmt.execute();

                        return true;
                    } catch (SQLException e) {
                        System.out.println("Se produjo un error al intentar actualizar el siguiente registro: " + alumno.toString());
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
                    alert.setContentText("Limite de caracteres excedido");
                    alert.show();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Control academico");
                alert.setHeaderText(null);
                alert.setContentText("Verifique que los campos no contengan un espacio al inicio");
                alert.show();
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
                tblAlumnos.getSelectionModel().clearSelection();
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

                    if (eliminarAlumno()) {
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
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setHeaderText(null);
        alerta.setTitle("Informacion");
        alerta.setContentText("Función solo disponible en la versión pro.");
        Stage stageAlert = (Stage) alerta.getDialogPane().getScene().getWindow();
        stageAlert.getIcons().add(new Image("org/in5bm/jsaldana_lperez/resources/images/informacion.png"));
        alerta.show();
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
