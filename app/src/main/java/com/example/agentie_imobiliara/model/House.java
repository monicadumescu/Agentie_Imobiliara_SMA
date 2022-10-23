package com.example.agentie_imobiliara.model;

import java.util.Objects;

public class House {
    private String Address;
    private String Size;
    private String Rooms;
    private String Baths;
    private String Floors;
    private String Special;

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }

    public String getRooms() {
        return Rooms;
    }

    public void setRooms(String rooms) {
        Rooms = rooms;
    }

    public String getBaths() {
        return Baths;
    }

    public void setBaths(String baths) {
        Baths = baths;
    }

    public String getFloors() {
        return Floors;
    }

    public void setFloors(String floors) {
        Floors = floors;
    }

    public String getSpecial() {
        return Special;
    }

    public void setSpecial(String special) {
        Special = special;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        House house = (House) o;
        return Address.equals(house.Address) && Size.equals(house.Size) && Rooms.equals(house.Rooms) && Baths.equals(house.Baths) && Floors.equals(house.Floors) && Special.equals(house.Special);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Address, Size, Rooms, Baths, Floors, Special);
    }

    @Override
    public String toString() {
        return "House{" +
                "Address='" + Address + '\'' +
                ", Size='" + Size + '\'' +
                ", Rooms='" + Rooms + '\'' +
                ", Baths='" + Baths + '\'' +
                ", Floors='" + Floors + '\'' +
                ", Special='" + Special + '\'' +
                '}';
    }
}
