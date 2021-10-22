package service;

import dao.EmplReimbDao;
import dao.EmplReimbDaoImpl;
import model.Reimbursement;
import java.util.List;

public class EmplReimbService {
    EmplReimbDao emplReimbDao;
    DescriptionService descriptionService;

    public EmplReimbService(){
        this.emplReimbDao = new EmplReimbDaoImpl();
        this.descriptionService = new DescriptionService();
    }

    public EmplReimbService(EmplReimbDao emplReimbDao){
        this.emplReimbDao = emplReimbDao;
    }

    public List<Reimbursement> getUserTickets(Integer userId){
        List<Reimbursement> emp = emplReimbDao.getUserTickets(userId);
        for (int i = 0; i < emp.size(); i++) {
            emp.get(i).setResolverDetails(UserService.getUserDesc(emp.get(i).getResolver()));
            emp.get(i).setStatus(descriptionService.status.get(emp.get(i).getStatusId()));
            emp.get(i).setType(descriptionService.types.get(emp.get(i).getTypeId()));
        }
        return emp;
    }

    public Reimbursement addReimbTicket(Reimbursement reimbursement){
        return emplReimbDao.addReimbursementTicket(reimbursement);
    }
}
