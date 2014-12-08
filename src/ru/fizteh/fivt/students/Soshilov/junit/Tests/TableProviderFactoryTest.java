package ru.fizteh.fivt.students.Soshilov.junit.Tests;

import ru.fizteh.fivt.students.Soshilov.junit.Shell;
import ru.fizteh.fivt.students.Soshilov.junit.DataBaseTableProviderFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

/**
 * Created with IntelliJ IDEA.
 * User: soshikan
 * Date: 07 December 2014
 * Time: 22:36
 */
public class TableProviderFactoryTest {
    Path testDir;

    @Before
    public void init() {

        Path tempPath = Paths.get("").resolve(System.getProperty("user.dir"));

        try {
            testDir = Files.createTempDirectory(tempPath, "temp");
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }

    @Test
    public void testCreateExisting() {

        DataBaseTableProviderFactory factory = new DataBaseTableProviderFactory();

        String name = "db1";

        Path path = testDir.resolve(name);

        try {
            Files.createDirectory(path);
        } catch (IOException ex) {
            System.out.println("Directory was not created");
        }

        try {
            assertNotNull(factory.create(name));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.exit(1);
        }

    }

    @Test
    public void testCreateNotExisting() {

        DataBaseTableProviderFactory factory = new DataBaseTableProviderFactory();

        String name = "non.existing.directory";

        Path path = testDir.resolve(name);

        while (Files.exists(path)) {
            name += "/1";
            path = testDir.resolve(name);
        }

        try {
            assertNotNull(factory.create(name));
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            System.exit(1);
        }

    }

    @After
    public void finish() {
        Shell.delete(testDir);
    }
}
