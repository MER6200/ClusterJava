package org.example;

import java.util.ArrayList;
import java.lang.Math;

public class Correction {

    public ArrayList<Adress> adr = new ArrayList<>();
    public Double [][] matrix;
    public ArrayList<Adress> closestPointInClu = new ArrayList<>();
    public ArrayList<Adress> closetPoint = new ArrayList<>();



    public ArrayList<Integer> listePointCluster  = new ArrayList<>();

    public ArrayList<Integer> corrected_index;

    public ArrayList<Integer> getCorrected_index() {
        return corrected_index;
    }


    public void setAdr(ArrayList<Adress> adr) {
        this.adr = adr;
    }

    public Double Distance(Adress a1, Adress a2)
    {
        return Math.sqrt( Math.pow(a1.getLat()-a2.getLat(),2) + Math.pow(a1.getLon()-a2.getLon(),2)) ;
    }

    public void Matrix()
    {
        matrix =  new Double[adr.size()][adr.size()];
        ArrayList<Double> test = new ArrayList<>();
        for(int i = 0; i<adr.size();i++)
        {
            for(int j = 0; j<adr.size();j++)
            {
                matrix[i][j] = Distance(adr.get(i), adr.get(j));

            }
        }
    }

    public void ComputeClosestPointInCluster(ArrayList<Integer> indexClu)
    {
        Adress point = adr.get(0);
        for(int i = 0; i<adr.size();i++)
        {

            Double tmp = Double.POSITIVE_INFINITY;
            for(int j= 0;j<adr.size();j++)
            {
                if (tmp > matrix[i][j] && i != j && indexClu.get(i) == indexClu.get(j))
                {
                    tmp = matrix[i][j];
                    point = adr.get(j);
                }
            }
            closestPointInClu.add(point);
        }
    }

    public  void ComputeClosestPoint(ArrayList<Integer> indexClu)
    {
        Adress point = adr.get(0);
        int pointCluster = 0;
        for(int i=0;i<adr.size();i++)
        {
            Double tmp = Double.POSITIVE_INFINITY;
            for(int j=0;j<adr.size();j++)
            {
                if(tmp > matrix[i][j] && i!=j)
                {
                    tmp = matrix[i][j];
                    point = adr.get(j);
                    pointCluster = indexClu.get(j);
                }
            }
            closetPoint.add(point);
            listePointCluster.add(pointCluster);
        }
    }

    public Double Cost(double a1, double a2, Adress adr1, Adress cpic, Adress center)
    {
        double d1,d2;
        d1 = Distance(adr1,cpic);
        d2 = Distance(adr1,center);

        return a1*d1+a2*d2;
    }

    Correction(ArrayList<Adress> centre,double a1, double a2, int maxIter2,ArrayList<Integer> indexClu, ArrayList<Adress> adr)
    {
        setAdr(adr);
        Matrix();
        int count = 0;

        while(count<maxIter2)
        {
            ComputeClosestPointInCluster(indexClu);
            ComputeClosestPoint(indexClu);
             for(int i=0;i< adr.size();i++)
             {
                 //System.out.println(closestPointInClu);
                 if(closestPointInClu.get(i).getLat()!=closetPoint.get(i).getLat() || closestPointInClu.get(i).getLon()!=closetPoint.get(i).getLon())
                 {
                     if( Cost(a1,a2,adr.get(i),closestPointInClu.get(i),centre.get(indexClu.get(i))) >
                             Cost(a1,a2,adr.get(i),closetPoint.get(i),centre.get(listePointCluster.get(i))) )
                     {
                         indexClu.set(i,listePointCluster.get(i));
                         ComputeClosestPoint(indexClu);
                     }

                 }
             }
             count +=1;
        }
        corrected_index = indexClu;


    }
}
