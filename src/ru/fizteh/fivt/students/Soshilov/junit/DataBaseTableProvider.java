package ru.fizteh.fivt.students.Soshilov.junit;

import ru.fizteh.fivt.students.Soshilov.junit.storage.Table;
import ru.fizteh.fivt.students.Soshilov.junit.storage.TableProvider;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: soshikan
 * Date: 07 December 2014
 * Time: 20:58
 */
public final class DataBaseTableProvider implements TableProvider {
    /**
     * Tables: name + variable of Table class.
     */
    private Map<String, DataBaseTable> tables = new HashMap<>();
    /**
     * The table we are working on now.
     */
    private DataBaseTable currentTable = null;
    /**
     * Variable of Path type - a path to the main directory.
     */
    private Path dbPath;

    /**
     * Return current table.
     * @return current table.
     */
    public DataBaseTable getCurrentTable() {
        return currentTable;
    }

    /**
     * Set current table.
     * @param name A name of table that we want to be ours current.
     */
    public void setCurrentTable(final String name) {
        if (!tables.containsKey(name)) {
            System.out.println(name + " not exists");
            return;
        }

        if (currentTable != null && currentTable.changedNum() > 0) {
            System.out.println(currentTable.changedNum() + " unsaved changes");
            return;
        }

        currentTable = tables.get(name);
        System.out.println("using " + name);
    }

    /**
     * Возвращает таблицу с указанным названием.
     *
     * @param name Название таблицы.
     * @return Объект, представляющий таблицу. Если таблицы с указанным именем не существует, возвращает null.
     * @throws IllegalArgumentException Если название таблицы null или имеет недопустимое значение.
     */
    @Override
    public Table getTable(final String name) {
        if (name == null) {
            throw new IllegalArgumentException("Database '" + dbPath + "': getTable: null table name");
        }

        if (name.isEmpty()) {
            throw new IllegalArgumentException("Database '" + dbPath + "': getTable: empty table name");
        }

        String fileName = Paths.get("").resolve(name).getFileName().toString();

        if (!Shell.checkName(fileName)) {
            throw new IllegalArgumentException("Database '" + fileName + "': getTable: unacceptable table name");
        }


        return tables.get(name);
    }

    /**
     * Создаёт таблицу с указанным названием.
     *
     * @param name Название таблицы.
     * @return Объект, представляющий таблицу. Если таблица уже существует, возвращает null.
     * @throws IllegalArgumentException Если название таблицы null или имеет недопустимое значение.
     */
    public Table createTable(final String name) {

        if (name == null) {
            throw new IllegalArgumentException("Database '" + dbPath + "': createTable: null table name");
        }

        if (name.isEmpty()) {
            throw new IllegalArgumentException("Database '" + dbPath + "': createTable:  table name");
        }

        String fileName = Paths.get("").resolve(name).getFileName().toString();

        if (!Shell.checkName(fileName)) {
            throw new IllegalArgumentException("Database '" + fileName + "': createTable: unacceptable table name");
        }

        if (tables.containsKey(name)) {
            return null;
        } else {
            DataBaseTable table = new DataBaseTable(dbPath.resolve(name));
            tables.put(name, table);
            return table;
        }

    }

    /**
     * Удаляет таблицу с указанным названием.
     *
     * @param name Название таблицы.
     * @throws IllegalArgumentException Если название таблицы null или имеет недопустимое значение.
     * @throws IllegalStateException Если таблицы с указанным названием не существует.
     */
    public void removeTable(final String name) {
        if (name == null) {
            throw new IllegalArgumentException("Database '" + dbPath + "': removeTable: null Table name");
        }

        if (name.isEmpty()) {
            throw new IllegalArgumentException("Database '" + dbPath + "': removeTable: empty Table name");
        }

        String fileName = Paths.get("").resolve(name).getFileName().toString();

        if (!Shell.checkName(fileName)) {
            throw new IllegalArgumentException("Database '" + fileName + "': removeTable: unacceptable table name");
        }

        if (!tables.containsKey(name)) {
            throw new IllegalStateException("Database '" + dbPath + "': removeTable: table does not exist");
        }

        tables.remove(name);
    }


    /**
     * Show every table and its' elements' quantity.
     */
    public void showTableSet() {

        System.out.println("table_name row_count");
        for (HashMap.Entry<String, DataBaseTable> entry: tables.entrySet()) {
            DataBaseTable table = entry.getValue();
            System.out.println(table.getName() + " " + table.size());
        }

    }

    /**
     * Check whether the exit is possible: all data was commited or rollback.
     * @return True if we can exit, false otherwise.
     */
    public boolean isExitPossible() {

        for (HashMap.Entry<String, DataBaseTable> entry: tables.entrySet()) {
            DataBaseTable table = entry.getValue();
            if (table.changedNum() != 0) {
                return false;
            }
        }

        return true;
    }

    /**
     * OConstructor which assigns a dbPath field and makes a HashMap tables. Then we start reading.
     * @param inpPath The Path to database.
     */
    public DataBaseTableProvider(final Path inpPath) {
        dbPath = inpPath;
        try {
            read();
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
            System.exit(1);
        }
    }

    /**
     * Reading from database (it contains 16 directories, each contains 16 files), by using read from class Table.
     * @throws IOException Exception In case if we can not read from a table.
     * @throws IllegalArgumentException Exception In case if we can not read from a table.
     */
    public void read() throws IOException, IllegalArgumentException {
        if (dbPath == null) {
            throw new IllegalArgumentException("no database path");
        } else if (!Files.exists(dbPath)) {
            throw new IllegalArgumentException("wrong database path: '" + dbPath.toString() + "'");
        }
        File currentDirectory = new File(dbPath.toString());
        File[] content = currentDirectory.listFiles();
        if (content != null) {
            for (File item: content) {
                if (Files.isDirectory(item.toPath())) {
                    DataBaseTable table = new DataBaseTable(item.toPath());
                    table.read();
                    tables.put(item.getName(), table);
                }
            }
        }
        //Else directory is empty.
        currentTable = null;
    }

    /**
     * Writes to database from tables, by using write from class Table.
     * @throws IOException In case if we can not write to a table.
     */
    public void write() throws IOException {
        Shell.deleteContent(dbPath);

        for (HashMap.Entry<String, DataBaseTable> entry: tables.entrySet()) {
            entry.getValue().write();
        }

    }
}
