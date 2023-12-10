package org.stryukovsky;

import org.stryukovsky.commons.Author;
import org.stryukovsky.commons.Book;
import org.stryukovsky.publishing.Publishing;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.regex.Pattern;

public class Main {

    private static <T> T readWithRetry(Scanner scanner, String inputPrompt, String errorPrompt, Callable<T> inputFunction) {
        T result = null;
        while (result == null) {
            try {
                System.out.println(inputPrompt);
                result = inputFunction.call();
            } catch (Exception e) {
                scanner.next();
                System.out.println(errorPrompt + ": " + e);
            }
        }
        return result;
    }

    private static void executeWithRetry(String inputPrompt, String errorPrompt, Callable<Void> inputFunction) {
        boolean exit = false;
        while (!exit) {
            try {
                System.out.println(inputPrompt);
                inputFunction.call();
                exit = true;
            } catch (Exception e) {
                System.out.println(errorPrompt + ": " + e);
            }
        }

    }

    private static Pattern nameRegex = Pattern.compile("[a-zA-Zа-яА-Я]+");

    private static Author readAuthor(Scanner scanner) throws Exception {
        System.out.println("Provide name of author");
        String name = scanner.next();
        if (!nameRegex.matcher(name).matches())
            throw new Exception("Bad name");
        System.out.println("Provide surname of author");
        String surname = scanner.next();
        if (!nameRegex.matcher(surname).matches())
            throw new Exception("Bad surname");
        return new Author(name, surname);
    }

    private static Void publishBook(Publishing publishing, Scanner scanner) throws Exception {
        System.out.println("Select numeric choice for author of book or provide your own");
        Object[] authors = publishing.getAuthors().toArray();
        for (int i = 0; i < authors.length; i++) {
            Author author = (Author) authors[i];
            System.out.println(i + ": " + author);
        }
        int choice = readWithRetry(scanner, authors.length + ": input your own author", "bad choice", () -> readChoice(scanner, authors.length));
        Author author = (choice == authors.length) ? readAuthor(scanner) : (Author) authors[choice];
        String title = readWithRetry(scanner, "Input title of the book, 1 symbol at least, 50 symbols max", "bad title", () -> readTitle(scanner));
        int circulation = readWithRetry(scanner, "Input circulation of the book, 100 is minimum, 100000 is maximum", "bad circulation", () -> readCirculation(scanner));
        publishing.publishBook(author, title, circulation);
        return null;
    }

    private static String readTitle(Scanner scanner) throws Exception {
        String title = scanner.next() + scanner.nextLine();
        if (title.isEmpty())
            throw new Exception("Title is too small");
        if (title.length() > 50)
            throw new Exception("Title is too long");
        return title;
    }

    private static int readChoice(Scanner scanner, int maxValue) throws Exception {
        int choice = scanner.nextInt();
        if (choice > maxValue)
            throw new Exception("Too big choice");
        if (choice < 0)
            throw new Exception("Too small choice");
        return choice;
    }


    private static int readCirculation(Scanner scanner) throws Exception {
        int circulation = scanner.nextInt();
        if (circulation > 100000)
            throw new Exception("Too big circulation");
        if (circulation < 100)
            throw new Exception("Too small circulation");
        return circulation;
    }

    private static String readCommand(Scanner scanner) throws Exception {
        String command = scanner.next();
        if (
                !command.equals("publish-book") &&
                        !command.equals("add-co-author") &&
                        !command.equals("list-books") &&
                        !command.equals("list-authors"
                        ))
            throw new Exception("Bad command");
        return command;
    }

    private static Void addCoAuthor(Publishing publishing, Scanner scanner) throws Exception {
        System.out.println("Select numeric choice for author of a book");
        Object[] authors = publishing.getAuthors().toArray();
        for (int i = 0; i < authors.length; i++) {
            Author author = (Author) authors[i];
            System.out.println(i + ": " + author);
        }
        int authorChoice = readWithRetry(scanner, "Select your author", "bad choice", () -> readChoice(scanner, authors.length - 1));
        Author author = (Author) authors[authorChoice];
        System.out.println("Select a book");
        ArrayList<Book> books = publishing.getBooks();
        for (int i = 0; i < books.size(); i++) {
            System.out.println(i + ": " + books.get(i).getTitle());
        }
        int bookChoice = readWithRetry(scanner, "Select your book", "bad choice", () -> readChoice(scanner, books.size() - 1));
        Book book = books.get(bookChoice);
        publishing.addCoAuthor(book, author);
        return null;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int maxBooksPerAuthor = readWithRetry(
                scanner,
                "Input number of maximum books count per author",
                "Bad integer provided", scanner::nextInt);
        Publishing publishing = new Publishing(maxBooksPerAuthor);
        while (true) {
            String command = readWithRetry(
                    scanner,
                    "Select action with publishing\npublish-book publishes a new book\nadd-co-author adds a new co-author to existing book. Note: there shall be already book by co-author\nlist-books lists all books\nlist-authors lists all authors",
                    "bad command", () -> readCommand(scanner)
            );
            if (command.equals("publish-book")) {
                executeWithRetry("Input data for published book", "Error on publishsing", () -> publishBook(publishing, scanner));
            }
            if (command.equals("add-co-author")) {
                executeWithRetry("Select co-author", "Error on adding co-author", () -> addCoAuthor(publishing, scanner));
            }
            if (command.equals("list-books")) {
                publishing.getBooks().forEach((book -> {
                    System.out.print(book.getTitle() + " by ");
                    book.getAuthors().forEach(author -> System.out.print(author + " "));
                    System.out.println();
                }));
            }
            if (command.equals("list-authors")) {
                publishing.getAuthors().forEach(System.out::println);
            }
        }
    }
}