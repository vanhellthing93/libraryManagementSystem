package sf.mephi.study.lms.repository;

import sf.mephi.study.lms.models.Book;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BookRepositoryImpl implements BookRepository {
    private Map<String, Book> books = new HashMap<>();

    @Override
    public void addBook(Book book) {
        books.put(book.getIsbn(), book);
    }

    @Override
    public boolean removeBook(String isbn) {
        if (!books.containsKey(isbn)) return false;
        books.remove(isbn);
        return true;
    }

    @Override
    public Book findBook(String isbn) {
        return books.get(isbn);
    }

    @Override
    public List<Book> searchBooks(String query) {
        List<Book> result = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.getTitle().contains(query) || book.getAuthor().contains(query) || book.getIsbn().contains(query) || book.getGenre().contains(query)) {
                result.add(book);
            }
        }
        return result;
    }

    @Override
    public List<Book> getAllBooks() {
        return new ArrayList<>(books.values());
    }

}

