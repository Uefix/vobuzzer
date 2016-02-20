package com.uefix.vobuzzer.excel;

import com.uefix.vobuzzer.model.Frage;
import com.uefix.vobuzzer.model.FragenKatalog;
import com.uefix.vobuzzer.model.FragenKategorie;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Uefix on 20.02.2016.
 */
public class FragenKatalogExcelLoader {

    public FragenKatalog loadFragen(InputStream inputStream, FragenKategorie... gsFragenKategorien) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = workbook.getSheetAt(0);

        Iterator<Row> rowIterator = sheet.iterator();
        Row row = rowIterator.next();
        Iterator<Cell> cellIterator = row.cellIterator();

        FragenKatalog katalog = new FragenKatalog();
        return katalog;
    }


    private XSSFSheet findSheet(XSSFWorkbook workbook, FragenKategorie fragenKategorie) {
        return null;
    }
}
