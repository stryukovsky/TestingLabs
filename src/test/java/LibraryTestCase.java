import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.stryukovsky.commons.*;
import org.stryukovsky.library.BadAuthorException;
import org.stryukovsky.library.BookDuplicateException;
import org.stryukovsky.library.Library;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class LibraryTestCase {

    private Library library;
    private Author firstAuthor;
    private Author secondAuthor;
    private Author badAuthor;
    private Book firstBook;
    private Book secondBook;

    @BeforeEach
    public void setupContents() {
        firstAuthor = new Author("Ivan", "Petrov");
        secondAuthor = new Author("Vladimir", "Sidorov");
        badAuthor = new Author("Dmitry", "Stryukovsky");
        firstBook = new Book(firstAuthor, "First book", 2000, 10000);
        secondBook = new Book(secondAuthor, "Second book", 1999, 50000);
    }

    @BeforeEach
    public void setupLibrary() {
        library = new Library();
    }

    @Test
    public void shouldSuccessfullyAddBook() {
        assertDoesNotThrow(() -> library.addBook(firstBook));
        assertTrue(library.getBooks().contains(firstBook));
        assertTrue(library.getAuthors().contains(firstAuthor));
    }

    @Test
    public void shouldFailAddingDuplicateBook() throws Exception {
        try {
            library.addBook(firstBook);
            library.addBook(secondBook);
        } catch (Exception exception) {
            throw new Exception("Bad test fixture");
        }
        assertThrows(BookDuplicateException.class, () -> library.addBook(firstBook));
        assertEquals(library.getBooks().size(), 2);
    }

    @Test
    public void shouldSuccessfullyAddCoAuthor() throws Exception {
        try {
            library.addBook(firstBook);
            library.addBook(secondBook);
        } catch (Exception exception) {
            throw new Exception("Bad test fixture");
        }
        assertDoesNotThrow(() -> library.addCoAuthorToBook(secondBook, firstAuthor));
        assertTrue(secondBook.getAuthors().contains(firstAuthor));
    }

    @Test
    public void shouldFailDuplicatingAuthor() throws Exception {
        try {
            library.addBook(secondBook);
        } catch (Exception exception) {
            throw new Exception("Bad test fixture");
        }
        assertThrows(AuthorDuplicateException.class,
                () -> library.addCoAuthorToBook(secondBook, secondAuthor));
        assertEquals(secondBook.getAuthors().size(), 1);
    }

    @Disabled
    public void shouldFailAddingBadAuthor() throws Exception {
        try {
            library.addBook(firstBook);
            library.addBook(secondBook);
        } catch (Exception exception) {
            throw new Exception("Bad test fixture");
        }
        assertThrows(BadAuthorException.class,
                () -> library.addCoAuthorToBook(firstBook, badAuthor));
        assertEquals(secondBook.getAuthors().size(), 1);
    }
}
