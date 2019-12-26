package com.example.lastdiaryapp.ClassCrl;

public class Event {
    private int ID;
    private String Title;
    private String Content;
    private String TimeCreate;
    private String DateCreate;
    private String DateEvent;
    private String TimeEvent;
    private int Photo;
    private String Notifical;

    public Event(){}

    public Event(String title, String content, String timeCreate, String dateCreate, String dateEvent, String timeEvent, int photo,String notifical) {
        Title = title;
        Content = content;
        TimeCreate = timeCreate;
        DateCreate = dateCreate;
        DateEvent = dateEvent;
        TimeEvent = timeEvent;
        Photo = photo;
        Notifical=notifical;
    }

    public Event(int ID, String title, String content, String timeCreate, String dateCreate, String dateEvent, String timeEvent, int photo,String notifical) {
        this.ID = ID;
        Title = title;
        Content = content;
        TimeCreate = timeCreate;
        DateCreate = dateCreate;
        DateEvent = dateEvent;
        TimeEvent = timeEvent;
        Photo = photo;
        Notifical=notifical;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getTimeCreate() {
        return TimeCreate;
    }

    public void setTimeCreate(String timeCreate) {
        TimeCreate = timeCreate;
    }

    public String getDateCreate() {
        return DateCreate;
    }

    public void setDateCreate(String dateCreate) {
        DateCreate = dateCreate;
    }

    public String getDateEvent() {
        return DateEvent;
    }

    public void setDateEvent(String dateEvent) {
        DateEvent = dateEvent;
    }

    public String getTimeEvent() {
        return TimeEvent;
    }

    public void setTimeEvent(String timeEvent) {
        TimeEvent = timeEvent;
    }

    public int getPhoto() {
        return Photo;
    }

    public void setPhoto(int photo) {
        Photo = photo;
    }

    public String getNotifical (){
        return Notifical;
    }

    public void setNotifical(String notifical) {
        Notifical = notifical;
    }
}
