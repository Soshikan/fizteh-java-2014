package ru.fizteh.fivt.students.Soshilov.junit.DataBaseCommands;

import ru.fizteh.fivt.students.Soshilov.junit.Command;
import ru.fizteh.fivt.students.Soshilov.junit.CommandException;
import ru.fizteh.fivt.students.Soshilov.junit.DataBaseTableProvider;
import ru.fizteh.fivt.students.Soshilov.junit.Main;

/**
 * Created with IntelliJ IDEA.
 * User: soshikan
 * Date: 23 October 2014
 * Time: 1:53
 */
public class DataBaseUse implements Command {
    /**
     * Change current table and use a new one.
     * @param args Commands that were entered.
     * @param db Our main table.
     * @throws ru.fizteh.fivt.students.Soshilov.junit.CommandException Error in wrong arguments count.
     */
    @Override
    public void execute(final String[] args, DataBaseTableProvider db) throws CommandException {
        final int argumentsCount = 1;

        Main.checkArguments("use", args.length, argumentsCount);

        db.setCurrentTable(args[0]);

    }
}
