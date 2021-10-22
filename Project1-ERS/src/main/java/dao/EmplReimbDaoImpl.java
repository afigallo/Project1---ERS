package dao;

import model.Reimbursement;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * EmplReimbDaoImpl contains the implemented methods from the EmplReimbDao Interface
 *
 * @author      Alberto Figallo
 * @version     1.5
 * @since       2021-09-07
 */
public class EmplReimbDaoImpl implements EmplReimbDao {
    public EmplReimbDaoImpl(){
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    private static final Logger logger = LogManager.getLogger();

    /**
     * @param userId Integer used in the sql statement to fetch the precise record
     * from the reimbursement_tickets view table WHERE author = userId
     * @return tickets List containing all tickets of the provided userId
     * */
    @Override
    public List<Reimbursement> getUserTickets(Integer userId) {
        List<Reimbursement> tickets = new ArrayList<>();
        try(Connection connection = DriverManager.getConnection(ConnUtilities.url,ConnUtilities.username,ConnUtilities.password)) {
            String sql = "SELECT * FROM reimbursement_tickets WHERE author = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,userId);
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
            logger.error("SQLException on getUserTickets:"+e.getMessage());
            e.printStackTrace();
        }
        return tickets;
    }

    /**
     * @param ticket Reimbursement object to get the Amount, Description, Author and
     * TypeId to INSERT a new record in the reimbursements table
     * @return ticket with the values after creating the new record
     * */
    @Override
    public Reimbursement addReimbursementTicket(Reimbursement ticket) {
        try(Connection connection = DriverManager.getConnection(ConnUtilities.url,ConnUtilities.username,ConnUtilities.password)) {
            String sql = "INSERT INTO reimbursements VALUES (DEFAULT,?,DEFAULT,NULL,?,?,NULL,DEFAULT,?)";
            PreparedStatement ps = connection.prepareStatement(sql);

            ps.setDouble(1,ticket.getAmount());
            ps.setString(2,ticket.getDescription());
            ps.setInt(3,ticket.getAuthor());
            ps.setInt(4,ticket.getTypeId());

            ps.executeUpdate();
        } catch (SQLException e){
            logger.error("SQLException on addReimbursementTicket:"+e.getMessage());
            e.printStackTrace();
        }
        return ticket;
    }
}
