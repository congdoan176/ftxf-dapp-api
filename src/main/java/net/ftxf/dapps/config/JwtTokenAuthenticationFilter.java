package net.ftxf.dapps.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

@Component
@Order(1)
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

  static final Logger logger = LoggerFactory.getLogger(JwtTokenAuthenticationFilter.class);

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    String token = this.getToken(request);
    if (token == null) {
      filterChain.doFilter(request, response);
      return;
    }

    Algorithm algorithm = Algorithm.HMAC256(SecurityTokenConfig.TOKEN_SECRET);
    JWTVerifier verifier =
        JWT.require(algorithm).withIssuer(SecurityTokenConfig.TOKEN_ISSUER).build();
    DecodedJWT jwt = verifier.verify(token);

    String userId = jwt.getId();

    String group = jwt.getClaim("group").asString();

    logger.debug("Logged user id " + userId + " with group " + group);

    SimpleGrantedAuthority sga = new SimpleGrantedAuthority("group");
    List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<SimpleGrantedAuthority>();
    grantedAuthorities.add(sga);
    SecurityContextHolder.getContext()
        .setAuthentication(
            new UsernamePasswordAuthenticationToken(userId, token, grantedAuthorities));
    filterChain.doFilter(request, response);
  }

  private String getToken(HttpServletRequest request) {
    if (request.getParameter(SecurityTokenConfig.TOKEN_NAME) != null) {
      return request.getParameter(SecurityTokenConfig.TOKEN_NAME);
    }
    if (request.getHeader(SecurityTokenConfig.TOKEN_NAME) != null) {
      return request.getHeader(SecurityTokenConfig.TOKEN_NAME);
    }
    if (request.getHeader("Authorization") != null) {
      return request.getHeader("Authorization").replaceAll("Bearer", "").trim();
    }
    if (WebUtils.getCookie(request, SecurityTokenConfig.TOKEN_NAME) != null) {
      return WebUtils.getCookie(request, SecurityTokenConfig.TOKEN_NAME).getValue();
    }
    return null;
  }
}
