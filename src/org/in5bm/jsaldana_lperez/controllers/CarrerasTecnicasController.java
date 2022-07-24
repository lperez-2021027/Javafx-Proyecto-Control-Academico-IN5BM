package org.in5bm.jsaldana_lperez.controllers;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import org.in5bm.jsaldana_lperez.db.Conexion;
import org.in5bm.jsaldana_lperez.models.CarrerasTecnicas;
import org.in5bm.jsaldana_lperez.system.Principal;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import org.in5bm.jsaldana_lperez.reports.GenerarReporte;

/**
 *
 * @author José Roberto Saldaña Arrazola
 * @author Luis Carlos Pérez
 * @date 26-abr-2022
 * @time 8:26:32
 *
 * Código técnico: IN5BM
 */
public class CarrerasTecnicasController implements Initializable {

    private final String PAQUETE_IMAGE = "org/in5bm/jsaldana_lperez/resources/images/";
    private final String TITULO_ALERT = "Control Académico Kinal";
    private final String TIPO_ALERT_WARNING = "warning";
    private final String TIPO_ALERT_INFORMATION = "information";

    private enum Operacion {
        NINGUNO, GUARDAR, MODIFICAR
    }

    private Operacion operacion = Operacion.NINGUNO;

    private Principal escenarioPrincipal;

    @FXML
    private TextField txtCodigoTecnico;

    @FXML
    private TextField txtCarrera;

    @FXML
    private TextField txtGrado;

    @FXML
    private TextField txtSeccion;

    @FXML
    private TextField txtJornada;

    @FXML
    private Button btnNuevo;

    @FXML
    private Button btnModificar;

    @FXML
    private Button btnEliminar;

    @FXML
    private Button btnReporte;

    @FXML
    private TableView tblCarrerasTecnicas;

    @FXML
    private TableColumn colCodigoTecnico;

    @FXML
    private TableColumn colCarrera;

    @FXML
    private TableColumn colGrado;

    @FXML
    private TableColumn colSeccion;

    @FXML
    private TableColumn colJornada;

    @FXML
    private ImageView imgNuevo;

    @FXML
    private ImageView imgModificar;

    @FXML
    private ImageView imgEliminar;

    @FXML
    private ImageView imgReporte;
    
    @FXML
    private Label lblRegistros;

