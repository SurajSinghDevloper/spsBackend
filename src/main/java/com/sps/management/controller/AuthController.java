package com.sps.management.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.sps.management.security.AuthService;
import com.sps.management.security.JwtAuthResponse;
import com.sps.management.security.LoginDto;
import com.sps.management.security.UserRequestDTO;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

	private AuthService authService;

	// Build Login REST API
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
		JwtAuthResponse response = authService.login(loginDto);
		if (response != null) {
			return new ResponseEntity<>(response, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("Invalid Crendentals ..... ", HttpStatus.BAD_REQUEST);
		}

	}

	// Building Post API
	@PostMapping("/signup")
	public ResponseEntity<?> signup(@RequestBody UserRequestDTO userRequestdto) {
		String successMessage = "";
		try {
			successMessage = authService.save(userRequestdto);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new ResponseEntity<>(successMessage, HttpStatus.OK);

	}

}
