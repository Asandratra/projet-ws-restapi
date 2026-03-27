package mg.projet.restapi.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import mg.projet.restapi.controller.ModePaiementController;
import mg.projet.restapi.dto.ModePaiementDto;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class ModePaiementAssembler
        extends RepresentationModelAssemblerSupport<ModePaiementDto, EntityModel<ModePaiementDto>> {

    @SuppressWarnings("unchecked")
    public ModePaiementAssembler() {
        super(ModePaiementController.class, (Class<EntityModel<ModePaiementDto>>) (Class<?>) EntityModel.class);
    }

    @Override
    public EntityModel<ModePaiementDto> toModel(ModePaiementDto dto) {
        return EntityModel.of(dto,
                linkTo(methodOn(ModePaiementController.class).getById(dto.getId())).withSelfRel(),
                linkTo(methodOn(ModePaiementController.class).getAll()).withRel("all-mode-paiements"));
    }
}
