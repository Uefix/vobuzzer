package com.uefix.vobuzzer.excel;

import com.uefix.vobuzzer.exception.FragenKatalogLoaderException;
import com.uefix.vobuzzer.model.Frage;
import com.uefix.vobuzzer.model.FrageId;
import com.uefix.vobuzzer.model.FragenKatalog;
import com.uefix.vobuzzer.model.FragenKategorie;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.io.InputStream;

import static org.junit.Assert.*;

/**
 * Created by Uefix on 20.02.2016.
 */
public class FragenKatalogExcelLoaderTest {

    public static final Logger LOG = Logger.getLogger(FragenKatalogExcelLoaderTest.class);

    private FragenKatalogExcelLoader loader = new FragenKatalogExcelLoader();

    private InputStream testInputStream;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setup() {
        testInputStream = FragenKatalogExcelLoader.class.getClassLoader().getResourceAsStream("Fragenkatalog_mit_Fehlern.xlsx");
        assertNotNull(testInputStream);
    }

    @After
    public void tearDown() {
        IOUtils.closeQuietly(testInputStream);
    }

    @Test
    public void loadFragen_ersteAllgmeineFrage_liefertErsteAllgmeineFrage() throws Exception {
        FragenKatalog actualKatalog = loader.loadFragen(testInputStream, FragenKategorie.ALLGEMEIN);
        assertNotNull(actualKatalog);
        assertFalse(actualKatalog.getFragen().isEmpty());

        Frage actualFrage = actualKatalog.getFrage(new FrageId(1, FragenKategorie.ALLGEMEIN));
        assertNotNull(actualFrage);

        LOG.debug(actualFrage);
    }


    @Test
    public void loadFragen_jugend_wirftErwarteteException() throws Exception {
        expectedException.expect(FragenKatalogLoaderException.class);
        expectedException.expectMessage("Unerwarteter Header in Sheet 'Jugend' (erste Zelle ist nicht 'Frage')");

        loader.loadFragen(testInputStream, FragenKategorie.JUGEND);
    }


    @Test
    public void findSheet_allgmein_liefertAllgemeinSheet() throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook(testInputStream);

        XSSFSheet actualSheet = loader.findSheet(workbook, FragenKategorie.ALLGEMEIN);
        assertNotNull(actualSheet);
        assertEquals("Allgemein", actualSheet.getSheetName());
        assertEquals(49, actualSheet.getLastRowNum());
    }


    @Test
    public void findSheet_gs32_liefertAllgemeinSheet() throws Exception {
        XSSFWorkbook workbook = new XSSFWorkbook(testInputStream);

        XSSFSheet actualSheet = loader.findSheet(workbook, FragenKategorie.GS32);
        assertNotNull(actualSheet);
        assertEquals("GS32", actualSheet.getSheetName());
        assertEquals(29, actualSheet.getLastRowNum());
    }
}