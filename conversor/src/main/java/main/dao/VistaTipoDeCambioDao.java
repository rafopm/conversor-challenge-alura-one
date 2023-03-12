package main.dao;

import main.conexion.ConexionMySQL;
import main.model.VistaTipoDeCambio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class VistaTipoDeCambioDao {
    private ConexionMySQL fabricaConexion;

    public VistaTipoDeCambioDao() {
        this.fabricaConexion = new ConexionMySQL();
    }


    public List<VistaTipoDeCambio> listar() {
        List<VistaTipoDeCambio> listaTipoDeCambio = new ArrayList<>();

        try {

            String SQL = "select * from vista_tipodecambio order by fechaactualizacion;";
            Connection connection = this.fabricaConexion.getConnection();
            PreparedStatement sentencia = connection.prepareStatement(SQL);
            ResultSet data = sentencia.executeQuery();
            while (data.next()) {
                VistaTipoDeCambio vistaTipoDeCambio = new VistaTipoDeCambio();
                vistaTipoDeCambio.setIdtipocambio(data.getString(1)); //USDEUR
                vistaTipoDeCambio.setIsoo(data.getString(2)); //USD
                vistaTipoDeCambio.setNombreo(data.getString(3)); //"Dólar estadounidense"
                vistaTipoDeCambio.setBandera1(data.getString(4)); //bandera1
                vistaTipoDeCambio.setIsod(data.getString(5)); // EUR
                vistaTipoDeCambio.setCambio1(data.getDouble(6)); //1
                vistaTipoDeCambio.setNombred(data.getString(7)); //Euro
                vistaTipoDeCambio.setBandera2(data.getString(8)); //bandera2
                vistaTipoDeCambio.setFechaactualizacion(data.getString(9)); //2023-03-11 16:15:53
                vistaTipoDeCambio.setCambio2(data.getDouble(10)); //0.9400000000


                listaTipoDeCambio.add(vistaTipoDeCambio);
            }
            //idtipocambio,  isoo, nombreo, bandera1,  cambio1, isod, nombred, bandera2, fechaactualizacion, cambio2
            data.close();
            sentencia.close();

        } catch (Exception e) {
            System.err.println("Ocurrió un error al mostrar los tipos de cambio");
            System.err.println("Mensaje del error" + e.getMessage());
            System.err.println("Detalle del error: ");
            e.printStackTrace();
        }

        return listaTipoDeCambio;
    }

}
