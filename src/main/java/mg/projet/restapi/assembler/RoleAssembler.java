package mg.projet.restapi.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import mg.projet.restapi.controller.RoleController;
import mg.projet.restapi.model.Role;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class RoleAssembler extends RepresentationModelAssemblerSupport<Role,EntityModel<Role>> {

    @SuppressWarnings("unchecked")
    public RoleAssembler(){
        super(RoleController.class, (Class<EntityModel<Role>>) (Class<?>) EntityModel.class );
    }

    @Override
    public EntityModel<Role> toModel(Role role) {
        return EntityModel.of(role,
            linkTo(methodOn(RoleController.class).findAll()).withRel("find-all"),
            linkTo(methodOn(RoleController.class).findById(role.getId())).withRel("by-id"),
            linkTo(methodOn(RoleController.class).updateRoleName(role.getId(),null)).withRel("update"),
            linkTo(methodOn(RoleController.class).delete(role.getId())).withRel("delete")
        );
    }

}
