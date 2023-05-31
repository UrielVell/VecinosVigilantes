package com.example.vecinosvigilantes.vecino.dominio;

public class MiembroClass {

    String nombre;
    String imagen_perfil;

    public MiembroClass(String nombre, String imagen_perfil) {
        this.nombre = nombre;
        this.imagen_perfil = imagen_perfil;
    }

    public MiembroClass() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImagen_perfil() {
        return imagen_perfil;
    }

    public void setImagen_perfil(String imagen_perfil) {
        this.imagen_perfil = imagen_perfil;
    }
}
