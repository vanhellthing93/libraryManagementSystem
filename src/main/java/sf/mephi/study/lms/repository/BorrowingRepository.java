package sf.mephi.study.lms.repository;

import sf.mephi.study.lms.models.Book;
import sf.mephi.study.lms.models.BorrowingRecord;
import sf.mephi.study.lms.models.User;

import java.util.List;

public interface BorrowingRepository {
    boolean borrowBook(User user, Book book);

    boolean returnBook(User user, Book book);

    List<BorrowingRecord> getOverdueBooks();
}