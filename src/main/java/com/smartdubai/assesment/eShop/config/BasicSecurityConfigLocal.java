package com.smartdubai.assesment.eShop.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@Profile("!prod")
public class BasicSecurityConfigLocal extends WebSecurityConfigurerAdapter {


// Helper methods
    @Override
    protected void configure (HttpSecurity http) throws Exception {
      // TODO: Bad practice. It should be at least secured by basic auth. Hence restricted it to profile other than prod.
        http.csrf().disable().authorizeRequests().antMatchers("/").permitAll();
    }
}
