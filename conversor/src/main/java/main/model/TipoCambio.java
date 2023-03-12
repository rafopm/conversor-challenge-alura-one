package main.model;

public class TipoCambio {
    String idtipocambio;
    double cambio;
    String fechaactualizacion;
    int iddivisaorigen;
    int iddivisadestino;

    public TipoCambio() {
    }

    public String getIdtipocambio() {
        return idtipocambio;
    }

    public void setIdtipocambio(String idtipocambio) {
        this.idtipocambio = idtipocambio;
    }

    public double getCambio() {
        return cambio;
    }

    public void setCambio(double cambio) {
        this.cambio = cambio;
    }

    public String getFechaactualizacion() {
        return fechaactualizacion;
    }

    public void setFechaactualizacion(String fechaactualizacion) {
        this.fechaactualizacion = fechaactualizacion;
    }

    public int getIddivisaorigen() {
        return iddivisaorigen;
    }

    public void setIddivisaorigen(int iddivisaorigen) {
        this.iddivisaorigen = iddivisaorigen;
    }

    public int getIddivisadestino() {
        return iddivisadestino;
    }

    public void setIddivisadestino(int iddivisadestino) {
        this.iddivisadestino = iddivisadestino;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("TipoCambio{");
        sb.append("idtipocambio='").append(idtipocambio).append('\'');
        sb.append(", cambio=").append(cambio);
        sb.append(", fechaactualizacion='").append(fechaactualizacion).append('\'');
        sb.append(", iddivisaorigen=").append(iddivisaorigen);
        sb.append(", iddivisadestino=").append(iddivisadestino);
        sb.append('}');
        return sb.toString();
    }
}
