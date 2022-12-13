package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.*;
import java.util.ArrayList;

public class SqlReader {

    private int id;

    private ArrayList<Adress> adr = new ArrayList<>();

    public ArrayList<Adress> getAdr() {
        return adr;
    }


    public void readBd(String path) throws ClassNotFoundException, SQLException {

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection conn = DriverManager.getConnection(path, "root","");
        Statement stmt = conn.createStatement();
        ResultSet res = stmt.executeQuery("SELECT COUNT(*) FROM mission_model;");
        // Récupere le nombre de mission dans la BD
        while(res.next()){
            //Récupérer par nom de colonne
            id = res.getInt(1);
            //Afficher les valeurs
            System.out.println("ID: " + id);
        }

        if(id>0){

            Statement stmt1 = conn.createStatement();
            ResultSet res1 = stmt1.executeQuery("SELECT id,start_address,start_latitude,start_longitude FROM mission_model;");
            while (res1.next()) {
                Adress address = new Adress();
                //Récupérer par nom de colonne
                int id_miss = res1.getInt("id");
                //System.out.println(id_miss);
                address.setID(id_miss);
                String adress_start = res1.getString("start_address");
                address.setCluId(adress_start);
                //System.out.println(adress_start);
                double lat = res1.getFloat("start_latitude");
                address.setLat(lat);
                //System.out.println(lat);
                double lon = res1.getFloat("start_longitude");
                address.setLon(lon);
                //System.out.println(lon);
                this.adr.add(address);
            }
            conn.close();

        }

    }




}
