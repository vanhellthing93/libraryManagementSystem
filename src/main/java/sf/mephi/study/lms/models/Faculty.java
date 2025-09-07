package sf.mephi.study.lms.models;

/*
Faculty - в данном случае рассматривается как преподавательский состав, а не факультет. Если рассматривать его, как
факультет, то наследование было бы совсем иным. Факультет тоже бы расширял класс Юзера, но содержал бы список студентов.
Класс студента содержал бы ссылку на факультет. Методы по получению и сдаче книги увеличивали (уменьшали) бы энкаунтер
как в классе студента, так и в классе факультета. И проверки тоже бы должны были проверять, как то, так и другое.
 */

public class Faculty extends User {
    public Faculty(String name, String userId, String email) {
        super(name, userId, email);
    }

    @Override
    public int getMaxBooks() {
        return 10;
    }

    @Override
    public int getBorrowDays() {
        return 30;
    }

    @Override
    public double getFinePerDay() {
        return 1.0;
    }
}
