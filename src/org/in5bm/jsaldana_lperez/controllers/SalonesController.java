package org.in5bm.jsaldana_lperez.controllers;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.image.ImageView;
import org.in5bm.jsaldana_lperez.db.Conexion;
import org.in5bm.jsaldana_lperez.models.Salones;
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

public class SalonesController implements Initializable {

    private final String PAQUETE_IMAGE = "org/in5bm/jsaldana_lperez/resources/images/";

    private enum Operacion {
        NINGUNO, GUARDAR, MODIFICAR
    }

    private Operacion operacion = Operacion.NINGUNO;

    private Principal escenarioPrincipal;

    @FXML
    TextField txtCodigoSalon;

    @FXML
    TextField txtDescripcion;

    @FXML
    TextField txtCapacidadMax;

    @FXML
    TextField txtEdificio;

    @FXML
    TextField txtNivel;

    @FXML
    Button btnNuevo;

    @FXML
    Button btnModificar;

    @FXML
    Button btnEliminar;

    @FXML
    Button btnReporte;

    @FXML
    private TableView tblSalones;

    @FXML
    private TableColumn colCodigoSalon;

    @FXML
    private TableColumn colDescripcion;

    @FXML
    private TableColumn colCapacidadMax;

    @FXML
    private TableColumn colEdificio;

    @FXML
    private TableColumn colNivel;

    @FXML
    private ImageView imgNuevo;

    @FXML
    private ImageView imgModificar;

    @FXML
    private ImageView imgEliminar;

    @FXML
    private ImageView imgReporte;

