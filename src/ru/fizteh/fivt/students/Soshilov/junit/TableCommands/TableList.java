package ru.fizteh.fivt.students.Soshilov.junit.TableCommands;

import ru.fizteh.fivt.students.Soshilov.junit.Command;
import ru.fizteh.fivt.students.Soshilov.junit.DataBaseTableProvider;
import ru.fizteh.fivt.students.Soshilov.junit.CommandException;
import ru.fizteh.fivt.students.Soshilov.junit.Main;
import ru.fizteh.fivt.students.Soshilov.junit.DataBaseTable;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: soshikan
 * Date: 23 October 2014
 * Time: 1:30
 */
public final class TableList implements Command {
    /**
     * List whole table.
     * @param args Commands that were entered.
     * @param db Our main table.
     * @throws ru.fizteh.fivt.students.Soshilov.junit.CommandException Error in wrong arguments count.
     */
    @Override
    public void execute(final String[] args, final DataBaseTableProvider db) throws CommandException {
        final int argumentsCount = 0;
        Main.checkArguments("list", args.length, argumentsCount);
        Main.checkDataBaseExisting(db);

        DataBaseTable table = db.getCurrentTable();

        List<String> list = table.list();

        int counter = 0;
        for (String s : list) {
            ++counter;
            if (counter == list.size() - 1) {
                System.out.print(s + ", ");
            } else {
                System.out.print(s);
            }

        }

        System.out.println("");

    }
}
