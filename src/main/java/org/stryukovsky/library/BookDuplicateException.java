package org.stryukovsky.library;

import org.stryukovsky.commons.Book;

public class BookDuplicateException extends Exception{
    public BookDuplicateException(Book book) {
        super("Library already contains \""+ book.getTitle() + "\" by " + book.getAuthors().size() + " author (s)");
    }
}
