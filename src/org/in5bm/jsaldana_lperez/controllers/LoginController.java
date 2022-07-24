package org.in5bm.jsaldana_lperez.controllers;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.in5bm.jsaldana_lperez.db.Conexion;
import org.in5bm.jsaldana_lperez.system.Principal;
import org.in5bm.jsaldana_lperez.models.Usuarios;
import org.in5bm.jsaldana_lperez.models.Roles;

/**
 *
 * @author Luis Carlos Pérez
 * @date 19/07/2022
 * @time 08:18:15
 *
 * Código técnico: IN5BM
 *
 */
public class LoginController implements Initializable {

    private Principal escenarioPrincipal;
    
    Usuarios usuario = new Usuarios();
    Roles rol = new Roles();

    ArrayList<String> listaUser = new ArrayList<>();
    ArrayList<String> listaPass = new ArrayList<>();

    public boolean rolType;
    public String nombre;
    public String descripcion;
    
    
    @FXML
    private Label lblContraseña;

    @FXML
    private Label lblUser;

    @FXML
    private TextField txtUsuario;

    @FXML
    private PasswordField pswPass;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        obtenerCampos();
    }
    
    public int obtenerRol() {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_comprobacion_roles(?, ?)}");
            pstmt.setString(1, usuario.getNombre());
            pstmt.setString(2, usuario.getPass());
            rs = pstmt.executeQuery();
            System.out.println(pstmt);

            while (rs.next()) {
                usuario.setNombre(rs.getString(1));
                rol.setId(rs.getInt(2));
                rol.setDescripcion(rs.getString(3));
            }
            
        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar consultar");
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
        return rol.getId();
    }

    public void obtenerCampos() {

        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            pstmt = Conexion.getInstance().getConexion().prepareCall("{call sp_comprobacion_campos()}");
            rs = pstmt.executeQuery();

            while (rs.next()) {
                usuario.setUsuario(rs.getString(1));
                usuario.setPass(rs.getString(2));

                listaUser.add(usuario.getUsuario());
                listaPass.add(usuario.getPass());
            }
        } catch (SQLException e) {
            System.err.println("\nSe produjo un error al intentar consultar");
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
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    /*public String datos(int a) {
        String[] data = new String[2];
        data[0] = usuario.getNombre();
        data[1] = usuario.getPass();
        return data[a];
    }*/

    @FXML
    public void clicIngresar() {
        if (listaUser.contains(txtUsuario.getText())) {
            lblUser.setVisible(false);
            if (listaPass.contains(pswPass.getText())) {
                MenuPrincipalController menu = new MenuPrincipalController();
                lblContraseña.setVisible(false);
                usuario.setNombre(txtUsuario.getText());
                //usuario.setNombre("pedro");
                usuario.setPass(pswPass.getText());
                //obtenerRol();
                menu.setRol(obtenerRol());
                nombre = usuario.getNombre();
                descripcion = rol.getDescripcion();
                //rolType = rol.getId();
                usuario.toString();
                rol.toString();
                //obtenerRol(usuario.getNombre(), usuario.getPass());
                System.out.println(usuario.getNombre()+rol.getId()+rol.getDescripcion());
                
                
                escenarioPrincipal.mostrarEscenaPrincipal();
            } else {
                lblContraseña.setText("Campo erroneo");
                lblContraseña.setVisible(true);
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Login");
                alert.setHeaderText(null);
                alert.setContentText("Credenciales invalidas.");
                alert.show();
            }
        } else {
            lblUser.setText("Campo erroneo");
            lblUser.setVisible(true);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Login");
            alert.setHeaderText(null);
            alert.setContentText("Credenciales invalidas.");
            alert.show();
        }
    }
}
