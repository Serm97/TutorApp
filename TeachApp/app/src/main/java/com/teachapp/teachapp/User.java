package com.teachapp.teachapp;

import java.util.Date;
import java.util.List;

public class User {

    String email;
    String name;
    String lastName;
    String city;
    String university;
    List<Area> areas;

    public User() {
    }

    public User(String email, String name, String lastName, String city, String university, List<Area> areas) {
        this.email = email;
        this.name = name;
        this.lastName = lastName;
        this.city = city;
        this.university = university;
        this.areas = areas;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getUniversity() {
        return university;
    }

    public void setUniversity(String university) {
        this.university = university;
    }

    public List<Area> getAreas() {
        return areas;
    }

    public void setAreas(List<Area> areas) {
        this.areas = areas;
    }
}

class Categorie{
    String name;

    public Categorie() {
    }

    public Categorie(String namecategories) {
        this.name = namecategories;
    }

    public String getNamecategories() {
        return name;
    }

    public void setNamecategories(String namecategories) {
        this.name = namecategories;
    }
}

class Area{

    String name;
    Categorie categorie;

    public Area() {
    }

    public Area(String name, Categorie categorie) {
        this.name = name;
        this.categorie = categorie;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }
}

class Notification{

    Date notificationDate;
    String message;

    public Notification(Date notificationDate, String message) {
        this.notificationDate = notificationDate;
        this.message = message;
    }

    public Date getNotificationDate() {
        return notificationDate;
    }

    public void setNotificationDate(Date notificationDate) {
        this.notificationDate = notificationDate;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

class Tutor extends User{

    int ntutorials;
    double score;
    List<Area> lstAreas;

    public Tutor(String email, String name, String lastName, String city, String university, List<Area> areas, int ntutorials, double score, List<Area> lstAreas) {
        super(email, name, lastName, city, university, areas);
        this.ntutorials = ntutorials;
        this.score = score;
        this.lstAreas = lstAreas;
    }

    public int getNtutorials() {
        return ntutorials;
    }

    public void setNtutorials(int ntutorials) {
        this.ntutorials = ntutorials;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public List<Area> getLstAreas() {
        return lstAreas;
    }

    public void setLstAreas(List<Area> lstAreas) {
        this.lstAreas = lstAreas;
    }
}

class Tutorial{

    User applicant;
    User tutor;
    Area area;
    Area requestedArea;
    Date date;
    double score;

    public Tutorial(User applicant, User tutor, Area area, Area requestedArea, Date date, double score) {
        this.applicant = applicant;
        this.tutor = tutor;
        this.area = area;
        this.requestedArea = requestedArea;
        this.date = date;
        this.score = score;
    }

    public User getApplicant() {
        return applicant;
    }

    public void setApplicant(User applicant) {
        this.applicant = applicant;
    }

    public User getTutor() {
        return tutor;
    }

    public void setTutor(User tutor) {
        this.tutor = tutor;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public Area getRequestedArea() {
        return requestedArea;
    }

    public void setRequestedArea(Area requestedArea) {
        this.requestedArea = requestedArea;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}

class Utilities{

    String name;

    public Utilities() {

    }
    public Utilities(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}