package main.dao;

import main.conexion.ConexionMySQL;
import main.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDao {
    private ConexionMySQL fabricaConexion;

    public UsuarioDao() {
        this.fabricaConexion = new ConexionMySQL();
    }

    public boolean registrar(Usuario usuario) {
        try {
            String SQL = "INSERT INTO usuario(nombre, usuario, password, permisos) "
                    + " values(?,?,?,?)";

            Connection connection = this.fabricaConexion.getConnection();

            PreparedStatement sentencia = connection.prepareStatement(SQL);
            sentencia.setString(1, usuario.getNombre());
            sentencia.setString(2, usuario.getUsuario());
            sentencia.setString(3, usuario.getPassword());
            sentencia.setString(4, usuario.getPermisos());

            sentencia.executeUpdate();
            sentencia.close();
            return true;

        } catch (Exception e) {
            System.err.println("Ocurrió un error al registrar el usuario");
            System.err.println("Mensaje del error" + e.getMessage());
            System.err.println("Detalle del error: ");
            e.printStackTrace();

            return false;
        }

    }

    public List<Usuario> listar() {
        List<Usuario> listaUsuarios = new ArrayList<>();

        try {

            String SQL = "SELECT * FROM usuario;";
            Connection connection = this.fabricaConexion.getConnection();
            PreparedStatement sentencia = connection.prepareStatement(SQL);
            ResultSet data = sentencia.executeQuery();
            while (data.next()) {
                Usuario usuario = new Usuario();
                usuario.setIdusuario(data.getInt(1));
                usuario.setNombre(data.getString(2));
                usuario.setUsuario(data.getString(3));
                usuario.setPassword(data.getString(4));
                usuario.setPermisos(data.getString(5));

                listaUsuarios.add(usuario);
            }
            data.close();
            sentencia.close();

        } catch (Exception e) {
            System.err.println("Ocurrió un error al mostrar los usuarios");
            System.err.println("Mensaje del error" + e.getMessage());
            System.err.println("Detalle del error: ");
            e.printStackTrace();
        }

        return listaUsuarios;
    }

    public boolean editar(Usuario usuario) {

        try {
            String SQL = "UPDATE usuario set nombre=?, usuario=?, password=?, permisos=? "
                    + "WHERE idusuario=?";
            Connection connection = this.fabricaConexion.getConnection();
            PreparedStatement sentencia = connection.prepareStatement(SQL);
            sentencia.setString(1, usuario.getNombre());
            sentencia.setString(2, usuario.getUsuario());
            sentencia.setString(3, usuario.getPassword());
            sentencia.setString(4, usuario.getPermisos());

            sentencia.setInt(5, usuario.getIdusuario());

            sentencia.executeUpdate();
            sentencia.close();
            return true;

        } catch (Exception e) {
            System.err.println("Ocurrió un error al editar el usuario");
            System.err.println("Mensaje del error" + e.getMessage());
            System.err.println("Detalle del error: ");
            e.printStackTrace();

            return false;
        }

    }

    public boolean eliminar( int idusuario) {

        try {
            String SQL = "DELETE from usuario where idusuario = ?";
            Connection connection = this.fabricaConexion.getConnection();
            PreparedStatement sentencia = connection.prepareStatement(SQL);
            sentencia.setInt(1,idusuario);
            sentencia.executeUpdate();
            sentencia.close();
            return true;
        } catch (Exception e) {
            System.err.println("Ocurrió un error al eliminar el usuario");
            System.err.println("Mensaje del error" + e.getMessage());
            System.err.println("Detalle del error: ");
            e.printStackTrace();

            return false;
        }
    }

    public Usuario login (String usuario, String password) throws SQLException {
        Usuario usuarioLogin = new Usuario();
        ResultSet rs;
        PreparedStatement sentencia = null;

        try {
            String SQL = "SELECT * FROM usuario WHERE BINARY usuario=? AND password=?";
            Connection connection = this.fabricaConexion.getConnection();
            sentencia = connection.prepareStatement(SQL);

            sentencia.setString(1, usuario);
            sentencia.setString(2, password);

            rs = sentencia.executeQuery();

            while(rs.next()) {
                usuarioLogin.setIdusuario(rs.getInt("idusuario"));
                usuarioLogin.setNombre(rs.getString("nombre"));
                usuarioLogin.setUsuario(rs.getString("usuario"));
                usuarioLogin.setPermisos(rs.getString("permisos"));
                System.out.println("ID: " + usuario.toString() );
            }

        } catch (Exception e) {
            System.err.println("Ocurrió un error al validar el usuario");
            System.err.println("Mensaje del error" + e.getMessage());
            System.err.println("Detalle del error: ");
            e.printStackTrace();
        }finally {

                sentencia.close();

        }

        return usuarioLogin;
    }
}
