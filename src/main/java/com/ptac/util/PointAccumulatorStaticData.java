package com.ptac.util;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.ptac.model.PtacTransaction;
import com.ptac.model.PtacUser;

/**
 * Data Storage and initial data on Spring load. (in-mem db).
 * @author Mardolfh Del Rosario
 *
 */
public class PointAccumulatorStaticData {
	public static Map<Integer, PtacTransaction> PTAC_TX_DATA;
	public static Set<PtacUser> PTAC_USERS;
	public static int PTAC_ID = 1;
	static {
	    PTAC_TX_DATA = new HashMap<>();
	    PTAC_USERS = new HashSet<>();
	    populateInitialData();
	}

	/**
	 * Populate static map PTAC_TX_DATA and PTAC_USERS.
	 */
	private static void populateInitialData() {
	    //User 1
	    PtacUser user1 = new PtacUser();
	    user1.setUserId(1);
	    user1.setUserName("kevin.durant@brooklynnets.com");
	    user1.setPassword("kd1234");
	    PTAC_USERS.add(user1);
	    PtacTransaction trx1 = new PtacTransaction(LocalDate.of(2020, 1, 22), user1, Double.valueOf(55.0));
	    PTAC_TX_DATA.put(PTAC_ID++, trx1);
	    PtacTransaction trx2 = new PtacTransaction(LocalDate.of(2020, 1, 29), user1, Double.valueOf(15.25));
	    PTAC_TX_DATA.put(PTAC_ID++, trx2);
	    PtacTransaction trx3 = new PtacTransaction(LocalDate.of(2020, 2, 11), user1, Double.valueOf(8.16));
	    PTAC_TX_DATA.put(PTAC_ID++, trx3);
	    PtacTransaction trx4 = new PtacTransaction(LocalDate.of(2020, 3, 1), user1, Double.valueOf(70.95));
	    PTAC_TX_DATA.put(PTAC_ID++, trx4);

	    //User 2
	    PtacUser user2 = new PtacUser();
	    user2.setUserId(2);
	    user2.setUserName("donovan.mitchell@utahjazz.com");
	    user2.setPassword("dm1234");
        PTAC_USERS.add(user2);
        PtacTransaction trxD1 = new PtacTransaction(LocalDate.of(2020, 2, 17), user2, Double.valueOf(5.50));
        PTAC_TX_DATA.put(PTAC_ID++, trxD1);
        PtacTransaction trxD2 = new PtacTransaction(LocalDate.of(2020, 3, 23), user2, Double.valueOf(11.63));
        PTAC_TX_DATA.put(PTAC_ID++, trxD2);

        //User 3
        PtacUser user3 = new PtacUser();
        user3.setUserId(3);
        user3.setUserName("jimmy.butler@miamiheat.com");
        user3.setPassword("jb1234");
        PTAC_USERS.add(user3);
        PtacTransaction trxJ1 = new PtacTransaction(LocalDate.of(2020, 1, 1), user3, Double.valueOf(2.00));
        PTAC_TX_DATA.put(PTAC_ID++, trxJ1);
        PtacTransaction trxJ2 = new PtacTransaction(LocalDate.of(2020, 1, 23), user3, Double.valueOf(20.74));
        PTAC_TX_DATA.put(PTAC_ID++, trxJ2);
        PtacTransaction trxJ3 = new PtacTransaction(LocalDate.of(2020, 2, 1), user3, Double.valueOf(84.32));
        PTAC_TX_DATA.put(PTAC_ID++, trxJ3);
        PtacTransaction trxJ4 = new PtacTransaction(LocalDate.of(2020, 2, 29), user3, Double.valueOf(0.25));
        PTAC_TX_DATA.put(PTAC_ID++, trxJ4);
        PtacTransaction trxJ5 = new PtacTransaction(LocalDate.of(2020, 3, 29), user3, Double.valueOf(44.44));
        PTAC_TX_DATA.put(PTAC_ID++, trxJ5);

        //Admin
        PtacUser admin = new PtacUser();
        admin.setUserId(4);
        admin.setUserName("kobe.bryant@lalakers.com");
        admin.setPassword("kb1234");
        PTAC_USERS.add(admin);
	}

}
