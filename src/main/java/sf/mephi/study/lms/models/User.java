package sf.mephi.study.lms.models;

import java.util.ArrayList;
import java.util.List;

public abstract class User {
    protected String name;
    protected String userId;
    protected String email;
    protected List<String> borrowedBooks;

    public User(String name, String userId, String email) {
        this.name = name;
        this.userId = userId;
        this.email = email;
        this.borrowedBooks = new ArrayList<>();
    }

    // Абстрактные методы для каждого типа пользователя
    public abstract int getMaxBooks();

    public abstract int getBorrowDays();

    public abstract double getFinePerDay();

    // Проверка возможности взять книгу
    public boolean canBorrow() {
        return borrowedBooks.size() < getMaxBooks();
    }

    // Геттеры и сеттеры
    public String getName() {
        return name;
    }

    public String getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public List<String> getBorrowedBooks() {
        return borrowedBooks;
    }


}
