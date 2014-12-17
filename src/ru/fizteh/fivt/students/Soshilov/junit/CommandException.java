package ru.fizteh.fivt.students.Soshilov.junit;

/**
 * Created with IntelliJ IDEA.
 * User: soshikan
 * Date: 07 December 2014
 * Time: 21:43
 */
public final class CommandException extends Exception {
    public CommandException() {
        super();
    }

    public CommandException(final String message) {
        super(message);
    }

    public CommandException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public CommandException(final Throwable cause) {
        super(cause);
    }

}
