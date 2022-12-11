package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.FileWriter;
import java.io.Writer;
import java.sql.*;
import java.util.ArrayList;


public class App
{

    public static void main( String[] args ) throws Exception {

        int k = 4;


        //Excel reading
        ArrayList<Adress> adr = new ArrayList<>();
        Excel_Reader fichier = new Excel_Reader();
        fichier.read("inputClustering.xlsx");
        adr = fichier.getAdr();

        //Set a warehouse
        Adress Warehouse = new Adress();
        Warehouse.setCluId("ICI");
        Warehouse.setLat(50.809047);
        Warehouse.setLon(4.441386);

        //Main algorithm
        long start = System.currentTimeMillis();
        Kmean kmean1 = new Kmean(k,adr,400,0.005);
        Correction correction1 = new Correction(kmean1.getCentre(),0.8,0.2,200,kmean1.getClusters_index(),adr);

        //Update des clusters
        for( int i=0; i<k; i++)
        {
            kmean1.getClu().get(i).getAdr().clear();
        }
        for(int i=0;i<correction1.getCorrected_index().size();i++)
        {
            kmean1.getClu().get(correction1.getCorrected_index().get(i)).getAdr().add(adr.get(i));
        }

        //Affichage
        System.out.println("Runtime : "+(System.currentTimeMillis() - start)+"ms");
        //System.out.println(correction1.getCorrected_index());


        // Connection BD et insertion
        Class.forName("com.mysql.cj.jdbc.Driver");
            try {
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/itoptics?useSSL=false&serverTimezone=UTC", "root","");
                Statement stmt1 = con.createStatement();
                stmt1.execute("DELETE FROM cluster_data_model;");
                Statement stmt = con.createStatement();
                stmt.execute("DELETE FROM cluster_model;");
                int taille =0;

                for(int i = 0;i< kmean1.getClu().size();i++) {

                 PreparedStatement prstm = con.prepareStatement("INSERT INTO cluster_model(id,user_id,name_region,number_of_point) VALUES(?,?,?,?);");
                 prstm.setInt(1,i+1);
                 prstm.setInt(2,1);
                 prstm.setString(3,"Belgique");

                 prstm.setInt(4,kmean1.getClu().get(i).getAdr().size());
                 prstm.execute();

                 for(int j = 0;j<kmean1.getClu().get(i).getAdr().size();j++) {
                     taille++;
                     PreparedStatement prstm1 = con.prepareStatement("INSERT INTO cluster_data_model(id,cluster_id,longitude,latitude) VALUES(?,?,?,?);");
                     prstm1.setInt(1,taille);
                     prstm1.setInt(2, i+ 1);
                     prstm1.setDouble(3,kmean1.getClu().get(i).getAdr().get(j).getLon() );
                     prstm1.setDouble(4,kmean1.getClu().get(i).getAdr().get(j).getLat());
                     prstm1.execute();
                 }
                }

                con.close();

            } catch (SQLException e) {
                e.printStackTrace();
            }


        //Json writing
        ToJson fin =new ToJson();
        fin.setWarehouse(Warehouse);

        fin.setClusters(kmean1.getClu());

        try (Writer writer = new FileWriter("CluJson.json")) {
            Gson gsonW = new GsonBuilder().create();
            gsonW.toJson(fin, writer);
        }

    }
}
