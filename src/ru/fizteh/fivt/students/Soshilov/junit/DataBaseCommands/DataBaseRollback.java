package ru.fizteh.fivt.students.Soshilov.junit.DataBaseCommands;

import ru.fizteh.fivt.students.Soshilov.junit.Command;
import ru.fizteh.fivt.students.Soshilov.junit.DataBaseTableProvider;
import ru.fizteh.fivt.students.Soshilov.junit.CommandException;
import ru.fizteh.fivt.students.Soshilov.junit.Main;
import ru.fizteh.fivt.students.Soshilov.junit.DataBaseTable;

/**
 * Created with IntelliJ IDEA.
 * User: soshikan
 * Date: 07 December 2014
 * Time: 22:04
 */
public final class DataBaseRollback implements Command {
    /**
     * Write changes to the disk.
     * @param args Commands that were entered.
     * @param db Our main table.
     * @throws ru.fizteh.fivt.students.Soshilov.junit.CommandException Error in wrong arguments count.
     */
    @Override
    public void execute(final String[] args, final DataBaseTableProvider db) throws CommandException {
        final int argumentsCount = 0;
        Main.checkArguments("drop", args.length, argumentsCount);

        DataBaseTable table = db.getCurrentTable();

        System.out.println(table.rollback());

    }
}
