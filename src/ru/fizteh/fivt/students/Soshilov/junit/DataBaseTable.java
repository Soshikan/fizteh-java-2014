package ru.fizteh.fivt.students.Soshilov.junit;

import ru.fizteh.fivt.students.Soshilov.junit.storage.Table;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: soshikan
 * Date: 07 December 2014
 * Time: 19:28
 */
public final class DataBaseTable  implements Table {
    /**
     * Data that was commited.
     */
    private Map<String, String> commitedData = new HashMap<>();
    /**
     * Data that was added (add new pair of key and value) after last commit.
     */
    private Map<String, String> addedData = new HashMap<>();
    /**
     * Data that was removed (remove existed pair of key and value) after last commit.
     */
    private Map<String, String> removedData = new HashMap<>();
    /**
     * Data that was changed (change value of existed key) after last commit.
     */
    private Map<String, String> changedData = new HashMap<>();
    /**
     * The name of table.
     */
    private String tableName;
    /**
     * A path to the table.
     */
    private Path tablePath;
    /**
     * Quantity of directories and files in each directory.
     */
    private static final int F_AND_DIR_COUNT = 16;

    /**
     * Constructor which puts path to variables.
     * @param path A path to the table.
     */
    public DataBaseTable(final Path path) {
        tablePath = path;
        tableName = path.getFileName().toString();
    }

    /**
     * Возвращает название базы данных.
     */
    @Override
    public String getName() {
        return tableName;
    }

    /**
     * Получает значение по указанному ключу.
     *
     * @param key Ключ.
     * @return Значение. Если не найдено, возвращает null.
     *
     * @throws IllegalArgumentException Если значение параметра key является null.
     */
    @Override
    public String get(final String key) throws IllegalArgumentException {

        if (key == null) {
            throw new IllegalArgumentException("Table '" + tableName + "': get: null key");
        }

        if (key.isEmpty()) {
            throw new IllegalArgumentException("Table '" + tableName + "': get: empty key");
        }


        String value = addedData.get(key);
        if (value != null) {
            return value;
        }

        value = changedData.get(key);
        if (value != null) {
            return value;
        }

        value = commitedData.get(key);
        if (value != null) {
            return value;
        }

        return null;
    }

    /**
     * Устанавливает значение по указанному ключу.
     *
     * @param key Ключ.
     * @param value Значение.
     * @return Значение, которое было записано по этому ключу ранее. Если ранее значения не было записано,
     * возвращает null.
     *
     * @throws IllegalArgumentException Если значение параметров key или value является null.
     */
    @Override
    public String put(final String key, final String value) {

        if (key == null) {
            throw new IllegalArgumentException("Table '" + tableName + "': put: null key");
        }

        if (value == null) {
            throw new IllegalArgumentException("Table '" + tableName + "': put: null value");
        }

        if (key.isEmpty()) {
            throw new IllegalArgumentException("Table '" + tableName + "': put: empty key");
        }

        if (commitedData.containsKey(key)) {

            if (removedData.containsKey(key)) {
                removedData.remove(key);
                if (!commitedData.get(key).equals(value)) {
                    changedData.put(key, value);
                }
                return null;
            } else if (changedData.containsKey(key)) {

                if (commitedData.get(key).equals(value)) {
                    return changedData.remove(key);
                } else {
                    return changedData.put(key, value);
                }
            } else {

                changedData.put(key, value);
                return commitedData.get(key);

            }

        } else {
            return addedData.put(key, value);
        }

    }

    /**
     * Удаляет значение по указанному ключу.
     *
     * @param key Ключ.
     * @return Значение. Если не найдено, возвращает null.
     *
     * @throws IllegalArgumentException Если значение параметра key является null.
     */
    @Override
    public String remove(final String key) {

        if (key == null) {
            throw new IllegalArgumentException("Table '" + tableName + "': remove: null key");
        }

        if (key.isEmpty()) {
            throw new IllegalArgumentException("Table '" + tableName + "': remove: empty key");
        }

        if (addedData.containsKey(key)) {
            return addedData.remove(key);
        }

        if (changedData.containsKey(key)) {
            String value = changedData.get(key);
            changedData.remove(key);
            removedData.put(key, value);
            return value;
        }

        if (removedData.containsKey(key)) {
            return null;
        }

        if (commitedData.containsKey(key)) {
            String value = commitedData.get(key);
            removedData.put(key, value);
            return value;
        }

        return null;
    }

    /**
     * Возвращает количество ключей в таблице.
     *
     * @return Количество ключей в таблице.
     */
    @Override
    public int size() {
        return commitedData.size() - removedData.size() + addedData.size();
    }

    /**
     * Выполняет фиксацию изменений.
     *
     * @return Количество сохранённых ключей.
     */
    @Override
    public int commit() {

        commitedData.keySet().removeAll(removedData.keySet());
        commitedData.putAll(addedData);
        commitedData.putAll(changedData);

        int changedKeys = changedNum();
        addedData.clear();
        removedData.clear();
        changedData.clear();

        return changedKeys;
    }

    /**
     * Quantity of pair of key and value that were changed somehow.
     * @return Quantity.
     */
    public int changedNum() {
        return addedData.size() + removedData.size() + changedData.size();
    }

    /**
     * Выполняет откат изменений с момента последней фиксации.
     *
     * @return Количество отменённых ключей.
     */
    @Override
    public int rollback() {

        int changedKeys = changedNum();
        addedData.clear();
        removedData.clear();
        changedData.clear();

        return changedKeys;
    }

