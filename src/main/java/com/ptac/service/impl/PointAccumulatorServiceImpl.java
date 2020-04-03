package com.ptac.service.impl;

import static com.ptac.util.PointAccumulatorStaticData.PTAC_ID;
import static com.ptac.util.PointAccumulatorStaticData.PTAC_TX_DATA;
import static com.ptac.util.PointAccumulatorStaticData.PTAC_USERS;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.ptac.model.PtacNewTransaction;
import com.ptac.model.PtacTransaction;
import com.ptac.model.PtacUser;
import com.ptac.service.PointAccumulatorService;

/**
 * Implements the PointAccumulatorService.
 * @author Mardolfh Del Rosario
 *
 */
@Service
public class PointAccumulatorServiceImpl implements PointAccumulatorService {

    @Value("${two.points}")
    private Double twoPointsDollarAmount;

    @Value("${one.point}")
    private Double onePointDollarAmount;

    @Override
    public Map<String, Double> getAllCustomerRewardPoints() throws Exception {
        Map<String, Double> allCustomerRewardPoints = new HashMap<>();

        if (PTAC_TX_DATA != null && !PTAC_TX_DATA.isEmpty()) {
            PTAC_TX_DATA.forEach((k,v) -> {
               PtacTransaction ptx = v;
               PtacUser user = ptx.getUser();
               if (allCustomerRewardPoints.get(user.getUserName()) != null) {
                   Double val = allCustomerRewardPoints.get(user.getUserName());
                   Double addVal = ptx.getPoints();
                   Double sum = Double.valueOf((val + addVal));
                   allCustomerRewardPoints.put(user.getUserName(),
                           BigDecimal.valueOf(sum).setScale(2, RoundingMode.HALF_UP).doubleValue());
               } else {
                   allCustomerRewardPoints.put(user.getUserName(), ptx.getPoints());
               }
            });
        } else {
            throw new Exception("No data detected in static mem.");
        }
        return allCustomerRewardPoints;
    }

    @Override
    public void saveCustomerTransactionPoints(PtacNewTransaction newTx, Double amountValue) throws Exception {
        Double pts = this.getPointsFromAmount(amountValue);
        List<PtacUser> ptacUsers = this.validateUser(PTAC_USERS,
                (PtacUser user) -> user.getUserName().equals(newTx.getUserName()));
        if (ptacUsers == null || ptacUsers.isEmpty()) {
            throw new Exception("User " + newTx.getUserName() + " does not exists.");
        }
        if (!pts.equals(Double.valueOf(0.0))) {
            PtacTransaction ptx = new PtacTransaction(LocalDate.now(), ptacUsers.get(0), pts);
            PTAC_TX_DATA.put(PTAC_ID++, ptx);
        }
    }

    /**
     * Get equivalent points from customer's transaction amount.
     * @param amountValue               Total amount of the transaction.
     * @return points.
     */
    private Double getPointsFromAmount(Double amountValue) {
        Double pts = Double.valueOf(0.0);
        if (amountValue < this.onePointDollarAmount) {
            return pts;
        } else {
            pts += this.onePointDollarAmount;
        }

        if (amountValue < this.twoPointsDollarAmount) {
            return pts;
        }

        pts += (2 * (amountValue - this.twoPointsDollarAmount));


        return pts;
    }

    /**
     * Returns if a certain user exists.
     *
     * @param userSet               PTAC_USERS.
     * @param predicat              Predicate statement.
     * @return                      List of valid user.
     */
    private List<PtacUser> validateUser(Set<PtacUser> userSet, Predicate<PtacUser> predicat) {
        List<PtacUser> userResult = null;
        userResult = userSet.stream()
                .filter(user -> predicat.test(user)).collect(Collectors.toList());

        return userResult;
    }

}