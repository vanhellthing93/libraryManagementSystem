package sf.mephi.study.lms.service;

import sf.mephi.study.lms.models.*;
import sf.mephi.study.lms.repository.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
    public void registerUser(String name, String email, int userType) {
        String userId = generateUserId();
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

    // Метод добавлен для удобной генерации тестовых данных
    public String findUserIDByName(String name) {
        User user = userRepository.findUserByName(name);
        if (user == null) {
            return null;
        } else {
            return user.getUserId();
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

    public boolean borrowBookAtTime(String userId, String isbn, String dateString) {
        User user = userRepository.findUser(userId);
        Book book = bookRepository.findBook(isbn);

        if (user == null || book == null) {
            return false;
        }

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate date = LocalDate.parse(dateString, formatter);
            return borrowingRepository.borrowBookAtTime(user, book, date);
        } catch (DateTimeParseException e) {
            return false;
        }
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
                    "Книга: %s\nПользователь: %s\nДата выдачи: %s\nСрок возврата: %s\nШтраф: %.2f\n",
                    record.getBook().getTitle(),
                    record.getUser().getName(),
                    record.getBorrowDate(),
                    record.getDueDate(),
                    record.calculateFine()
            );
            overdueBooksInfo.add(bookInfo);
        }

        return overdueBooksInfo;
    }

    private String generateUserId() {
        return UUID.randomUUID().toString();
    }

    public List<String> getAllUsers() {
        List<User> users = userRepository.getAllUsers();
        List<String> userStrings = new ArrayList<>();
        for (User user : users) {
            userStrings.add("ID: " + user.getUserId() + "\nИмя: " + user.getName() + "\nEmail: " + user.getEmail());
        }
        return userStrings;
    }

    public List<String> getAllBooks() {
        List<Book> books = bookRepository.getAllBooks();
        List<String> bookStrings = new ArrayList<>();
        for (Book book : books) {
            bookStrings.add(book.toString());
        }
        return bookStrings;
    }

    public List<String> getAllBorrowingOperations() {
        List<BorrowingRecord> borrowingRecords = borrowingRepository.getAllBorrowingRecords();
        List<String> operations = new ArrayList<>();

        for (BorrowingRecord record : borrowingRecords) {
            String operationInfo = String.format(
                    "Книга: %s\nПользователь: %s\nДата выдачи: %s\nСрок возврата: %s\nПросрочена: %s\n",
                    record.getBook().getTitle(),
                    record.getUser().getName(),
                    record.getBorrowDate(),
                    record.getDueDate(),
                    record.isOverdue() ? "Да" : "Нет"
            );
            operations.add(operationInfo);
        }

        return operations;
    }

}

