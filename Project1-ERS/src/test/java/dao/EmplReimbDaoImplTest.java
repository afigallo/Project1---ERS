package dao;

import junit.framework.TestCase;
import model.Reimbursement;
import org.junit.jupiter.api.BeforeEach;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

public class EmplReimbDaoImplTest extends TestCase {

    EmplReimbDao emplReimbDao;

    @BeforeEach
    public void setUp() {
        emplReimbDao = new EmplReimbDaoImpl();
    }

    public void testGetUserTickets() {
        //Assign
        Integer userId = 1;
        List<Reimbursement> unexpectedResult = new ArrayList<>();

        //Act
        List<Reimbursement> actualResult = emplReimbDao.getUserTickets(userId);

        //Assert
        assertNotEquals(unexpectedResult, actualResult);
        for(Reimbursement ticket : actualResult) {
            System.out.println(ticket);
        }
    }

    public void testAddReimbursementTicket() {
        //Assign
        Reimbursement ticket = new Reimbursement(
                100.25,
                "Restaurant dinner",
                3,
                3);
        Reimbursement expectedResult = ticket;

        //Act
        Reimbursement actualResult = emplReimbDao.addReimbursementTicket(ticket);

        //Assert
        assertEquals(expectedResult, actualResult);
        System.out.println(actualResult);
    }
}