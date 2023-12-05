package org.stryukovsky.commons;

import java.util.ArrayList;
import java.util.Objects;

public class Book {
    private ArrayList<Author> authors;
    private String title;
    private int yearOfRelease;
    private long circulation;

    public Book(Author author, String title, int yearOfRelease, long circulation) {
        this.authors = new ArrayList<>();
        authors.add(author);
        this.title = title;
        this.yearOfRelease = yearOfRelease;
        this.circulation = circulation;
    }

    public void addAuthor(Author author) {
        this.authors.add(author);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return getYearOfRelease() == book.getYearOfRelease() && getCirculation() == book.getCirculation() && Objects.equals(getAuthors(), book.getAuthors()) && Objects.equals(getTitle(), book.getTitle());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAuthors(), getTitle(), getYearOfRelease(), getCirculation());
    }

    public ArrayList<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(ArrayList<Author> authors) {
        this.authors = authors;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getYearOfRelease() {
        return yearOfRelease;
    }

    public void setYearOfRelease(int yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
    }

    public long getCirculation() {
        return circulation;
    }

    public void setCirculation(long circulation) {
        this.circulation = circulation;
    }
}
