package dao;

import model.User;

public interface UserDao {
    User getUser(User user);
    User createUser(User user);
    User getUserById(Integer userId);
}
