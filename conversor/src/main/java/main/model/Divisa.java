package main.model;

import javafx.scene.image.ImageView;

public class Divisa {
    int iddivisa;
    String nombre;
    String iso;
    String simbolo;
    String pais;
    String imagen;

    public Divisa() {
    }

    public int getIddivisa() {
        return iddivisa;
    }

    public void setIddivisa(int iddivisa) {
        this.iddivisa = iddivisa;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIso() {
        return iso;
    }

    public void setIso(String iso) {
        this.iso = iso;
    }

    public String getSimbolo() {
        return simbolo;
    }

    public void setSimbolo(String simbolo) {
        this.simbolo = simbolo;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Divisa{");
        sb.append("iddivisa=").append(iddivisa);
        sb.append(", nombre='").append(nombre).append('\'');
        sb.append(", iso='").append(iso).append('\'');
        sb.append(", simbolo='").append(simbolo).append('\'');
        sb.append(", pais='").append(pais).append('\'');
        sb.append(", imagen='").append(imagen).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
