package org.example;

public class Adress {
    private  double lat;
    private  double lon;
    private  String rue;

    private int ID;

    public double getLat() {
        return lat;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public double getLon() {
        return lon;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public void setCluId(String cluId) {
        this.rue = cluId;
    }
}
