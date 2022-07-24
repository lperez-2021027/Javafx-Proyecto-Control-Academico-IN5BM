
package org.in5bm.jsaldana_lperez.models;

/**
 *
 * @author Luis Carlos Pérez
 * @date 19/07/2022
 * @time 11:41:13
 * 
 *Código técnico: IN5BM
 *
 */
public class Usuarios {
    private String usuario;
    private String pass;
    private String nombre;
    private int rolesId;
    
    public Usuarios() {
    }

    public Usuarios(String usuario, String pass, String nombre, int rolesId) {
        this.usuario = usuario;
        this.pass = pass;
        this.nombre = nombre;
        this.rolesId = rolesId;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getRolesId() {
        return rolesId;
    }

    public void setRolesId(int rolesId) {
        this.rolesId = rolesId;
    }

    @Override
    public String toString() {
        return "Usuarios{" + "usuario=" + usuario + ", pass=" + pass + ", nombre=" + nombre + ", rolesId=" + rolesId + '}';
    }
}
