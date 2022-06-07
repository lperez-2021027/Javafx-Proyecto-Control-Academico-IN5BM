package org.in5bm.jsaldana_lperez.models;

/**
 *
 * @author José Roberto SAldaña Arrazola
 * @author Luis Carlos Pérez
 * @date 27-abr-2022
 * @time 19:03:57
 *
 * Código técnico: IN5BM
 */

public class CarrerasTecnicas {
    
    //Atributos
    private String codigoTecnico;
    private String carrera;
    private String grado;
    private char seccion;
    private String jornada;
    
    //Constructores
    //Constructor null
    public CarrerasTecnicas(){
    }
    
    //Constructo con párametro codigoTecnico (Primary key)
    public CarrerasTecnicas(String codigoTecnico){
        this.codigoTecnico = codigoTecnico;
    }    
    
    //Constructor con párametros not null (lo son todos los atributos)
    public CarrerasTecnicas(String codigoTecnico, String carrera, 
            String grado, char seccion, String jornada){
        this.codigoTecnico = codigoTecnico;
        this.carrera = carrera;
        this.grado = grado;
        this.seccion = seccion;
        this.jornada = jornada;
    }
    
    //Metodos Getter´s y Setter´s

    public String getCodigoTecnico() {
        return codigoTecnico;
    }

    public void setCodigoTecnico(String codigoTecnico) {
        this.codigoTecnico = codigoTecnico;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public String getGrado() {
        return grado;
    }

    public void setGrado(String grado) {
        this.grado = grado;
    }

    public char getSeccion() {
        return seccion;
    }

    public void setSeccion(char seccion) {
        this.seccion = seccion;
    }

    public String getJornada() {
        return jornada;
    }

    public void setJornada(String jornada) {
        this.jornada = jornada;
    }
    
    //Método toString

    @Override
    public String toString() {
        return "CarrerasTecnicas{" + "codigoTecnico=" + codigoTecnico + ", carrera=" + carrera + ", grado=" + grado + ", seccion=" + seccion + ", jornada=" + jornada + '}';
    }
    
}