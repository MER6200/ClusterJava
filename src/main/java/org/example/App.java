package org.example;
// TODO Rajouter les id dans les adress
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

        Correction correction = new Correction();

        ArrayList<Cluster> clu = new ArrayList<>();
        long start = System.currentTimeMillis();
        Kmean test = new Kmean(5,adr,400,0.005);
        clu = test.getClu();
        ArrayList<Integer> corrected_index;
        corrected_index = correction.CorrectionAlgorithm(test.getCentre(),0.8,0.2,200,test.getClusters_index(),adr);

        System.out.println("Runtime : "+(System.currentTimeMillis() - start)+"ms");

        System.out.println(corrected_index);
        //System.out.println(clu);
        //affichage des points
        int total = 0;


        //System.out.println(total);

        //ArrayList<Cluster> cluJ = new ArrayList<>();
        Adress Warehouse = new Adress();

        Warehouse.setCluId("ICI");
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
