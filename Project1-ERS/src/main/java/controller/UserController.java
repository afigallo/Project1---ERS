package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Response;
import model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.UserService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

/**
 * UserController contains the methods to receive the request from the front end
 * and send back the response.
 *
 * @author      Alberto Figallo
 * @version     1.5
 * @since       2021-09-07
 */
public class UserController {
    private static UserController userController;
    UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    private UserController(){
        userService = new UserService();
    }

    public static UserController getInstance(){
        if (userController == null){
            userController = new UserController();
        }
        return userController;
    }

    private static final Logger logger = LogManager.getLogger();

    /**
     * @param req First parameter to login method
     * @param resp Second parameter to login method
     */
    public void login(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        String requestBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        User user = new ObjectMapper().readValue(requestBody, User.class);
        User currentUser = userService.loginUser(user);

        if (currentUser != null){
            HttpSession httpSession = req.getSession(true);
            httpSession.setAttribute("activeUser", currentUser);
            logger.info("UserController.login - Login Successful");
            out.println(new ObjectMapper().writeValueAsString(new Response("Login Successful", true, currentUser)));
        } else {
            logger.warn("UserController.login - Invalid username and/or password");
            out.println(new ObjectMapper().writeValueAsString(new Response("Invalid username and/or password", false, null)));
        }
    }

    /**
     * @param req First parameter to login method
     * @param resp Second parameter to login method
     */
    public void checkSession(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        User activeUser = (User) req.getSession().getAttribute("activeUser");

        if (activeUser != null){
            logger.info("UserController.checkSession - Session found");
            out.println(new ObjectMapper().writeValueAsString(new Response("Session found", true, activeUser)));
        } else {
            logger.warn("UserController.checkSession - Session not found");
            out.println(new ObjectMapper().writeValueAsString(new Response("Session not found", false, null)));
        }
    }

    /**
     * @param req First parameter to login method
     * @param resp Second parameter to login method
     */
    public void logout(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        User activeUser = (User) req.getSession().getAttribute("activeUser");

        if (activeUser != null){
            req.getSession().setAttribute("activeUser", null);
            logger.info("UserController.logout - Session terminated");
            out.println(new ObjectMapper().writeValueAsString(new Response("Session terminated", true, null)));
        } else {
            logger.warn("UserController.logout - No session found to terminate");
            out.println(new ObjectMapper().writeValueAsString(new Response("No session found to terminate", false, null)));
        }
    }
}
