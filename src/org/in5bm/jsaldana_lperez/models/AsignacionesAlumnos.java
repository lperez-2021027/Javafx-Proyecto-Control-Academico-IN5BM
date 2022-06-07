package org.in5bm.jsaldana_lperez.models;

import javafx.beans.property.StringProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty; // Para tipos de datos inexistentes en java (Date)
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import java.time.LocalDate;

/**
 *
 * @author Luis Carlos Pérez
 * @date 4/05/2022
 * @time 19:40:35
 *
 * Código técnico: IN5BM
 *
 */
public class AsignacionesAlumnos {

    // Atributos
    private IntegerProperty id;
    private StringProperty alumnoId;
    private IntegerProperty cursoId;
    private ObjectProperty<LocalDate> fechaAsignacion;

    // Constructor nulo
    public AsignacionesAlumnos() {
        this.id = new SimpleIntegerProperty();
        this.alumnoId = new SimpleStringProperty();
        this.cursoId = new SimpleIntegerProperty();
        this.fechaAsignacion = new SimpleObjectProperty<>(); // Inferencia de datos
    }

    // Construcor con parámetros
    public AsignacionesAlumnos(int id, String alumnoId, int cursoId, LocalDate fechaAsignacion) {
        this.id = new SimpleIntegerProperty(id);
        this.alumnoId = new SimpleStringProperty(alumnoId);
        this.cursoId = new SimpleIntegerProperty(cursoId);
        this.fechaAsignacion = new SimpleObjectProperty<>(fechaAsignacion);
    }

    // Métodos Getter´s y Setter´s
    // Método nuevo:
    public IntegerProperty id() {
        return id;
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public StringProperty alumnoId() {
        return alumnoId;
    }

    public String getAlumnoId() {
        return alumnoId.get();
    }

    public void setAlumnoId(String alumnoId) {
        this.alumnoId.set(alumnoId);
    }

    public IntegerProperty cursoId() {
        return cursoId;
    }

    public int getCursoId() {
        return cursoId.get();
    }

    public void setCursoId(int cursoId) {
        this.cursoId.set(cursoId);
    }
    
    public ObjectProperty<LocalDate> fechaAsignacion() {
        return fechaAsignacion;
    }
    
    public LocalDate getFechaAsignacion() {
        return fechaAsignacion.get();
    }
    
    public void setFechaAsignacion(LocalDate fechaAsignacion) {
        this.fechaAsignacion.set(fechaAsignacion);
    }

    @Override
    public String toString() {
        return ("id: " + id + " alumnoId: " + alumnoId + " cursoId: " + cursoId
                + " fechaAsignacion: " + fechaAsignacion);
    }
}
