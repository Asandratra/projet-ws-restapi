package mg.projet.restapi.controller;

import org.springframework.web.bind.annotation.RestController;

import mg.projet.restapi.assembler.UserDtoAssembler;
import mg.projet.restapi.dto.UserDto;
import mg.projet.restapi.service.UserService;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private UserDtoAssembler userAssembler;

    @GetMapping()
    public CollectionModel<EntityModel<UserDto>> getAllUsers() {
        List<EntityModel<UserDto>> users = userService.findAll()
            .stream()
            .map(userAssembler::toModel)
            .collect(Collectors.toList());
        
        return CollectionModel.of(users,
            linkTo(methodOn(UserController.class).getAllUsers()).withSelfRel()
        );
    }
    @GetMapping("/{id}")
    public EntityModel<UserDto> getUserById(@PathVariable Long id) {
        return userAssembler.toModel(userService.findById(id));
    }
    
    

}
