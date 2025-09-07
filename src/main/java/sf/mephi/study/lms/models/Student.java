package sf.mephi.study.lms.models;

public class Student extends User {
    public Student(String name, String userId, String email) {
        super(name, userId, email);
    }

    @Override
    public int getMaxBooks() {
        return 3;
    }

    @Override
    public int getBorrowDays() {
        return 14;
    }

    @Override
    public double getFinePerDay() {
        return 1.0;
    }
}