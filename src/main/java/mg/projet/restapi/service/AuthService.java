package mg.projet.restapi.service;

import java.sql.Timestamp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import mg.projet.restapi.dto.UserDto;
import mg.projet.restapi.exception.InvalidRequestException;
import mg.projet.restapi.exception.NotFoundException;
import mg.projet.restapi.model.User;
import mg.projet.restapi.repository.UserRepository;
import mg.projet.restapi.request.CreateUserRequest;
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

            UserDto user = userService.toDto(userRepository.findByMail(loginRequest.mail()).orElseThrow(() -> new NotFoundException("Utilisateur introuvable")));

            return jwtService.generateToken(user, jwtService.getExpirationMs());

        } catch (NotFoundException e) {
            throw e;
        } catch (Exception e) {
            throw e;
        }
    }

    public UserDto register(CreateUserRequest createUserRequest){

        if(!createUserRequest.mdp().equals(createUserRequest.confirmMdp())){
            throw new InvalidRequestException("La confirmation de mot de passe invalide.");
        }
        User user = new User();
        user.setDateentree(new Timestamp(System.currentTimeMillis()));
        user.setEtat(1);
        user.setMail(createUserRequest.mail());
        user.setMdp(createUserRequest.mdp());
        user.setTelephone(createUserRequest.telephone());
        user.setNom(createUserRequest.nom());
        user.setPrenom(createUserRequest.prenom());

        return userService.toDto(userService.save(user));
    }
}
