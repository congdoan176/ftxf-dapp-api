package net.ftxf.dapps.controllers;

import java.security.SecureRandom;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.ftxf.dapps.api.VerifyEmail;
import net.ftxf.dapps.repositories.UserRepository;

@RestController
@RequestMapping(value = "/user")
public class UserController {
	@Autowired
	UserRepository userRepository;
	
	@PostMapping(value = "/verify/email")
	public ResponseEntity verifyEmail(@RequestBody VerifyEmail data, HttpServletRequest request) {
		String email = data.getEmail();
		if(userRepository.findByEmail(email).isPresent()) {
			return new ResponseEntity<>("Verified email", HttpStatus.CONFLICT);
		}
		String keyCode = generateRandomString(15);
		System.out.println("check email: " + keyCode);
		return new ResponseEntity<>("OK", HttpStatus.OK);
	}
	public static String generateRandomString(int length) {
		// You can customize the characters that you want to add into
		// the random strings
		String CHAR_LOWER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		String CHAR_UPPER = CHAR_LOWER.toUpperCase();
		String NUMBER = "0123456789";

		String DATA_FOR_RANDOM_STRING = CHAR_LOWER + CHAR_UPPER + NUMBER;
		SecureRandom random = new SecureRandom();

		if (length < 1)
			throw new IllegalArgumentException();

		StringBuilder sb = new StringBuilder(length);

		for (int i = 0; i < length; i++) {
			// 0-62 (exclusive), random returns 0-61
			int rndCharAt = random.nextInt(DATA_FOR_RANDOM_STRING.length());
			char rndChar = DATA_FOR_RANDOM_STRING.charAt(rndCharAt);
			sb.append(rndChar);
		}
		System.out.println("check kasd:" + sb.toString());
		return sb.toString();
	}
}
