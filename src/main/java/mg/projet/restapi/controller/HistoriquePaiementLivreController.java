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
import mg.projet.restapi.assembler.HistoriquePaiementLivreAssembler;
import mg.projet.restapi.dto.HistoriquePaiementLivreDto;
import mg.projet.restapi.request.HistoriquePaiementLivreRequest;
import mg.projet.restapi.service.HistoriquePaiementLivreService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/historique-paiement-livre")
public class HistoriquePaiementLivreController {

    @Autowired
    private HistoriquePaiementLivreService historiquePaiementLivreService;

    @Autowired
    private HistoriquePaiementLivreAssembler historiquePaiementLivreAssembler;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<EntityModel<HistoriquePaiementLivreDto>> create(
            @Valid @RequestBody HistoriquePaiementLivreRequest request) {
        HistoriquePaiementLivreDto created = historiquePaiementLivreService.save(request);
        return new ResponseEntity<>(historiquePaiementLivreAssembler.toModel(created), HttpStatus.CREATED);
    }

    @GetMapping
    public CollectionModel<EntityModel<HistoriquePaiementLivreDto>> getAll() {
        List<EntityModel<HistoriquePaiementLivreDto>> list = historiquePaiementLivreService.findAll()
                .stream()
                .map(historiquePaiementLivreAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(list,
                linkTo(methodOn(HistoriquePaiementLivreController.class).getAll()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<HistoriquePaiementLivreDto> getById(@PathVariable Long id) {
        return historiquePaiementLivreAssembler.toModel(historiquePaiementLivreService.findById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<HistoriquePaiementLivreDto>> update(
            @PathVariable Long id, @Valid @RequestBody HistoriquePaiementLivreRequest request) {
        HistoriquePaiementLivreDto updated = historiquePaiementLivreService.update(id, request);
        return new ResponseEntity<>(historiquePaiementLivreAssembler.toModel(updated), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        historiquePaiementLivreService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
