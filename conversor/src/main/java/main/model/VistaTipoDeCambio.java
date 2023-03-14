package main.model;

public class VistaTipoDeCambio {
    private String idtipocambio;
    private String isoo;
    private String nombreo;
    private String bandera1;
    private double cambio1;
    private String simbolo1;
    private String isod;
    private String nombred;
    private String bandera2;
    private double cambio2;
    private String simbolo2;
    private String fechaactualizacion;


    public VistaTipoDeCambio() {
    }

    public String getIdtipocambio() {
        return idtipocambio;
    }

    public void setIdtipocambio(String idtipocambio) {
        this.idtipocambio = idtipocambio;
    }

    public String getIsoo() {
        return isoo;
    }

    public void setIsoo(String isoo) {
        this.isoo = isoo;
    }

    public String getNombreo() {
        return nombreo;
    }

    public void setNombreo(String nombreo) {
        this.nombreo = nombreo;
    }

    public String getBandera1() {
        return bandera1;
    }

    public void setBandera1(String bandera1) {
        this.bandera1 = bandera1;
    }

    public double getCambio1() {
        return cambio1;
    }

    public void setCambio1(double cambio1) {
        this.cambio1 = cambio1;
    }

    public String getSimbolo1() {
        return simbolo1;
    }

    public void setSimbolo1(String simbolo1) {
        this.simbolo1 = simbolo1;
    }

    public String getIsod() {
        return isod;
    }

    public void setIsod(String isod) {
        this.isod = isod;
    }

    public String getNombred() {
        return nombred;
    }

    public void setNombred(String nombred) {
        this.nombred = nombred;
    }

    public String getBandera2() {
        return bandera2;
    }

    public void setBandera2(String bandera2) {
        this.bandera2 = bandera2;
    }

    public double getCambio2() {
        return cambio2;
    }

    public void setCambio2(double cambio2) {
        this.cambio2 = cambio2;
    }

    public String getSimbolo2() {
        return simbolo2;
    }

    public void setSimbolo2(String simbolo2) {
        this.simbolo2 = simbolo2;
    }

    public String getFechaactualizacion() {
        return fechaactualizacion;
    }

    public void setFechaactualizacion(String fechaactualizacion) {
        this.fechaactualizacion = fechaactualizacion;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("VistaTipoDeCambio{");
        sb.append("idtipocambio='").append(idtipocambio).append('\'');
        sb.append(", isoo='").append(isoo).append('\'');
        sb.append(", nombreo='").append(nombreo).append('\'');
        sb.append(", bandera1='").append(bandera1).append('\'');
        sb.append(", cambio1=").append(cambio1);
        sb.append(", simbolo1='").append(simbolo1).append('\'');
        sb.append(", isod='").append(isod).append('\'');
        sb.append(", nombred='").append(nombred).append('\'');
        sb.append(", bandera2='").append(bandera2).append('\'');
        sb.append(", cambio2=").append(cambio2);
        sb.append(", simbolo2='").append(simbolo2).append('\'');
        sb.append(", fechaactualizacion='").append(fechaactualizacion).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
