package dao;

import model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;

/**
 * UserDaoImpl contains the implemented methods from the UserDao Interface
 *
 * @author      Alberto Figallo
 * @version     1.5
 * @since       2021-09-07
 */
public class UserDaoImpl implements UserDao {
    public UserDaoImpl(){
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    private static final Logger logger = LogManager.getLogger();

    /**
     * @param user user object to set the username and password for the sql WHERE statement
     * @return returnUser which is a new user object
     * */
    @Override
    public User getUser(User user) {
        User returnUser = new User();

        try(Connection connection = DriverManager.getConnection(ConnUtilities.url,ConnUtilities.username,ConnUtilities.password)) {
            String sql = "SELECT * FROM users WHERE user_username = ? AND user_password = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, user.getUsername());
            ps.setString(2, user.getPassword());
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                returnUser = new User(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getInt(7));
            }
        } catch (SQLException e){
            logger.error("SQLException on getUser:"+e.getMessage());
            e.printStackTrace();
        }
        return returnUser;
    }

    /**
     * @param userId Integer used in the sql WHERE statement to fetch the precise record from
     * the users table
     * @return returnUser which is a new user object of the userId
     * */
    @Override
    public User getUserById(Integer userId) {
        User returnUser = new User();

        try(Connection connection = DriverManager.getConnection(ConnUtilities.url,ConnUtilities.username,ConnUtilities.password)) {
            String sql = "SELECT * FROM users WHERE user_id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                returnUser = new User(rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getInt(7));
            }
        } catch (SQLException e){
            logger.error("SQLException on getUserById:"+e.getMessage());
            e.printStackTrace();
        }
        return returnUser;
    }

    /**
     * @param user user object to set all the properties of the User
     * @return user which is a new user object
     * */
    @Override
    public User createUser(User user) {
        try(Connection connection = DriverManager.getConnection(ConnUtilities.url,ConnUtilities.username,ConnUtilities.password)) {
            String sql = "INSERT INTO users VALUES (DEFAULT,?,?,?,?,?,?);";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setString(1,user.getUsername());
            ps.setString(2,user.getPassword());
            ps.setString(3,user.getFirstName());
            ps.setString(4,user.getLastName());
            ps.setString(5,user.getEmail());
            ps.setInt(6,user.getRoleId());

            ps.executeUpdate();
        } catch (SQLException e){
            logger.error("SQLException on createUser:"+e.getMessage());
            e.printStackTrace();
            return null;
        }
        return user;
    }
}
