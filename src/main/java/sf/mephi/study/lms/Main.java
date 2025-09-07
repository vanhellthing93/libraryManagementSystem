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

        // Генерация тестовых книг
        libraryService.addBook("Война и мир", "Лев Толстой", "978-5-389-01123-4", "Роман");
        libraryService.addBook("Преступление и наказание", "Фёдор Достоевский", "978-5-389-01124-1", "Роман");
        libraryService.addBook("Мастер и Маргарита", "Михаил Булгаков", "978-5-389-01125-8", "Фантастика");
        libraryService.addBook("1984", "Джордж Оруэлл", "978-5-389-01126-5", "Антиутопия");

        // Генерация тестовых пользователей
        libraryService.registerUser("Иван Иванов", "ivan.ivanov@example.com", 1); // Студент
        libraryService.registerUser("Мария Петрова", "maria.petrova@example.com", 2); // Преподаватель
        libraryService.registerUser("Алексей Сидоров", "alexey.sidorov@example.com", 3); // Гость

        // Генерация тестовых операций с книгами
        // Получение
        libraryService.borrowBookAtTime(libraryService.findUserIDByName("Иван Иванов"), "978-5-389-01123-4", "01.09.2025");
        libraryService.borrowBookAtTime(libraryService.findUserIDByName("Иван Иванов"), "978-5-389-01124-1", "01.08.2025");
        libraryService.borrowBookAtTime(libraryService.findUserIDByName("Мария Петрова"), "978-5-389-01125-8", "03.08.2025");
        libraryService.borrowBookAtTime(libraryService.findUserIDByName("Алексей Сидоров"), "978-5-389-01126-5", "04.09.2025");
        // Возврат
        libraryService.returnBook(libraryService.findUserIDByName("Иван Иванов"), "978-5-389-01123-4");


        LibraryController controller = new LibraryController(libraryService);
        controller.run();
    }
}