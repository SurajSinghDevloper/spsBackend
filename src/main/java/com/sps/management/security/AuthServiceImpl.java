package com.sps.management.security;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.sps.management.models.Role;
import com.sps.management.models.Users;
import com.sps.management.repositories.RoleRepository;
import com.sps.management.repositories.UserRepository;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService{

    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired 
    private RoleRepository roleDao;
    
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    
    @Override
    public JwtAuthResponse login(LoginDto loginDto) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(),
                loginDto.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        String token = jwtTokenProvider.generateToken(authentication);
        
        Users user = userRepository.findByEmail(loginDto.getEmail());
        JwtAuthResponse response = new JwtAuthResponse();
        response.setAccessToken(token);
        response.setUser(user);

        return response;
    }

	@Override
	public String save(UserRequestDTO userRequestDto) throws Exception {
		Users user = new Users();
		if(userRequestDto.getEmail()==null || userRequestDto.getName()==null || userRequestDto.getPassword()==null  )
		{
			throw new Exception("No_Records_Found");
		}
		
		Role setRole = roleDao.findByName("ROLE_OPERATOR");
		
		user.setEmail(userRequestDto.getEmail());
		user.setName(userRequestDto.getName());
		user.setPassword(bCryptPasswordEncoder.encode(userRequestDto.getPassword()));
		user.setUsername(userRequestDto.getEmail());
		Set<Role> roles =new HashSet<>();
		roles.add(setRole);
		user.setRoles(roles);
		
		
		log.info("user ----- {}",user);
		
		
		userRepository.save(user);
		
		
		return "Resgister Successfull....";
	}
}
