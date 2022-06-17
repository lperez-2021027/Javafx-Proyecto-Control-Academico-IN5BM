package org.in5bm.jsaldana_lperez.controllers;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.net.URL;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import org.in5bm.jsaldana_lperez.db.Conexion;
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
    private SpinnerValueFactory<Integer> valueFactoryCiclo;
    @FXML
    private Spinner<Integer> spnCupoMaximo;
    private SpinnerValueFactory<Integer> valueFactoryMaximo;
    @FXML
    private Spinner<Integer> spnCupoMinimo;
    private SpinnerValueFactory<Integer> valueFactoryMinimo;
    @FXML
    private ComboBox<CarrerasTecnicas> cmbCarreraTecnica;
    @FXML
    private ComboBox<Horarios> cmbHorario;
    @FXML
    private ComboBox<Salones> cmbSalon;
    @FXML
    private ComboBox<Instructores> cmbInstructor;
    @FXML
    private TextField txtId;
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

    private ObservableList<Cursos> listaObservableCursos;
    private ObservableList<Instructores> listaObservableInstructores;
    private ObservableList<Salones> listaObservableSalones;
    private ObservableList<CarrerasTecnicas> listaObservableCarrerasTecnicas;
    private ObservableList<Horarios> listaObservableHorarios;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        valueFactoryCiclo = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 2050, 2022);
        spnCiclo.setValueFactory(valueFactoryCiclo);

        valueFactoryMaximo = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);
        spnCupoMaximo.setValueFactory(valueFactoryMaximo);

        valueFactoryMinimo = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 100, 0);
        spnCupoMinimo.setValueFactory(valueFactoryMinimo);

        cargarDatos();
    }

    public void cargarDatos() {
        tblCursos.setItems(getCursos());
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNombreCurso.setCellValueFactory(new PropertyValueFactory<>("nombreCurso"));
        colCiclo.setCellValueFactory(new PropertyValueFactory<>("ciclo"));
        colMaximo.setCellValueFactory(new PropertyValueFactory<>("cupoMaximo"));
        colMinimo.setCellValueFactory(new PropertyValueFactory<>("cupoMinimo"));
        colCarreraTecnica.setCellValueFactory(new PropertyValueFactory<>("carreraTecnicaId"));
        colHorario.setCellValueFactory(new PropertyValueFactory<>("horarioId"));
        colInstructor.setCellValueFactory(new PropertyValueFactory<>("instructorId"));
        colSalon.setCellValueFactory(new PropertyValueFactory<>("salonId"));

        cmbCarreraTecnica.setItems(getCarrerasTecnicas());
        cmbSalon.setItems(getSalones());
        cmbHorario.setItems(getHorarios());
        cmbInstructor.setItems(getInstructores());
    }

    public boolean existeElementosSeleccionado() {
        return tblCursos.getSelectionModel().getSelectedItem() != null;
    }

    @FXML
    public void seleccionarElemento() {
        if (existeElementosSeleccionado()) {
            txtId.setText(Integer.toString(((Cursos) tblCursos.getSelectionModel().getSelectedItem()).getId()));
            txtNombreCurso.setText(((Cursos) tblCursos.getSelectionModel().getSelectedItem()).getNombreCurso());

            spnCiclo.getValueFactory().setValue(((Cursos) tblCursos.getSelectionModel().getSelectedItem()).getCiclo());
            spnCupoMaximo.getValueFactory().setValue(((Cursos) tblCursos.getSelectionModel().getSelectedItem()).getCupoMaximo());
            spnCupoMinimo.getValueFactory().setValue(((Cursos) tblCursos.getSelectionModel().getSelectedItem()).getCupoMinimo());

            cmbCarreraTecnica.getSelectionModel().select(buscarCarrerasTecnicas(((Cursos) tblCursos.getSelectionModel().getSelectedItem()).getCarreraTecnicaId()));
            cmbInstructor.getSelectionModel().select(buscarInstructor(((Cursos) tblCursos.getSelectionModel().getSelectedItem()).getInstructorId()));
            cmbSalon.getSelectionModel().select(buscarSalones(((Cursos) tblCursos.getSelectionModel().getSelectedItem()).getSalonId()));
            cmbHorario.getSelectionModel().select(buscarHorario(((Cursos) tblCursos.getSelectionModel().getSelectedItem()).getHorarioId()));
        }
    }

    public ObservableList getCursos() {

        ArrayList<Cursos> lista = new ArrayList<>();

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_cursos_read()}");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                Cursos curso = new Cursos();
                curso.setId(rs.getInt(1));
                curso.setNombreCurso(rs.getString(2));
                curso.setCiclo(rs.getInt(3));
                curso.setCupoMaximo(rs.getInt(4));
                curso.setCupoMinimo(rs.getInt(5));
                curso.setCarreraTecnicaId(rs.getString(6));
                curso.setHorarioId(rs.getInt(7));
                curso.setInstructorId(rs.getInt(8));
                curso.setSalonId(rs.getString(9));

                lista.add(curso);

                System.out.println(curso.toString());
            }

            listaObservableCursos = FXCollections.observableArrayList(lista);

        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar consultar la lista de cursos");
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
        return listaObservableCursos;
    }

    public ObservableList getCarrerasTecnicas() {

        ArrayList<CarrerasTecnicas> lista = new ArrayList<>();

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_carreras_tecnicas_read()}");
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

            listaObservableCarrerasTecnicas = FXCollections.observableArrayList(lista);

        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar consultar la lista de carreras tecnicas");
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
        return listaObservableCarrerasTecnicas;
    }

    public CarrerasTecnicas buscarCarrerasTecnicas(String id) {

        CarrerasTecnicas carreraTecnica = new CarrerasTecnicas();

        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_carreras_tecnicas_read_by_id(?)}");
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();

            while (rs.next()) {

                carreraTecnica.setCodigoTecnico(rs.getString(1));
                carreraTecnica.setCarrera(rs.getString(2));
                carreraTecnica.setGrado(rs.getString(3));
                carreraTecnica.setSeccion(rs.getString(4).charAt(0));
                carreraTecnica.setJornada(rs.getString(5));

                System.out.println(carreraTecnica.toString());
            }
        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar consultar la lista de carreras tecnicas");
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
        return carreraTecnica;
    }

    public ObservableList getSalones() {

        ArrayList<Salones> lista = new ArrayList<>();

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_salones_read()}");
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

            listaObservableSalones = FXCollections.observableArrayList(lista);

        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar consultar la lista de salones");
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
        return listaObservableSalones;
    }

    public Salones buscarSalones(String id) {
        Salones salon = new Salones();
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_salones_read_by_id(?)}");
            pstmt.setString(1, id);
            rs = pstmt.executeQuery();

            while (rs.next()) {
                salon.setCodigoSalon(rs.getString(1));
                salon.setDescripcion(rs.getString(2));
                salon.setCapacidadMaxima(rs.getInt(3));
                salon.setEdificio(rs.getString(4));
                salon.setNivel(rs.getInt(5));

                System.out.println(salon.toString());
            }
        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar consultar la lista de salones");
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
        return salon;
    }

    public ObservableList getHorarios() {

        ArrayList<Horarios> arrayListaHorarios = new ArrayList<>();

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_horarios_read()}");
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

            listaObservableHorarios = FXCollections.observableArrayList(arrayListaHorarios);

        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar consultar la lista de horarios");
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
        return listaObservableHorarios;
    }

    public Horarios buscarHorario(int id) {

        Horarios horario = new Horarios();

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_horarios_read_by_id(?)}");
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            while (rs.next()) {

                horario.setId(rs.getInt(1));
                horario.setHorarioFinal(rs.getTime(2).toLocalTime());
                horario.setHorarioInicio(rs.getTime(3).toLocalTime());
                horario.setLunes(rs.getBoolean(4));
                horario.setMartes(rs.getBoolean(5));
                horario.setMiercoles(rs.getBoolean(6));
                horario.setJueves(rs.getBoolean(7));
                horario.setViernes(rs.getBoolean(8));

                System.out.println(horario.toString());
            }
        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar consultar la lista de horarios");
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
        return horario;
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

            listaObservableInstructores = FXCollections.observableArrayList(lista);

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
        return listaObservableInstructores;
    }

    public Instructores buscarInstructor(int id) {
        Instructores instructor = new Instructores();

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_instructores_read_by_id(?)}");
            pstmt.setInt(1, id);
            rs = pstmt.executeQuery();

            while (rs.next()) {

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

                System.out.println(instructor.toString());
            }
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
        return instructor;
    }

    private void deshabilitarCampos() {
        txtId.setEditable(false);
        txtNombreCurso.setEditable(false);
        spnCiclo.setEditable(false);
        spnCupoMaximo.setEditable(false);
        spnCupoMinimo.setEditable(false);
        cmbCarreraTecnica.setEditable(false);
        cmbHorario.setEditable(false);
        cmbInstructor.setEditable(false);
        cmbSalon.setEditable(false);

        txtId.setDisable(true);
        txtNombreCurso.setDisable(true);
        spnCiclo.setDisable(true);
        spnCupoMaximo.setDisable(true);
        spnCupoMinimo.setDisable(true);
        cmbCarreraTecnica.setDisable(true);
        cmbHorario.setDisable(true);
        cmbInstructor.setDisable(true);
        cmbSalon.setDisable(true);
    }

    private void habilitarCampos() {
        txtNombreCurso.setEditable(true);
        spnCiclo.setEditable(false);
        spnCupoMaximo.setEditable(false);
        spnCupoMinimo.setEditable(false);

        txtNombreCurso.setDisable(false);
        spnCiclo.setDisable(false);
        spnCupoMaximo.setDisable(false);
        spnCupoMinimo.setDisable(false);
        cmbCarreraTecnica.setDisable(false);
        cmbHorario.setDisable(false);
        cmbInstructor.setDisable(false);
        cmbSalon.setDisable(false);
    }

    private void limpiarCampos() {
        txtId.clear();
        txtNombreCurso.setText("");
        spnCiclo.getValueFactory().setValue(2022);
        spnCupoMaximo.getValueFactory().setValue(0);
        spnCupoMinimo.getValueFactory().setValue(0);
        cmbCarreraTecnica.valueProperty().set(null);
        cmbHorario.valueProperty().set(null);
        cmbInstructor.valueProperty().set(null);
        cmbSalon.valueProperty().set(null);
    }

    @FXML
    private void clicNuevo() {
        switch (operacion) {
            case NINGUNO:
                if (existeElementosSeleccionado()) {
                    tblCursos.getSelectionModel().clearSelection();
                }

                habilitarCampos();
                tblCursos.setDisable(true);
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
                if (comprobacionCampos()) {
                    if (agregarCurso()) {
                        cargarDatos();
                        limpiarCampos();
                        deshabilitarCampos();

                        tblCursos.setDisable(false);
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
                }
                break;
        }
    }

    private boolean agregarCurso() {

        Cursos curso = new Cursos(
                txtNombreCurso.getText(),
                spnCiclo.getValue(),
                spnCupoMaximo.getValue(),
                spnCupoMinimo.getValue(),
                cmbCarreraTecnica.getSelectionModel().getSelectedItem().getCodigoTecnico(),
                cmbHorario.getSelectionModel().getSelectedItem().getId(),
                cmbInstructor.getSelectionModel().getSelectedItem().getId(),
                cmbSalon.getSelectionModel().getSelectedItem().getCodigoSalon()
        );

        PreparedStatement pstmt = null;

        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_cursos_create(?, ?, ?, ?, ?, ?, ?, ?)}");

            pstmt.setString(1, curso.getNombreCurso());
            pstmt.setInt(2, curso.getCiclo());
            pstmt.setInt(3, curso.getCupoMaximo());
            pstmt.setInt(4, curso.getCupoMinimo());
            pstmt.setString(5, curso.getCarreraTecnicaId());
            pstmt.setInt(6, curso.getHorarioId());
            pstmt.setInt(7, curso.getInstructorId());
            pstmt.setString(8, curso.getSalonId());

            System.out.println(pstmt.toString());
            pstmt.execute();
            return true;
        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar insertar el siguiente registro: " + curso.toString());
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

        return false;
    }

    private boolean comprobacionCampos() {
        // Comprobando que los campos not null contengan datos.
        if (txtNombreCurso.getText().isEmpty()
                || cmbInstructor.getValue() == null
                || cmbCarreraTecnica.getValue() == null
                || cmbHorario.getValue() == null
                || cmbSalon.getValue() == null) {
            mostrarAlert(TIPO_ALERT_WARNING, "Verifique que los campos contengan datos.");
        } else {
            // Comprobando que los datos ingresados en el campo Nombre Curso no sean espacios.
            if (txtNombreCurso.getText().charAt(0) == ' ') {
                mostrarAlert(TIPO_ALERT_WARNING, "Verifique que el campo Nombre del curso no contenga espacios al inicio.");
            } // Comprobando la longitud del campo Nombre Curso.
            else if (txtNombreCurso.getText().length() > 255) {
                mostrarAlert(TIPO_ALERT_WARNING, "Dato invalido el campo Nombre del curso, muy extenso.");
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
                    mostrarAlert(TIPO_ALERT_WARNING, "Antes de continuar seleccione un registro");
                }
                break;

            case GUARDAR:
                limpiarCampos();
                deshabilitarCampos();

                tblCursos.getSelectionModel().clearSelection();

                tblCursos.setDisable(false);
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
                break;
            case MODIFICAR:
                if (comprobacionCampos()) {
                    if (actualizarCursos()) {
                        cargarDatos();
                        limpiarCampos();

                        tblCursos.setDisable(false);
                        tblCursos.getSelectionModel().clearSelection();
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
                }
                break;
        }
    }

    private boolean actualizarCursos() {

        Cursos curso = new Cursos(
                Integer.parseInt(txtId.getText()),
                txtNombreCurso.getText(),
                spnCiclo.getValue(),
                spnCupoMaximo.getValue(),
                spnCupoMinimo.getValue(),
                cmbCarreraTecnica.getSelectionModel().getSelectedItem().getCodigoTecnico(),
                cmbHorario.getSelectionModel().getSelectedItem().getId(),
                cmbInstructor.getSelectionModel().getSelectedItem().getId(),
                cmbSalon.getSelectionModel().getSelectedItem().getCodigoSalon()
        );

        PreparedStatement pstmt = null;

        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_cursos_update(?, ?, ?, ?, ?, ?, ?, ?, ?)}");
            pstmt.setInt(1, curso.getId());
            pstmt.setString(2, curso.getNombreCurso());
            pstmt.setInt(3, curso.getCiclo());
            pstmt.setInt(4, curso.getCupoMaximo());
            pstmt.setInt(5, curso.getCupoMinimo());
            pstmt.setString(6, curso.getCarreraTecnicaId());
            pstmt.setInt(7, curso.getHorarioId());
            pstmt.setInt(8, curso.getInstructorId());
            pstmt.setString(9, curso.getSalonId());

            System.out.println(pstmt.toString());

            pstmt.execute();

            return true;
        } catch (SQLException e) {
            System.out.println("Se produjo un error al intentar actualizar el siguiente registro: " + curso.toString());
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
        return false;
    }

    @FXML
    private void clicEliminar() {
        switch (operacion) {
            case MODIFICAR:
                limpiarCampos();
                deshabilitarCampos();

                tblCursos.getSelectionModel().clearSelection();

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

                operacion = Operacion.NINGUNO;
                break;
            case NINGUNO:
                if (existeElementosSeleccionado()) {

                    Alert aler = new Alert(Alert.AlertType.CONFIRMATION);
                    aler.setTitle("Control Académico Kinal");
                    aler.setHeaderText(null);
                    aler.setContentText("¿Desea eliminar el registro seleccionado?");
                    Optional<ButtonType> result = aler.showAndWait();

                    if (result.get() == ButtonType.OK) {
                        if (eliminarCurso()) {
                            cargarDatos();
                            limpiarCampos();
                            Alert alert = new Alert(Alert.AlertType.INFORMATION);
                            alert.setTitle("Control academico");
                            alert.setHeaderText(null);
                            alert.setContentText("Registro eliminado exitosamente");
                            alert.show();
                        }
                    } else {
                        tblCursos.getSelectionModel().clearSelection();
                        limpiarCampos();
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

    public boolean eliminarCurso() {
        Cursos curso = (Cursos) tblCursos.getSelectionModel().getSelectedItem();

        PreparedStatement pstmt = null;
        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_cursos_delete(?)}");
            pstmt.setInt(1, curso.getId());
            System.out.println(pstmt.toString());
            pstmt.execute();
            return true;
        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar eliminar el alumno siguiente registro: " + curso.toString());
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
        return false;
    }

    @FXML
    private void clicReporte() {
        mostrarAlert(TIPO_ALERT_INFORMATION, "Funcion disponible en la versión pro.");
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
