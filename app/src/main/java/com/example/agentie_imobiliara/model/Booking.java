package com.example.agentie_imobiliara.model;

import java.util.Objects;

public class Booking {
    private String house_key;
    private String user;
    private long date;
    private String hour;
    private boolean accept_booking;
    private String rejection_message;
    private String object_key;

    public Booking()
    {

    }

    public Booking(String house_key, String user, long date, String hour, boolean accept_booking, String rejection_message) {
        this.house_key = house_key;
        this.user = user;
        this.date = date;
        this.hour = hour;
        this.accept_booking = accept_booking;
        this.rejection_message = rejection_message;
    }

    public String getHouse_key() {
        return house_key;
    }

    public void setHouse_key(String house_key) {
        this.house_key = house_key;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public boolean isAccept_booking() {
        return accept_booking;
    }

    public void setAccept_booking(boolean accept_booking) {
        this.accept_booking = accept_booking;
    }

    public String getRejection_message() {
        return rejection_message;
    }

    public void setRejection_message(String rejection_message) {
        this.rejection_message = rejection_message;
    }

    public String getObject_key() {
        return object_key;
    }

    public void setObject_key(String object_key) {
        this.object_key = object_key;
    }

    public long getDate() {
        return date;
    }

    public void setDate(long date) {
        this.date = date;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return date == booking.date && accept_booking == booking.accept_booking && house_key.equals(booking.house_key) && user.equals(booking.user) && hour.equals(booking.hour) && rejection_message.equals(booking.rejection_message) && object_key.equals(booking.object_key);
    }

    @Override
    public int hashCode() {
        return Objects.hash(house_key, user, date, hour, accept_booking, rejection_message, object_key);
    }

    @Override
    public String toString() {
        return "Booking{" +
                "house_key='" + house_key + '\'' +
                ", user='" + user + '\'' +
                ", date=" + date +
                ", hour='" + hour + '\'' +
                ", accept_booking=" + accept_booking +
                ", rejection_message='" + rejection_message + '\'' +
                ", object_key='" + object_key + '\'' +
                '}';
    }
}
