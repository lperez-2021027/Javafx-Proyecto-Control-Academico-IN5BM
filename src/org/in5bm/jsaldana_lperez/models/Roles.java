
package org.in5bm.jsaldana_lperez.models;

/**
 *
 * @author Luis Carlos Pérez
 * @date 19/07/2022
 * @time 11:44:05
 * 
 *Código técnico: IN5BM
 *
 */
public class Roles {
    private int id;
    private String descripcion;
    
    public Roles() {
    }

    public Roles(int id, String descripcion) {
        this.id = id;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public String toString() {
        return "Roles{" + "id=" + id + ", descripcion=" + descripcion + '}';
    }
}
