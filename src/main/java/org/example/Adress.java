package org.example;

public class Adress {

    private  double lat;
    private  double lon;

    private  String rue;



//    public Adress(int lat, int lon, String rue) {
//        this.lat = lat;
//        this.lon = lon;
//        this.rue = rue;
//    }
    public double getLat() {
        return lat;
    }

    public double getLon() {
        return lon;
    }

    public String getcluId() {
        return rue;
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
