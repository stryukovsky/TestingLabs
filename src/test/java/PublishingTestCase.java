import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.stryukovsky.commons.Author;
import org.stryukovsky.commons.Book;
import org.stryukovsky.library.BookDuplicateException;
import org.stryukovsky.publishing.Publishing;
import org.stryukovsky.publishing.TooManyBooksPerAuthorException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class PublishingTestCase {
    private Publishing publishing;
    private Author firstAuthor;


    @BeforeEach
    public void setup() {
        publishing = new Publishing(3);
        firstAuthor = new Author("Ivan", "Petrov");
    }

    @Test
    public void shouldSuccessfullyAddBook() {
        assertDoesNotThrow(() ->
                publishing.publishBook(firstAuthor, "Simple book", 1000));
        Optional<Book> expectedBook = publishing.getBooks().stream().filter(book -> book.getTitle().equals("Simple book")).findFirst();
        assertTrue(expectedBook.isPresent());
    }

    @Test
    public void shouldFailAddingDuplicateBook() {
        assertDoesNotThrow(() ->
                publishing.publishBook(firstAuthor, "Simple book", 1000));
        assertThrows(BookDuplicateException.class, () ->
                publishing.publishBook(firstAuthor, "Simple book", 1000));
    }

    @Test
    public void shouldFailAddingTooManyBooks() {
        assertDoesNotThrow(() ->
                publishing.publishBook(firstAuthor, "Simple book 1", 1000));
        assertDoesNotThrow(() ->
                publishing.publishBook(firstAuthor, "Simple book 2", 1000));
        assertThrows(TooManyBooksPerAuthorException.class, () ->
                publishing.publishBook(firstAuthor, "Simple book 3", 1000));
    }
}
