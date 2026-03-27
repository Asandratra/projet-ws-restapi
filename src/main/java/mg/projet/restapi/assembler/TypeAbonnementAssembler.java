package mg.projet.restapi.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import mg.projet.restapi.controller.TypeAbonnementController;
import mg.projet.restapi.dto.TypeAbonnementDto;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TypeAbonnementAssembler
        extends RepresentationModelAssemblerSupport<TypeAbonnementDto, EntityModel<TypeAbonnementDto>> {

    @SuppressWarnings("unchecked")
    public TypeAbonnementAssembler() {
        super(TypeAbonnementController.class, (Class<EntityModel<TypeAbonnementDto>>) (Class<?>) EntityModel.class);
    }

    @Override
    public EntityModel<TypeAbonnementDto> toModel(TypeAbonnementDto dto) {
        return EntityModel.of(dto,
                linkTo(methodOn(TypeAbonnementController.class).getById(dto.getId())).withSelfRel(),
                linkTo(methodOn(TypeAbonnementController.class).getAll()).withRel("all-type-abonnements"));
    }
}
