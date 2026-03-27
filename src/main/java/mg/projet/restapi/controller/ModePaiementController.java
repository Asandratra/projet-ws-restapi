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
import mg.projet.restapi.assembler.ModePaiementAssembler;
import mg.projet.restapi.dto.ModePaiementDto;
import mg.projet.restapi.request.ModePaiementRequest;
import mg.projet.restapi.service.ModePaiementService;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/mode-paiement")
public class ModePaiementController {

    @Autowired
    private ModePaiementService modePaiementService;

    @Autowired
    private ModePaiementAssembler modePaiementAssembler;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<EntityModel<ModePaiementDto>> create(@Valid @RequestBody ModePaiementRequest request) {
        ModePaiementDto created = modePaiementService.save(request);
        return new ResponseEntity<>(modePaiementAssembler.toModel(created), HttpStatus.CREATED);
    }

    @GetMapping
    public CollectionModel<EntityModel<ModePaiementDto>> getAll() {
        List<EntityModel<ModePaiementDto>> list = modePaiementService.findAll()
                .stream()
                .map(modePaiementAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(list,
                linkTo(methodOn(ModePaiementController.class).getAll()).withSelfRel());
    }

    @GetMapping("/{id}")
    public EntityModel<ModePaiementDto> getById(@PathVariable Long id) {
        return modePaiementAssembler.toModel(modePaiementService.findById(id));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<ModePaiementDto>> update(
            @PathVariable Long id, @Valid @RequestBody ModePaiementRequest request) {
        ModePaiementDto updated = modePaiementService.update(id, request);
        return new ResponseEntity<>(modePaiementAssembler.toModel(updated), HttpStatus.ACCEPTED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        modePaiementService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
