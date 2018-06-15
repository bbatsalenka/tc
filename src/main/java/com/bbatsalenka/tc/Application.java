package com.bbatsalenka.tc;

import java.util.TimeZone;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import com.bbatsalenka.tc.filters.UserAuthenticationFilter;

@EnableGlobalMethodSecurity(prePostEnabled = true)
@SpringBootApplication
public class Application {
	
	@Autowired
	UserAuthenticationFilter userAuthenticationFilter;
	
	@Bean
    public ApplicationSecurity applicationSecurity() {
        return new ApplicationSecurity();
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setEnabled(false);
        filterRegistrationBean.setFilter(userAuthenticationFilter);
        return filterRegistrationBean;
    }

    @Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
    public static class ApplicationSecurity extends WebSecurityConfigurerAdapter {

        @Autowired
        private UserAuthenticationFilter userAuthenticationFilter;

        @Override
        protected void configure(HttpSecurity http) throws Exception {
        	http.addFilterBefore(userAuthenticationFilter, BasicAuthenticationFilter.class)
            .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
                .antMatcher("/testcases/**")
                .authorizeRequests().anyRequest().authenticated()
            .and()
                .csrf()
                .disable();
        }
    }

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
		SpringApplication.run(Application.class, args);
	}

}
