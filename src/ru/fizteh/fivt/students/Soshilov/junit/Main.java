package ru.fizteh.fivt.students.Soshilov.junit;

/**
 * Created with IntelliJ IDEA.
 * User: soshikan
 * Date: 22 October 2014
 * Time: 22:27
 */
public final class Main {
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
     * @throws CommandException Problems with arguments.
     */
    public static void checkArguments(final String command, final int length, final int requiredLength)
            throws CommandException {
        if (length != requiredLength) {
            if (length < requiredLength) {
                throw new CommandException(command + ": not enough arguments");
            } else if (length > requiredLength) {
                throw new CommandException(command + ": too many arguments");
            }
        }
    }

    /**
     * Check whether the database is used now for working or not.
     * @param db A database we want to use.
     */
    public static void checkDataBaseExisting(final DataBaseTableProvider db) {
        DataBaseTable table = db.getCurrentTable();
        if (table == null) {
            System.out.println("no table");
            return;
        }
    }

    /**
     * Removes name of function in arguments.
     * @param args Arguments that were entered.
     * @param wordsInName Quantity of words that are command.
     * @return Array of strings without commands.
     */
    public static String[] removeNameInArguments(final String[] args, final int wordsInName) {
        String[] result = new String[args.length - wordsInName];
        System.arraycopy(args, wordsInName, result, 0, args.length - wordsInName);
        return result;
    }

    /**
     * Parse commands in definite way to use after it.
     * @param commands Entered data.
     * @return Array of strings which is defined by every argument.
     */
    public static String[] parsingCommands(final String[] commands) {
        StringBuilder builder = new StringBuilder();
        for (String s : commands) {
            builder.append(s).append(" ");
        }
        String string = new String(builder);
        return string.split("\\s*;\\s*");
    }
}
