package com.example.lastdiaryapp.ClassCrl;

public class Diary {

    private int ID;
    private String Title;
    private String Content;
    private String TimeCreate;
    private String DateCreate;
    private String DayCreate;
    private String MonthCreate;
    private String YearCreate;
    private byte[] Photo;
    private String StatusLock;
    private String Password;

    public Diary(){}

    public Diary(String title, String content, String timeCreate, String dateCreate, String dayCreate, String monthCreate, String yearCreate, byte[] photo,String statusLock,String password) {
        Title = title;
        Content = content;
        TimeCreate = timeCreate;
        DateCreate = dateCreate;
        DayCreate = dayCreate;
        MonthCreate = monthCreate;
        YearCreate = yearCreate;
        Photo = photo;
        StatusLock=statusLock;
        Password=password;
    }

    public Diary(int ID, String title, String content, String timeCreate, String dateCreate, String dayCreate, String monthCreate, String yearCreate, byte[] photo,String statusLock,String password) {
        this.ID = ID;
        Title = title;
        Content = content;
        TimeCreate = timeCreate;
        DateCreate = dateCreate;
        DayCreate = dayCreate;
        MonthCreate = monthCreate;
        YearCreate = yearCreate;
        Photo = photo;
        StatusLock=statusLock;
        Password=password;
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

    public String getDayCreate() {
        return DayCreate;
    }

    public void setDayCreate(String dayCreate) {
        DayCreate = dayCreate;
    }

    public String getMonthCreate() {
        return MonthCreate;
    }

    public void setMonthCreate(String monthCreate) {
        MonthCreate = monthCreate;
    }

    public String getYearCreate() {
        return YearCreate;
    }

    public void setYearCreate(String yearCreate) {
        YearCreate = yearCreate;
    }

    public byte[] getPhoto() {
        return Photo;
    }

    public void setPhoto(byte[] photo) {
        Photo = photo;
    }

    public String getStatusLock() {
        return StatusLock;
    }

    public void setStatusLock(String statusLock) {
        StatusLock = statusLock;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
