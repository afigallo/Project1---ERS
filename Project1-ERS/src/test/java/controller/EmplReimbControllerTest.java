package controller;

import junit.framework.TestCase;
import model.Reimbursement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import service.EmplReimbService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class EmplReimbControllerTest extends TestCase {

    EmplReimbController emplReimbController;
    EmplReimbService emplReimbService = Mockito.mock(EmplReimbService.class);
    HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
    HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);

    @BeforeEach
    void setup() {
        emplReimbController = new EmplReimbController(emplReimbService);
    }

    @Test
    void getUserTickets() throws IOException {
        List<Reimbursement> userReimbursements = new ArrayList<>();
        Reimbursement reimbursement = new Reimbursement(
                75.00,
                "food at restaurant",
                3,
                3);
        userReimbursements.add(reimbursement);
        Integer userId = 1;
        Mockito.when(req.getParameter("id")).thenReturn(String.valueOf(userId));
        Mockito.when(emplReimbService.getUserTickets(userId)).thenReturn(userReimbursements);

        StringWriter stringWriter = new StringWriter();
        PrintWriter out = new PrintWriter(stringWriter);
        Mockito.when(resp.getWriter()).thenReturn(out);

        //Act
        emplReimbController.getUserTickets(req, resp);

        //Assert
        Mockito.verify(req, Mockito.times(1)).getParameter("id");
        Mockito.verify(emplReimbService, Mockito.times(1)).getUserTickets(userId);
    }
}