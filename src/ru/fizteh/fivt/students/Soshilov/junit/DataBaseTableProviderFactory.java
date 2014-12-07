package ru.fizteh.fivt.students.Soshilov.junit;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created with IntelliJ IDEA.
 * User: soshikan
 * Date: 07 December 2014
 * Time: 21:34
 */
public class DataBaseTableProviderFactory {
    /**
     * Возвращает объект для работы с базой данных.
     *
     * @param dir Директория с файлами базы данных.
     * @return Объект для работы с базой данных.
     * @throws IllegalArgumentException Если значение директории null или имеет недопустимое значение.
     */
    public TableProvider create(String dir) throws IllegalArgumentException {

        if (dir == null) {
            throw new IllegalArgumentException("Database '" + dir + "': null path");
        }

        if (dir.contains("\000")) {
            throw new IllegalArgumentException("Database '" + dir + "': unacceptable path");
        }

        Path path = Paths.get(System.getProperty("user.dir")).resolve(dir);

        if (!Files.exists(path)) {
            try {
                Files.createDirectory(path);
            } catch (IOException ex) {
                throw new IllegalArgumentException("Database '" + dir + "': invalid path");
            }
        } else if (!Files.isDirectory(path)) {
            throw new IllegalArgumentException("Database '" + dir + "': invalid path");
        }

        return new DataBaseTableProvider(path);
    }
}
