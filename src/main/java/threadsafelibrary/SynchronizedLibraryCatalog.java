
package threadsafelibrary;

import java.util.ArrayList;
import java.util.List;

/**
 * A thread-safe library catalog using synchronized methods.
 *
 * Students: Complete the TODOs.
 */
public class SynchronizedLibraryCatalog implements LibraryCatalog {
    private final List<Book> books = new ArrayList<>();
    private final List<String> auditLog = new ArrayList<>();

    /** Adds a new book to the catalog. */
    @Override
    public void addBook(String title, String author) {
        // TODO: Make this method synchronized
        books.add(new threadsafelibrary.Book(title, author));
        logChange("Added: " + title);
    }

    /**
     * Checks out a book if available.
     * Returns true if successful, false otherwise.
     */
    @Override
    public boolean checkoutBook(String title) {
        // TODO: Make this method synchronized
        for (Book book : books) {
            if (book.getTitle().equals(title) && !book.isCheckedOut()) {
                book.checkout();
                logChange("Checked out: " + title);
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a checked-out book.
     * Returns true if successful, false otherwise.
     */
    @Override
    public boolean returnBook(String title) {
        // TODO: Make this method synchronized
        for (threadsafelibrary.Book book : books) {
            if (book.getTitle().equals(title) && book.isCheckedOut()) {
                book.returnToLibrary();
                logChange("Returned: " + title);
                return true;
            }
        }
        return false;
    }

    /** Prints all books currently available for checkout. */
    @Override
    public void printAvailableBooks() {
        // TODO: Make this method synchronized
        String name = Thread.currentThread().getName();
        System.out.println(name + " START reading catalog");

        // Print all books that are not checked out
        for (Book book : books) {
            if (!book.isCheckedOut()) {
                System.out.println("  " + name + ": " + book.getTitle()
                        + " by " + book.getAuthor());
            }
        }
        // Simulate a slow read operation.The thread should hold the lock during this pause.
        pauseForDemonstration();

        System.out.println(name + " END reading catalog");
    }

    /**
     * Adds a message to the audit log.
     * Called from addBook(), checkoutBook(), and returnBook().
     */
    private void logChange(String message) {
        // TODO: Make this method synchronized
        // Add the message to auditLog
        auditLog.add(message);
    }

    /** Prints all audit log entries. */
    @Override
    public void printLog() {
        // TODO: Make this method synchronized
        // Print all messages in auditLog
        for (String message : auditLog) {
            System.out.println("  " + message);
        }
    }

    /** Simulates a slow operation for demonstration purposes. */
    private static void pauseForDemonstration() {
        try {
            Thread.sleep(600);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
