package main.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexionMySQL {
    private Connection connection;
    /*
    private String usuario = "nr1g5p7mgobi7njqj8rp";
    private String password = "pscale_pw_epCsEsqySpVrs7JQMf266EbkdrUy4Gw7OlAmnUxtQb3";
    private String servidor = "aws-sa-east-1.connect.psdb.cloud";
    private String nombreBD= "rafopm_db";
    */
    private String usuario = "root";
    private String password = "123456";
    private String servidor = "localhost:3306";
    private String nombreBD= "conversor";
    private String url = "jdbc:mysql://"+servidor+"/"+nombreBD;
    private String driver = "com.mysql.cj.jdbc.Driver";

    public ConexionMySQL() {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url,usuario,password);
            if (connection != null){
                System.out.println("Conexión realizada correctamente");
            }
        }catch (Exception e){
            System.err.println("Ocurrió un error en la conexión");
            System.err.println("Mensaje del error"+e.getMessage());
            System.err.println("Detalle del error: ");
            e.printStackTrace();
        }
    }

    public Connection getConnection () {
        return connection;
    }
}
