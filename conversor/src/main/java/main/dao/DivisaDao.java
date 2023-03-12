package main.dao;

import main.conexion.ConexionMySQL;
import main.model.Divisa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DivisaDao {
    private ConexionMySQL fabricaConexion;

    public DivisaDao() {
        this.fabricaConexion = new ConexionMySQL();
    }

    public boolean registrar(Divisa divisa) {
        try {
            String SQL = "INSERT INTO divisa( nombre, iso, simbolo, pais,imagen) "
                    + " values(?,?,?,?,?)";


            Connection connection = this.fabricaConexion.getConnection();

            PreparedStatement sentencia = connection.prepareStatement(SQL);
            sentencia.setString(1, divisa.getNombre());
            sentencia.setString(2, divisa.getIso());
            sentencia.setString(3, divisa.getSimbolo());
            sentencia.setString(4, divisa.getPais());
            sentencia.setString(5, divisa.getImagen());

            sentencia.executeUpdate();
            sentencia.close();
            return true;

        } catch (Exception e) {
            System.err.println("Ocurrió un error al registrar la divisa");
            System.err.println("Mensaje del error" + e.getMessage());
            System.err.println("Detalle del error: ");
            e.printStackTrace();

            return false;
        }

    }

    public List<Divisa> listar() {
        List<Divisa> listaDivisas = new ArrayList<>();

        try {
            String SQL = "SELECT * FROM divisa;";
            Connection connection = this.fabricaConexion.getConnection();
            PreparedStatement sentencia = connection.prepareStatement(SQL);
            ResultSet data = sentencia.executeQuery();
            while (data.next()) {
                Divisa divisa = new Divisa();
                divisa.setIddivisa(data.getInt(1));
                divisa.setNombre(data.getString(2));
                divisa.setIso(data.getString(3));
                divisa.setSimbolo(data.getString(4));
                divisa.setPais(data.getString(5));
                divisa.setImagen(data.getString(6));

                listaDivisas.add(divisa);
            }
            data.close();
            sentencia.close();

        } catch (Exception e) {
            System.err.println("Ocurrió un error al mostrar las divisas");
            System.err.println("Mensaje del error" + e.getMessage());
            System.err.println("Detalle del error: ");
            e.printStackTrace();
        }

        return listaDivisas;
    }

    public boolean editar(Divisa divisa) {

        try {
            String SQL = "UPDATE divisa set nombre=?, iso=?, simbolo=?, pais=?, imagen=? "
                    + "WHERE iddivisa=?";

            Connection connection = this.fabricaConexion.getConnection();
            PreparedStatement sentencia = connection.prepareStatement(SQL);
            sentencia.setString(1, divisa.getNombre());
            sentencia.setString(2, divisa.getIso());
            sentencia.setString(3, divisa.getSimbolo());
            sentencia.setString(4, divisa.getPais());
            sentencia.setString(5, divisa.getImagen());

            sentencia.setInt(6, divisa.getIddivisa());

            sentencia.executeUpdate();
            sentencia.close();
            return true;

        } catch (Exception e) {
            System.err.println("Ocurrió un error al editar la divisa");
            System.err.println("Mensaje del error" + e.getMessage());
            System.err.println("Detalle del error: ");
            e.printStackTrace();

            return false;
        }

    }

    public boolean eliminar( int iddivisa) {

        try {
            String SQL = "DELETE from divisa where iddivisa = ?";
            Connection connection = this.fabricaConexion.getConnection();
            PreparedStatement sentencia = connection.prepareStatement(SQL);
            sentencia.setInt(1,iddivisa);
            sentencia.executeUpdate();
            sentencia.close();
            return true;
        } catch (Exception e) {
            System.err.println("Ocurrió un error al eliminar la divisa");
            System.err.println("Mensaje del error" + e.getMessage());
            System.err.println("Detalle del error: ");
            e.printStackTrace();

            return false;
        }
    }

}
