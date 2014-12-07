package ru.fizteh.fivt.students.Soshilov.junit;

/**
 * Created with IntelliJ IDEA.
 * User: soshikan
 * Date: 23 October 2014
 * Time: 1:40
 */
public class TableRemove implements Command {
    /**
     * Remove key + value by getting the key as argument.
     * @param args Commands that were entered.
     * @param db Our main table.
     * @throws CommandException Error in wrong arguments count.
     */
    @Override
    public void execute(final String[] args, DataBaseTableProvider db) throws CommandException {
        final int argumentsCount = 1;
        Main.checkArguments("remove", args.length, argumentsCount);

        DataBaseTable table = db.getCurrentTable();
        if (table == null) {
            System.out.println("no table");
            return;
        }

        String key = args[0];
        String value = table.remove(key);

        if (value == null) {
            System.out.println("not found");
        } else {
            System.out.println("removed");
        }

    }
}
