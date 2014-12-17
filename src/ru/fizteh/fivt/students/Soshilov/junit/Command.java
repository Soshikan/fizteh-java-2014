package ru.fizteh.fivt.students.Soshilov.junit;

/**
 * Created with IntelliJ IDEA.
 * User: soshikan
 * Date: 07 December 2014
 * Time: 21:42
 */
public interface Command {
    /**
     * Function for execution every command that would be entered.
     * @param args Commands that were entered.
     * @param db Our main table.
     * @throws CommandException Error in wrong arguments count.
     */
    void execute(final String[] args, final DataBaseTableProvider db) throws CommandException;
}
