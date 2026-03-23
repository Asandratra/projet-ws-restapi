package mg.projet.restapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import mg.projet.restapi.dto.UserDto;
import mg.projet.restapi.model.Role;
import mg.projet.restapi.request.AssignRoleRequest;
import mg.projet.restapi.request.CreateRoleRequest;
import mg.projet.restapi.service.RoleService;
import mg.projet.restapi.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/role")
public class RoleController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @PostMapping
    public ResponseEntity<Role> create(@RequestBody CreateRoleRequest request) {
        return new ResponseEntity<Role>(roleService.save(request), HttpStatus.CREATED);
    }

    @GetMapping
    public List<Role> findAll() {
        return roleService.findAll();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        roleService.delete(id);
        return new ResponseEntity<>("Role supprimer.",HttpStatus.GONE);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Role> putMethodName(@PathVariable Long id, @RequestBody CreateRoleRequest request) {
        return new ResponseEntity<Role>(roleService.update(id, request), HttpStatus.ACCEPTED);
    }

    @PostMapping("/assign")
    public ResponseEntity<UserDto> assignRole(@RequestBody AssignRoleRequest request) {
        UserDto user = userService.findById(request.idUser());
        Role role = roleService.findById(request.idRole());

        return new ResponseEntity<UserDto>(userService.assignRole(user, role), HttpStatus.ACCEPTED);
    }
}
