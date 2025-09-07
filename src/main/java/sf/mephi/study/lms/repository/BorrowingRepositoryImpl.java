package sf.mephi.study.lms.repository;

import sf.mephi.study.lms.models.Book;
import sf.mephi.study.lms.models.BorrowingRecord;
import sf.mephi.study.lms.models.User;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class BorrowingRepositoryImpl implements BorrowingRepository {
    private List<BorrowingRecord> borrowingHistory = new ArrayList<>();

    @Override
    public boolean borrowBookAtTime(User user, Book book, LocalDate date) {
        if (!book.isAvailable() || !user.canBorrow()) {
            return false;
        }

        book.setAvailable(false);
        user.getBorrowedBooks().add(book.getIsbn());
        BorrowingRecord record = new BorrowingRecord(user, book, date);
        borrowingHistory.add(record);
        return true;
    }

    @Override
    public boolean borrowBook(User user, Book book) {
        return borrowBookAtTime(user, book, LocalDate.now());
    }



    @Override
    public boolean returnBook(User user, Book book) {
        if (!user.getBorrowedBooks().contains(book.getIsbn())) {
            return false;
        }

        book.setAvailable(true);
        user.getBorrowedBooks().remove(book.getIsbn());
        return true;
    }

    @Override
    public List<BorrowingRecord> getOverdueBooks() {
        List<BorrowingRecord> overdueBooks = new ArrayList<>();
        for (BorrowingRecord record : borrowingHistory) {
            if (record.isOverdue()) {
                overdueBooks.add(record);
            }
        }
        return overdueBooks;
    }

    @Override
    public List<BorrowingRecord> getAllBorrowingRecords() {
        return new ArrayList<>(borrowingHistory);
    }

}
