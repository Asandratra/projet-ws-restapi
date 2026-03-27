package mg.projet.restapi.assembler;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import mg.projet.restapi.controller.HistoriqueLectureController;
import mg.projet.restapi.dto.HistoriqueLectureDto;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;


@Component
public class HistoriqueLectureAssembler extends RepresentationModelAssemblerSupport<HistoriqueLectureDto, EntityModel<HistoriqueLectureDto>> {

    @SuppressWarnings("unchecked")
    public HistoriqueLectureAssembler() {
        super(HistoriqueLectureController.class, (Class<EntityModel<HistoriqueLectureDto>>) (Class<?>) EntityModel.class);
    }

    @Override
    public EntityModel<HistoriqueLectureDto> toModel(HistoriqueLectureDto dto) {
        return EntityModel.of(dto,
                linkTo(methodOn(HistoriqueLectureController.class).getById(dto.getId())).withSelfRel(),
                linkTo(methodOn(HistoriqueLectureController.class).getAll()).withRel("all-historique-lectures"));
    }
}
