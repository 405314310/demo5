package com.lyz.demo5.newSecurity;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private UserDetailsService userDetailsService;
    @Resource
    private LoginAuthenticationSuccessHandler loginAuthenticationSuccessHandler;
    @Resource
    private LoginAuthenticationFailureHandler loginAuthenticationFailureHandler;
    @Resource
    private CustomAccessDeniedHandler customAccessDeniedHandler;
    @Resource
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    @Resource
    private JwtTokenFilter jwtTokenFilter;

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().requestMatchers(CorsUtils::isPreFlightRequest).permitAll(); // ???????????????!!!!!!!!????????????????????????????????????????????? option ??????
        //??????????????????
        http.csrf().disable();
        // ??????token??????????????????session
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        //http.formLogin().successHandler(loginAuthenticationSuccessHandler);//????????????
        //http.formLogin().failureHandler(loginAuthenticationFailureHandler);//????????????


        //????????????
        http.exceptionHandling().authenticationEntryPoint(customAuthenticationEntryPoint); //??????????????????
        http.authorizeRequests().
                antMatchers("/auth/login"
                        ,"/auth/register"
                        ,"/auth/forgotPwd"
                        ,"/auth/resetPwd"
                        ,"/auth/test"
                        ,"/auth/getUserInfo").permitAll();//??????????????????
        http.authorizeRequests().anyRequest().authenticated();//???????????????????????????????????????
        http.exceptionHandling().accessDeniedHandler(customAccessDeniedHandler);
        http.addFilterAfter(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        http.headers().cacheControl();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
