package ru.fizteh.fivt.students.Soshilov.MultiFileHashMap;

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
     */
    @Override
    public void execute(final String[] args, DataBase db) {
        Main.checkArguments("drop", args.length, 2);

        if (db.tables.remove(args[1]) == null) {
            System.out.println("tablename does not exists");
        } else {
            System.out.println("dropped");
        }

    }
}