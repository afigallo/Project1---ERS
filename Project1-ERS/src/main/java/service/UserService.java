package service;

import dao.UserDao;
import dao.UserDaoImpl;
import model.User;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

public class UserService {
    static UserDao userDao;

    public UserService(){
        this.userDao = new UserDaoImpl();
    }

    public UserService(UserDao userDao){
        this.userDao = userDao;
    }

    private static final Logger logger = LogManager.getLogger();

    public User loginUser(User user){
        User currentUser = userDao.getUser(user);
        if (currentUser.getUserId() != null){
            System.out.println("Login successful");
            logger.info("Login successful");
            return currentUser;
        } else {
            System.out.println("Invalid username and/or password");
            logger.warn("Invalid username and/or password");
            return null;
        }
    }

    public static User getUserDesc(Integer userId){
        User currentUser = userDao.getUserById(userId);
        if (currentUser.getUserId() != null){
            return currentUser;
        } else {
            logger.info("No user description found for userId:"+userId);
            return null;
        }
    }
}
