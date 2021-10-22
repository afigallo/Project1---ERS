package dao;

import junit.framework.TestCase;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class UserDaoImplTest extends TestCase {

    UserDao userDao;

    @BeforeEach
    public void setUp() {
        userDao = new UserDaoImpl();
    }

    @Test
    void getUser() {
        //Assign
        User user = new User();
        user.setUsername("emp1");
        user.setPassword("password");
        User expectedResult = user;

        //Act
        User actualResult = userDao.getUser(user);

        //Assert
        assertEquals(expectedResult.getUsername(), actualResult.getUsername());
        System.out.println(actualResult);
    }

    @Test
    void createUser() { //test will create a manipulation in data
        //Assign
        User user = new User(null, //usersId may be set to null; schema will assign an ID
                "mgr4",
                "password",
                "Emma",
                "Stone",
                "estone@test.com",
                2);
        User expectedResult = user;

        //Act
        User actualResult = userDao.createUser(user);

        //Assert
        assertEquals(expectedResult.toString(), actualResult.toString());
        System.out.println(expectedResult == actualResult);
    }

    @Test
    void createUserWithExistingUsername() {
        //Assign
        User user = new User(null,
                "emp1", //username exists in database
                "password",
                "Audrey",
                "Vegaso",
                "avegaso@test.com",
                1);
        User expectedResult = null;

        //Act
        User actualResult = userDao.createUser(user);

        //Assert
        assertEquals(expectedResult, actualResult);
        System.out.println(actualResult);
    }

    @Test
    void createUserWithExistingEmail() {
        //Assign
        User user = new User(null,
                "emp3",
                "password",
                "Alex",
                "River",
                "ariver@test.com", //email exists in database
                1);
        User expectedResult = null;

        //Act
        User actualResult = userDao.createUser(user);

        //Assert
        assertEquals(expectedResult, actualResult);
        System.out.println(actualResult);
    }
}