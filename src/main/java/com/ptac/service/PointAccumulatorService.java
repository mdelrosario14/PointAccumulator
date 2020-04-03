package com.ptac.service;

import java.util.Map;

import com.ptac.model.PtacNewTransaction;
import com.ptac.model.PtacUser;

/**
 * Service in getting and storing new transactions.
 * @author Mardolfh Del Rosario
 *
 */
public interface PointAccumulatorService {

    /**
     * Get all customer's consolidated points.
     * @return username, tota amount points.
     * @throws Business logic exception.
     */
    Map<String, Double> getAllCustomerRewardPoints() throws Exception;

    /**
     * Saves the customer's points based on amount transaction.
     * @param newTx                 New transaction reference.
     * @param amountValue           Total amount of transaction
     * @throws Business logic exception.
     */
    void saveCustomerTransactionPoints(PtacNewTransaction newTx, Double amountValue) throws Exception;

    /**
     * Create new customer.
     * @param user                  New Ptac User.
     * @throws Business logic exception.
     */
    void createCustomerData(PtacUser user) throws Exception;
}
