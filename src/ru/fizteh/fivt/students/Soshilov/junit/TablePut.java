package ru.fizteh.fivt.students.Soshilov.junit;

/**
 * Created with IntelliJ IDEA.
 * User: soshikan
 * Date: 23 October 2014
 * Time: 1:16
 */
public class TablePut implements Command {
    /**
     * Put a key + value into a table.
     * @param args Commands that were entered.
     * @param db Our main table.
     * @throws CommandException Error in wrong arguments count.
     */
    @Override
    public void execute(final String[] args, DataBaseTableProvider db) throws CommandException {
        final int argumentsCount = 2;
        Main.checkArguments("put", args.length, argumentsCount);

        DataBaseTable table = db.getCurrentTable();
        if (table == null) {
            System.out.println("no table");
            return;
        }

        String key = args[0];
        String value = args[1];

        if (table.put(key, value) != null) {
            System.out.println("overwrite");
        } else {
            System.out.println("new");

        }
    }
}