    /**
     * Выводит список ключей таблицы.
     *
     * @return Список ключей.
     */
    @Override
    public List<String> list() {

        List<String> keyList = new LinkedList<>();
        keyList.addAll(commitedData.keySet());
        keyList.addAll(addedData.keySet());

        return keyList;
    }

    /**
     * Reading key and value from file and putting it into a HashMap.
     * @param filePath The path to file read from.
     * @param dir Expected dir name.
     * @param file Expected file name.
     * @throws IOException All exceptions than could be thrown by different problems with reading from file.
     */
    private void readKeyAndValue(final Path filePath, final int dir, final int file) throws IOException {
        //If File(filePath) does not exist, get away from here.
        if (Files.exists(filePath)) {
            try (DataInputStream inputStream = new DataInputStream(Files.newInputStream(filePath))) {
                if (inputStream.available() == 0) {
                    throw new IOException("file '" + filePath + "' is empty");
                }

                while (inputStream.available() > 0) {
                    //available from FilterInputStream Returns an estimate of the number of bytes that can be read.
                    int keyLength = inputStream.readInt();
                    //readInt from DataInput Reads four input bytes and returns an int value.

                    if (inputStream.available() < keyLength) {
                        throw new IOException("wrong key size");
                    }

                    byte[] keyInBytes = new byte[keyLength];
                    inputStream.read(keyInBytes, 0, keyLength);
                    //read from FilterInputStream Read from keyInBytes[0] to keyInBytes[keyLength]

                    int valueLength = inputStream.readInt();
                    if (inputStream.available() < valueLength) {
                        throw new IOException("wrong value size");
                    }
                    byte[] valueInBytes = new byte[valueLength];
                    inputStream.read(valueInBytes, 0, valueLength);

                    String keyString = new String(keyInBytes, "UTF-8");
                    String valueString = new String(valueInBytes, "UTF-8");

                    int hashValue = keyString.hashCode();
                    if (hashValue % F_AND_DIR_COUNT != dir || hashValue / F_AND_DIR_COUNT % F_AND_DIR_COUNT != file) {
                        throw new IOException("wrong key file");
                    }

                    put(keyString, valueString);
                }

            } catch (IOException e) {
                throw new IOException("cannot read from file");
            }
        }
    }

    /**
     * Reading and filling the HashMap from every file.
     * @throws IOException All exceptions than could be thrown from previous method readKeyAndValue.
     */
    public void read() throws IOException {
        commitedData.clear();
        //first clear the HashMap
        for (int dir = 0; dir < F_AND_DIR_COUNT; ++dir) {
            for (int file = 0; file < F_AND_DIR_COUNT; ++file) {
                Path filePath = tablePath.resolve(dir + ".dir").resolve(file + ".dat");
                try {
                    readKeyAndValue(filePath, dir, file);
                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                    System.exit(1);
                }
            }
        }
    }


    /**
     * Whiting key and value to a file.
     * @param filePath A path to a File to write to.
     * @param keyStr A String presentment of key.
     * @param valueStr A String presentment of value.
     * @throws IOException All exceptions than could be writing to file.
     */
    private void writeKeyAndValue(final Path filePath, final String keyStr, final String valueStr) throws IOException {
        try (DataOutputStream outputStream = new DataOutputStream(Files.newOutputStream(filePath))) {
            byte[] keyInBytes = keyStr.getBytes("UTF-8");
            byte[] valueInBytes = valueStr.getBytes("UTF-8");
            outputStream.writeInt(keyInBytes.length);
            //writeInt from DataOutput Writes an keyInBytes.length to the outputStream as four bytes, high byte first.
            outputStream.write(keyInBytes);
            //write from FilterOutputStream Writes keyInBytes.length bytes to this output stream.
            outputStream.writeInt(valueInBytes.length);
            outputStream.write(valueInBytes);
        } catch (IOException ex) {
            throw new IOException("cannot read from file");
        }
    }

    /**
     * Write the content of HashMap (by using Nested Class).
     * @throws IOException All exceptions than could be thrown from previous method writeKeyAndValue.
     */
    public void write() throws IOException {
        if (Files.exists(tablePath)) {
            throw new IOException("directory exists: an empty directory must not exist");
        } else {
            try {
                Files.createDirectory(tablePath);
            } catch (IOException ex) {
                throw new IOException("cannot create a directory" + tablePath.toString());
            }
        }

        for (HashMap.Entry<String, String> entry : commitedData.entrySet()) {
            //Entry from Map (only Java 8, Nested Class of Map) - A map entry (key-value pair)
            //The Map.entrySet method returns a collection-view of the map, whose elements are of this class.
            String keyInString = entry.getKey();
            String valueInString = entry.getValue();
            int hashCode = keyInString.hashCode();
            int dir = hashCode % F_AND_DIR_COUNT;
            int file = hashCode / F_AND_DIR_COUNT % F_AND_DIR_COUNT;

            Path dirPath = tablePath.resolve(dir + ".dir");
            Path filePath = dirPath.resolve(file + ".dat");

            if (!Files.exists(dirPath)) {
                try {
                    Files.createDirectory(dirPath);
                } catch (IOException ex) {
                    throw new IOException("cannot create '" + dirPath + "'");
                }
            }
            if (!Files.exists(filePath)) {
                try {
                    Files.createFile(filePath);
                } catch (IOException ex) {
                    throw new IOException("cannot create '" + filePath + "'");
                }
            }
            writeKeyAndValue(filePath, keyInString, valueInString);
        }
    }
}
