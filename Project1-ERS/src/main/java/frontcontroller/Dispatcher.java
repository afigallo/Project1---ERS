package frontcontroller;

import controller.EmplReimbController;
import controller.MgrReimbController;
import controller.UserController;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Dispatcher contains the methods to redirect the request and response to
 * the corresponding uri
 *
 * @author      Alberto Figallo
 * @version     1.5
 * @since       2021-09-07
 */
@WebServlet(name="dispatcher", urlPatterns="/api/*")
public class Dispatcher extends HttpServlet {

    /**
     * @param req First parameter to login method
     * @param resp Second parameter to login method
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp){
        String uri = req.getRequestURI();
        System.err.println(uri);
        try {
            switch (uri){
                case "/Project1-ERS/api/check-session":
                    UserController.getInstance().checkSession(req,resp);
                    break;
                case "/Project1-ERS/api/logout":
                    UserController.getInstance().logout(req,resp);
                    break;
                case "/Project1-ERS/api/tickets":
                    EmplReimbController.getInstance().getUserTickets(req,resp);
                    break;
                case "/Project1-ERS/api/mgr-tickets":
                    MgrReimbController.getInstance().retrieveEmplTickets(req,resp);
                    break;
                default:
                    break;
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * @param req First parameter to login method
     * @param resp Second parameter to login method
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp){
        String uri = req.getRequestURI();
        System.err.println(uri);
        try {
            if (uri.equals("/Project1-ERS/api/login"))
                UserController.getInstance().login(req,resp);
            if (uri.equals("/Project1-ERS/api/tickets"))
                EmplReimbController.getInstance().addTicket(req,resp);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * @param req First parameter to login method
     * @param resp Second parameter to login method
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp){
        String uri = req.getRequestURI();
        System.err.println(uri);
        try {
            if (uri.equals("/Project1-ERS/api/resolve-ticket"))
                MgrReimbController.getInstance().resolveTicket(req,resp);
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
