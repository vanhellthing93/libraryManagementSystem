package sf.mephi.study.lms.models;

public class Book {
    private String isbn;
    private String title;
    private String author;
    private String genre;
    private boolean isAvailable;

    public Book(String isbn, String title, String author, String genre) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.isAvailable = true;
    }


    public String getIsbn() {
        return isbn;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public String toString() {
        return String.format(
                "ISBN: %s\nНазвание: %s\nАвтор: %s\nЖанр: %s\nДоступна: %s",
                isbn, title, author, genre, isAvailable ? "Да" : "Нет"
        );
    }


}
