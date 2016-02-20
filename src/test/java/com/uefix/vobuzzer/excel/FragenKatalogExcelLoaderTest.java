package com.uefix.vobuzzer.excel;

import com.uefix.vobuzzer.model.Frage;
import com.uefix.vobuzzer.model.FrageId;
import com.uefix.vobuzzer.model.FragenKatalog;
import com.uefix.vobuzzer.model.FragenKategorie;
import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Test;

import java.io.InputStream;

import static org.junit.Assert.*;

/**
 * Created by Uefix on 20.02.2016.
 */
public class FragenKatalogExcelLoaderTest {

    private FragenKatalogExcelLoader loader = new FragenKatalogExcelLoader();

    private InputStream testInputStream;

    @After
    public void tearDown() {
        IOUtils.closeQuietly(testInputStream);
    }

    @Test
    public void loadFragen_ersteAllgmeineFrage_liefertErsteAllgmeineFrage() throws Exception {
        testInputStream = FragenKatalogExcelLoader.class.getClassLoader().getResourceAsStream("Fragenkatalog.xlsx");
        assertNotNull(testInputStream);

        FragenKatalog actualKatalog = loader.loadFragen(testInputStream, FragenKategorie.ALLGEMEIN);
        assertNotNull(actualKatalog);
        assertFalse(actualKatalog.getFragen().isEmpty());

        Frage actualFrage2Allgmein = actualKatalog.getFrage(new FrageId(2, FragenKategorie.ALLGEMEIN));
        assertNotNull(actualFrage2Allgmein);
    }
}