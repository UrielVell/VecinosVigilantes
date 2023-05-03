package com.example.vecinosvigilantes.vecino.dominio;

public class UsuarioClass {
    private String nombre;
    private String correo;
    private String pp;

    public UsuarioClass(String nombre, String correo, String pp) {
        this.nombre = nombre;
        this.correo = correo;
        this.pp = pp;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPp() {
        return pp;
    }

    public void setPp(String pp) {
        this.pp = pp;
    }
}
