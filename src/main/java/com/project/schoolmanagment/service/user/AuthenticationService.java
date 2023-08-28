package com.project.schoolmanagment.service.user;

import com.project.schoolmanagment.payload.request.user.LoginRequest;
import com.project.schoolmanagment.payload.response.user.LoginResponse;
import com.project.schoolmanagment.security.jwt.JwtUtils;
import com.project.schoolmanagment.security.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;


    public ResponseEntity<LoginResponse>authenticateUser(LoginRequest request){
        String username=request.getUsername();
        String password=request.getPassword();
        //we will authenticate the user
       Authentication authentication= authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
        //if authenticated successfully we will upload the user infor to security context
        SecurityContextHolder.getContext().setAuthentication(authentication);
        //we have to create a token for header
       String token="Bearer "+jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails=(UserDetailsImpl) authentication.getPrincipal();
        //TODO ssn and isAdvisory properties are not set
        Set<String> roles=userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toSet());

        Optional<String>userRole=roles.stream().findFirst();

        return ResponseEntity.ok(LoginResponse.builder()
                .username(userDetails.getUsername())
                .token(token)
                .name(userDetails.getName())
                .role(userRole.get())
                .build());


    }
}
