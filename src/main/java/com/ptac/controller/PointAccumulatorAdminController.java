package com.ptac.controller;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ptac.model.PtacMultiTransaction;
import com.ptac.model.PtacNewTransaction;
import com.ptac.model.PtacUser;
import com.ptac.service.PointAccumulatorService;
import com.ptac.util.PointAccumulatorUtil;

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
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

    /**
     * Add up customer's points.
     * @param newTx                 PtacNewTransaction reference.
     * @return ResponseEntity       reference.
     */
    @PostMapping("/saveCustomerPoints")
    public ResponseEntity<?> saveCustomerPoints(@RequestBody PtacNewTransaction newTx) {
        try {
            if (PointAccumulatorUtil.isModelValid(newTx)) {
                this.pointAccumulatorService.saveCustomerTransactionPoints(newTx);
                return ResponseEntity.ok(newTx);
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to process request.");
    }

    /**
     * Create a customer in the system.
     * @param user          PtacUser user input reference.
     * @return ResponseEntity       reference.
     */
    @PutMapping("/createCustomer")
    public ResponseEntity<?> createCustomer(@RequestBody PtacUser user) {
        try {
            if (PointAccumulatorUtil.isModelValid(user)) {
                this.pointAccumulatorService.createCustomerData(user);
                return ResponseEntity.ok("Successfully created user: " + user.getUserName());
            }
        } catch (Exception e) {
            LOGGER.error(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());

        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to process request.");
    }

    /**
     * Create customer and append points instantly.
     * @param user          PtacUser user input reference.
     * @param newTx         PtacNewTransaction reference.
     * @return ResponseEntity       reference.
     */
    @PutMapping("/createCustomerSaveCustomerPts")
    public ResponseEntity<?> createCustomerAndPoints(@RequestBody PtacMultiTransaction multiTx) {

        try {
            if (PointAccumulatorUtil.isModelValid(multiTx)) {
                PtacUser user = multiTx.getUser();
                PtacNewTransaction newTx = multiTx.getNewTx();
                this.pointAccumulatorService.createCustomerData(user);
                this.pointAccumulatorService.saveCustomerTransactionPoints(newTx);
                return ResponseEntity.ok("Successfully created user: " + user.getUserName() +
                        " and added pts: " + newTx.getAddedPts());
            }
        }  catch (Exception e) {
            LOGGER.error(e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());

        }

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Unable to process request.");
    }

}
