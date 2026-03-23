package mg.projet.restapi.data;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import mg.projet.restapi.model.Role;
import mg.projet.restapi.model.User;
import mg.projet.restapi.repository.RoleRepository;
import mg.projet.restapi.repository.UserRepository;
import mg.projet.restapi.service.UserService;

@Component
@Slf4j
public class DataSeeder implements ApplicationRunner {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(ApplicationArguments args){
        seedRoles();
        seedUsers();
    }

    private void seedRoles() {
        List<String> roleNames = List.of(
            "ROLE_ADMIN",
            "ROLE_USER",
            "ROLE_VIEW_USERS",
            "ROLE_ADD_USER",
            "ROLE_EDIT_USER",
            "ROLE_DELETE_USER",
            "ROLE_VIEW_ROLES",
            "ROLE_CREATE_ROLE",
            "ROLE_EDIT_ROLE",
            "ROLE_DELETE_ROLE",
            "ROLE_ASSIGN_ROLE"
        );

        roleNames.forEach(name -> {
            if(roleRepository.findByName(name).isEmpty()){
                roleRepository.save(new Role(name));
            }
        });
    }

    private void seedUsers(){
        List<User> seeds = List.of(
            new User("Admin",     "System",  "0331849367",  "admin@mail.com", "admin123",  List.of(
                roleRepository.findByName("ROLE_ADMIN").get(),
                roleRepository.findByName("ROLE_VIEW_USERS").get(),
                roleRepository.findByName("ROLE_ADD_USER").get(),
                roleRepository.findByName("ROLE_EDIT_USER").get(),
                roleRepository.findByName("ROLE_DELETE_USER").get()
            )),
            new User("Moderator",     "System",  "0371046392",  "moderator@mail.com", "mod123",  List.of(
                roleRepository.findByName("ROLE_USER").get(),
                roleRepository.findByName("ROLE_VIEW_USERS").get(),
                roleRepository.findByName("ROLE_ADD_USER").get()
            ))
        );

        seeds.forEach(user -> {
            if(userRepository.findByMail(user.getMail()).isEmpty()){
                userService.save(user);
            }
        });
    }

}
