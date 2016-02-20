package com.uefix.vobuzzer.excel;

import com.uefix.vobuzzer.exception.FragenKatalogLoaderException;
import com.uefix.vobuzzer.model.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/**
 * Created by Uefix on 20.02.2016.
 */
public class FragenKatalogExcelLoader {

    public static final Logger LOG = Logger.getLogger(FragenKatalogExcelLoader.class);


    private DataFormatter formatter = new DataFormatter(Locale.GERMANY);

    public FragenKatalog loadFragen(InputStream inputStream, FragenKategorie... fragenKategorien) throws IOException {
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);

        FragenKatalog katalog = new FragenKatalog();
        for (FragenKategorie kategorie : fragenKategorien) {
            XSSFSheet sheet = findSheet(workbook, kategorie);

            LOG.debug("Processing sheet '" + sheet.getSheetName() + "'...");

            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {


                Row row = rowIterator.next();

                // LOG.debug("Processing row#" + row.getRowNum() + "...");

                Cell frageCell = getCell(row, 0);

                String frageText = frageCell.getStringCellValue();
                if (row.getRowNum() == 0) {
                    if (!frageText.equals("Frage")) {
                        throw new FragenKatalogLoaderException("Unerwarteter Header in Sheet '" + sheet.getSheetName() + "' (erste Zelle ist nicht 'Frage')");
                    } else {
                        continue;
                    }
                }

                Frage frage = new Frage();
                frage.setId(new FrageId(row.getRowNum(), kategorie));
                frage.setText(frageText);

                Cell richtigeAntwortCell = getCell(row, 5);
                String richtigeAntwortText = getStringCellValue(richtigeAntwortCell);
                if (StringUtils.isBlank(richtigeAntwortText)) {
                    throw new FragenKatalogLoaderException("Es gibt keinen Wert fuer die richtige Antwort in Zeile#" + row.getRowNum() + " in Sheet '" + sheet.getSheetName() + "'; Frage: '" + frage.getText() + "'");
                }

                AntwortSlot richtigeAntwortSlot = determineAntwortSlot(richtigeAntwortText);


                Antwort antwortA = buildAntwort(getCell(row, 1), AntwortSlot.A, richtigeAntwortSlot, richtigeAntwortText);
                Antwort antwortB = buildAntwort(getCell(row, 2), AntwortSlot.B, richtigeAntwortSlot, richtigeAntwortText);
                Antwort antwortC = buildAntwort(getCell(row, 3), AntwortSlot.C, richtigeAntwortSlot, richtigeAntwortText);
                Antwort antwortD = buildAntwort(getCell(row, 4), AntwortSlot.D, richtigeAntwortSlot, richtigeAntwortText);


                frage.addAntwort(antwortA);
                frage.addAntwort(antwortB);
                frage.addAntwort(antwortC);
                frage.addAntwort(antwortD);

                if (frage.getRichtigeAntwort() == null) {
                    throw new FragenKatalogLoaderException("Es gibt keine richtige Antwort fuer Zeile#" + row.getRowNum() + " in Sheet '" + sheet.getSheetName() + "'; Frage: '" + frage.getText() + "'");
                }

                katalog.addFrage(frage);
            }
        }
        return katalog;
    }




    XSSFSheet findSheet(XSSFWorkbook workbook, FragenKategorie fragenKategorie) {
        for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
            XSSFSheet sheet = workbook.getSheetAt(i);
            if (fragenKategorie.getSheetName().equalsIgnoreCase(sheet.getSheetName())) {
                return sheet;
            }
        }
        return null;
    }


    private Antwort buildAntwort(Cell cell, AntwortSlot slot, AntwortSlot richtigeAntwortSlot, String richtigeAntwortText) {

        String antwortText = getStringCellValue(cell);
        if (StringUtils.isBlank(antwortText)) {
            throw new FragenKatalogLoaderException("Die Zelle in Zeile#" + cell.getRowIndex() + " und Spalte#" + cell.getColumnIndex() + " hat keinen Wert");
        }


        boolean isRichtigeAntwort = false;
        if (richtigeAntwortSlot != null) {
            isRichtigeAntwort = slot == richtigeAntwortSlot;
        } else {
            isRichtigeAntwort = antwortText.equalsIgnoreCase(richtigeAntwortText);
        }

        Antwort antwort = new Antwort();
        antwort.setSlot(slot);
        antwort.setText(antwortText);
        antwort.setRichtig(isRichtigeAntwort);

        return antwort;
    }

    private String getStringCellValue(Cell cell) {
        final String value;
        if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
            value = cell.getStringCellValue();
        } else {
            value = formatter.formatCellValue(cell);
        }
        return value != null ? value.trim() : null;
    }


    private AntwortSlot determineAntwortSlot(String antwortText) {
        char antwortSpalte = antwortText.charAt(0);
        switch (antwortSpalte) {
            case 'B':
                return AntwortSlot.A;
            case 'C':
                return AntwortSlot.B;
            case 'D':
                return AntwortSlot.C;
            case 'E':
                return AntwortSlot.D;
        }
        return null;
    }


    private Cell getCell(Row row, int index) {
        Cell cell = row.getCell(index);
        if (cell == null) {
            throw new FragenKatalogLoaderException("Es gibt keine Zelle fuer Zeile#" + row.getRowNum() + " und Spalte#" + index + " in Sheet '" + row.getSheet().getSheetName() + "'");
        }
        return cell;
    }
}
