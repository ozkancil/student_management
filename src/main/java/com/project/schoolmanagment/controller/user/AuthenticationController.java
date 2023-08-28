package com.project.schoolmanagment.controller.user;


import com.project.schoolmanagment.payload.request.user.LoginRequest;
import com.project.schoolmanagment.payload.response.user.LoginResponse;
import com.project.schoolmanagment.service.user.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse>authenticateUser(@Valid @RequestBody LoginRequest request){
        return authenticationService.authenticateUser(request);
    }
}
