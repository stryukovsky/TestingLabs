package org.stryukovsky.library;

import org.stryukovsky.commons.*;

import java.util.ArrayList;
import java.util.HashSet;

public class Library {
    private final ArrayList<Book> books;

    private final HashSet<Author> authors;

    public Library() {
        books = new ArrayList<>();
        authors = new HashSet<>();
    }

    public ArrayList<Book> getBooks() {
        return books;
    }

    public HashSet<Author> getAuthors() {
        return authors;
    }

    public void addBook(Book book) throws BookDuplicateException {
        if (books.contains(book)) {
            throw new BookDuplicateException(book);
        }
        books.add(book);
        authors.addAll(book.getAuthors());
    }

    public void addCoAuthorToBook(Book book, Author author) throws AuthorDuplicateException {
        if (book.getAuthors().contains(author))
            throw new AuthorDuplicateException(book, author);
        book.addAuthor(author);
    }

    public boolean hasBook(Book book) {
        return books.contains(book);
    }
}
