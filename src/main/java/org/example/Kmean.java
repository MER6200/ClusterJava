package org.example;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Random;
import java.lang.Math;

public class Kmean {

    private ArrayList<Adress> adr= new ArrayList();
    private ArrayList<Cluster> clu = new ArrayList<>();

    private ArrayList<Adress> centre= new ArrayList();

    private ArrayList<Integer> clusters_index = new ArrayList();

    private int k;


    //Constructor pour initialiser les cluster et ensuite les centroid
    Kmean(int k,ArrayList<Adress> adr, int max_iter,double limit_tol  )
    {
        this.k = k;

        for( int i=0; i<k; i++)
        {
            String id = String.valueOf(i);
            Cluster tmp = new Cluster();

            clu.add(tmp);
        }
        this.centre = KmeanPP(k, adr);
        this.clusters_index = Clustering(adr,centre,max_iter,limit_tol);
        //System.out.println("Liste des clusters : "+clusters_index.size());
    }

    private ArrayList<Integer> Clustering(ArrayList<Adress> adr,ArrayList<Adress> centre, int max_iter,double limit_tol) {
        double mean_tol = Double.POSITIVE_INFINITY;
        int count = 0;

        while (count < max_iter && limit_tol < mean_tol) {
            for( int i=0; i<k; i++)
            {
                clu.get(i).getAdr().clear();
            }
            clusters_index.clear();
            //tem.out.println(adr.size());
            for (int i = 0; i < adr.size(); i++) {
                double dist = Double.POSITIVE_INFINITY;
                int ind = 0;
                double tmp;
                for (int j = 0; j < centre.size(); j++) {
                    tmp = Math.pow(Math.pow(centre.get(j).getLat() - adr.get(i).getLat(), 2) +
                            Math.pow(centre.get(j).getLon() - adr.get(i).getLon(), 2), 0.5);
                    if (tmp <= dist) {
                        dist = tmp;
                        ind = j;
                    }
                }
                clu.get(ind).getAdr().add(adr.get(i));
                clusters_index.add(ind);

            }

            mean_tol = 0;
            for (int j = 0; j < clu.size(); j++) {
                double mean_lon = 0;
                double mean_lat = 0;
                for (int l = 0; l < clu.get(j).getAdr().size(); l++) {
                    mean_lon = clu.get(j).getAdr().get(l).getLon() + mean_lon;
                    mean_lat = clu.get(j).getAdr().get(l).getLat() + mean_lat;
                }

                if (clu.get(j).getAdr().isEmpty() == false) {
                    mean_tol = +Math.pow(Math.pow(centre.get(j).getLat() - mean_lat / clu.get(j).getAdr().size(), 2) +
                            Math.pow(centre.get(j).getLon() - mean_lon / clu.get(j).getAdr().size(), 2), 0.5);
                    centre.get(j).setLat(mean_lat / clu.get(j).getAdr().size());
                    centre.get(j).setLon(mean_lon / clu.get(j).getAdr().size());

                }
                mean_tol = mean_tol / clu.size();
                count = +1;
            }
        }
        return clusters_index;
    }

        private ArrayList<Adress> KmeanPP (int k,ArrayList<Adress> adr){

        ArrayList<Adress> tmp_adr = (ArrayList<Adress>) adr.clone();

        Adress initialisation;
        //Pour choisir le premier point aleatoire
        Random aleatoire = new Random();
        initialisation = tmp_adr.get(aleatoire.nextInt(tmp_adr.size()));
        centre.add(initialisation);
        tmp_adr.remove(initialisation);

        for(int j = 0; j<k-1;j++)
        {
            ArrayList<Double> distance = new ArrayList<>();
            distance.clear();

            for( int i=0; i<tmp_adr.size();i++)
            {
                // Calcul de la distance au carré que l'on stock dans le tableau
                distance.add(Math.pow(initialisation.getLat()-tmp_adr.get(i).getLat(),2)+
                        Math.pow(initialisation.getLon()-tmp_adr.get(i).getLon(),2));
            }

            double somme = 0;

            for( int i=0; i<distance.size();i++)
            {
                somme = somme + distance.get(i);
                if (i>0) {
                    distance.set(i, (distance.get(i - 1) + distance.get(i)));
                }
            }

            Double aleatoireTpm = 0.00;

            while(aleatoireTpm == 0.00)
            {
                aleatoireTpm = aleatoire.nextDouble()*somme;
            }

            for(int b=0; b<distance.size();b++)
            {
                if( distance.get(b)/aleatoireTpm <= 1  )
                {
                    centre.add(tmp_adr.get(b));
                    tmp_adr.remove(tmp_adr.get(b));
                    break;
                }
            }
        }
        return centre;
    }

























    public ArrayList<Adress> getAdr() {
        return adr;
    }

    public void setAdr(ArrayList<Adress> adr) {
        this.adr = adr;
    }

    public ArrayList<Cluster> getClu() {
        return clu;
    }

    public void setClu(ArrayList<Cluster> clu) {
        this.clu = clu;
    }

    public ArrayList<Adress> getCentre() {
        return centre;
    }

    public ArrayList<Integer> getClusters_index() {
        return clusters_index;
    }
}
