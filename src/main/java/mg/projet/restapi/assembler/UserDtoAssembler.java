package mg.projet.restapi.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import mg.projet.restapi.controller.UserController;
import mg.projet.restapi.dto.UserDto;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class UserDtoAssembler extends RepresentationModelAssemblerSupport<UserDto,EntityModel<UserDto>> {

    @SuppressWarnings("unchecked")
    public UserDtoAssembler() {
        super(UserController.class, (Class<EntityModel<UserDto>>) (Class<?>) EntityModel.class);
    }

    @Override
    public EntityModel<UserDto> toModel(UserDto userDto) {
        return EntityModel.of(userDto,
            linkTo(methodOn(UserController.class).getAllUsers()).withRel("all-users"),
            linkTo(methodOn(UserController.class).getUserById(userDto.getId())).withRel("user-profile"),
            linkTo(methodOn(UserController.class).assignRole(null)).withRel("assign-role"),
            linkTo(methodOn(UserController.class).updateUser(userDto.getId(), null)).withRel("update-user"),
            linkTo(methodOn(UserController.class).changeUserPassword(userDto.getId(), null)).withRel("change-password"),
            linkTo(methodOn(UserController.class).deleteUser(userDto.getId())).withRel("delete")

        );
    }
}
