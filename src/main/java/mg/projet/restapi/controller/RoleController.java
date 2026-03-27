package mg.projet.restapi.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import mg.projet.restapi.assembler.RoleAssembler;
import mg.projet.restapi.model.Role;
import mg.projet.restapi.request.CreateRoleRequest;
import mg.projet.restapi.service.RoleService;
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
    private RoleService roleService;
    @Autowired
    private RoleAssembler roleAssembler;

    @PostMapping
    public ResponseEntity<Role> create(@RequestBody CreateRoleRequest request) {
        return new ResponseEntity<Role>(roleService.save(request), HttpStatus.CREATED);
    }

    @GetMapping
    public List<EntityModel<Role>> findAll() {
        List<EntityModel<Role>> roles = roleService.findAll()
            .stream()
            .map(roleAssembler::toModel)
            .collect(Collectors.toList());

        return roles;
    }

    @GetMapping("/{id}")
    public EntityModel<Role> findById(@PathVariable Long id) {
        return roleAssembler.toModel(roleService.findById(id));
    }
    

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        roleService.delete(id);
        return new ResponseEntity<>("Role supprimer.",HttpStatus.GONE);
    }

    @PutMapping("/{id}")
    public EntityModel<Role> updateRoleName(@PathVariable Long id, @RequestBody CreateRoleRequest request) {
        return roleAssembler.toModel(roleService.update(id,request));
    }
}
