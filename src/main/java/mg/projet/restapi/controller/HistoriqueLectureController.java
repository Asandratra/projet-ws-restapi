package mg.projet.restapi.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import mg.projet.restapi.assembler.HistoriqueLectureAssembler;
import mg.projet.restapi.dto.HistoriqueLectureDto;
import mg.projet.restapi.request.HistoriqueLectureRequest;
import mg.projet.restapi.service.HistoriqueLectureService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * tout le monde peut enregistrer et consulter des lectures
 * seul admin a acces au suppression
 */
@RestController
@RequestMapping("/api/historique-lecture")
public class HistoriqueLectureController {

    @Autowired
    private HistoriqueLectureService historiqueLectureService;

    @Autowired
    private HistoriqueLectureAssembler historiqueLectureAssembler;

    @PostMapping
    public ResponseEntity<EntityModel<HistoriqueLectureDto>> create(
            @Valid @RequestBody HistoriqueLectureRequest request) {
        HistoriqueLectureDto created = historiqueLectureService.save(request);
        return new ResponseEntity<>(historiqueLectureAssembler.toModel(created), HttpStatus.CREATED);
    }

    @GetMapping
    public CollectionModel<EntityModel<HistoriqueLectureDto>> getAll() {
        List<EntityModel<HistoriqueLectureDto>> list = historiqueLectureService.findAll()
                .stream()
                .map(historiqueLectureAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(list,
                linkTo(methodOn(HistoriqueLectureController.class).getAll()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<HistoriqueLectureDto> getById(@PathVariable Long id) {
        return historiqueLectureAssembler.toModel(historiqueLectureService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<HistoriqueLectureDto>> update(
            @PathVariable Long id, @Valid @RequestBody HistoriqueLectureRequest request) {
        HistoriqueLectureDto updated = historiqueLectureService.update(id, request);
        return new ResponseEntity<>(historiqueLectureAssembler.toModel(updated), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        historiqueLectureService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
