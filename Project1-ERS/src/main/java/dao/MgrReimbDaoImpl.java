package dao;

import model.Reimbursement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * MgrReimbDaoImpl contains the implemented methods from the MgrReimbDao Interface
 *
 * @author      Alberto Figallo
 * @version     1.5
 * @since       2021-09-07
 */
public class MgrReimbDaoImpl implements MgrReimbDao {
    public MgrReimbDaoImpl(){
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    private static final Logger logger = LogManager.getLogger();

    /**
     * @return tickets List of all reimbursement tickets
     * */
    @Override
    public List<Reimbursement> viewAllTickets() {
        List<Reimbursement> tickets = new ArrayList<>();
        try(Connection connection = DriverManager.getConnection(ConnUtilities.url,ConnUtilities.username,ConnUtilities.password)) {
            String sql = "SELECT * FROM reimbursement_tickets;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                tickets.add(new Reimbursement(rs.getInt(1),
                        rs.getDouble(2),
                        rs.getTimestamp(3),
                        rs.getTimestamp(4),
                        rs.getString(5),
                        rs.getInt(6),
                        rs.getInt(7),
                        rs.getInt(8),
                        rs.getInt(9)));
            }

        } catch (SQLException e){
            logger.error("SQLException on viewAllTickets:"+e.getMessage());
            e.printStackTrace();
        }
        return tickets;
    }

    /**
     * @param ticketStatusId to filter the sql statement with the provided ticketStatusId
     * @return tickets List of all reimbursement tickets with the ticketStatusId
     * */
    @Override
    public List<Reimbursement> viewFilteredTickets(Integer ticketStatusId) {
        List<Reimbursement> tickets = new ArrayList<>();
        try(Connection connection = DriverManager.getConnection(ConnUtilities.url,ConnUtilities.username,ConnUtilities.password)) {
            String sql = ticketStatusId == 0 ? "SELECT * FROM reimbursement_tickets;":"SELECT * FROM reimbursement_tickets WHERE reimb_status_id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            if (ticketStatusId != 0){
                ps.setInt(1,ticketStatusId);
            }
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                tickets.add(new Reimbursement(rs.getInt(1),
                        rs.getDouble(2),
                        rs.getTimestamp(3),
                        rs.getTimestamp(4),
                        rs.getString(5),
                        rs.getInt(6),
                        rs.getInt(7),
                        rs.getInt(8),
                        rs.getInt(9)));
            }

        } catch (SQLException e){
            logger.error("SQLException on viewFilteredTickets:"+e.getMessage());
            e.printStackTrace();
        }
        return tickets;
    }

    /**
     * @param reimbTicket Reimbursement object to get the Resolver, StatusId and
     * ReimbursementId to UPDATE the matching record in the reimbursements table
     * @return reimbTicket with the updated changes
     * */
    @Override
    public Reimbursement resolveTicket(Reimbursement reimbTicket) {
        try(Connection connection = DriverManager.getConnection(ConnUtilities.url,ConnUtilities.username,ConnUtilities.password)) {
            String sql = "UPDATE reimbursements SET resolved = DEFAULT, resolver = ?, reimb_status_id = ? WHERE reimb_id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setInt(1,reimbTicket.getResolver());
            ps.setInt(2,reimbTicket.getStatusId());
            ps.setInt(3,reimbTicket.getReimbursementId());

            ps.executeUpdate();
        } catch (SQLException e){
            logger.error("SQLException on resolveTicket:"+e.getMessage());
            e.printStackTrace();
        }
        return reimbTicket;
    }
}
