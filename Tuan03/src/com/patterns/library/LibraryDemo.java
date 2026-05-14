package com.patterns.library;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class LibraryDemo {
    public static void main(String[] args) {
        Library library = Library.getInstance();

        library.addObserver(new Librarian("Morgan"));
        library.addObserver(new Subscriber("Nora"));

        BookFactory paperFactory = new PaperBookFactory();
        BookFactory ebookFactory = new EBookFactory();
        BookFactory audioFactory = new AudioBookFactory();

        library.addBook(paperFactory.create("B1", "Clean Code", "Robert Martin", "Programming"));
        library.addBook(ebookFactory.create("B2", "Refactoring", "Martin Fowler", "Programming"));
        library.addBook(audioFactory.create("B3", "Dune", "Frank Herbert", "SciFi"));

        System.out.println("Available books:");
        for (Book book : library.listAvailableBooks()) {
            System.out.println("  " + book);
        }

        SearchStrategy authorSearch = new AuthorSearch();
        System.out.println("Search by author 'Martin':");
        for (Book book : library.searchBooks(authorSearch, "Martin")) {
            System.out.println("  " + book);
        }

        Borrowing borrowing = new BasicBorrowing("Alex", 14);
        borrowing = new ExtendedLoanDecorator(borrowing, 7);
        borrowing = new SpecialEditionDecorator(borrowing, "Large Print");
        library.borrowBook("B1", borrowing);

        library.markOverdue("B1");
        library.returnBook("B1");
    }
}

abstract class Book {
    private final String id;
    private final String title;
    private final String author;
    private final String genre;

    Book(String id, String title, String author, String genre) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public abstract String getType();

    @Override
    public String toString() {
        return id + " | " + title + " | " + author + " | " + genre + " | " + getType();
    }
}

class PaperBook extends Book {
    PaperBook(String id, String title, String author, String genre) {
        super(id, title, author, genre);
    }

    @Override
    public String getType() {
        return "Paper";
    }
}

class EBook extends Book {
    EBook(String id, String title, String author, String genre) {
        super(id, title, author, genre);
    }

    @Override
    public String getType() {
        return "EBook";
    }
}

class AudioBook extends Book {
    AudioBook(String id, String title, String author, String genre) {
        super(id, title, author, genre);
    }

    @Override
    public String getType() {
        return "Audio";
    }
}

abstract class BookFactory {
    public abstract Book create(String id, String title, String author, String genre);
}

class PaperBookFactory extends BookFactory {
    @Override
    public Book create(String id, String title, String author, String genre) {
        return new PaperBook(id, title, author, genre);
    }
}

class EBookFactory extends BookFactory {
    @Override
    public Book create(String id, String title, String author, String genre) {
        return new EBook(id, title, author, genre);
    }
}

class AudioBookFactory extends BookFactory {
    @Override
    public Book create(String id, String title, String author, String genre) {
        return new AudioBook(id, title, author, genre);
    }
}

interface SearchStrategy {
    List<Book> search(List<Book> books, String query);
}

class TitleSearch implements SearchStrategy {
    @Override
    public List<Book> search(List<Book> books, String query) {
        String q = query.toLowerCase();
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(q)) {
                result.add(book);
            }
        }
        return result;
    }
}

class AuthorSearch implements SearchStrategy {
    @Override
    public List<Book> search(List<Book> books, String query) {
        String q = query.toLowerCase();
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.getAuthor().toLowerCase().contains(q)) {
                result.add(book);
            }
        }
        return result;
    }
}

class GenreSearch implements SearchStrategy {
    @Override
    public List<Book> search(List<Book> books, String query) {
        String q = query.toLowerCase();
        List<Book> result = new ArrayList<>();
        for (Book book : books) {
            if (book.getGenre().toLowerCase().contains(q)) {
                result.add(book);
            }
        }
        return result;
    }
}

interface LibraryObserver {
    void onNotify(String event, Book book);
}

interface LibrarySubject {
    void addObserver(LibraryObserver observer);
    void removeObserver(LibraryObserver observer);
    void notifyObservers(String event, Book book);
}

