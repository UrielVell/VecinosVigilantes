package com.example.vecinosvigilantes.vecino.dominio;

public class AlertaClass {

    String tipoAlarma;
    String idUsuario;
    String ppUsuario;
    String fecha;
    String hora;
    String ubicacion;

    public AlertaClass(String tipoAlarma, String idUsuario, String ppUsuario, String fecha, String hora, String ubicacion) {
        this.tipoAlarma = tipoAlarma;
        this.idUsuario = idUsuario;
        this.ppUsuario = ppUsuario;
        this.fecha = fecha;
        this.hora = hora;
        this.ubicacion = ubicacion;
    }

    public String getTipoAlarma() {
        return tipoAlarma;
    }

    public void setTipoAlarma(String tipoAlarma) {
        this.tipoAlarma = tipoAlarma;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getPpUsuario() {
        return ppUsuario;
    }

    public void setPpUsuario(String ppUsuario) {
        this.ppUsuario = ppUsuario;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
}
