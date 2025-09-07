package sf.mephi.study.lms.repository;


import sf.mephi.study.lms.models.User;

public interface UserRepository {
    void registerUser(User user);

    User findUser(String userId);
}
