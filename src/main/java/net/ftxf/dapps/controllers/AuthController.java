package net.ftxf.dapps.controllers;

import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;
import java.util.logging.Logger;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import net.ftxf.dapps.api.ChangePassRequest;
import net.ftxf.dapps.api.LoginRequest;
import net.ftxf.dapps.config.SecurityTokenConfig;
import net.ftxf.dapps.entities.User;
import net.ftxf.dapps.repositories.UserRepository;

@RestController
public class AuthController {

	@Autowired
	UserRepository userRepository;

	@PostMapping(value = "/auth/login")
	@ResponseBody
	public ResponseEntity login(@RequestBody LoginRequest loginRequest, HttpServletResponse response,
			HttpServletRequest request) throws InterruptedException, ExecutionException {
		User user = userRepository.findByEmail(loginRequest.getEmail()).get();

		if (user != null) {
			if (user.getPassword().equals(loginRequest.getPassword())) {

				Calendar cal = Calendar.getInstance(); // creates calendar
				cal.setTime(new Date()); // sets calendar time/date
				cal.add(Calendar.MONTH, 1); // adds one hour
				Date exp = cal.getTime(); // token will expire in 1 day

				Algorithm algorithm = Algorithm.HMAC256(SecurityTokenConfig.TOKEN_SECRET);
				String token = JWT.create().withJWTId(loginRequest.getEmail())
						.withIssuer(SecurityTokenConfig.TOKEN_ISSUER).withClaim("group", user.getGroup())
						.withExpiresAt(exp).sign(algorithm);
				Cookie cookie = new Cookie(SecurityTokenConfig.TOKEN_NAME, token);

				cookie.setMaxAge(7 * 24 * 60 * 60);
				cookie.setPath("/");
				response.addCookie(cookie);

				return new ResponseEntity<>(token, HttpStatus.OK);
			}
			return new ResponseEntity<>("Wrong password", HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<>("User not found", HttpStatus.FORBIDDEN);
	}

	@PostMapping(value = "/auth/logout")
	@ResponseBody
	public String logout(HttpServletRequest request) throws InterruptedException, ExecutionException {
		System.out.println("check request log out: " + request.getRemoteUser());
		request.getRemoteUser();
		return "No need to logout, just delete your token";
	}

	@PostMapping(value = "/auth/changepass")
	@ResponseBody
	public ResponseEntity changeThePassWord(@RequestBody ChangePassRequest changePassRequest)
			throws InterruptedException, ExecutionException {
		User user = userRepository.findByEmail(changePassRequest.getEmail()).get();
		if (user != null) {
			if (user.getPassword().equals(changePassRequest.oldPassword())) {
				user.setPassword(changePassRequest.newPassword());
				userRepository.save(user);
				return new ResponseEntity<>("OK", HttpStatus.OK);
			}
			return new ResponseEntity<>("Wrong password", HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<>("User not found", HttpStatus.FORBIDDEN);
	}
}
