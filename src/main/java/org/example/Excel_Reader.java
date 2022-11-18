package org.example;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;



import java.util.ArrayList;





public class Excel_Reader {

    private int rows;
    private int columns;
    private ArrayList<Adress> adr = new ArrayList<>();

    public ArrayList<Adress> getAdr() {
        return adr;
    }

    public void setAdr(ArrayList<Adress> adr) {
        this.adr = adr;
    }

    public int getColumns() {
        return columns;
    }

    public int getRows() {
        return rows;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public void read(String path) throws Exception {

        Workbook wb =  new Workbook(path);
        Worksheet worksheet = wb.getWorksheets().get(0);
        this.rows = worksheet.getCells().getMaxDataRow();
        this.columns = worksheet.getCells().getMaxDataColumn();

        System.out.println("Le nombre de ligne est de :"+rows+"\net il y a "+columns+" colognes.");
        for (int i = 1; i < 21; i++) {

            // Boucle sur chaque colonne de la ligne sélectionnée
            Adress address = new Adress();
            for (int j = 0; j < columns +1; j++) {

                if(j==0){
                    String nom = worksheet.getCells().get(i, j).getValue().toString();
                    address.setRue((nom));

                } else if (j==1) {

                    address.setLat(worksheet.getCells().get(i, j).getDoubleValue());

                } else if (j==2) {

                    address.setLon(worksheet.getCells().get(i, j).getDoubleValue());
                }

            }
            this.adr.add(address);
        }
    }

}
