package ru.fizteh.fivt.students.Soshilov.junit;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * Created with IntelliJ IDEA.
 * User: soshikan
 * Date: 07 December 2014
 * Time: 21:01
 */
public class Shell {
    /**
     * Check whether name of a dir or a of file is acceptable.
     * @param name The name of a dir or of a file.
     * @return True if acceptable, false if is not.
     */
    public static boolean checkName(String name) {

        return (!name.contains("\\") && !name.contains("/000"));

    }

    /**
     * Recursive deletion of directories (we consider them empty because we have read them).
     * @param path A Path to a directory, which should be deleted.
     */
    private static void deleteDirectory(final Path path) throws RuntimeException {
        File currentDirectory = new File(path.toString());
        File[] content = currentDirectory.listFiles();

        if (content != null) {
            for (File item: content) {
                if (item.isFile()) {
                    try {
                        Files.delete(item.toPath());
                    } catch (IOException ex) {
                        throw new RuntimeException("cannot delete file '" + item.toPath().toString() + "'");
                    }
                } else {
                    deleteDirectory(item.toPath());
                }
            }
        }

        try {
            Files.delete(path);
        } catch (IOException ex) {
            throw new RuntimeException("cannot delete '" + path.toString() + "'");
        }

    }

    /**
     * First we delete the database content.
     * If dbPath points at not a directory, we delete it. Else use next function.
     * @param dbPath A path to file or a directory.
     * @throws RuntimeException In case if we can not delete a file by its' path.
     */
    public static void delete(Path dbPath) throws RuntimeException {
        if (!Files.isDirectory(dbPath)) {
            try {
                Files.delete(dbPath);
            } catch (IOException ex) {
                throw new RuntimeException("cannot delete '" + dbPath.toString() + "'");
            }
        } else {
            deleteDirectory(dbPath);
        }
    }
}
