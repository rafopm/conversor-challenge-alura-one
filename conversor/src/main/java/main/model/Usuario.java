package main.model;

public class Usuario {
    private int idusuario;
    private String nombre;
    private String usuario;
    private String password;
    private String permisos;

    public Usuario() {
    }

    public int getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(int idusuario) {
        this.idusuario = idusuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPermisos() {
        return permisos;
    }

    public void setPermisos(String permisos) {
        this.permisos = permisos;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Usuario{");
        sb.append("idusuario=").append(idusuario);
        sb.append(", nombre='").append(nombre).append('\'');
        sb.append(", usuario='").append(usuario).append('\'');
        sb.append(", password='").append(password).append('\'');
        sb.append(", permisos='").append(permisos).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
