package mg.projet.restapi.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import mg.projet.restapi.assembler.UserDtoAssembler;
import mg.projet.restapi.dto.UserDto;
import mg.projet.restapi.request.CreateUserRequest;
import mg.projet.restapi.request.LoginRequest;
import mg.projet.restapi.service.AuthService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private ConfigurableEnvironment environment;

    @Autowired
    private UserDtoAssembler userAssembler;

    @PostMapping("/login")
    public Map<String,String> login(@Valid @RequestBody LoginRequest loginRequest) {
        return Map.of("access-token",authService.login(loginRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<EntityModel<UserDto>> register(@Valid @RequestBody CreateUserRequest request) {
        return new ResponseEntity<EntityModel<UserDto>>(userAssembler.toModel(authService.register(request)),HttpStatus.CREATED);
    }

    @PostMapping("/config/jwt-expiration")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> updateJwtExpiration(@RequestParam Long duration) {

        MutablePropertySources propertySources = environment.getPropertySources();

        Map<String, Object> runtimeProps = new HashMap<>();
        runtimeProps.put("jwt.expiration", duration);

        propertySources.addFirst(new MapPropertySource("runtima-jwt", runtimeProps));

        return ResponseEntity.ok("Expiration du JWT en "+ duration +" millisecondes.");
    }
    
    
    
}