class Library implements LibrarySubject {
    private static final Library INSTANCE = new Library();

    private final Map<String, Book> catalog = new LinkedHashMap<>();
    private final Set<String> borrowed = new HashSet<>();
    private final List<LibraryObserver> observers = new ArrayList<>();

    private Library() {
    }

    public static Library getInstance() {
        return INSTANCE;
    }

    public void addBook(Book book) {
        catalog.put(book.getId(), book);
        notifyObservers("NEW_BOOK", book);
    }

    public boolean borrowBook(String bookId, Borrowing borrowing) {
        Book book = catalog.get(bookId);
        if (book == null) {
            System.out.println("Borrow failed: missing " + bookId);
            return false;
        }
        if (borrowed.contains(bookId)) {
            System.out.println("Borrow failed: already borrowed " + bookId);
            return false;
        }
        borrowed.add(bookId);
        System.out.println("Borrowed: " + book + " | " + borrowing.getDescription()
            + " | days=" + borrowing.getDays());
        return true;
    }

    public boolean returnBook(String bookId) {
        if (!borrowed.contains(bookId)) {
            System.out.println("Return failed: not borrowed " + bookId);
            return false;
        }
        borrowed.remove(bookId);
        System.out.println("Returned: " + bookId);
        return true;
    }

    public List<Book> listAvailableBooks() {
        List<Book> result = new ArrayList<>();
        for (Book book : catalog.values()) {
            if (!borrowed.contains(book.getId())) {
                result.add(book);
            }
        }
        return result;
    }

    public List<Book> searchBooks(SearchStrategy strategy, String query) {
        return strategy.search(new ArrayList<>(catalog.values()), query);
    }

    public void markOverdue(String bookId) {
        Book book = catalog.get(bookId);
        if (book != null) {
            notifyObservers("OVERDUE", book);
        }
    }

    @Override
    public void addObserver(LibraryObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(LibraryObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String event, Book book) {
        for (LibraryObserver observer : observers) {
            observer.onNotify(event, book);
        }
    }
}

class Librarian implements LibraryObserver {
    private final String name;

    Librarian(String name) {
        this.name = name;
    }

    @Override
    public void onNotify(String event, Book book) {
        System.out.println("Librarian " + name + " notified: " + event + " -> " + book);
    }
}

class Subscriber implements LibraryObserver {
    private final String name;

    Subscriber(String name) {
        this.name = name;
    }

    @Override
    public void onNotify(String event, Book book) {
        System.out.println("Subscriber " + name + " notified: " + event + " -> " + book);
    }
}

interface Borrowing {
    String getDescription();
    int getDays();
}

class BasicBorrowing implements Borrowing {
    private final String borrower;
    private final int days;

    BasicBorrowing(String borrower, int days) {
        this.borrower = borrower;
        this.days = days;
    }

    @Override
    public String getDescription() {
        return "Borrowing for " + borrower;
    }

    @Override
    public int getDays() {
        return days;
    }
}

abstract class BorrowingDecorator implements Borrowing {
    protected final Borrowing inner;

    BorrowingDecorator(Borrowing inner) {
        this.inner = inner;
    }

    @Override
    public String getDescription() {
        return inner.getDescription();
    }

    @Override
    public int getDays() {
        return inner.getDays();
    }
}

class ExtendedLoanDecorator extends BorrowingDecorator {
    private final int extraDays;

    ExtendedLoanDecorator(Borrowing inner, int extraDays) {
        super(inner);
        this.extraDays = extraDays;
    }

    @Override
    public String getDescription() {
        return inner.getDescription() + " + extended " + extraDays + " days";
    }

    @Override
    public int getDays() {
        return inner.getDays() + extraDays;
    }
}

class SpecialEditionDecorator extends BorrowingDecorator {
    private final String edition;

    SpecialEditionDecorator(Borrowing inner, String edition) {
        super(inner);
        this.edition = edition;
    }

    @Override
    public String getDescription() {
        return inner.getDescription() + " + special edition: " + edition;
    }
}
