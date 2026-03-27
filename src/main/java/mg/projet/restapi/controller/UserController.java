package mg.projet.restapi.controller;

import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import mg.projet.restapi.assembler.UserDtoAssembler;
import mg.projet.restapi.dto.UserDto;
import mg.projet.restapi.model.Role;
import mg.projet.restapi.request.AssignRoleRequest;
import mg.projet.restapi.request.ChangePasswordRequest;
import mg.projet.restapi.request.UpdateUserRequest;
import mg.projet.restapi.service.RoleService;
import mg.projet.restapi.service.UserService;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;
    
    @Autowired
    private UserDtoAssembler userAssembler;

    @GetMapping()
    @PreAuthorize("hasAnyRole('ADMIN','VIEW_USERS')")
    public List<EntityModel<UserDto>> getAllUsers() {
        List<EntityModel<UserDto>> users = userService.findAll()
            .stream()
            .map(userAssembler::toModel)
            .collect(Collectors.toList());
        
        return users;
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN','VIEW_USERS')")
    public EntityModel<UserDto> getUserById(@PathVariable Long id) {
        return userAssembler.toModel(userService.findById(id));
    }

    @PatchMapping("/update/{id}")
    public EntityModel<UserDto> updateUser(@PathVariable Long id, @RequestBody UpdateUserRequest request) {
        return userAssembler.toModel(userService.update(id, request));
    }

    @PatchMapping("/change-password/{id}")
    public EntityModel<UserDto> changeUserPassword(@PathVariable Long id, @Valid @RequestBody ChangePasswordRequest request){
        return userAssembler.toModel(userService.changePassword(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String,String>> deleteUser(@PathVariable Long id){
        userService.deleteById(id);
        return new ResponseEntity<>(Map.of("messag","Utilisateur supprimé"), HttpStatus.GONE);
    
    }
    
    @PostMapping("/assign-role")
    public EntityModel<UserDto> assignRole(@RequestBody AssignRoleRequest request) {
        UserDto user = userService.findById(request.idUser());
        Role role = roleService.findById(request.idRole());

        return userAssembler.toModel(userService.assignRole(user, role));
    }
    

}
