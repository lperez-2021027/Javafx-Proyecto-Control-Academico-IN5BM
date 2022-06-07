package org.in5bm.jsaldana_lperez.models;

// Librerias exclusivas de javaFx
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Luis Carlos Pérez
 * @date 4/05/2022
 * @time 18:11:17
 *
 * Código técnico: IN5BM
 *
 */
public class Cursos {

    // Si el modelo de datos esta directamente relacionado con el tblView se actualizan automaticamente los datos
    // Atributos
    private IntegerProperty id;
    private StringProperty nombreCurso;
    private IntegerProperty ciclo;
    private IntegerProperty cupoMaximo;
    private IntegerProperty cupoMinimo;
    private StringProperty carreraTecnicaId;
    private IntegerProperty horarioId;
    private IntegerProperty instructorId;
    private StringProperty salonId;

    // Constructores
    // Constructor nulo
    public Cursos() {
        this.id = new SimpleIntegerProperty();
        this.nombreCurso = new SimpleStringProperty();
        this.ciclo = new SimpleIntegerProperty();
        this.cupoMaximo = new SimpleIntegerProperty();
        this.cupoMinimo = new SimpleIntegerProperty();
        this.carreraTecnicaId = new SimpleStringProperty();
        this.horarioId = new SimpleIntegerProperty();
        this.instructorId = new SimpleIntegerProperty();
        this.salonId = new SimpleStringProperty();
    }

    // Constructor con parámetros
    public Cursos(int id, String nombreCurso) {
        
    }

    // Métodos Getter´s y Setter´s
    @Override
    public String toString() {
        return "Cursos{" + "nombreCurso=" + nombreCurso + ", ciclo=" + ciclo + ", cupoMaximo=" + cupoMaximo + ", cupoMinimo=" + cupoMinimo + ", carreraTecnicaId=" + carreraTecnicaId + ", horarioId=" + horarioId + ", instructorId=" + instructorId + ", salonId=" + salonId + '}';
    }
}
