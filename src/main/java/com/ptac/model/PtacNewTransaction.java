package com.ptac.model;

/**
 * Request model to use for persisting customer points.
 * @author Mardolfh Del Rosario
 *
 */
public class PtacNewTransaction {
    private String userName;
    private String amount;
    private Double addedPts;

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getAmount() {
        return amount;
    }
    public void setAmount(String amount) {
        this.amount = amount;
    }
    public Double getAddedPts() {
        return addedPts;
    }
    public void setAddedPts(Double addedPts) {
        this.addedPts = addedPts;
    }
}
