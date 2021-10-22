package controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.Reimbursement;
import model.Response;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import service.MgrReimbService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

/**
 * MgrReimbController contains the methods to receive the request from the front end
 * and send back the response.
 *
 * @author      Alberto Figallo
 * @version     1.5
 * @since       2021-09-07
 */
public class MgrReimbController {
    private static MgrReimbController mgrReimbController;
    MgrReimbService mgrReimbService;

    public MgrReimbController(MgrReimbService mgrReimbService){
        this.mgrReimbService = mgrReimbService;
    }

    private MgrReimbController(){
        this.mgrReimbService = new MgrReimbService();
    }

    public static MgrReimbController getInstance(){
        if (mgrReimbController == null){
            mgrReimbController = new MgrReimbController();
        }
        return mgrReimbController;
    }

    private static final Logger logger = LogManager.getLogger();

    /**
     * @param req First parameter to login method
     * @param resp Second parameter to login method
     */
    public void retrieveEmplTickets(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        Integer statusId = Integer.parseInt(req.getParameter("statusId"));
        List<Reimbursement> employeeTickets = mgrReimbService.getFilteredEmployeeTickets(statusId);

        if (statusId == 0){
            logger.info("MgrReimbController.retrieveEmplTickets - All tickets retrieved");
            out.println(new ObjectMapper().writeValueAsString(new Response("All tickets retrieved", true, employeeTickets)));
        } else {
            logger.warn("MgrReimbController.retrieveEmplTickets - Filtered tickets retrieved");
            out.println(new ObjectMapper().writeValueAsString(new Response("Filtered tickets retrieved", true, employeeTickets)));

        }
    }

    /**
     * @param req First parameter to login method
     * @param resp Second parameter to login method
     */
    public void resolveTicket(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();

        String requestBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

        Reimbursement reimbTicket = new ObjectMapper().readValue(requestBody, Reimbursement.class);

        mgrReimbService.resolveTicket(reimbTicket);
        logger.info("MgrReimbController.resolveTicket - Ticket successfully resolved");
        out.println(new ObjectMapper().writeValueAsString(new Response("Ticket successfully resolved", true, null)));
    }
}
