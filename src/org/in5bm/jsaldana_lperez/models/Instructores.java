package org.in5bm.jsaldana_lperez.models;

/**
 *
 * @author Luis Carlos Pérez
 * @date 2/05/2022
 * @time 17:12:07
 * 
 *Código técnico: IN5BM
 *
 */
public class Instructores {
    
    // Atributos
    private String nombre1;
    private String nombre2;
    private String nombre3;
    private String apellido1;
    private String apellido2;
    private String direccion;
    private String email;
    private String telefono;
    private String fechaNacimiento;
    
    // Constructores
    // Constructor nulo
    public Instructores(){
        
    }
    
    // Constructor con parametros not null
    public Instructores(String nombre1, String apellido1, String email, String telefono){
        this.nombre1 = nombre1;
        this.apellido1 = apellido1;
        this.email = email;
        this.telefono = telefono;
    }
    
    // Constructor con todos los parametros
    public Instructores(String nombre1, String nombre2, String nombre3, String apellido1, 
            String apellido2, String direccion, String email, String telefono, String fechaNacimiento){
        this.nombre1 = nombre1;
        this.nombre2 = nombre2;
        this.nombre3 = nombre3;
        this.apellido1 = apellido1;
        this.apellido2 = apellido2;
        this.direccion = direccion;
        this.email = email;
        this.telefono = telefono;
        this.fechaNacimiento = fechaNacimiento;
    }
    
    // Métodos Getter´s y Setter´s
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
    
    public String getApellido1(){
        return apellido1;
    }
    
    public void setApellido1(String apellido1){
        this.apellido1 = apellido1;
    }
    
    public String getApellido2(){
        return apellido2;
    }
    
    public void setApellido2(String apellido2){
        this.apellido2 = apellido2;
    }
    
    public String getDireccion(){
        return direccion;
    }
    
    public void setDireccion(String direccion){
        this.direccion = direccion;
    }
    
    public String getEmail(){
        return email;
    }
    
    public void setEmail(String email){
        this.email = email;
    }
    
    public String getTelefono(){
        return telefono;
    }
    
    public void setTelefono(String telefono){
        this.telefono = telefono;
    }
    
    public String getFechaNacimiento(){
        return fechaNacimiento;
    }
    
    public void setFechaNacimiento(String fechaNacimiento){
        this.fechaNacimiento = fechaNacimiento;
    }

    @Override
    public String toString() {
        return "Instructores{" + "nombre1=" + nombre1 + ", nombre2=" + nombre2 + ", nombre3=" + nombre3 + ", apellido1=" + apellido1 + ", apellido2=" + apellido2 + ", direccion=" + direccion + ", email=" + email + ", telefono=" + telefono + ", fechaNacimiento=" + fechaNacimiento + '}';
    }
}