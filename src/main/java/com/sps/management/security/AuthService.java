package com.sps.management.security;

public interface AuthService {
	JwtAuthResponse login(LoginDto loginDto);
    String save(UserRequestDTO userRequestDto) throws Exception;
}
