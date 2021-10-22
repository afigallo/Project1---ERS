package dao;

import junit.framework.TestCase;
import model.Reimbursement;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class MgrReimbDaoImplTest extends TestCase {
    MgrReimbDao finMgrReimbDao;

    @BeforeEach
    public void setUp() {
        finMgrReimbDao = new MgrReimbDaoImpl();
    }

    public void testViewAllTickets() {
        //Assign
        List<Reimbursement> unexpectedResult = new ArrayList<>();

        //Act
        List<Reimbursement> actualResult = finMgrReimbDao.viewAllTickets();

        //Assert
        assertNotEquals(unexpectedResult, actualResult);
        for (Reimbursement ticket : actualResult) {
            System.out.println(ticket);
        }
    }

    public void testViewFilteredTickets() {
        Integer ticketStatusId = 2;
        //Assign
        List<Reimbursement> unexpectedResult = new ArrayList<>();

        //Act
        List<Reimbursement> actualResult = finMgrReimbDao.viewFilteredTickets(ticketStatusId);

        //Assert
        assertNotEquals(unexpectedResult, actualResult);
        for (Reimbursement ticket : actualResult) {
            System.out.println(ticket);
        }
    }

    public void testResolveTicket() {
        Integer ticketId = 9;
        Integer resolverId = 2;
        Integer statusId = 3;

        Reimbursement ticket = new Reimbursement();
        ticket.setResolver(resolverId);
        ticket.setStatusId(statusId);
        ticket.setReimbursementId(ticketId);
        //Assign
        Reimbursement expectedResult = ticket;

        //Act
        Reimbursement actualResult = finMgrReimbDao.resolveTicket(ticket);

        //Assert
        assertEquals(expectedResult, actualResult);
        System.out.println(actualResult);
    }
}