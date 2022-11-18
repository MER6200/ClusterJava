package org.example;

import java.util.ArrayList;

public class ToJson {


    public Adress Warehouse;
    public ArrayList<Cluster> clusters;

    public Adress getWarehouse() {
        return Warehouse;
    }

    public void setWarehouse(Adress warehouse) {
        Warehouse = warehouse;
    }
    public void setClusters(ArrayList<Cluster> clusters) {
        this.clusters = clusters;
    }

    public ArrayList<Cluster> getClusters() {
        return clusters;
    }
}
