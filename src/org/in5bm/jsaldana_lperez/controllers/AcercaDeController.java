
package org.in5bm.jsaldana_lperez.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import org.in5bm.jsaldana_lperez.system.Principal;

/**
 *
 * @author Luis Carlos Pérez
 * @date 12/07/2022
 * @time 16:30:19
 * 
 *Código técnico: IN5BM
 *
 */
public class AcercaDeController implements Initializable{
    
    private Principal escenarioPrincipal;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
    
    @FXML
    public void clicRegresar(MouseEvent event) {
        escenarioPrincipal.mostrarEscenaPrincipal();
    }

    public Principal getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Principal escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
}
