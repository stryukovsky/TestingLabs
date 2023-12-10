package org.stryukovsky.publishing;

import org.stryukovsky.commons.Author;
import org.stryukovsky.commons.AuthorDuplicateException;
import org.stryukovsky.commons.Book;
import org.stryukovsky.library.BadAuthorException;
import org.stryukovsky.library.BookDuplicateException;
import org.stryukovsky.library.Library;

import java.util.*;

public class Publishing {
    private final HashMap<Author, Integer> authorPublishedBooksCount;
    private final Library library;

    public final int maxAuthorBooksCount;

    public Publishing(int maxAuthorBooksCount) {
        this.library = new Library();
        this.authorPublishedBooksCount = new HashMap<>();
        this.maxAuthorBooksCount = maxAuthorBooksCount;
    }

    public HashSet<Author> getAuthors() {
        return library.getAuthors();
    }

    public ArrayList<Book> getBooks() {
        return library.getBooks();
    }

    public void publishBook(Author author, String title, int circulation) throws BookDuplicateException, TooManyBooksPerAuthorException {
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        int alreadyPublishedCount = getAlreadyPublishedBooksCount(author);
        if (alreadyPublishedCount + 1 >= maxAuthorBooksCount) {
            throw new TooManyBooksPerAuthorException(author, maxAuthorBooksCount);
        }
        Book book = new Book(author, title, currentYear, circulation);
        if (library.hasBook(book)) {
            throw new BookDuplicateException(book);
        }
        library.addBook(book);
        incrementAlreadyPublishedBooksCount(author);
    }

    public void addCoAuthor(Book book, Author author) throws AuthorDuplicateException, BadAuthorException {
        library.addCoAuthorToBook(book, author);
    }

    private int getAlreadyPublishedBooksCount(Author author) {
        return authorPublishedBooksCount.getOrDefault(author, 0);
    }

    private void incrementAlreadyPublishedBooksCount(Author author) {
        int currentValue = authorPublishedBooksCount.getOrDefault(author, 0);
        currentValue++;
        authorPublishedBooksCount.put(author, currentValue);
    }
}
