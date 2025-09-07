package sf.mephi.study.lms.repository;


import sf.mephi.study.lms.models.User;

import java.util.List;

public interface UserRepository {
    void registerUser(User user);

    User findUser(String userId);

    User findUserByName(String name);

    List<User> getAllUsers();
}
