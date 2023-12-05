package org.stryukovsky.library;

import org.stryukovsky.commons.Author;

public class BadAuthorException extends Exception {
    public BadAuthorException(Author author) {
        super("Library has no book with author " + author.getName() + " " + author.getSurname());
    }
}
