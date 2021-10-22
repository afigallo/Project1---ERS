package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Reimbursement;
import model.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.EmplReimbService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.stream.Collectors;

/**
 * EmplReimbController contains the methods to receive the request from the front end
 * and send back the response.
 *
 * @author      Alberto Figallo
 * @version     1.5
 * @since       2021-09-07
 */
public class EmplReimbController {
    private static EmplReimbController emplReimbController;
    EmplReimbService emplReimbService;

    public EmplReimbController(EmplReimbService emplReimbService){
        this.emplReimbService = emplReimbService;
    }

    private EmplReimbController(){
        emplReimbService = new EmplReimbService();
    }

    public static EmplReimbController getInstance(){
        if (emplReimbController == null){
            emplReimbController = new EmplReimbController();
        }
        return emplReimbController;
    }

    private static final Logger logger = LogManager.getLogger();

    /**
     * @param req First parameter to login method
     * @param resp Second parameter to login method
     */
    public void getUserTickets(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        Integer userId = Integer.parseInt(req.getParameter("id"));
        logger.info("EmplReimbController.getUserTickets - Tickets retrieved");
        out.println(new ObjectMapper().writeValueAsString(new Response("Tickets retrieved", true, emplReimbService.getUserTickets(userId))));
    }

    /**
     * @param req First parameter to login method
     * @param resp Second parameter to login method
     */
    public void addTicket(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        String requestBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        Reimbursement reimbursement = new ObjectMapper().readValue(requestBody, Reimbursement.class);

        emplReimbService.addReimbTicket(reimbursement);
        logger.info("EmplReimbController.addTicket - Your ticket was successfully generated");
        out.println(new ObjectMapper().writeValueAsString(new Response("Your ticket was successfully generated", true, null)));
    }
}
