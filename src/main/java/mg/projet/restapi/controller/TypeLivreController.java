package mg.projet.restapi.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import mg.projet.restapi.assembler.TypeLivreAssembler;
import mg.projet.restapi.dto.TypeLivreDto;
import mg.projet.restapi.request.TypeLivreRequest;
import mg.projet.restapi.service.TypeLivreService;


import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/type-livre")
public class TypeLivreController {

    @Autowired
    private TypeLivreService typeLivreService;

    @Autowired
    private TypeLivreAssembler typeLivreAssembler;

    @PostMapping
    public ResponseEntity<EntityModel<TypeLivreDto>> create(@Valid @RequestBody TypeLivreRequest request) {
        TypeLivreDto created = typeLivreService.save(request);
        return new ResponseEntity<>(typeLivreAssembler.toModel(created), HttpStatus.CREATED);
    }

    @GetMapping
    public CollectionModel<EntityModel<TypeLivreDto>> getAll() {
        List<EntityModel<TypeLivreDto>> list = typeLivreService.findAll()
                .stream()
                .map(typeLivreAssembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(list,
                linkTo(methodOn(TypeLivreController.class).getAll()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<TypeLivreDto> getById(@PathVariable Long id) {
        return typeLivreAssembler.toModel(typeLivreService.findById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<TypeLivreDto>> update(@PathVariable Long id, @Valid @RequestBody TypeLivreRequest request) {
        TypeLivreDto updated = typeLivreService.update(id, request);
        return new ResponseEntity<>(typeLivreAssembler.toModel(updated), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        typeLivreService.delete(id);
        return new ResponseEntity<>("TypeLivre supprimé.", HttpStatus.NO_CONTENT);
    }
}
