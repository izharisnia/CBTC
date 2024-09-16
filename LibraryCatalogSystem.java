import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

class Book {
    private String title;
    private String author;
    private int year;

    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public int getYear() {
        return year;
    }

    public void displayBookDetails() {
        System.out.println("===================================");
        System.out.println("| Title:   " + title);
        System.out.println("| Author:  " + author);
        System.out.println("| Year:    " + year);
        System.out.println("===================================\n");
    }
}

class Library {
    private List<Book> catalog = new ArrayList<>();

    // Add a new book to the catalog
    public void addBook(Book book) {
        catalog.add(book);
        System.out.println("\n*** Book added successfully! ***\n");
    }

    // Search for books by title
    public void searchByTitle(String title) {
        System.out.println("\n*** Searching for books with title: " + title + " ***\n");
        boolean found = false;
        for (Book book : catalog) {
            if (book.getTitle().equalsIgnoreCase(title)) {
                book.displayBookDetails();
                found = true;
            }
        }
        if (!found) {
            System.out.println(">>> No books found with title: " + title + "\n");
        }
    }

    // Search for books by author
    public void searchByAuthor(String author) {
        System.out.println("\n*** Searching for books by author: " + author + " ***\n");
        boolean found = false;
        for (Book book : catalog) {
            if (book.getAuthor().equalsIgnoreCase(author)) {
                book.displayBookDetails();
                found = true;
            }
        }
        if (!found) {
            System.out.println(">>> No books found by author: " + author + "\n");
        }
    }

    // List all books in the catalog
    public void listAllBooks() {
        System.out.println("\n*** Listing all available books ***\n");
        if (catalog.isEmpty()) {
            System.out.println(">>> No books in the catalog.\n");
        } else {
            for (Book book : catalog) {
                book.displayBookDetails();
            }
        }
    }
}

public class LibraryCatalogSystem {
    public static void main(String[] args) {
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n===============================");
            System.out.println("||   Library Catalog System   ||");
            System.out.println("===============================");
            System.out.println("1. Add a new book");
            System.out.println("2. Search by title");
            System.out.println("3. Search by author");
            System.out.println("4. List all books");
            System.out.println("5. Exit");
            System.out.println("===============================");
            System.out.print("Choose an option (1-5): ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    System.out.println("\n>>> Enter Book Details <<<");
                    System.out.print("Enter book title: ");
                    String title = scanner.nextLine();
                    System.out.print("Enter book author: ");
                    String author = scanner.nextLine();
                    System.out.print("Enter publication year: ");
                    int year = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline
                    Book newBook = new Book(title, author, year);
                    library.addBook(newBook);
                    break;
                case 2:
                    System.out.print("\nEnter title to search: ");
                    String searchTitle = scanner.nextLine();
                    library.searchByTitle(searchTitle);
                    break;
                case 3:
                    System.out.print("\nEnter author to search: ");
                    String searchAuthor = scanner.nextLine();
                    library.searchByAuthor(searchAuthor);
                    break;
                case 4:
                    library.listAllBooks();
                    break;
                case 5:
                    System.out.println("\n*** Exiting system. Goodbye! ***");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("\n>>> Invalid option. Please try again.");
            }
        }
    }
}