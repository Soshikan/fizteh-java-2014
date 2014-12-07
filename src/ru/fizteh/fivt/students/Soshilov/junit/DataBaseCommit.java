package ru.fizteh.fivt.students.Soshilov.junit;

/**
 * Created with IntelliJ IDEA.
 * User: soshikan
 * Date: 07 December 2014
 * Time: 22:06
 */
public class DataBaseCommit implements Command {
    /**
     * Commit changes.
     * @param args Commands that were entered.
     * @param db Our main table.
     * @throws CommandException Error in wrong arguments count.
     */
    @Override
    public void execute(final String[] args, DataBaseTableProvider db) throws CommandException {
        final int argumentsCount = 0;
        Main.checkArguments("create", args.length, argumentsCount);

        DataBaseTable table = db.getCurrentTable();

        System.out.println(table.commit());

    }
}