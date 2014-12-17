package ru.fizteh.fivt.students.Soshilov.junit.TableCommands;

import ru.fizteh.fivt.students.Soshilov.junit.Command;
import ru.fizteh.fivt.students.Soshilov.junit.DataBaseTableProvider;
import ru.fizteh.fivt.students.Soshilov.junit.CommandException;
import ru.fizteh.fivt.students.Soshilov.junit.Main;
import ru.fizteh.fivt.students.Soshilov.junit.DataBaseTable;

/**
 * Created with IntelliJ IDEA.
 * User: soshikan
 * Date: 23 October 2014
 * Time: 1:26
 */
public final class TableGet implements Command {
    /**
     * Get the value by having a key.
     * @param args Commands that were entered.
     * @param db Our main table.
     * @throws ru.fizteh.fivt.students.Soshilov.junit.CommandException Error in wrong arguments count.
     */
    @Override
    public void execute(final String[] args, final DataBaseTableProvider db) throws CommandException {
        final int argumentsCount = 1;
        Main.checkArguments("get", args.length, argumentsCount);


        DataBaseTable table = db.getCurrentTable();
        if (table == null) {
            System.out.println("no table");
            return;
        }

        String key = args[0];
        String value = table.get(key);
        if (value != null) {
            System.out.println("found\n" + value);
        } else {
            System.out.println("not found");
        }

    }
}