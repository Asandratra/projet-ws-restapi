package mg.projet.restapi.service;

import java.util.List;
import java.util.Optional;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import mg.projet.restapi.dto.UserDto;
import mg.projet.restapi.exception.NotFoundException;
import mg.projet.restapi.model.Role;
import mg.projet.restapi.model.User;
import mg.projet.restapi.repository.UserRepository;
import mg.projet.restapi.request.ChangePasswordRequest;
import mg.projet.restapi.request.UpdateUserRequest;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User save(User user){
        user.setMdp(passwordEncoder.encode(user.getMdp()));
        return userRepository.save(user);
    }

    public UserDto toDto(User user){
        return new UserDto(
            user.getId(),
            user.getDateentree(),
            user.getNom(),
            user.getPrenom(),
            user.getTelephone(),
            user.getMail(),
            user.getEtat(),
            user.getRoles()
        );
    }

    public List<UserDto> findAll(){
        return userRepository.findAll().stream().map(user -> toDto(user)).toList();
    }
    public UserDto findById(Long id){
        Optional<User> searchUser = userRepository.findById(id);
        if(searchUser.isEmpty()){
            throw new NotFoundException("Utilisateur introuvable");
        }
        return toDto(searchUser.get());
    }

    public UserDto update(Long id, UpdateUserRequest request){
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("Utilisateur introuvable."));
        if (!(request.nom().isEmpty()||request.nom().isBlank())) user.setNom(request.nom());
        if (!(request.prenom().isEmpty()||request.prenom().isBlank())) user.setPrenom(request.prenom());
        if (!(request.telephone().isEmpty()||request.telephone().isBlank())) user.setTelephone(request.telephone());
        if (!(request.mail().isEmpty()||request.mail().isBlank())){
            EmailValidator emailValidator = EmailValidator.getInstance();
            if(!emailValidator.isValid(request.mail())){
                throw new NotFoundException("Format email invalide.");
            }
            user.setMail(request.mail());
        }

        return toDto(userRepository.save(user));
    }

    public UserDto changePassword(Long id, ChangePasswordRequest request){
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("Utilisateur introuvable."));
        if(!request.mdp().equals(request.confirmMdp())){
            throw new NotFoundException("Erreur de confirmation de mot de passe.");
        }

        user.setMdp(request.mdp());

        return toDto(userRepository.save(user));
    }

    public String deleteById(Long id){
        userRepository.deleteById(id);
        return "Utilisateur : "+id+", a été retirer";
    }

    public UserDto assignRole(UserDto userDto, Role role){
        User user = userRepository.findById(userDto.getId()).orElseThrow(() -> new NotFoundException("Utilisateur introuvable."));
        user.addRole(role);
        return toDto(userRepository.save(user));
    }
}
