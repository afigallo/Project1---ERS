package model;

import java.sql.Timestamp;
/**
 * Reimbursement model class. Contains the properties, getters, setters and toString
 *
 * @author      Alberto Figallo
 * @version     1.5
 * @since       2021-09-07*
 * */
public class Reimbursement {
    private Integer reimbursementId;
    private Double amount;
    private Timestamp submittedOn;
    private Timestamp resolvedOn;
    private String description;
    private Integer author;
    private User authorDetails;
    private Integer resolver;
    private User resolverDetails;
    private Integer statusId;
    private String status;
    private Integer typeId;
    private String type;

    public Reimbursement() {
    }

    /**
     * For creating reimbursement ticket*/
    public Reimbursement(Double amount, String description, Integer author, Integer typeId) {
        this.amount = amount;
        this.description = description;
        this.author = author;
        this.typeId = typeId;
    }

    /**
     * For getting user(s) tickets*/
    public Reimbursement(Integer reimbursementId, Double amount, Timestamp submittedOn, Timestamp resolvedOn, String description, Integer author, Integer resolver, Integer statusId, Integer typeId) {
        this.reimbursementId = reimbursementId;
        this.amount = amount;
        this.submittedOn = submittedOn;
        this.resolvedOn = resolvedOn;
        this.description = description;
        this.author = author;
        this.resolver = resolver;
        this.statusId = statusId;
        this.typeId = typeId;
    }

    public Integer getReimbursementId() {
        return reimbursementId;
    }

    public void setReimbursementId(Integer reimbursementId) {
        this.reimbursementId = reimbursementId;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Timestamp getSubmittedOn() {
        return submittedOn;
    }

    public void setSubmittedOn(Timestamp submittedOn) {
        this.submittedOn = submittedOn;
    }

    public Timestamp getResolvedOn() {
        return resolvedOn;
    }

    public void setResolvedOn(Timestamp resolvedOn) {
        this.resolvedOn = resolvedOn;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getAuthor() {
        return author;
    }

    public void setAuthor(Integer author) {
        this.author = author;
    }

    public User getAuthorDetails() {
        return authorDetails;
    }

    public void setAuthorDetails(User authorDetails) {
        this.authorDetails = authorDetails;
    }

    public Integer getResolver() {
        return resolver;
    }

    public void setResolver(Integer resolver) {
        this.resolver = resolver;
    }

    public User getResolverDetails() {
        return resolverDetails;
    }

    public void setResolverDetails(User resolverDetails) {
        this.resolverDetails = resolverDetails;
    }

    public Integer getStatusId() {
        return statusId;
    }

    public void setStatusId(Integer statusId) {
        this.statusId = statusId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getTypeId() {
        return typeId;
    }

    public void setTypeId(Integer typeId) {
        this.typeId = typeId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Reimbursement{" +
                "reimbursementId=" + reimbursementId +
                ", amount=" + amount +
                ", submittedOn=" + submittedOn +
                ", resolvedOn=" + resolvedOn +
                ", description='" + description + '\'' +
                ", author=" + author +
                ", resolver=" + resolver +
                ", statusId=" + statusId +
                ", typeId=" + typeId +
                '}';
    }
}
