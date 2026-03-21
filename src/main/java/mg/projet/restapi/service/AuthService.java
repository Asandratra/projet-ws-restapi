package mg.projet.restapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import mg.projet.restapi.dto.UserDto;
import mg.projet.restapi.repository.UserRepository;
import mg.projet.restapi.request.LoginRequest;

@Service
public class AuthService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public String login(LoginRequest loginRequest){
        try{
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.mail(),
                    loginRequest.mdp()
                )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDto user = userService.toDto(userRepository.findByMail(loginRequest.mail()).orElseThrow(() -> new RuntimeException("Utilisateur introuvable")));

            return jwtService.generateToken(user, loginRequest.milliseconds()<=0?1000*60*15:loginRequest.milliseconds());

        } catch (Exception e){
            throw e;
        }
    }
}
