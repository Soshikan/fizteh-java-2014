package ru.fizteh.fivt.students.Soshilov.junit;

import ru.fizteh.fivt.students.Soshilov.junit.DataBaseCommands.DataBaseCommit;
import ru.fizteh.fivt.students.Soshilov.junit.DataBaseCommands.DataBaseCreate;
import ru.fizteh.fivt.students.Soshilov.junit.DataBaseCommands.DataBaseDrop;
import ru.fizteh.fivt.students.Soshilov.junit.DataBaseCommands.DataBaseRollback;
import ru.fizteh.fivt.students.Soshilov.junit.DataBaseCommands.DataBaseShowTables;
import ru.fizteh.fivt.students.Soshilov.junit.DataBaseCommands.DataBaseSize;
import ru.fizteh.fivt.students.Soshilov.junit.DataBaseCommands.DataBaseUse;
import ru.fizteh.fivt.students.Soshilov.junit.TableCommands.TableGet;
import ru.fizteh.fivt.students.Soshilov.junit.TableCommands.TableList;
import ru.fizteh.fivt.students.Soshilov.junit.TableCommands.TablePut;
import ru.fizteh.fivt.students.Soshilov.junit.TableCommands.TableRemove;
import ru.fizteh.fivt.students.Soshilov.junit.storage.TableProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: soshikan
 * Date: 22 October 2014
 * Time: 22:29
 */
public final class MapRun {
    /**
     * Map where every function is. Then we put the command of by entering the key.
     */
    private static Map<String, Command> commandMap = new HashMap<>();
    /**
     * Database we use.
     */
    private static TableProvider db;

    /**
     * A sign, starting every line in interactive mode.
     */
    public static final String SIGN_OF_INVITATION = "$ ";

    /**
     * Fill db and commandMap before the object of the class would be made.
     */
    static {

        String dbDir = System.getProperty("fizteh.db.dir");

        DataBaseTableProviderFactory factory = new DataBaseTableProviderFactory();
        db = factory.create(dbDir);

        commandMap.put("exit", new Exit());
        commandMap.put("put", new TablePut());
        commandMap.put("get", new TableGet());
        commandMap.put("remove", new TableRemove());
        commandMap.put("list", new TableList());
        commandMap.put("create", new DataBaseCreate());
        commandMap.put("drop", new DataBaseDrop());
        commandMap.put("use", new DataBaseUse());
        commandMap.put("show", new DataBaseShowTables());
        commandMap.put("size", new DataBaseSize());
        commandMap.put("commit", new DataBaseCommit());
        commandMap.put("rollback", new DataBaseRollback());
    }

    /**
     * We switch commands into different classes.
     * @param args Commands that were entered: name, its' arguments.
     * @throws CommandException Problems with arguments.
     */
    public static void run(final String[] args) throws CommandException {
        if (args.length == 0) {
            throw new RuntimeException("no command");
        }

        Command command = commandMap.get(args[0]);
        if (command == null) {
            throw new CommandException(args[0] + ": command not found");
        } else {
            if (args[0].equals("show") && !args[1].equals("tables")) {
                throw new CommandException(args[0] + " " + args[1] + ": command not found");
            } else {
                command.execute(Main.removeNameInArguments(args, args[0].equals("show") ? 2 : 1),
                        (DataBaseTableProvider) db);
            }
        }
    }

    /**
     * Interactive Mode: do entered functions.
     */
    public static void interactiveMode() {
        try (Scanner sc = new Scanner(System.in)) {
            while (true) {
                System.out.print(SIGN_OF_INVITATION);
                String currentString = sc.nextLine();
                currentString = currentString.trim();
                try {
                    run(currentString.split("\\s+"));
                } catch (CommandException | IllegalArgumentException | IllegalStateException ex) {
                    System.err.println(ex.getMessage());
                }

            }
        }
    }

    /**
     * Package mode: we enter command constantly.
     * @param args Commands that were entered: name, its' arguments.
     */
    public static void batchMode(final String[] args) {
        String[] commands = Main.parsingCommands(args);
        for (String commandParams : commands) {
            try {
                run(commandParams.split("\\s+"));
            } catch (CommandException | IllegalArgumentException | IllegalStateException ex) {
                System.err.println(ex.getMessage());
                System.exit(1);
            }
        }
        System.exit(0);
    }

    /**
     * The main function, which do the program: first we see, interactive or package mode is.
     * @param args Commands that were entered: name, its' arguments.
     */
    public static void main(final String[] args) {
        if (args.length == 0) {
            interactiveMode();
        } else {
            batchMode(args);
        }
    }
}
