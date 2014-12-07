package ru.fizteh.fivt.students.Soshilov.junit;

/**
 * Created with IntelliJ IDEA.
 * User: soshikan
 * Date: 23 October 2014
 * Time: 1:51
 */
public class DataBaseDrop implements Command {
    /**
     * Drop (destroy) table.
     * @param args Commands that were entered.
     * @param db Our main table.
     * @throws CommandException Error in wrong arguments count.
     */
    @Override
    public void execute(final String[] args, DataBaseTableProvider db) throws CommandException {
        final int argumentsCount = 1;
        Main.checkArguments("drop", args.length, argumentsCount);

        try {
            db.removeTable(args[0]);
            System.out.println("dropped");
        } catch (IllegalStateException ex) {
            System.out.println("table does not exists");
        }

    }
}
