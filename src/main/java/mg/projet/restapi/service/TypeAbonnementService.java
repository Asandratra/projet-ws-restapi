package mg.projet.restapi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mg.projet.restapi.dto.TypeAbonnementDto;
import mg.projet.restapi.model.TypeAbonnement;
import mg.projet.restapi.repository.TypeAbonnementRepository;
import mg.projet.restapi.request.TypeAbonnementRequest;

@Service
public class TypeAbonnementService {

    @Autowired
    private TypeAbonnementRepository typeAbonnementRepository;

    private TypeAbonnementDto toDto(TypeAbonnement ta) {
        return new TypeAbonnementDto(ta.getId(), ta.getTypeAbonnement(), ta.getPrix());
    }

    /** ajout nouveau type d'abonnement */
    public TypeAbonnementDto save(TypeAbonnementRequest request) {
        TypeAbonnement saved = typeAbonnementRepository.save(
                new TypeAbonnement(request.typeAbonnement(), request.prix()));
        return toDto(saved);
    }

    /** liste des types d'abonnement */
    public List<TypeAbonnementDto> findAll() {
        return typeAbonnementRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /** type d'abonnement par son identifiant */
    public TypeAbonnementDto findById(Long id) {
        TypeAbonnement ta = typeAbonnementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TypeAbonnement introuvable."));
        return toDto(ta);
    }

    /** update type d'abonnement existant */
    public TypeAbonnementDto update(Long id, TypeAbonnementRequest request) {
        TypeAbonnement ta = typeAbonnementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TypeAbonnement introuvable."));
        ta.setTypeAbonnement(request.typeAbonnement());
        ta.setPrix(request.prix());
        return toDto(typeAbonnementRepository.save(ta));
    }

    /** Supprime un type d'abonnement par son identifiant */
    public void delete(Long id) {
        typeAbonnementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TypeAbonnement introuvable."));
        typeAbonnementRepository.deleteById(id);
    }
}
