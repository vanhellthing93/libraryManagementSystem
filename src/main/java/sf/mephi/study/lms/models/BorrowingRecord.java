package sf.mephi.study.lms.models;

import java.time.LocalDate;

public class BorrowingRecord {
    private User user;
    private Book book;
    private LocalDate borrowDate;
    private LocalDate dueDate;

    public BorrowingRecord(User user, Book book, LocalDate borrowDate) {
        this.user = user;
        this.book = book;
        this.borrowDate = borrowDate;
        this.dueDate = borrowDate.plusDays(user.getBorrowDays());
    }

    // Геттеры
    public User getUser() {
        return user;
    }

    public Book getBook() {
        return book;
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    // Проверка на просрочку
    public boolean isOverdue() {
        return LocalDate.now().isAfter(dueDate);
    }

    // Расчет штрафа
    public double calculateFine() {
        if (!isOverdue()) return 0.0;
        long daysOverdue = LocalDate.now().toEpochDay() - dueDate.toEpochDay();
        return daysOverdue * user.getFinePerDay();
    }


}
