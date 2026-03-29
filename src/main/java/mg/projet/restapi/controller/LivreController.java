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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import mg.projet.restapi.assembler.LivreAssembler;
import mg.projet.restapi.dto.LivreDto;
import mg.projet.restapi.request.LivreRequest;
import mg.projet.restapi.service.LivreService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/livres")
public class LivreController {

    @Autowired
    private LivreService livreService;

    @Autowired
    private LivreAssembler livreAssembler;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<EntityModel<LivreDto>> create(@Valid @RequestBody LivreRequest request) {
        LivreDto created = livreService.save(request);
        return new ResponseEntity<>(livreAssembler.toModel(created), HttpStatus.CREATED);
    }

    @GetMapping
    public CollectionModel<EntityModel<LivreDto>> getAll(
            @RequestParam(required = false) String titre,
            @RequestParam(required = false) String auteur,
            @RequestParam(required = false) String typeLivreNom) {
        List<EntityModel<LivreDto>> list = livreService.findAll(titre, auteur, typeLivreNom)
                .stream()
                .map(livreAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(list,
                linkTo(methodOn(LivreController.class).getAll(null, null, null)).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<LivreDto> getById(@PathVariable Long id) {
        return livreAssembler.toModel(livreService.findById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<LivreDto>> update(
            @PathVariable Long id, @Valid @RequestBody LivreRequest request) {
        LivreDto updated = livreService.update(id, request);
        return new ResponseEntity<>(livreAssembler.toModel(updated), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        livreService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
