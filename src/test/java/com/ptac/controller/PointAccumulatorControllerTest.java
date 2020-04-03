package com.ptac.controller;

import static org.junit.Assert.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.ptac.model.PtacTransactionEntity;
import com.ptac.model.PtacUser;
import com.ptac.util.PointAccumulatorStaticData;

/**
 * Execute RestController api test.
 * @author Mardolfh Del Rosario
 *
 */
@ContextConfiguration
@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class PointAccumulatorControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext)
                .apply(springSecurity()).build();
    }

    /**
     * Happy path in creating a basic customer.
     * @throws Exception
     */
    @Test
    public void testCreateCustomer() throws Exception {
        String body =
                "{\"userId\" : null, \"userName\" : \"larry.bird@bostonceltics.com\", \"password\" : \"lb1234\"}";
        this.mockMvc.perform(put("/ptacAdmin/createCustomer")
                .with(user("kobe.bryant@lalakers.com").password("kb1234").roles("ADMIN"))
                .content(body.getBytes()).contentType("application/json;charset=UTF-8"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("text/plain;charset=UTF-8"))
            .andExpect(content().string("Successfully created user: larry.bird@bostonceltics.com"));
        PtacUser user = new PtacUser();
        user.setUserName("larry.bird@bostonceltics.com");
        assertTrue(PointAccumulatorStaticData.PTAC_USERS.contains(user));
    }

    /**
     * Customer already exists.
     * @throws Exception
     */
    @Test
    public void testCreateCustomerAlreadyExists() throws Exception {
        String body =
                "{\"userId\" : null, \"userName\" : \"jimmy.butler@miamiheat.com\", \"password\" : \"jb1234\"}";
        this.mockMvc.perform(put("/ptacAdmin/createCustomer")
                .with(user("kobe.bryant@lalakers.com").password("kb1234").roles("ADMIN"))
                .content(body.getBytes()).contentType("application/json;charset=UTF-8"))
            .andExpect(status().isInternalServerError())
            .andExpect(content().contentType("text/plain;charset=UTF-8"))
            .andExpect(content().string("User jimmy.butler@miamiheat.com already exists."));

        PtacUser user = new PtacUser();
        user.setUserName("jimmy.butler@miamiheat.com");
        assertTrue(PointAccumulatorStaticData.PTAC_USERS.contains(user));
    }

    /**
     * Customer has a null username.
     * @throws Exception
     */
    @Test
    public void testCreateCustomerUserNameNull() throws Exception {
        String body =
                "{\"userId\" : null, \"userName\" : null, \"password\" : \"jb1234\"}";
        this.mockMvc.perform(put("/ptacAdmin/createCustomer")
                .with(user("kobe.bryant@lalakers.com").password("kb1234").roles("ADMIN"))
                .content(body.getBytes()).contentType("application/json;charset=UTF-8"))
            .andExpect(status().isInternalServerError())
            .andExpect(content().contentType("text/plain;charset=UTF-8"))
            .andExpect(content().string("Unable to process request."));

        assertTrue(!PointAccumulatorStaticData.PTAC_USERS.contains(null));
    }

    /**
     * Customer has empty password.
     * @throws Exception
     */
    @Test
    public void testCreateCustomerPasswordEmpty() throws Exception {
        String body =
                "{\"userId\" : null, \"userName\" : \"lebron.james@lalakers.com\", \"password\" : \"\"}";
        this.mockMvc.perform(put("/ptacAdmin/createCustomer")
                .with(user("kobe.bryant@lalakers.com").password("kb1234").roles("ADMIN"))
                .content(body.getBytes()).contentType("application/json;charset=UTF-8"))
            .andExpect(status().isInternalServerError())
            .andExpect(content().contentType("text/plain;charset=UTF-8"))
            .andExpect(content().string("Unable to process request."));

        PtacUser user = new PtacUser();
        user.setUserName("lebron.james@lalakers.com");
        assertTrue(!PointAccumulatorStaticData.PTAC_USERS.contains(user));

    }

    /**
     * Happy path in creating a transaction for an existing customer.
     * Given: $225.25 total amount
     * $225.25 - $100 = 125.25 x 2 = 250.5 pts.
     * $225.25 is greater than $50 = 50 x 1 = 50 pts.
     * 300.5 total points.
     * @throws Exception
     */
    @Test
    public void testSaveCustomerPointsGreaterHundred() throws Exception {
        String body =
                "{\"userName\" : \"donovan.mitchell@utahjazz.com\", \"amount\" : \"225.25\"}";
        this.mockMvc.perform(post("/ptacAdmin/saveCustomerPoints")
                .with(user("kobe.bryant@lalakers.com").password("kb1234").roles("ADMIN"))
                .content(body.getBytes()).contentType("application/json;charset=UTF-8"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=UTF-8"))
            .andExpect(jsonPath("$.userName").value("donovan.mitchell@utahjazz.com"))
            .andExpect(jsonPath("$.addedPts").value("300.5"));
        PtacUser user = new PtacUser();
        user.setUserName("donovan.mitchell@utahjazz.com");
        assertTrue(PointAccumulatorStaticData.PTAC_USERS.contains(user));

        //check static map storage
        Map<Integer, PtacTransactionEntity> res = PointAccumulatorStaticData.PTAC_TX_DATA.entrySet().stream()
            .filter(o -> o.getValue().getUser().equals(user))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        PtacTransactionEntity pte = new PtacTransactionEntity(LocalDate.now(), user, Double.parseDouble("300.5"));
        assertTrue(res.containsValue(pte));
    }

    /**
     * Create a transaction for an existing customer less than a hundred but greater than 50.
     * Given: $90.99 total amount
     * $90.99 is greater than $50 = 50 x 1 = 50 pts.
     * 50 total points.
     * @throws Exception
     */
    @Test
    public void testSaveCustomerPointsLessThanHundred() throws Exception {
        String body =
                "{\"userName\" : \"kevin.durant@brooklynnets.com\", \"amount\" : \"90.99\"}";
        this.mockMvc.perform(post("/ptacAdmin/saveCustomerPoints")
                .with(user("kobe.bryant@lalakers.com").password("kb1234").roles("ADMIN"))
                .content(body.getBytes()).contentType("application/json;charset=UTF-8"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=UTF-8"))
            .andExpect(jsonPath("$.userName").value("kevin.durant@brooklynnets.com"))
            .andExpect(jsonPath("$.addedPts").value("50.0"));
        PtacUser user = new PtacUser();
        user.setUserName("kevin.durant@brooklynnets.com");
        assertTrue(PointAccumulatorStaticData.PTAC_USERS.contains(user));

        //check static map storage
        Map<Integer, PtacTransactionEntity> res = PointAccumulatorStaticData.PTAC_TX_DATA.entrySet().stream()
            .filter(o -> o.getValue().getUser().equals(user))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        PtacTransactionEntity pte = new PtacTransactionEntity(LocalDate.now(), user, Double.parseDouble("50.0"));
        assertTrue(res.containsValue(pte));
    }

    /**
     * Create a transaction for a newly created customer less than a hundred but greater than 50.
     * Given: $50.50 total amount
     * $50.50 is greater than $50 = 50 x 1 = 50 pts.
     * 50 total points.
     * @throws Exception
     */
    @Test
    public void testSaveCustomerPointsLessThanHundredForNewCustomer() throws Exception {

        String body =
                "{\"userId\" : null, \"userName\" : \"michael.jordan@chicagobulls.com\", \"password\" : \"mj1234\"}";
        this.mockMvc.perform(put("/ptacAdmin/createCustomer")
                .with(user("kobe.bryant@lalakers.com").password("kb1234").roles("ADMIN"))
                .content(body.getBytes()).contentType("application/json;charset=UTF-8"))
            .andExpect(status().isOk());

        body =
                "{\"userName\" : \"michael.jordan@chicagobulls.com\", \"amount\" : \"50.50\"}";
        this.mockMvc.perform(post("/ptacAdmin/saveCustomerPoints")
                .with(user("kobe.bryant@lalakers.com").password("kb1234").roles("ADMIN"))
                .content(body.getBytes()).contentType("application/json;charset=UTF-8"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=UTF-8"))
            .andExpect(jsonPath("$.userName").value("michael.jordan@chicagobulls.com"))
            .andExpect(jsonPath("$.addedPts").value("50.0"));
        PtacUser user = new PtacUser();
        user.setUserName("michael.jordan@chicagobulls.com");
        assertTrue(PointAccumulatorStaticData.PTAC_USERS.contains(user));

        //check static map storage
        Map<Integer, PtacTransactionEntity> res = PointAccumulatorStaticData.PTAC_TX_DATA.entrySet().stream()
            .filter(o -> o.getValue().getUser().equals(user))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        PtacTransactionEntity pte = new PtacTransactionEntity(LocalDate.now(), user, Double.parseDouble("50.0"));
        assertTrue(res.containsValue(pte));
    }

    /**
     * Creating a transaction for an existing customer less than than 50.
     * Given: $22.00 total amount
     * 0 total points. Must not save to the system as transaction
     * @throws Exception
     */
    @Test
    public void testSaveCustomerPointsLessThanFifty() throws Exception {
        String body =
                "{\"userName\" : \"kobe.bryant@lalakers.com\", \"amount\" : \"20.00\"}";
        this.mockMvc.perform(post("/ptacAdmin/saveCustomerPoints")
                .with(user("kobe.bryant@lalakers.com").password("kb1234").roles("ADMIN"))
                .content(body.getBytes()).contentType("application/json;charset=UTF-8"))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=UTF-8"))
            .andExpect(jsonPath("$.userName").value("kobe.bryant@lalakers.com"))
            .andExpect(jsonPath("$.addedPts").value("0.0"));
        PtacUser user = new PtacUser();
        user.setUserName("kobe.bryant@lalakers.com");
        assertTrue(PointAccumulatorStaticData.PTAC_USERS.contains(user));

        //check static map storage
        Map<Integer, PtacTransactionEntity> res = PointAccumulatorStaticData.PTAC_TX_DATA.entrySet().stream()
            .filter(o -> o.getValue().getUser().equals(user))
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        PtacTransactionEntity pte = new PtacTransactionEntity(LocalDate.now(), user, Double.parseDouble("0.0"));
        assertTrue(!res.containsValue(pte));
    }

    /**
     * Test if the newly created customer will get retrieved.
     * @throws Exception
     */
    @Test
    public void testGetAllCustomerPoints() throws Exception {
        this.mockMvc.perform(get("/ptacAdmin/getAllCustomerPoints")
                .with(user("kobe.bryant@lalakers.com").password("kb1234").roles("ADMIN")))
            .andExpect(status().isOk())
            .andExpect(content().contentType("application/json;charset=UTF-8"))
            .andExpect(jsonPath("$.['michael.jordan@chicagobulls.com']").value(Double.valueOf(50.0)));
    }


}
