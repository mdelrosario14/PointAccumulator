package com.ptac.util;

import com.ptac.model.PtacMultiTransaction;
import com.ptac.model.PtacNewTransaction;
import com.ptac.model.PtacUser;

/**
 * Static Utility class used for the PointAccumulator app.
 * @author Mardolfh Del Rosario
 *
 */
public class PointAccumulatorUtil {

    /**
     * Validate Point Accumulator's required fields.
     *
     * @param o                 Model reference to check.
     * @return                  true/false.
     * @throws Exception        Model not supported.
     */
    public static boolean isModelValid(Object o) throws Exception {
        if (o == null) {
            return false;
        }

        if (o instanceof PtacUser) {
            PtacUser user = (PtacUser) o;
            return isPtacUserValid(user);

        } else if (o instanceof PtacMultiTransaction) {
            PtacMultiTransaction pMultx = (PtacMultiTransaction) o;
            if (pMultx != null && pMultx.getUser() != null && pMultx.getNewTx() != null) {
                return isPtacUserValid(pMultx.getUser()) && isPtacNewTransactionValid(pMultx.getNewTx());
            } else {
                return false;
            }
        } else if (o instanceof PtacNewTransaction) {
            PtacNewTransaction nTx = (PtacNewTransaction) o;
            return isPtacNewTransactionValid(nTx);
        } else {
            throw new Exception("Model not supported.");
        }

    }

    /**
     * Validates the PtacUser model.
     * @param user PtacUser reference
     * @return true/false
     */
    private static boolean isPtacUserValid(PtacUser user) {
        if (user != null && user.getUserName() != null && user.getPassword() != null) {
            return !(user.getUserName().isEmpty() || user.getPassword().isEmpty());
        } else {
            return false;
        }
    }

    /**
     * Validates the PtacNewTransaction model.
     * @param pNtx PtacNewTransaction reference
     * @return true/false
     */
    private static boolean isPtacNewTransactionValid(PtacNewTransaction pNtx) {
        if (pNtx != null && pNtx.getUserName() != null && pNtx.getAmount() != null) {
            return !(pNtx.getUserName().isEmpty() || pNtx.getAmount().isEmpty());
        } else {
            return false;
        }
    }
}
