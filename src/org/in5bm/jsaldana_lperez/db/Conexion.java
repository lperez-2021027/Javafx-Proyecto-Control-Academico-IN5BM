package org.in5bm.jsaldana_lperez.db;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;

/**
 *
 * @author José Roberto Saldaña Arrazola
 * @author Luis Carlos Pérez
 * @date 26-abr-2022
 * @time 8:26:32
 *
 * Código técnico: IN5BM
 */

public class Conexion {
    
    private final String URL;
    private final String IP_SERVER = "127.0.0.1";
    private final String PORT = "3306";
    private final String DB = "db_control_academico_in5bm";
    private final String USER = "kinal";
    private final String PASSWORD = "admin";
    private Connection conexion; 

    public Connection getConexion() {
        return conexion;
    }
    
    private static Conexion instancia;
    
    public static Conexion getInstance(){
        
        if(instancia == null){
            instancia = new Conexion(); 
        }
        return instancia;
    }
    
    private Conexion(){
        URL = "jdbc:mysql://" + IP_SERVER + ":" + PORT + "/" + DB;
        
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            //Class.forName("com.mysql.jdbc.Driver");
            //Class.forName("com.mysql.jdbc.Driver").newInstance();
            
            conexion = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Conexion exitosa");
            
            DatabaseMetaData dma= conexion.getMetaData();
            System.out.println("\nConnected to: " + dma.getURL());
            System.out.println("Driver: " + dma.getDriverName());
            System.out.println("Version: " + dma.getDriverVersion());
        }catch (ClassNotFoundException e){
            System.err.println("No se encuentra ninguna definicion para la clase");
            e.printStackTrace();
        /*}catch (InstantiationException e){
            System.err.println("No se puede crear una instancia del objeto");
            e.printStackTrace();*/
        /*}catch (llegalAccessException e){
            System.err.println("No se tiene los permisos para acceder al paquete");
            e.printStackTrace();*/
        }catch(CommunicationsException e){
            System.err.println("No se pudo establecer conexion con el servidor de MYSQL");
            System.err.println("Verifique que el servicio este levantado,"
                    + "que la IP_SERVER y el puerto este correcto");
        }catch(SQLException e){
            System.err.println("Se produjo un error de tipo SQLException");
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("ErrorCode: " + e.getErrorCode());
            System.out.println("Message: " + e.getMessage());
            System.out.println("\n");
            e.printStackTrace();
        }catch(Exception e){
            System.err.println("Se produjo un error al intentar establecer la conexion con la base de datos");
            e.printStackTrace();
        }
    }
}
