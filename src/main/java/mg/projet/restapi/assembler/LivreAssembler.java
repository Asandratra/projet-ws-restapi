package mg.projet.restapi.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import mg.projet.restapi.controller.LivreController;
import mg.projet.restapi.dto.LivreDto;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class LivreAssembler extends RepresentationModelAssemblerSupport<LivreDto, EntityModel<LivreDto>> {

    @SuppressWarnings("unchecked")
    public LivreAssembler() {
        super(LivreController.class, (Class<EntityModel<LivreDto>>) (Class<?>) EntityModel.class);
    }

    @Override
    public EntityModel<LivreDto> toModel(LivreDto livreDto) {
        return EntityModel.of(livreDto,
                linkTo(methodOn(LivreController.class).getById(livreDto.getId())).withSelfRel(),
                linkTo(methodOn(LivreController.class).getAll(null, null, null)).withRel("all-livres"));
    }
}
