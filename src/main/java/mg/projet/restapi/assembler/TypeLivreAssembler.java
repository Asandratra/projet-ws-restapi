package mg.projet.restapi.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import mg.projet.restapi.controller.TypeLivreController;
import mg.projet.restapi.dto.TypeLivreDto;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class TypeLivreAssembler extends RepresentationModelAssemblerSupport<TypeLivreDto, EntityModel<TypeLivreDto>> {

    @SuppressWarnings("unchecked")
    public TypeLivreAssembler() {
        super(TypeLivreController.class, (Class<EntityModel<TypeLivreDto>>) (Class<?>) EntityModel.class);
    }

    @Override
    public EntityModel<TypeLivreDto> toModel(TypeLivreDto typeLivreDto) {
        return EntityModel.of(typeLivreDto,
                linkTo(methodOn(TypeLivreController.class).getById(typeLivreDto.getId())).withSelfRel(),
                linkTo(methodOn(TypeLivreController.class).getAll()).withRel("all-type-livres"));
    }
}