    @FXML
    private ObservableList<Salones> ListaSalones;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }

    public void cargarDatos() {
        tblSalones.setItems(getSalones());
        colCodigoSalon.setCellValueFactory(new PropertyValueFactory<Salones, String>("codigoSalon"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<Salones, String>("descripcion"));
        colCapacidadMax.setCellValueFactory(new PropertyValueFactory<Salones, Integer>("capacidadMaxima"));
        colEdificio.setCellValueFactory(new PropertyValueFactory<Salones, String>("edificio"));
        colNivel.setCellValueFactory(new PropertyValueFactory<Salones, Integer>("nivel"));
    }

    public boolean existeElementoSeleccionado() {
        return (tblSalones.getSelectionModel().getSelectedItem() != null);
    }

    @FXML
    public void seleccionarElemento() {
        if (existeElementoSeleccionado()) {
            txtCodigoSalon.setText(((Salones) tblSalones.getSelectionModel().getSelectedItem()).getCodigoSalon());
            txtDescripcion.setText(((Salones) tblSalones.getSelectionModel().getSelectedItem()).getDescripcion());
            txtCapacidadMax.setText(String.valueOf(((Salones) tblSalones.getSelectionModel().getSelectedItem()).getCapacidadMaxima()));
            txtEdificio.setText(((Salones) tblSalones.getSelectionModel().getSelectedItem()).getEdificio());
            txtNivel.setText(String.valueOf(((Salones) tblSalones.getSelectionModel().getSelectedItem()).getNivel()));
        }
    }

    public boolean eliminarSalon() {
        if (existeElementoSeleccionado()) {
            Salones salon = (Salones) tblSalones.getSelectionModel().getSelectedItem();

            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Control Académico Kinal");
            confirm.setHeaderText(null);
            confirm.setContentText("Esta apunto de eliminar el registro con los siguientes datos: "
                    + "\n" + salon.getCodigoSalon() + " || " + salon.getEdificio()
                    + "\nEsta seguro?");

            Optional<ButtonType> result = confirm.showAndWait();
            if (result.get().equals(ButtonType.OK)) {

                PreparedStatement pstmt = null;
                try {
                    pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_salones_delete(?)}");
                    pstmt.setString(1, salon.getCodigoSalon());
                    System.out.println(pstmt.toString());
                    pstmt.execute();
                    return true;
                } catch (SQLException e) {
                    System.err.println("\nSe produjo un error al intentar eliminar el alumno siguiente registro: " + salon.toString());
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
                tblSalones.getSelectionModel().clearSelection();
                limpiarCampos();
            }
        }
        return false;
    }

    public ObservableList getSalones() {

        ArrayList<Salones> lista = new ArrayList<>();

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{Call sp_salones_read()}");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Salones salon = new Salones();
                salon.setCodigoSalon(rs.getString(1));
                salon.setDescripcion(rs.getString(2));
                salon.setCapacidadMaxima(rs.getInt(3));
                salon.setEdificio(rs.getString(4));
                salon.setNivel(rs.getInt(5));

                lista.add(salon);

                System.out.println(salon.toString());
            }
        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar conmsultar la lista de salones");
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
        txtCodigoSalon.setEditable(false);
        txtDescripcion.setEditable(false);
        txtCapacidadMax.setEditable(false);
        txtEdificio.setEditable(false);
        txtNivel.setEditable(false);

        txtCodigoSalon.setDisable(true);
        txtDescripcion.setDisable(true);
        txtCapacidadMax.setDisable(true);
        txtEdificio.setDisable(true);
        txtNivel.setDisable(true);
    }

    private void habilitarCampos() {
        txtCodigoSalon.setEditable(false);
        txtDescripcion.setEditable(true);
        txtCapacidadMax.setEditable(true);
        txtEdificio.setEditable(true);
        txtNivel.setEditable(true);

        txtCodigoSalon.setDisable(true);
        txtDescripcion.setDisable(false);
        txtCapacidadMax.setDisable(false);
        txtEdificio.setDisable(false);
        txtNivel.setDisable(false);
    }

    private void limpiarCampos() {
        txtCodigoSalon.setText("");
        txtDescripcion.setText("");
        txtCapacidadMax.setText("");
        txtEdificio.setText("");
        txtNivel.setText("");
    }

    @FXML
    private void clicNuevo() {

        switch (operacion) {
            case NINGUNO:
                habilitarCampos();
                tblSalones.setDisable(true);
                txtCodigoSalon.setEditable(true);
                txtCodigoSalon.setDisable(false);
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
                if (agregarSalon()) {
                    cargarDatos();
                    limpiarCampos();
                    deshabilitarCampos();
                    tblSalones.setDisable(false);
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

    private boolean validarNumeros(String numero1, String numero2) {
        return numero1.matches("[0-9]*") && numero2.matches("[0-9]*");
    }

    private boolean agregarSalon() {
        if (!(txtCodigoSalon.getText().equals("") || txtCapacidadMax.getText().equals("") || txtNivel.getText().equals(""))) {
            if (!(txtCodigoSalon.getText().charAt(0) == ' ' || txtCapacidadMax.getText().charAt(0) == ' ' || txtNivel.getText().charAt(0) == ' ')) {
                if (!(txtCodigoSalon.getText().length() >= 6 || txtDescripcion.getText().length() >= 46 || txtEdificio.getText().length() >= 16)) {
                    if (validarNumeros(txtCapacidadMax.getText(), txtNivel.getText())) {

                        Salones salon = new Salones();
                        salon.setCodigoSalon(txtCodigoSalon.getText());
                        salon.setDescripcion(txtDescripcion.getText());
                        salon.setCapacidadMaxima(Integer.valueOf(txtCapacidadMax.getText()));
                        salon.setEdificio(txtEdificio.getText());
                        salon.setNivel(Integer.valueOf(txtNivel.getText()));

                        PreparedStatement pstmt = null;

                        try {
                            pstmt = Conexion.getInstance().getConexion().prepareCall("Call sp_salones_create(?, ?, ?, ?, ?)");

                            System.out.println(pstmt.toString());

                            pstmt.setString(1, salon.getCodigoSalon());
                            pstmt.setString(2, salon.getDescripcion());
                            pstmt.setInt(3, salon.getCapacidadMaxima());
                            pstmt.setString(4, salon.getEdificio());
                            pstmt.setInt(5, salon.getNivel());
                            pstmt.execute();
                            cargarDatos();
                            return true;

                        } catch (SQLException e) {
                            System.err.println("Se produjo un error al intentar insertar el siguiente registro: " + salon.toString());
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
                        alert.setContentText("Tipo de dato no valido en capacidad maxima o nivel");
                        alert.show();
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
                tblSalones.setDisable(false);
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
                tblSalones.getSelectionModel().clearSelection();
                operacion = Operacion.NINGUNO;
                break;
            case MODIFICAR:
                if (actualizarSalon()) {
                    cargarDatos();
                    limpiarCampos();
                    deshabilitarCampos();
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

    private boolean actualizarSalon() {
        if (!(txtCodigoSalon.getText().equals("") || txtCapacidadMax.getText().equals("") || txtNivel.getText().equals(""))) {
            if (!(txtCodigoSalon.getText().charAt(0) == ' ' || txtCapacidadMax.getText().charAt(0) == ' ' || txtNivel.getText().charAt(0) == ' ')) {
                if (!(txtCodigoSalon.getText().length() >= 6 || txtDescripcion.getText().length() >= 46 || txtEdificio.getText().length() >= 16)) {
                    if (validarNumeros(txtCapacidadMax.getText(), txtNivel.getText())) {
                        Salones salon = new Salones();
                        salon.setCodigoSalon(txtCodigoSalon.getText());
                        salon.setDescripcion(txtDescripcion.getText());
                        salon.setCapacidadMaxima(Integer.valueOf(txtCapacidadMax.getText()));
                        salon.setEdificio(txtEdificio.getText());
                        salon.setNivel(Integer.valueOf(txtNivel.getText()));

                        PreparedStatement pstmt = null;

                        try {
                            pstmt = Conexion.getInstance().getConexion().prepareCall("Call sp_salones_update(?, ?, ?, ?, ?)");
                            pstmt.setString(1, salon.getCodigoSalon());
                            pstmt.setString(2, salon.getDescripcion());
                            pstmt.setString(3, String.valueOf(salon.getCapacidadMaxima()));
                            pstmt.setString(4, salon.getEdificio());
                            pstmt.setString(5, String.valueOf(salon.getNivel()));

                            System.out.println(pstmt.toString());

                            pstmt.execute();

                            return true;
                        } catch (SQLException e) {
                            System.out.println("Se produjo un error al intentar actualizar el siguiente registro: " + salon.toString());
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
                        alert.setContentText("Tipo de dato no valido");
                        alert.show();
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
                tblSalones.getSelectionModel().clearSelection();
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

                    if (eliminarSalon()) {
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
    public void clicRegresar(MouseEvent event
    ) {
        escenarioPrincipal.mostrarEscenaPrincipal();
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
}
