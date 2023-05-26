package com.example.vecinosvigilantes.vecino.dominio;

public class UsuarioClass {
    private String id_usuario;
    private String nombre;
    private String correo;
    private String pp;
    private String id_grupo;

    public UsuarioClass(String nombre, String correo, String pp, String id_grupo) {
        this.nombre = nombre;
        this.correo = correo;
        this.pp = pp;
        this.id_grupo=id_grupo;
    }
    public UsuarioClass(String id_usuario){
        this.id_usuario = id_usuario;
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

    public String getId_grupo() {
        return id_grupo;
    }

    public void setId_grupo(String id_grupo) {
        this.id_grupo = id_grupo;
    }
}
