package controller;

import junit.framework.TestCase;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.stream.Collectors;

public class UserControllerTest extends TestCase {

    UserController userController;
    @Mock
    UserService userService = Mockito.mock(UserService.class);
    @Mock
    HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
    @Mock
    HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);

    @BeforeEach
    public void setUp() {
        userController = new UserController(userService);
    }

    @Test
    void login() throws IOException, NullPointerException {
        //Assign
        User user = new User();
        user.setUsername("emp1");
        user.setPassword("password");

        Mockito.when(req.getReader().lines().collect(Collectors.joining(System.lineSeparator()))).thenReturn(user.toString()); //NullPointerException

        StringWriter stringWriter = new StringWriter();
        PrintWriter out = new PrintWriter(stringWriter);
        Mockito.when(resp.getWriter()).thenReturn(out);

        //Act
        UserController.getInstance().login(req, resp);
        //Assert
        System.out.println(user.toString());
        Mockito.verify(req, Mockito.times(1)).getReader().lines().collect(Collectors.joining(System.lineSeparator()));
    }
}