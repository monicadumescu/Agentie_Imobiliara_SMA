package com.example.agentie_imobiliara.model;

import java.util.Objects;

public class Booking {
    private String address;
    private String day;
    private String month;
    private String year;
    private String hour;
    private String agent_book;
    private String special_req;
    private String accept_booking;
    private String rejection_message;
    private String user;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getAgent_book() {
        return agent_book;
    }

    public void setAgent_book(String agent_book) {
        this.agent_book = agent_book;
    }

    public String getSpecial_req() {
        return special_req;
    }

    public void setSpecial_req(String special_req) {
        this.special_req = special_req;
    }

    public String getAccept_booking() {
        return accept_booking;
    }

    public void setAccept_booking(String accept_booking) {
        this.accept_booking = accept_booking;
    }

    public String getRejection_message() {
        return rejection_message;
    }

    public void setRejection_message(String rejection_message) {
        this.rejection_message = rejection_message;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return address.equals(booking.address) && day.equals(booking.day) && month.equals(booking.month) && year.equals(booking.year) && hour.equals(booking.hour) && agent_book.equals(booking.agent_book) && special_req.equals(booking.special_req) && accept_booking.equals(booking.accept_booking) && rejection_message.equals(booking.rejection_message) && user.equals(booking.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(address, day, month, year, hour, agent_book, special_req, accept_booking, rejection_message, user);
    }

    @Override
    public String toString() {
        return "Booking{" +
                "address='" + address + '\'' +
                ", day='" + day + '\'' +
                ", month='" + month + '\'' +
                ", year='" + year + '\'' +
                ", hour='" + hour + '\'' +
                ", agent_book='" + agent_book + '\'' +
                ", special_req='" + special_req + '\'' +
                ", accept_booking='" + accept_booking + '\'' +
                ", rejection_message='" + rejection_message + '\'' +
                ", user='" + user + '\'' +
                '}';
    }
}
