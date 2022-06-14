package org.in5bm.jsaldana_lperez.models;

/**
 *
 * @author José Roberto Saldaña Arrazola
 * @author Luis Carlos Pérez
 * @date 26-abr-2022
 * @time 8:26:32
 *
 * Código técnico: IN5BM
 */

public class Alumnos {
    
    /*
        Constitución del modelo de datos:
        Atributos
        Constructores
        Métodos Getter y Setter
        Método toString
    */
    
    //Atributos
    private String carne;
    private String nombre1;
    private String nombre2;
    private String nombre3;
    private String apellido1;
    private String apellido2;
    
    //Constructores
    //Constructor null
    public Alumnos(){    
    }
    
    //Constructo con párametro carnet (Primary key)
    public Alumnos(String carne){
        this.carne = carne;
    }
    
    //Constructor con párametros not null
    public Alumnos(String carne, String nombre1, String apellido1){
        this.carne = carne;
        this.nombre1 = nombre1;
        this.apellido1 = apellido1;
    }
    
    //Constructor con todos los párametros
    public Alumnos(String carne, String nombre1, String nombre2, 
            String nombre3, String apellido1, String apellido2){
        this.carne = carne;
        this.nombre1 = nombre1;
        this.nombre2 = nombre2;
        this.nombre3 = nombre3;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
    }
    
    //Métodos Getter´s y Setter´s
    public String getCarne(){
        return carne;
    }
    
    public void setCarne(String carne){
        this.carne = carne;
    }
    
    public String getNombre1(){
        return nombre1;
    }
    
    public void setNombre1(String nombre1){
        this.nombre1 = nombre1;
    }
    
    public String getNombre2(){
        return nombre2;
    }
    
    public void setNombre2(String nombre2){
        this.nombre2 = nombre2;
    }
    
    public String getNombre3(){
        return nombre3;
    }
    
    public void setNombre3(String nombre3){
        this.nombre3 = nombre3;
    }

    public String getApellido1() {
        return apellido1;
    }

    public void setApellido1(String apellido1) {
        this.apellido1 = apellido1;
    }

    public String getApellido2() {
        return apellido2;
    }

    public void setApellido2(String apellido2) {
        this.apellido2 = apellido2;
    }
    
    //Método toString

    @Override
    public String toString() {
        return carne + " | " + nombre1 + " | " + apellido1;
    }
    
}