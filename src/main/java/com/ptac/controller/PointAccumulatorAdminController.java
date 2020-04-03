package com.ptac.controller;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ptac.model.PtacNewTransaction;
import com.ptac.service.PointAccumulatorService;

/**
 * Http request on getting/setting user's transactions.
 * @author Mardolfh Del Rosario
 *
 */
@RestController
@RequestMapping("/ptacAdmin/")
public class PointAccumulatorAdminController {

    private static final Logger LOGGER = Logger.getLogger(PointAccumulatorAdminController.class);

    @Autowired
    private PointAccumulatorService pointAccumulatorService;

    /**
     * Get all customer points per username.
     * @return ResponseEntity       reference.
     */
    @GetMapping("/getAllCustomerPoints")
    public ResponseEntity<?> getAllCustomerPoints() {
        try {
            Map<String, Double> response = this.pointAccumulatorService.getAllCustomerRewardPoints();
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            LOGGER.error(e);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to process request.");

    }

    /**
     * Add up customer's points.
     * @param user                  PtacUser reference.
     * @return ResponseEntity       reference.
     */
    @PostMapping("/saveCustomerPoints")
    public ResponseEntity<?> saveCustomerPoints(@RequestBody PtacNewTransaction newTx) {
        try {
            if (newTx != null && newTx.getUserName() != null && !newTx.getUserName().isEmpty()) {
                this.pointAccumulatorService.saveCustomerTransactionPoints(newTx, Double.valueOf(newTx.getAmount()));
                return ResponseEntity.ok("Successfully saved transaction");
            }
        }  catch (Exception e) {
            LOGGER.error(e);
        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to process request.");
    }

}
