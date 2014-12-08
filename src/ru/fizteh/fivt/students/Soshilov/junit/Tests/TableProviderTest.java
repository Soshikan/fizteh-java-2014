package ru.fizteh.fivt.students.Soshilov.junit.Tests;

import org.junit.Test;
import ru.fizteh.fivt.students.Soshilov.junit.DataBaseTableProvider;
import ru.fizteh.fivt.students.Soshilov.junit.DataBaseTableProviderFactory;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: soshikan
 * Date: 07 December 2014
 * Time: 22:36
 */
public class TableProviderTest {
    DataBaseTableProviderFactory factory = new DataBaseTableProviderFactory();

    @Test
    public void testCreateTable() {

        DataBaseTableProvider provider = (DataBaseTableProvider) factory.create("db");

        try {
            provider.createTable(null);
            assertTrue(false);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        assertNotNull(provider.createTable("Moscow"));
        assertNull(provider.createTable("Moscow"));

    }

    @Test
    public void testRemoveTable() {

        DataBaseTableProvider provider = (DataBaseTableProvider) factory.create("db");

        try {
            provider.removeTable(null);
            assertTrue(false);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        try {
            provider.removeTable("NonExistingTableName");
            assertTrue(false);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }


    @Test
    public void testGetTable() {

        DataBaseTableProvider provider = (DataBaseTableProvider) factory.create("db");

        try {
            provider.getTable(null);
            assertTrue(false);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        String name = "Table1";
        provider.createTable(name);
        assertNotNull(provider.getTable(name));
    }

    @Test
    public void testCombined() {

        DataBaseTableProvider provider = (DataBaseTableProvider) factory.create("db");
        ArrayList<String> names = new ArrayList<>();
        int size = 100;
        for (int i = 0; i < size; i++) {
            String name = UUID.randomUUID().toString();
            provider.createTable(name);
            names.add(name);
        }

        for (String name: names) {

            try {
                assertNotNull(provider.getTable(name));
                provider.removeTable(name);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
                assertTrue(false);
            }
        }

    }
}