    @FXML
    private ObservableList<CarrerasTecnicas> listaCarrerasTecnicas;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }

    public void cargarDatos() {
        tblCarrerasTecnicas.setItems(getCarrerasTecnicas());
        colCodigoTecnico.setCellValueFactory(new PropertyValueFactory<CarrerasTecnicas, String>("codigoTecnico"));
        colCarrera.setCellValueFactory(new PropertyValueFactory<CarrerasTecnicas, String>("carrera"));
        colGrado.setCellValueFactory(new PropertyValueFactory<CarrerasTecnicas, String>("grado"));
        colSeccion.setCellValueFactory(new PropertyValueFactory<CarrerasTecnicas, Character>("seccion"));
        colJornada.setCellValueFactory(new PropertyValueFactory<CarrerasTecnicas, String>("jornada"));
        contarRegistros();
    }
    
    public void contarRegistros() {
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_carreras_tecnicas_count()}");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                lblRegistros.setText("Total de registros: " + Integer.toString(rs.getInt(1)));
            }
        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar consultar el total de la lista carreras");
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

    public boolean existeElementosSeleccionado() {
        return tblCarrerasTecnicas.getSelectionModel().getSelectedItem() != null;
    }

    @FXML
    public void seleccionarElemento() {
        if (existeElementosSeleccionado()) {
            txtCodigoTecnico.setText(((CarrerasTecnicas) tblCarrerasTecnicas.getSelectionModel().getSelectedItem()).getCodigoTecnico());
            txtCarrera.setText(((CarrerasTecnicas) tblCarrerasTecnicas.getSelectionModel().getSelectedItem()).getCarrera());
            txtGrado.setText(((CarrerasTecnicas) tblCarrerasTecnicas.getSelectionModel().getSelectedItem()).getGrado());
            txtSeccion.setText(Character.toString(((CarrerasTecnicas) tblCarrerasTecnicas.getSelectionModel().getSelectedItem()).getSeccion()));
            txtJornada.setText(((CarrerasTecnicas) tblCarrerasTecnicas.getSelectionModel().getSelectedItem()).getJornada());
        }
    }

    public boolean eliminarCarreraTecnica() {
        if (existeElementosSeleccionado()) {
            CarrerasTecnicas carrerasTecnicas = (CarrerasTecnicas) tblCarrerasTecnicas.getSelectionModel().getSelectedItem();
            System.out.println(carrerasTecnicas.toString());

            PreparedStatement pstmt = null;
            try {
                pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_carreras_tecnicas_delete(?)}");
                pstmt.setString(1, carrerasTecnicas.getCodigoTecnico());
                System.out.println(pstmt);

                pstmt.execute();

                return true;
            } catch (SQLException e) {
                System.err.println("\n Se produjo un error al intentar eliminar el siguiente registro: " + carrerasTecnicas.toString());
            }
        }
        return false;
    }

    public ObservableList getCarrerasTecnicas() {

        ArrayList<CarrerasTecnicas> lista = new ArrayList<>();

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{Call sp_carreras_tecnicas_read()}");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                CarrerasTecnicas carreraTecnica = new CarrerasTecnicas();
                carreraTecnica.setCodigoTecnico(rs.getString(1));
                carreraTecnica.setCarrera(rs.getString(2));
                carreraTecnica.setGrado(rs.getString(3));
                carreraTecnica.setSeccion(rs.getString(4).charAt(0));
                carreraTecnica.setJornada(rs.getString(5));

                lista.add(carreraTecnica);

                System.out.println(carreraTecnica.toString());
            }

            listaCarrerasTecnicas = FXCollections.observableArrayList(lista);

        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar conmsultar la lista de alumnos");
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
        //return FXCollections.observableArrayList(lista);
        return listaCarrerasTecnicas;
    }

    private void habilitarCampos() {
        txtCodigoTecnico.setEditable(false);
        txtCarrera.setEditable(true);
        txtGrado.setEditable(true);
        txtSeccion.setEditable(true);
        txtJornada.setEditable(true);

        txtCodigoTecnico.setDisable(true);
        txtCarrera.setDisable(false);
        txtGrado.setDisable(false);
        txtSeccion.setDisable(false);
        txtJornada.setDisable(false);
    }

    private void deshabilitarCampos() {
        txtCodigoTecnico.setEditable(false);
        txtCarrera.setEditable(false);
        txtGrado.setEditable(false);
        txtSeccion.setEditable(false);
        txtJornada.setEditable(false);

        txtCodigoTecnico.setDisable(true);
        txtCarrera.setDisable(true);
        txtGrado.setDisable(true);
        txtSeccion.setDisable(true);
        txtJornada.setDisable(true);
    }

    private void limpiarCampos() {
        txtCodigoTecnico.setText("");
        txtCarrera.setText("");
        txtGrado.setText("");
        txtSeccion.setText("");
        txtJornada.setText("");
    }

    @FXML
    private void clicNuevo() {
        switch (operacion) {
            case NINGUNO:
                if (existeElementosSeleccionado()) {
                    tblCarrerasTecnicas.getSelectionModel().clearSelection();
                }

                habilitarCampos();
                limpiarCampos();

                txtCodigoTecnico.setEditable(true);
                txtCodigoTecnico.setDisable(false);

                tblCarrerasTecnicas.setDisable(true);

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
                    if (agregarCarreraTecnica()) {
                        cargarDatos();
                        deshabilitarCampos();
                        limpiarCampos();

                        tblCarrerasTecnicas.setDisable(false);

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

    private boolean agregarCarreraTecnica() {
        CarrerasTecnicas carreraTecnica = new CarrerasTecnicas();
        carreraTecnica.setCodigoTecnico(txtCodigoTecnico.getText());
        carreraTecnica.setCarrera(txtCarrera.getText());
        carreraTecnica.setGrado(txtGrado.getText());
        carreraTecnica.setSeccion(txtSeccion.getText().charAt(0));
        carreraTecnica.setJornada(txtJornada.getText());

        PreparedStatement pstmt = null;

        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_carreras_tecnicas_create(?, ?, ?, ?, ?)}");
            pstmt.setString(1, carreraTecnica.getCodigoTecnico().toUpperCase());
            pstmt.setString(2, carreraTecnica.getCarrera());
            pstmt.setString(3, carreraTecnica.getGrado());
            pstmt.setString(4, Character.toString(carreraTecnica.getSeccion()).toUpperCase());
            pstmt.setString(5, carreraTecnica.getJornada());
            System.out.println(pstmt);
            pstmt.execute();

            listaCarrerasTecnicas.add(carreraTecnica);

            return true;
        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar agregar el registro : " + carreraTecnica.toString());
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
        // Comprobando que los campos contengan datos.
        if (txtCodigoTecnico.getText().isEmpty()
                || txtCarrera.getText().isEmpty()
                || txtGrado.getText().isEmpty()
                || txtSeccion.getText().isEmpty()
                || txtJornada.getText().isEmpty()) {
            mostrarAlert(TIPO_ALERT_WARNING, "Verifique que los campos contengan datos.");
        } else {
            // Comprobando que los datos ingresados no sean espacios.
            if (txtCodigoTecnico.getText().charAt(0) == ' '
                    || txtCarrera.getText().charAt(0) == ' '
                    || txtGrado.getText().charAt(0) == ' '
                    || txtSeccion.getText().charAt(0) == ' '
                    || txtJornada.getText().charAt(0) == ' ') {
                mostrarAlert(TIPO_ALERT_WARNING, "Verifique que los campos no contenga un espacio al inicio.");
            } else if (txtSeccion.getText().length() > 1
                    || txtCarrera.getText().length() > 45
                    || txtGrado.getText().length() > 10
                    || txtJornada.getText().length() > 45
                    || txtCodigoTecnico.getText().length() > 6) {
                mostrarAlert(TIPO_ALERT_WARNING, "Datos invalidos, demasiado extensos, rectifique.");
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
                Stage stageAlert = (Stage) alert.getDialogPane().getScene().getWindow();
                stageAlert.getIcons().add(new Image(PAQUETE_IMAGE + "informacion.png"));
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
    private void clicModificar() {
        switch (operacion) {
            case NINGUNO:
                if (existeElementosSeleccionado()) {
                    habilitarCampos();
                    //limpiarCampos();
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

                tblCarrerasTecnicas.setDisable(false);

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
                if (comprobacionCamposTxt()) {
                    if (actualizarCarreraTecnica()) {
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

    private boolean actualizarCarreraTecnica() {
        CarrerasTecnicas carreraTecnica = new CarrerasTecnicas();
        carreraTecnica.setCodigoTecnico(txtCodigoTecnico.getText());
        carreraTecnica.setCarrera(txtCarrera.getText());
        carreraTecnica.setGrado(txtGrado.getText());
        carreraTecnica.setSeccion(txtSeccion.getText().charAt(0));
        carreraTecnica.setJornada(txtJornada.getText());

        PreparedStatement pstmt = null;
        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_carreras_tecnicas_update(?, ?, ?, ?, ?)}");
            pstmt.setString(1, carreraTecnica.getCodigoTecnico());
            pstmt.setString(2, carreraTecnica.getCarrera());
            pstmt.setString(3, carreraTecnica.getGrado());
            pstmt.setString(4, Character.toString(carreraTecnica.getSeccion()));
            pstmt.setString(5, carreraTecnica.getJornada());
            System.out.println(pstmt);
            pstmt.execute();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("\nSe produjo un error al intentar actualizar el registro : " + carreraTecnica.toString());
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
    private void clicEliminar() {
        switch (operacion) {
            case MODIFICAR:
                tblCarrerasTecnicas.getSelectionModel().clearSelection();

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
            case NINGUNO: // Eliminacion
                if (existeElementosSeleccionado()) {
                    Alert aler = new Alert(Alert.AlertType.CONFIRMATION);
                    aler.setTitle("Control Académico Kinal");
                    aler.setHeaderText(null);
                    aler.setContentText("¿Desea eliminar el registro seleccionado?");
                    //aler.showAndWait();
                    Optional<ButtonType> result = aler.showAndWait();

                    if (result.get() == ButtonType.OK) {
                        if (eliminarCarreraTecnica()) {
                            limpiarCampos();
                            listaCarrerasTecnicas.remove(tblCarrerasTecnicas.getSelectionModel().getFocusedIndex());
                            cargarDatos();
                            mostrarAlert(TIPO_ALERT_INFORMATION, "Registro eliminado exitosamente");
                        }
                    } else {
                        tblCarrerasTecnicas.getSelectionModel().clearSelection();
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.WARNING);
                    alert.setTitle("Control Academico Kinal");
                    alert.setHeaderText(null);
                    alert.setContentText("Antes de continuar, seleccione un registro.");
                    alert.show();
                }
                break;
        }
    }

    @FXML
    private void clicReporte() {
        Map<String, Object> parametros = new HashMap<>();
        parametros.put("IMAGE_ENTIDAD", PAQUETE_IMAGE + "Carrera_tecnica.png");
        GenerarReporte.getInstance().mostrarReporte("ReportCarreras.jasper", parametros, "Reporte Carreras Técnicas");
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
