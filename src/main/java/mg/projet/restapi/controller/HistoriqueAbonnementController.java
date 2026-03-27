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
import mg.projet.restapi.assembler.HistoriqueAbonnementAssembler;
import mg.projet.restapi.dto.HistoriqueAbonnementDto;
import mg.projet.restapi.request.HistoriqueAbonnementRequest;
import mg.projet.restapi.service.HistoriqueAbonnementService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/historique-abonnement")
public class HistoriqueAbonnementController {

    @Autowired
    private HistoriqueAbonnementService historiqueAbonnementService;

    @Autowired
    private HistoriqueAbonnementAssembler historiqueAbonnementAssembler;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<EntityModel<HistoriqueAbonnementDto>> create(
            @Valid @RequestBody HistoriqueAbonnementRequest request) {
        HistoriqueAbonnementDto created = historiqueAbonnementService.save(request);
        return new ResponseEntity<>(historiqueAbonnementAssembler.toModel(created), HttpStatus.CREATED);
    }

    @GetMapping
    public CollectionModel<EntityModel<HistoriqueAbonnementDto>> getAll() {
        List<EntityModel<HistoriqueAbonnementDto>> list = historiqueAbonnementService.findAll()
                .stream()
                .map(historiqueAbonnementAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(list,
                linkTo(methodOn(HistoriqueAbonnementController.class).getAll()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<HistoriqueAbonnementDto> getById(@PathVariable Long id) {
        return historiqueAbonnementAssembler.toModel(historiqueAbonnementService.findById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<HistoriqueAbonnementDto>> update(
            @PathVariable Long id, @Valid @RequestBody HistoriqueAbonnementRequest request) {
        HistoriqueAbonnementDto updated = historiqueAbonnementService.update(id, request);
        return new ResponseEntity<>(historiqueAbonnementAssembler.toModel(updated), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        historiqueAbonnementService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
