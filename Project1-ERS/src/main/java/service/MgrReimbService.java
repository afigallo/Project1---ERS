package service;

import dao.MgrReimbDao;
import dao.MgrReimbDaoImpl;
import model.Reimbursement;
import java.util.List;

public class MgrReimbService {
    MgrReimbDao mgrReimbDao;
    DescriptionService descriptionService;

    public MgrReimbService(){
        this.mgrReimbDao = new MgrReimbDaoImpl();
        this.descriptionService = new DescriptionService();
    }

    public MgrReimbService(MgrReimbDao mgrReimbDao){
        this.mgrReimbDao = mgrReimbDao;
    }

    public List<Reimbursement> getAllEmployeesTickets(){
        return mgrReimbDao.viewAllTickets();
    }

    public List<Reimbursement> getFilteredEmployeeTickets(Integer statusId){
        List<Reimbursement> mgr = mgrReimbDao.viewFilteredTickets(statusId);
        for (int i = 0; i < mgr.size(); i++) {
            mgr.get(i).setAuthorDetails(UserService.getUserDesc(mgr.get(i).getAuthor()));
            mgr.get(i).setStatus(descriptionService.status.get(mgr.get(i).getStatusId()));
            mgr.get(i).setType(descriptionService.types.get(mgr.get(i).getTypeId()));
        }
        return mgr;
    }

    public void resolveTicket(Reimbursement reimbTicket){
        mgrReimbDao.resolveTicket(reimbTicket);
    }
}
