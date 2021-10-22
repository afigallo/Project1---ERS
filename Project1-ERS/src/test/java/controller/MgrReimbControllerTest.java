package controller;

import junit.framework.TestCase;
import model.Reimbursement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import service.MgrReimbService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public class MgrReimbControllerTest extends TestCase {

    MgrReimbController finMgrReimbController;
    MgrReimbService finMgrReimbService = Mockito.mock(MgrReimbService.class);
    HttpServletRequest req = Mockito.mock(HttpServletRequest.class);
    HttpServletResponse resp = Mockito.mock(HttpServletResponse.class);

    @BeforeEach
    public void setUp() {
        finMgrReimbController = new MgrReimbController(finMgrReimbService);
    }

    @Test
    void retrieveAllEmplTickets() throws IOException {
        List<Reimbursement> userReimbursements = new ArrayList<>();
        Reimbursement reimbursement = new Reimbursement(
                125.75,
                "food at restaurant",
                3,
                3);
        userReimbursements.add(reimbursement);
        Integer statusId = 0;
        Mockito.when(req.getParameter("statusId")).thenReturn(String.valueOf(statusId));
        Mockito.when(finMgrReimbService.getAllEmployeesTickets()).thenReturn(userReimbursements);

        StringWriter stringWriter = new StringWriter();
        PrintWriter out = new PrintWriter(stringWriter);
        Mockito.when(resp.getWriter()).thenReturn(out);

        //Act
        finMgrReimbController.retrieveEmplTickets(req, resp);

        //Assert
        Mockito.verify(req, Mockito.times(1)).getParameter("statusId");
        Mockito.verify(finMgrReimbService, Mockito.times(1)).getAllEmployeesTickets();
    }

    @Test
    void retrieveFilteredEmplTickets() throws IOException {
        List<Reimbursement> userReimbursements = new ArrayList<>();
        Reimbursement reimbursement = new Reimbursement(
                50.25,
                "food at hotel",
                3,
                3);
        reimbursement.setStatusId(2);
        userReimbursements.add(reimbursement);
        Integer statusId = 2;
        Mockito.when(req.getParameter("statusId")).thenReturn(String.valueOf(statusId));
        Mockito.when(finMgrReimbService.getFilteredEmployeeTickets(statusId)).thenReturn(userReimbursements);

        StringWriter stringWriter = new StringWriter();
        PrintWriter out = new PrintWriter(stringWriter);
        Mockito.when(resp.getWriter()).thenReturn(out);

        //Act
        finMgrReimbController.retrieveEmplTickets(req, resp);

        //Assert
        Mockito.verify(req, Mockito.times(1)).getParameter("statusId");
        Mockito.verify(finMgrReimbService, Mockito.times(1)).getFilteredEmployeeTickets(statusId);
    }
}