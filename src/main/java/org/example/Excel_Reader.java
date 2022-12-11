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

    public void read(String path) throws Exception {

        Workbook wb =  new Workbook(path);
        Worksheet worksheet = wb.getWorksheets().get(0);
        this.rows = worksheet.getCells().getMaxDataRow();
        this.columns = worksheet.getCells().getMaxDataColumn();

        for (int i = 1; i < 81; i++) {

            // Boucle sur chaque colonne de la ligne sélectionnée
            Adress address = new Adress();
            for (int j = 0; j < columns +1; j++) {

                if(j==0){
                    String nom = worksheet.getCells().get(i, j).getValue().toString();
                    address.setCluId((nom));

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
