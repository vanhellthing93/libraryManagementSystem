package sf.mephi.study.lms.repository;

import sf.mephi.study.lms.models.Book;

import java.util.List;

public interface BookRepository {
    void addBook(Book book);

    boolean removeBook(String isbn);

    Book findBook(String isbn);

    List<Book> searchBooks(String query);
}