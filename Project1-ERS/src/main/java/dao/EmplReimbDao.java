package dao;

import model.Reimbursement;
import java.util.List;

public interface EmplReimbDao {
    List<Reimbursement> getUserTickets(Integer userId);
    Reimbursement addReimbursementTicket(Reimbursement reimbursement);
}
