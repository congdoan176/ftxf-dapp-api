package net.ftxf.dapps.config;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity // Enable security config. This annotation denotes config for spring security.
public class SecurityTokenConfig extends WebSecurityConfigurerAdapter {

  public static String TOKEN_NAME = "FTXF-ACCESS-KEY";
  public static String TOKEN_SECRET = "ftxfDappsproject";
  public static String TOKEN_ISSUER = "FtxFDapps";

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    String bypass[] = {
      "/auth/*",
      "/",
      "/auction",
      "/signIn",
      "/listItem/{category}",
      "/details/{id}",
      "/register",
      "/user/register",
      "/user/**",
      "/api/vndtcallback",     
      "/wallet/**",
      "/category/**",
      "/auth/confirm/**"
    };

    String ignore[] = {
      "/v2/api-docs",
      "/csrf",
      "/configuration/ui",
      "/swagger-resources/**",
      "/configuration/security",
      "/swagger-ui.html",
      "/webjars/**",
      "/js/**",
      "/css/**",
      "/images/**",
      "/lib/**"
    };

    http.csrf()
        .disable()
        // make sure we use stateless session; session won't be used to store user's
        // state.
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        // handle an authorized attempts
        .exceptionHandling()
        .authenticationEntryPoint(
            (req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_UNAUTHORIZED))
        .and()
        // Add a filter to validate the tokens with every request
        .addFilterBefore(
            new JwtTokenAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class)
        // authorization requests config
        .authorizeRequests()
        // allow all who are accessing "auth" service
        .antMatchers(null, bypass)
        .permitAll()
        .antMatchers(null, ignore)
        .permitAll()
        .antMatchers(HttpMethod.OPTIONS, "/**")
        .permitAll()
        // Any other request must be authenticated
        .anyRequest()
        .authenticated();
  }
}
