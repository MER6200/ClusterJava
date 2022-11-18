package org.example;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Random;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws Exception {

        //Récuperation des données depuis le fichier excel
        ArrayList<Adress> adr = new ArrayList<>();
        Excel_Reader fichier = new Excel_Reader();
        fichier.read("inputClustering.xlsx");
        adr = fichier.getAdr();

        ArrayList<Cluster> clu = new ArrayList<>();
        long start = System.currentTimeMillis();
        Kmean test = new Kmean(3,adr,100,0.005);
        clu = test.getClu();
        System.out.println("Runtime : "+(System.currentTimeMillis() - start)+"ms");
        System.out.println(clu);
        //affichage des points
        int total = 0;

        for (int i = 0; i < clu.size(); i++) {
            //System.out.println("Cluster : " + (i +1));
            total = total + clu.get(i).getAdr().size();
//            for (int j = 0; j < clu.get(i).getAdr().size(); j++) {
                System.out.println(" il y' a  "+ clu.get(i).getAdr().size());
//            }
        }
        System.out.println(total);

        //ArrayList<Cluster> cluJ = new ArrayList<>();
        Adress Warehouse = new Adress();

        Warehouse.setRue("ICI");
        Warehouse.setLat(50.809047);
        Warehouse.setLon(4.441386);

        ToJson fin =new ToJson();
        fin.setWarehouse(Warehouse);
        fin.setClusters(clu);

        try (Writer writer = new FileWriter("CluJson.json")) {
            Gson gsonW = new GsonBuilder().create();
            gsonW.toJson(fin, writer);
        }

//         for(int i=0; i<adr.size();i++)
//         {
//             System.out.println(" Lat : " + adr.get(i).getLat() + "\nLon : "+ adr.get(i).getLon());
//         }




    }
}
