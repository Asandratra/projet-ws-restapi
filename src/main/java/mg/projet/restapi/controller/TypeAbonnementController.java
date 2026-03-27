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
import mg.projet.restapi.assembler.TypeAbonnementAssembler;
import mg.projet.restapi.dto.TypeAbonnementDto;
import mg.projet.restapi.request.TypeAbonnementRequest;
import mg.projet.restapi.service.TypeAbonnementService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/type-abonnement")
public class TypeAbonnementController {

    @Autowired
    private TypeAbonnementService typeAbonnementService;

    @Autowired
    private TypeAbonnementAssembler typeAbonnementAssembler;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<EntityModel<TypeAbonnementDto>> create(@Valid @RequestBody TypeAbonnementRequest request) {
        TypeAbonnementDto created = typeAbonnementService.save(request);
        return new ResponseEntity<>(typeAbonnementAssembler.toModel(created), HttpStatus.CREATED);
    }

    @GetMapping
    public CollectionModel<EntityModel<TypeAbonnementDto>> getAll() {
        List<EntityModel<TypeAbonnementDto>> list = typeAbonnementService.findAll()
                .stream()
                .map(typeAbonnementAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(list,
                linkTo(methodOn(TypeAbonnementController.class).getAll()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<TypeAbonnementDto> getById(@PathVariable Long id) {
        return typeAbonnementAssembler.toModel(typeAbonnementService.findById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<TypeAbonnementDto>> update(
            @PathVariable Long id, @Valid @RequestBody TypeAbonnementRequest request) {
        TypeAbonnementDto updated = typeAbonnementService.update(id, request);
        return new ResponseEntity<>(typeAbonnementAssembler.toModel(updated), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        typeAbonnementService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
