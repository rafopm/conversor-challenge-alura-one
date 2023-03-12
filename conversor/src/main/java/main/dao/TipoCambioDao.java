package main.dao;

import main.conexion.ConexionMySQL;
import main.model.TipoCambio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class TipoCambioDao {
    private ConexionMySQL fabricaConexion;

    public TipoCambioDao() {
        this.fabricaConexion = new ConexionMySQL();
    }

    public boolean registrar(TipoCambio tipoCambio) {
        try {
            String SQL = "INSERT INTO tipodecambio(idtipocambio, cambio, iddivisaorigen, iddivisadestino) "
                    + " values(?,?,?,?)";

            Connection connection = this.fabricaConexion.getConnection();

            PreparedStatement sentencia = connection.prepareStatement(SQL);
            System.out.println(tipoCambio);
            sentencia.setString(1, tipoCambio.getIdtipocambio());
            sentencia.setDouble(2, tipoCambio.getCambio());
            sentencia.setInt(3, tipoCambio.getIddivisaorigen());
            sentencia.setInt(4, tipoCambio.getIddivisadestino());
            System.out.println(sentencia);
            sentencia.executeUpdate();
            sentencia.close();
            return true;

        } catch (Exception e) {
            System.err.println("Ocurrió un error al registrar el tipo de cambio");
            System.err.println("Mensaje del error" + e.getMessage());
            System.err.println("Detalle del error: ");
            e.printStackTrace();

            return false;
        }

    }

    public List<TipoCambio> listar() {
        List<TipoCambio> listaTiposCambios = new ArrayList<>();

        try {

            String SQL = "SELECT * FROM tipodecambio;";
            Connection connection = this.fabricaConexion.getConnection();
            PreparedStatement sentencia = connection.prepareStatement(SQL);
            ResultSet data = sentencia.executeQuery();
            while (data.next()) {
                TipoCambio tipoCambio = new TipoCambio();
                tipoCambio.setIdtipocambio(data.getString(1));
                tipoCambio.setCambio(data.getDouble(2));
                tipoCambio.setFechaactualizacion(data.getString(3));
                tipoCambio.setIddivisaorigen(data.getInt(4));
                tipoCambio.setIddivisadestino(data.getInt(5));

                listaTiposCambios.add(tipoCambio);
            }
            data.close();
            sentencia.close();

        } catch (Exception e) {
            System.err.println("Ocurrió un error al mostrar el tipo de cambio");
            System.err.println("Mensaje del error" + e.getMessage());
            System.err.println("Detalle del error: ");
            e.printStackTrace();
        }

        return listaTiposCambios;
    }

    public boolean editar(TipoCambio tipoCambio) {

        try {
            String SQL = "UPDATE tipodecambio set cambio=?, fechaactualizacion=?, iddivisaorigen=?, iddivisadestino=? "
                    + "WHERE idtipocambio=?";

            Connection connection = this.fabricaConexion.getConnection();
            PreparedStatement sentencia = connection.prepareStatement(SQL);
            sentencia.setDouble(1, tipoCambio.getCambio());
            sentencia.setString(2, tipoCambio.getFechaactualizacion());
            sentencia.setInt(3, tipoCambio.getIddivisaorigen());
            sentencia.setInt(4, tipoCambio.getIddivisadestino());

            sentencia.setString(5, tipoCambio.getIdtipocambio());

            sentencia.executeUpdate();
            sentencia.close();
            return true;

        } catch (Exception e) {
            System.err.println("Ocurrió un error al editar el tipo de cambio");
            System.err.println("Mensaje del error" + e.getMessage());
            System.err.println("Detalle del error: ");
            e.printStackTrace();

            return false;
        }

    }

    public boolean eliminar( String idtipocambio) {

        try {
            String SQL = "DELETE from tipodecambio where idtipocambio = ?";
            Connection connection = this.fabricaConexion.getConnection();
            PreparedStatement sentencia = connection.prepareStatement(SQL);
            sentencia.setString(1,idtipocambio);
            sentencia.executeUpdate();
            sentencia.close();
            return true;
        } catch (Exception e) {
            System.err.println("Ocurrió un error al eliminar el tipo de cambio");
            System.err.println("Mensaje del error" + e.getMessage());
            System.err.println("Detalle del error: ");
            e.printStackTrace();

            return false;
        }
    }

}
