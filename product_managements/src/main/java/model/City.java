package model;

public class City {
    private int idCity;
    private String address;

    public City(){}
    public City(int idCity, String address) {
        this.idCity = idCity;
        this.address = address;
    }

    public int getIdCity() {
        return idCity;
    }

    public void setIdCity(int idCity) {
        this.idCity = idCity;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
