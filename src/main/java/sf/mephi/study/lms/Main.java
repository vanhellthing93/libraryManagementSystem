package sf.mephi.study.lms;


import sf.mephi.study.lms.controller.LibraryController;
import sf.mephi.study.lms.repository.*;
import sf.mephi.study.lms.service.LibraryService;


public class Main {
    public static void main(String[] args) {
        BookRepository bookRepository = new BookRepositoryImpl();
        UserRepository userRepository = new UserRepositoryImpl();
        BorrowingRepository borrowingRepository = new BorrowingRepositoryImpl();

        LibraryService libraryService = new LibraryService(bookRepository, userRepository, borrowingRepository);
        LibraryController controller = new LibraryController(libraryService);

        controller.run();
    }
}