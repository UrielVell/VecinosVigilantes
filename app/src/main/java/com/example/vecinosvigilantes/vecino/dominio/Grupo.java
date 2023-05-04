package com.example.vecinosvigilantes.vecino.dominio;

import android.graphics.drawable.Icon;

import java.util.ArrayList;

public class Grupo {
    private String nombreGrupo=null;
    private String administrador=null;
    private String logo=null;

    public Grupo(String nombreGrupo, String administrador,  String logo) {
        this.nombreGrupo = nombreGrupo;
        this.administrador = administrador;
        this.logo = logo;
    }

    public String getNombreGrupo() {
        return nombreGrupo;
    }

    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }

    public String getAdministrador() {
        return administrador;
    }

    public void setAdministrador(String administrador) {
        this.administrador = administrador;
    }


    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
}
