package mg.projet.restapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import mg.projet.restapi.dto.UserDto;
import mg.projet.restapi.model.User;
import mg.projet.restapi.repository.UserRepository;

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
            throw new RuntimeException("Utilisateur introuvable");
        }
        return toDto(searchUser.get());
    }

    public UserDto update(User user){
        if(userRepository.findById(user.getId()).isEmpty()){
            throw new RuntimeException("Utilisateur inexistant");
        }
        return toDto(userRepository.save(user));
    }

    public String deleteById(Long id){
        userRepository.deleteById(id);
        return "Utilisateur : "+id+", a été retirer";
    }
}
