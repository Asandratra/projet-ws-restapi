package mg.projet.restapi.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import mg.projet.restapi.controller.HistoriqueAbonnementController;
import mg.projet.restapi.dto.HistoriqueAbonnementDto;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Component
public class HistoriqueAbonnementAssembler extends RepresentationModelAssemblerSupport<HistoriqueAbonnementDto, EntityModel<HistoriqueAbonnementDto>> {

    @SuppressWarnings("unchecked")
    public HistoriqueAbonnementAssembler() {
        super(HistoriqueAbonnementController.class, (Class<EntityModel<HistoriqueAbonnementDto>>) (Class<?>) EntityModel.class);
    }

    @Override
    public EntityModel<HistoriqueAbonnementDto> toModel(HistoriqueAbonnementDto dto) {
        return EntityModel.of(dto,
                linkTo(methodOn(HistoriqueAbonnementController.class).getById(dto.getId())).withSelfRel(),
                linkTo(methodOn(HistoriqueAbonnementController.class).getAll()).withRel("all-historique-abonnements"));
    }
}
