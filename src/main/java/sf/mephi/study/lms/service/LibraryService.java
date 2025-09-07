package sf.mephi.study.lms.service;

import sf.mephi.study.lms.models.*;
import sf.mephi.study.lms.repository.*;

import java.util.ArrayList;
import java.util.List;

public class LibraryService {
    private BookRepository bookRepository;
    private UserRepository userRepository;
    private BorrowingRepository borrowingRepository;

    public LibraryService(BookRepository bookRepository,
                          UserRepository userRepository,
                          BorrowingRepository borrowingRepository) {
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.borrowingRepository = borrowingRepository;
    }

    // Управление книгами
    public void addBook(String title, String author, String isbn, String genre) {
        Book book = new Book(isbn, title, author, genre);
        bookRepository.addBook(book);
    }

    public boolean removeBook(String isbn) {
        return bookRepository.removeBook(isbn);
    }

    public Book findBook(String isbn) {
        return bookRepository.findBook(isbn);
    }

    public List<String> searchBooks(String query) {
        List<Book> bookList = bookRepository.searchBooks(query);
        List<String> searchResults = new ArrayList<>();
        for (Book book : bookList) {
            searchResults.add(book.toString());
        }
        return searchResults;
    }


    // Управление пользователями
    public void registerUser(String name, String userId, String email, int userType) {
        User user;
        switch (userType) {
            case 1:
                user = new Student(name, userId, email);
                break;
            case 2:
                user = new Faculty(name, userId, email);
                break;
            case 3:
                user = new Guest(name, userId, email);
                break;
            default:
                throw new IllegalArgumentException("Некорректный тип пользователя.");
        }
        userRepository.registerUser(user);
    }

    public String findUser(String userId) {
        User user = userRepository.findUser(userId);
        if (user == null) {
            return null;
        } else {
            return "Имя: " + user.getName() + "\nEmail: " + user.getEmail();
        }
    }

    // Операции выдачи и возврата
    public boolean borrowBook(String userId, String isbn) {
        User user = userRepository.findUser(userId);
        Book book = bookRepository.findBook(isbn);

        if (user == null || book == null) {
            return false;
        }

        return borrowingRepository.borrowBook(user, book);
    }

    public boolean returnBook(String userId, String isbn) {
        User user = userRepository.findUser(userId);
        Book book = bookRepository.findBook(isbn);

        if (user == null || book == null) {
            return false;
        }

        return borrowingRepository.returnBook(user, book);
    }

    public List<String> getOverdueBooks() {
        List<BorrowingRecord> overdueRecords = borrowingRepository.getOverdueBooks();
        List<String> overdueBooksInfo = new ArrayList<>();

        for (BorrowingRecord record : overdueRecords) {
            String bookInfo = String.format(
                    "%s (Пользователь: %s, Штраф: %.2f)",
                    record.getBook().getTitle(),
                    record.getUser().getName(),
                    record.calculateFine()
            );
            overdueBooksInfo.add(bookInfo);
        }

        return overdueBooksInfo;
    }
}

