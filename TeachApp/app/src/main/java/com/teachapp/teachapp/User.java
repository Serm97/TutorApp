package com.teachapp.teachapp;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class User implements Serializable {

    private String email;
    private String name;
    private String lastName;
    private String phone;
    private String city;
    private String university;
    private List<Area> areas;
    private int ntutorials;
    private int ntutorialsReceived;
    private double score;

    public User() {
    }

    public User(String email, String name, String lastName,String phone, String city, String university, List<Area> areas) {
        this.email = email;
        this.name = name;
        this.lastName = lastName;
        this.phone = phone;
        this.city = city;
        this.university = university;
        this.areas = areas;
        this.ntutorials = 0;
        this.ntutorialsReceived = 0;
        this.score = 0;
    }

    public User(String email, String name, String lastName, String phone, String city, String university, List<Area> areas, int ntutorials, int ntutorialsReceived, double score) {
        this.email = email;
        this.name = name;
        this.lastName = lastName;
        this.phone = phone;
        this.city = city;
        this.university = university;
        this.areas = areas;
        this.ntutorials = ntutorials;
        this.ntutorialsReceived = ntutorialsReceived;
        this.score = score;
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
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

    public int getNtutorials() {
        return ntutorials;
    }

    public void setNtutorials(int ntutorials) {
        this.ntutorials = ntutorials;
    }

    public int getNtutorialsReceived() {
        return ntutorialsReceived;
    }

    public void setNtutorialsReceived(int ntutorialsReceived) {
        this.ntutorialsReceived = ntutorialsReceived;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}

class Category implements Serializable{
    private String name;
    private List<Area> areas;
    private int icon;

    public Category() {
    }

    public Category(String name, List<Area> areas, int icon) {
        this.name = name;
        this.areas = areas;
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Area> getAreas() {
        return areas;
    }

    public void setAreas(List<Area> areas) {
        this.areas = areas;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }
}

class Area implements Serializable{

    private String name;

    public Area() {
    }

    public Area(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}

class Notification implements Serializable{

    private User toUser;
    private Date notificationDate;
    private String message;
    private Request request;

    public Notification() {
    }

    public Notification(User toUser, Date notificationDate, String message, Request request) {
        this.toUser = toUser;
        this.notificationDate = notificationDate;
        this.message = message;
        this.request = request;
    }

    public User getToUser() {
        return toUser;
    }

    public void setToUser(User toUser) {
        this.toUser = toUser;
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

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }
}

class Tutorial{

    private User applicant;
    private User tutor;
    private Area area;
    private Area requestedArea;
    private Date date;
    private double score;

    public Tutorial() {
    }

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

class Request implements Serializable{
    private User applicant;
    private Area areaO;
    private Area areaS;
    private Date dateLimit;
    private int state;

    public Request() {
    }

    public Request(User applicant, Area areaO, Area areaS, Date dateLimit) {
        this.applicant = applicant;
        this.areaO = areaO;
        this.areaS = areaS;
        this.dateLimit = dateLimit;
        this.state = 0;
    }

    public User getApplicant() {
        return applicant;
    }

    public void setApplicant(User applicant) {
        this.applicant = applicant;
    }

    public Area getAreaO() {
        return areaO;
    }

    public void setAreaO(Area areaO) {
        this.areaO = areaO;
    }

    public Area getAreaS() {
        return areaS;
    }

    public void setAreaS(Area areaS) {
        this.areaS = areaS;
    }

    public Date getDateLimit() {
        return dateLimit;
    }

    public void setDateLimit(Date dateLimit) {
        this.dateLimit = dateLimit;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
class Utilities{

    private String name;

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