package mg.projet.restapi.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import mg.projet.restapi.controller.HistoriquePaiementLivreController;
import mg.projet.restapi.dto.HistoriquePaiementLivreDto;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class HistoriquePaiementLivreAssembler extends
        RepresentationModelAssemblerSupport<HistoriquePaiementLivreDto, EntityModel<HistoriquePaiementLivreDto>> {

    @SuppressWarnings("unchecked")
    public HistoriquePaiementLivreAssembler() {
        super(HistoriquePaiementLivreController.class,
                (Class<EntityModel<HistoriquePaiementLivreDto>>) (Class<?>) EntityModel.class);
    }

    @Override
    public EntityModel<HistoriquePaiementLivreDto> toModel(HistoriquePaiementLivreDto dto) {
        return EntityModel.of(dto,
                linkTo(methodOn(HistoriquePaiementLivreController.class).getById(dto.getId())).withSelfRel(),
                linkTo(methodOn(HistoriquePaiementLivreController.class).getAll())
                        .withRel("all-historique-paiements-livre"));
    }
}
