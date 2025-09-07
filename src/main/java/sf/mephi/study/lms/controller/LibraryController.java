package sf.mephi.study.lms.controller;

import sf.mephi.study.lms.service.LibraryService;

import java.util.List;
import java.util.Scanner;

public class LibraryController {
    private LibraryService libraryService;
    private Scanner scanner;

    public LibraryController(LibraryService libraryService) {
        this.libraryService = libraryService;
        this.scanner = new Scanner(System.in);
    }

    public void run() {
        while (true) {
            showMenu();
            int choice = getIntInput("Введите выбор: ");

            switch (choice) {
                case 1:
                    handleBookManagement();
                    break;
                case 2:
                    handleUserManagement();
                    break;
                case 3:
                    handleBorrowing();
                    break;
                case 0:
                    System.out.println("Выход...");
                    return;
                default:
                    System.out.println("Неверный выбор.");
            }
        }
    }

    private void showMenu() {
        System.out.println("\n=== Управление библиотекой ===");
        System.out.println("1. Управление книгами");
        System.out.println("2. Управление пользователями");
        System.out.println("3. Операции выдачи и возврата");
        System.out.println("0. Выход");
    }

    private int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Пожалуйста, введите корректное число.");
            }
        }
    }

    private void handleBookManagement() {
        System.out.println("\n=== Управление книгами ===");
        System.out.println("1. Добавить книгу");
        System.out.println("2. Удалить книгу");
        System.out.println("3. Поиск книг");
        System.out.println("0. Назад");
        int choice = getIntInput("Введите выбор: ");

        switch (choice) {
            case 1:
                System.out.print("Введите название: ");
                String title = scanner.nextLine();
                System.out.print("Введите автора: ");
                String author = scanner.nextLine();
                System.out.print("Введите ISBN: ");
                String isbn = scanner.nextLine();
                System.out.print("Введите жанр: ");
                String genre = scanner.nextLine();
                libraryService.addBook(title, author, isbn, genre);
                System.out.println("Книга добавлена.");
                break;
            case 2:
                System.out.print("Введите ISBN книги для удаления: ");
                isbn = scanner.nextLine();
                if (libraryService.removeBook(isbn)) {
                    System.out.println("Книга удалена.");
                } else {
                    System.out.println("Книга не найдена.");
                }
                break;
            case 3:
                System.out.print("Введите запрос для поиска: ");
                String query = scanner.nextLine();
                List<String> searchResults = libraryService.searchBooks(query);
                if (searchResults.isEmpty()) {
                    System.out.println("Книги не найдены.");
                } else {
                    for (String bookInfo : searchResults) {
                        System.out.println(bookInfo);
                    }
                }
                break;
            case 0:
                return;
            default:
                System.out.println("Неверный выбор.");
        }
    }

    private void handleUserManagement() {
        System.out.println("\n=== Управление пользователями ===");
        System.out.println("1. Зарегистрировать пользователя");
        System.out.println("2. Поиск пользователя");
        System.out.println("0. Назад");
        int choice = getIntInput("Введите выбор: ");

        switch (choice) {
            case 1:
                System.out.print("Введите имя: ");
                String name = scanner.nextLine();
                System.out.print("Введите ID пользователя: ");
                String userId = scanner.nextLine();
                System.out.print("Введите email: ");
                String email = scanner.nextLine();
                int userType;
                while (true) {
                    System.out.print("Тип пользователя (1 - Студент, 2 - Преподаватель, 3 - Гость): ");
                    userType = getIntInput("");

                    if (userType == 1 || userType == 2 || userType == 3) {
                        break;
                    } else {
                        System.out.println("Некорректный тип пользователя. Пожалуйста, введите 1, 2 или 3.");
                    }
                }
                libraryService.registerUser(name, userId, email, userType);
                System.out.println("Пользователь зарегистрирован.");
                break;
            case 2:
                System.out.print("Введите ID пользователя: ");
                userId = scanner.nextLine();
                String userString = libraryService.findUser(userId);
                if (userString == null) {
                    System.out.println("Пользователь не найден.");
                } else {
                    System.out.println(userString);
                }
                break;
            case 0:
                return;
            default:
                System.out.println("Неверный выбор.");
        }
    }

    private void handleBorrowing() {
        System.out.println("\n=== Операции выдачи и возврата ===");
        System.out.println("1. Выдать книгу");
        System.out.println("2. Вернуть книгу");
        System.out.println("3. Просроченные книги");
        System.out.println("0. Назад");
        int choice = getIntInput("Введите выбор: ");

        switch (choice) {
            case 1:
                System.out.print("Введите ID пользователя: ");
                String userId = scanner.nextLine();
                System.out.print("Введите ISBN книги: ");
                String isbn = scanner.nextLine();
                if (libraryService.borrowBook(userId, isbn)) {
                    System.out.println("Книга выдана.");
                } else {
                    System.out.println("Не удалось выдать книгу.");
                }
                break;
            case 2:
                System.out.print("Введите ID пользователя: ");
                userId = scanner.nextLine();
                System.out.print("Введите ISBN книги: ");
                isbn = scanner.nextLine();
                if (libraryService.returnBook(userId, isbn)) {
                    System.out.println("Книга возвращена.");
                } else {
                    System.out.println("Не удалось вернуть книгу.");
                }
                break;
            case 3:
                List<String> overdueBooks = libraryService.getOverdueBooks();
                if (overdueBooks.isEmpty()) {
                    System.out.println("Просроченных книг нет.");
                } else {
                    for (String bookInfo : overdueBooks) {
                        System.out.println(bookInfo);
                    }
                }
                break;
            case 0:
                return;
            default:
                System.out.println("Неверный выбор.");
        }
    }
}

