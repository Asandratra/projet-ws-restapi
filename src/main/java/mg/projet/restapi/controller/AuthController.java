package mg.projet.restapi.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import mg.projet.restapi.dto.UserDto;
import mg.projet.restapi.request.CreateUserRequest;
import mg.projet.restapi.request.LoginRequest;
import mg.projet.restapi.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public Map<String,String> login(@Valid @RequestBody LoginRequest loginRequest) {
        return Map.of("access-token",authService.login(loginRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@Valid @RequestBody CreateUserRequest request) {
        return new ResponseEntity<UserDto>(authService.register(request),HttpStatus.CREATED);
    }
    
    
}
