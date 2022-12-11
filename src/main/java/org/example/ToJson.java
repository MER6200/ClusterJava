package org.example;

import java.util.ArrayList;

public class ToJson {
    public Adress Warehouse;
    public ArrayList<Cluster> clusters;
    public void setWarehouse(Adress warehouse) {
        Warehouse = warehouse;
    }
    public void setClusters(ArrayList<Cluster> clusters) {
        this.clusters = clusters;
    }

}
