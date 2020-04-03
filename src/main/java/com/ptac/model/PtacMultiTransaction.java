package com.ptac.model;

/**
 * Request model used for multiple transactions.
 * @author Mardolfh Del Rosario
 *
 */
public class PtacMultiTransaction {
    private PtacUser user;
    private PtacNewTransaction newTx;

    public PtacUser getUser() {
        return user;
    }
    public PtacNewTransaction getNewTx() {
        return newTx;
    }
    public void setUser(PtacUser user) {
        this.user = user;
    }
    public void setNewTx(PtacNewTransaction newTx) {
        this.newTx = newTx;
    }

}
