package org.stryukovsky.publishing;

import org.stryukovsky.commons.Author;

public class OuterAuthorException extends Exception{
    public OuterAuthorException(Author author) {
        super("Author " + author.getName() + " " + author.getSurname() + " seems to have no agreement with publishing");
    }
}
