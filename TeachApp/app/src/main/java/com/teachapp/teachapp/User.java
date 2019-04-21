package com.teachapp.teachapp;

import java.util.Date;
import java.util.List;

public class User {

    String correo;
    String nombre;
    String ciudad;
    String universidad;

    public User(String correo, String nombre, String ciudad, String universidad) {
        this.correo = correo;
        this.nombre = nombre;
        this.ciudad = ciudad;
        this.universidad = universidad;
    }
}

class Categorie{
    String nombreCategoria;

    public Categorie(String nombreCategoria) {
        this.nombreCategoria = nombreCategoria;
    }
}

class Area extends Categorie{

    String nombreArea;

    public Area(String nombreCategoria, String nombreArea) {
        super(nombreCategoria);
        this.nombreArea = nombreArea;
    }
}

class Notification{

    Date notificationDate;
    String message;

    public Notification(Date notificationDate, String message) {
        this.notificationDate = notificationDate;
        this.message = message;
    }
}

class Tutor extends User{

    int nTutorias;
    double calificacion;
    List<Area> areas;

    public Tutor(String correo, String nombre, String ciudad, String universidad, int nTutorias, double calificacion, List<Area> areas) {
        super(correo, nombre, ciudad, universidad);
        this.nTutorias = nTutorias;
        this.calificacion = calificacion;
        this.areas = areas;
    }
}

class Tutorial{

    User applicant;
    User tutor;
    Area area;
    Date date;
    double score;

    public Tutorial(User applicant, User tutor, Area area, Date date, double score) {
        this.applicant = applicant;
        this.tutor = tutor;
        this.area = area;
        this.date = date;
        this.score = score;
    }
}