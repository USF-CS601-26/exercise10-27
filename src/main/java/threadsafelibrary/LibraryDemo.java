
package threadsafelibrary;

public class LibraryDemo {

    static void main(String[] args)
            throws InterruptedException {

        runDemo("SYNCHRONIZED METHODS",
                new SynchronizedLibraryCatalog());

       // runDemo("READ/WRITE LOCK",
         //       new ThreadSafeLibraryCatalog());


    }

    private static void runDemo(String label, LibraryCatalog catalog)
            throws InterruptedException {

        System.out.println("\n========== " + label + " ==========");

        catalog.addBook("Dune", "Herbert");
        catalog.addBook("1984", "Orwell");
        catalog.addBook("Foundation", "Asimov");

        // Test 1: Three concurrent readers
        System.out.println("\nTHREE CONCURRENT READERS:");

        Thread[] readers = new Thread[3];
        long start = System.nanoTime();

        for (int i = 0; i < readers.length; i++) {
            readers[i] = new Thread(
                    () -> catalog.printAvailableBooks(),
                    "Reader-" + (i + 1));
            readers[i].start();
        }

        for (Thread reader : readers) {
            reader.join();
        }

        long milliseconds =
                (System.nanoTime() - start) / 1_000_000;

        System.out.println("Total time for readers: "
                + milliseconds + " ms");

        // Test 2: Customers compete for the same book
        System.out.println("\nTHREE CUSTOMERS TRY TO CHECK OUT 1984:");

        Thread[] customers = new Thread[3];

        for (int i = 0; i < customers.length; i++) {
            customers[i] = new Thread(() -> {
                boolean success = catalog.checkoutBook("1984");

                System.out.println(Thread.currentThread().getName()
                        + (success ? " checked out 1984"
                        : " could not check out 1984"));
            }, "Customer-" + (i + 1));

            customers[i].start();
        }

        for (Thread customer : customers) {
            customer.join();
        }

        // Test 3: Return a book and print the catalog
        System.out.println();
        System.out.println("Returning 1984: "
                + catalog.returnBook("1984"));

        System.out.println();
        System.out.println("FINAL AVAILABLE BOOKS:");
        catalog.printAvailableBooks();

        System.out.println();
        System.out.println("AUDIT LOG:");
        catalog.printLog();
    }
}
