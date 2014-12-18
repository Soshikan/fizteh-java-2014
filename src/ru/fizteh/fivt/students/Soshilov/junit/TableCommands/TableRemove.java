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
 * Time: 1:40
 */
public final class TableRemove implements Command {
    /**
     * Remove key + value by getting the key as argument.
     * @param args Commands that were entered.
     * @param db Our main table.
     * @throws ru.fizteh.fivt.students.Soshilov.junit.CommandException Error in wrong arguments count.
     */
    @Override
    public void execute(final String[] args, final DataBaseTableProvider db) throws CommandException {
        final int argumentsCount = 1;
        Main.checkArguments("remove", args.length, argumentsCount);
        Main.checkDataBaseExisting(db);

        DataBaseTable table = db.getCurrentTable();

        String key = args[0];
        String value = table.remove(key);

        if (value == null) {
            System.out.println("not found");
        } else {
            System.out.println("removed");
        }

    }
}
