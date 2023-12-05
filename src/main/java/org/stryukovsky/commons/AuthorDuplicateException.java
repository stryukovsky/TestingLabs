package org.stryukovsky.commons;

public class AuthorDuplicateException extends Exception {
    public AuthorDuplicateException(Book book, Author author) {
        super("Book " + book.getTitle() + " already has author " + author.getName() + " " + author.getSurname());
    }
}
