package com.ptac.util;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.ptac.model.PtacTransactionEntity;
import com.ptac.model.PtacUser;

/**
 * Data Storage and initial data on Spring load. (in-mem db).
 * @author Mardolfh Del Rosario
 *
 */
public class PointAccumulatorStaticData {
	public static Map<Integer, PtacTransactionEntity> PTAC_TX_DATA;
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
	    user1.setUserId(1L);
	    user1.setUserName("kevin.durant@brooklynnets.com");
	    user1.setPassword("kd1234");
	    PTAC_USERS.add(user1);
	    PtacTransactionEntity trx1 = new PtacTransactionEntity(LocalDate.of(2020, 1, 22), user1, Double.valueOf(55.0));
	    PTAC_TX_DATA.put(PTAC_ID++, trx1);
	    PtacTransactionEntity trx2 = new PtacTransactionEntity(LocalDate.of(2020, 1, 29), user1, Double.valueOf(15.25));
	    PTAC_TX_DATA.put(PTAC_ID++, trx2);
	    PtacTransactionEntity trx3 = new PtacTransactionEntity(LocalDate.of(2020, 2, 11), user1, Double.valueOf(8.16));
	    PTAC_TX_DATA.put(PTAC_ID++, trx3);
	    PtacTransactionEntity trx4 = new PtacTransactionEntity(LocalDate.of(2020, 3, 1), user1, Double.valueOf(70.95));
	    PTAC_TX_DATA.put(PTAC_ID++, trx4);

	    //User 2
	    PtacUser user2 = new PtacUser();
	    user2.setUserId(2L);
	    user2.setUserName("donovan.mitchell@utahjazz.com");
	    user2.setPassword("dm1234");
        PTAC_USERS.add(user2);
        PtacTransactionEntity trxD1 = new PtacTransactionEntity(LocalDate.of(2020, 2, 17), user2, Double.valueOf(5.50));
        PTAC_TX_DATA.put(PTAC_ID++, trxD1);
        PtacTransactionEntity trxD2 = new PtacTransactionEntity(LocalDate.of(2020, 3, 23), user2, Double.valueOf(11.63));
        PTAC_TX_DATA.put(PTAC_ID++, trxD2);

        //User 3
        PtacUser user3 = new PtacUser();
        user3.setUserId(3L);
        user3.setUserName("jimmy.butler@miamiheat.com");
        user3.setPassword("jb1234");
        PTAC_USERS.add(user3);
        PtacTransactionEntity trxJ1 = new PtacTransactionEntity(LocalDate.of(2020, 1, 1), user3, Double.valueOf(2.00));
        PTAC_TX_DATA.put(PTAC_ID++, trxJ1);
        PtacTransactionEntity trxJ2 = new PtacTransactionEntity(LocalDate.of(2020, 1, 23), user3, Double.valueOf(20.74));
        PTAC_TX_DATA.put(PTAC_ID++, trxJ2);
        PtacTransactionEntity trxJ3 = new PtacTransactionEntity(LocalDate.of(2020, 2, 1), user3, Double.valueOf(84.32));
        PTAC_TX_DATA.put(PTAC_ID++, trxJ3);
        PtacTransactionEntity trxJ4 = new PtacTransactionEntity(LocalDate.of(2020, 2, 29), user3, Double.valueOf(0.25));
        PTAC_TX_DATA.put(PTAC_ID++, trxJ4);
        PtacTransactionEntity trxJ5 = new PtacTransactionEntity(LocalDate.of(2020, 3, 29), user3, Double.valueOf(44.44));
        PTAC_TX_DATA.put(PTAC_ID++, trxJ5);

        //Admin
        PtacUser admin = new PtacUser();
        admin.setUserId(4L);
        admin.setUserName("kobe.bryant@lalakers.com");
        admin.setPassword("kb1234");
        PTAC_USERS.add(admin);
	}

}
