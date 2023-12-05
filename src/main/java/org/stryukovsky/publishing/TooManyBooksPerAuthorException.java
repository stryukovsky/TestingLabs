package org.stryukovsky.publishing;

import org.stryukovsky.commons.Author;

public class TooManyBooksPerAuthorException extends Exception{
    public TooManyBooksPerAuthorException(Author author, int possibleAmount) {
        super("Author " + author.getName() + " " + author.getSurname() + " already has enough books, max possible is " + possibleAmount);
    }
}
