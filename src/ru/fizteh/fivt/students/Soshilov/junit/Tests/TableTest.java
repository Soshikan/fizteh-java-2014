package ru.fizteh.fivt.students.Soshilov.junit.Tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.fizteh.fivt.students.Soshilov.junit.DataBaseTable;
import ru.fizteh.fivt.students.Soshilov.junit.DataBaseTableProvider;
import ru.fizteh.fivt.students.Soshilov.junit.DataBaseTableProviderFactory;
import ru.fizteh.fivt.students.Soshilov.junit.Shell;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created with IntelliJ IDEA.
 * User: soshikan
 * Date: 07 December 2014
 * Time: 22:21
 */
public final class TableTest {
    Path dbPath;
    DataBaseTableProviderFactory factory = new DataBaseTableProviderFactory();
    DataBaseTableProvider provider;

    @Before
    public void init() {

        dbPath = Paths.get("").resolve(System.getProperty("user.dir"));

        try {
            dbPath = Files.createTempDirectory(dbPath, "test");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }

        provider = (DataBaseTableProvider) factory.create(dbPath.toString());

    }


    @Test
    public void testGetName() {


        try {
            String name = "ABC";
            DataBaseTable table = (DataBaseTable) provider.createTable(name);
            assertEquals(name, table.getName());

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.exit(1);
        }

    }

    @Test
    public void testPut() {

        DataBaseTable table = (DataBaseTable) provider.createTable("justTable");

        String smth = "smth";

        try {
            table.put(null, smth);
            assertTrue(false);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }

        try {
            table.put("", smth);
            assertTrue(false);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }

        try {
            table.put(smth, null);
            assertTrue(false);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }


        assertEquals(table.put("key1", "val1"), null);
        assertEquals(table.put("key2", "val2"), null);
        table.commit();

        table.remove("key1");
        assertEquals(table.put("key1", "val1_"), null);

        assertEquals(table.put("key1", "val1__"), "val1_");
        assertEquals(table.put("key1", "val1"), "val1__");

        assertEquals(table.put("key1", "val1_"), "val1");

    }



    @Test
    public void testGet() {

        DataBaseTable table = (DataBaseTable) provider.createTable("justTable");

        try {
            table.get(null);
            assertTrue(false);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }

        try {
            table.get("");
            assertTrue(false);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }

        table.put("key1", "val1");
        table.put("key2", "val2");
        table.commit();

        table.put("key3", "val3");
        assertEquals(table.get("key3"), "val3");

        table.put("key2", "val2_");
        assertEquals(table.get("key2"), "val2_");

        assertEquals(table.get("key1"), "val1");

        assertEquals(table.get("keyX"), null);

    }


    @Test
    public void testRemove() {

        DataBaseTable table = (DataBaseTable) provider.createTable("justTable");

        try {
            table.remove(null);
            assertTrue(false);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }

        try {
            table.remove("");
            assertTrue(false);
        } catch (IllegalArgumentException ex) {
            System.out.println(ex.getMessage());
        }

        table.put("key1", "val1");
        table.put("key2", "val2");
        table.commit();

        table.put("key3", "val3");
        assertEquals(table.remove("key3"), "val3");

        table.put("key2", "val2_");
        assertEquals(table.remove("key2"), "val2_");

        assertEquals(table.remove("key2"), null);

        assertEquals(table.remove("key1"), "val1");

        assertEquals(table.remove("keyX"), null);

    }

    @Test
    public void testList() {

        DataBaseTable table = (DataBaseTable) provider.createTable("justTable");

        List<String> list = new LinkedList<>();
        list.add("key1");
        list.add("key2");
        list.add("key3");
        list.add("key4");
        table.put("key1", "val1");
        table.put("key2", "val2");
        table.put("key3", "val3");
        table.put("key4", "val4");

        assertEquals(list, table.list());
    }



    @Test
    public void testCombined() {

        Map<String, String> map = new HashMap<>();
        DataBaseTable table = (DataBaseTable) provider.createTable("table");

        int size = 1000;
        for (int i = 0; i < size; i++) {
            String key = UUID.randomUUID().toString();
            String value = UUID.randomUUID().toString();
            map.put(key, value);
            table.put(key, value);
            if (i % 100 == 0) {
                table.commit();
            }
        }

        for (HashMap.Entry<String, String> entry: map.entrySet()) {

            String key = entry.getKey();
            String value = UUID.randomUUID().toString();
            map.put(key, value);
            table.put(key, value);

            String val1 = map.get(key);
            String val2 = table.get(key);
            assertEquals(val1, val2);

            if (key.hashCode() % 2 == 0) {
                table.commit();
            }

        }


        int curSize = size;
        for (HashMap.Entry<String, String> entry: map.entrySet()) {

            String key = entry.getKey();
            String value = entry.getValue();
            String expValue = table.get(key);

            assertNotNull(expValue);
            assertEquals(expValue, value);
            assertEquals(table.size(), curSize--);
            table.remove(key);

        }

        assertTrue(table.size() == 0);

    }


    @Test
    public void testCommitRollback() {

        DataBaseTable table = (DataBaseTable) provider.createTable("table");
        table.put("key1", "val1");
        table.put("key2", "val2");

        table.commit();
        table.rollback();
        assertEquals(table.size(), 2);

        table.put("key3", "val3");
        table.put("key4", "val4");

        assertEquals(table.put("key1", "newVal1"), "val1");
        assertEquals(table.put("key2", "newVal2"), "val2");

        table.rollback();
        assertEquals(table.size(), 2);

    }

    @After
    public void finish() {

        Shell.delete(dbPath);

    }
}
