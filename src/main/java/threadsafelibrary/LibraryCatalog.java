package threadsafelibrary;

public interface LibraryCatalog {
    void addBook(String title, String author);
    boolean checkoutBook(String title);
    boolean returnBook(String title);
    void printAvailableBooks();
    void printLog();
}
