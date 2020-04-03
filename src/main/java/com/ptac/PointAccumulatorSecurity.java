package com.ptac;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.ptac.model.PtacUser;
import com.ptac.util.PointAccumulatorStaticData;

/**
 * Security configuration for PointAccumulator system, uses Basic Auth only.
 * @author Mardolfh Del Rosario
 *
 */
@Configuration
public class PointAccumulatorSecurity extends WebSecurityConfigurerAdapter {

    private static final Logger LOGGER = Logger.getLogger(PointAccumulatorSecurity.class);


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        for (PtacUser user : PointAccumulatorStaticData.PTAC_USERS) {
            auth.inMemoryAuthentication().withUser(user.getUserName())
            .password(user.getPassword()).roles(user.getUserName().contains("kobe") ? "ADMIN" : "USER");
        }
    }

    @Override
    public void configure(HttpSecurity httpSec) throws Exception {
        httpSec.csrf().disable()
            .authorizeRequests().antMatchers("/", "/ptacAdmin/**").hasAnyRole("ADMIN")
            .anyRequest().authenticated()
            .and().httpBasic();
    }

}
