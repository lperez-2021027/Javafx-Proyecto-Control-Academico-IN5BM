package org.in5bm.jsaldana_lperez.models;

/**
 *
 * @author José Roberto Saldaña Arrazola
 * @author Luis Carlos Pérez
 * @date 27-abr-2022
 * @time 19:03:42
 *
 * Código técnico: IN5BM
 */

public class Salones {
    
    //Atributos
    private String codigoSalon;
    private String descripcion;
    private int capacidadMaxima;
    private String edificio;
    private int nivel;
    
    
    //Construcores
    //Constructor null
    public Salones(){
    }
    
    //Constructo con párametro codigoSalon (Primary key)
    public Salones(String codigoSalon){
        this.codigoSalon = codigoSalon;
    }
    
    //Constructor con párametros not null
    public Salones(String codigoSalon, int capacidadMaxima){
        this.codigoSalon = codigoSalon;
        this.capacidadMaxima = capacidadMaxima;
    }
    
    //Constructor con todos los párametros
    public Salones (String codigoSalon, String descripcion, 
            int capacidadMaxima, String edificio, int nivel){
        this.codigoSalon = codigoSalon;
        this.descripcion = descripcion;
        this.capacidadMaxima = capacidadMaxima;
        this.edificio = edificio;
        this.nivel = nivel;
    }
    
    //Metodos Getter´s y Setter´s
    public String getCodigoSalon() {
        return codigoSalon;
    }
    
    public void setCodigoSalon(String codigoSalon){
        this.codigoSalon = codigoSalon;
    }
    
    public String getDescripcion(){
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
    
    public int getCapacidadMaxima(){
        return capacidadMaxima;
    }
    
    public void setCapacidadMaxima(int capacidadMaxima){
        this.capacidadMaxima = capacidadMaxima;
    }
    
    public String getEdificio(){
        return this.edificio;
    }
    
    public void setEdificio(String edificio){
        this.edificio = edificio;
    }
    
    public int getNivel(){
        return nivel;
    }
    
    public void setNivel(int nivel){
        this.nivel = nivel;
    }
    
    //Método toString

    @Override
    public String toString() {
        return "Salones{" + "codigoSalon=" + codigoSalon + ", descripcion=" + descripcion + ", capacidadMaxima=" + capacidadMaxima + ", edificio=" + edificio + ", nivel=" + nivel + '}';
    }
}