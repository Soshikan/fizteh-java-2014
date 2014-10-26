package ru.fizteh.fivt.students.Soshilov.MultiFileHashMap;

/**
 * Created with IntelliJ IDEA.
 * User: soshikan
 * Date: 22 October 2014
 * Time: 22:27
 */
public class Main {
    /**
     * Run the whole program.
     * @param args Arguments that were entered.
     */
    public static void main(final String[] args) {
        //System.setProperty("db.file",
        //        "/home/soshikan/IdeaProjects/FileMap/src/ru/fizteh/fivt/students/Soshilov/FileMap/db.file");
        MapRun.main(args);
    }

    /**
     * Checking whether the arguments have normal count.
     * @param command THe command we examine.
     * @param length The count of arguments.
     * @param requiredLength The correct count.
     */
    public static void checkArguments(final String command, final int length, final int requiredLength) {
        if (length != requiredLength) {
            System.err.println(command + ": " + (length < requiredLength ? "not enough" : "too many") + " arguments");
            System.exit(1);
        }
    }
}