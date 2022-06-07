package org.in5bm.jsaldana_lperez.models;

/**
 *
 * @author Luis Carlos Pérez
 * @date 4/05/2022
 * @time 12:08:59
 * 
 *Código técnico: IN5BM
 *
 */
public class Horarios {
    
    // Atributos
    private String horarioInicio;
    private String horarioFinal;
    private byte lunes;
    private byte martes;
    private byte miercoles;
    private byte jueves;
    private byte viernes;
    
    // Constructores
    // Constructor nulo
    public Horarios(){
        
    }
    
    // Constructor con parámetros not null
    public Horarios(String horarioInicio, String horarioFinal){
        this.horarioInicio = horarioInicio;
        this.horarioFinal = horarioFinal;
    }
    
    // Constructor con todos los parámetros
    public Horarios(String horarioInicio, String horarioFinal, byte lunes,
            byte martes, byte miercoles, byte jueves, byte viernes){
        this.horarioInicio = horarioInicio;
        this.horarioFinal = horarioFinal;
        this.lunes = lunes;
        this.martes = martes;
        this.miercoles = miercoles;
        this.jueves = jueves;
        this.viernes = viernes;
    }
    
    // Métodos Getter´s y Setter´s
    public String getHorarioInicio(){
        return horarioInicio;
    }
    
    public void setHorarioInicio(String horarioInicio){
        this.horarioInicio = horarioInicio;
    }
    
    public String getHorarioFinal(){
        return horarioFinal;
    }
    
    public void setHorarioFinal(String horarioFinal){
        this.horarioFinal = horarioFinal;
    }
    
    public byte getLunes(){
        return lunes;
    }
    
    public void setLunes(byte lunes){
        this.lunes = lunes;
    }
    
    public byte getMartes(){
        return martes;
    }
    
    public void setMartes(byte martes){
        this.martes = martes;
    }
    
    public byte getMiercoles(){
        return miercoles;
    }
    
    public void setMiercoles(byte miercoles){
        this.miercoles = miercoles;
    }
    
    public byte getJueves(){
        return jueves;
    }
    
    public void setJueves(byte jueves){
        this.jueves = jueves;
    }
    
    public byte getViernes(){
        return viernes;
    }
    
    public void setViernes(byte viernes){
        this.viernes = viernes;
    }

    @Override
    public String toString() {
        return "Horarios{" + "horarioInicio=" + horarioInicio + ", horarioFinal=" + horarioFinal + ", lunes=" + lunes + ", martes=" + martes + ", miercoles=" + miercoles + ", jueves=" + jueves + ", viernes=" + viernes + '}';
    }
}