package sf.mephi.study.lms.repository;

import sf.mephi.study.lms.models.User;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserRepositoryImpl implements UserRepository {
    private Map<String, User> users = new HashMap<>();

    @Override
    public void registerUser(User user) {
        users.put(user.getUserId(), user);
    }

    @Override
    public User findUser(String userId) {
        return users.get(userId);
    }

    @Override
    public User findUserByName(String name) {
        for (User user : users.values()) {
            if (user.getName().equals(name)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(users.values());
    }
}

