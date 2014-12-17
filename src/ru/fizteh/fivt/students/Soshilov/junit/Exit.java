package ru.fizteh.fivt.students.Soshilov.junit;

/**
 * Created with IntelliJ IDEA.
 * User: soshikan
 * Date: 23 October 2014
 * Time: 1:08
 */
public final class Exit implements Command {
    /**
     * Stop working with database.
     * @param args Commands that were entered.
     * @param db Our main table.
     */
    @Override
    public void execute(final String[] args, final DataBaseTableProvider db) {
        boolean isExitPossible = db.isExitPossible();
        if (isExitPossible) {
            try {
                db.write();
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
                System.exit(1);
            }
            System.exit(0);
        } else {
            System.out.println("commit or rollback changes");
        }
    }
}
