package mg.projet.restapi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mg.projet.restapi.dto.LivreDto;
import mg.projet.restapi.model.Livre;
import mg.projet.restapi.model.TypeLivre;
import mg.projet.restapi.repository.LivreRepository;
import mg.projet.restapi.repository.TypeLivreRepository;
import mg.projet.restapi.request.LivreRequest;

@Service
public class LivreService {

    @Autowired
    private LivreRepository livreRepository;

    @Autowired
    private TypeLivreRepository typeLivreRepository;

    private LivreDto toDto(Livre livre) {
        Long typeLivreId = livre.getTypeLivre() != null ? livre.getTypeLivre().getId() : null;
        String typeLivreNom = livre.getTypeLivre() != null ? livre.getTypeLivre().getType_livre() : null;
        return new LivreDto(
                livre.getId(),
                typeLivreId,
                typeLivreNom,
                livre.getTitre(),
                livre.getSousTitre(),
                livre.getSaison(),
                livre.getAuteur(),
                livre.getDateEdition(),
                livre.getDescription(),
                livre.getImage(),
                livre.getDocument(),
                livre.getEtat());
    }

    /** ajout nouveau livre */
    public LivreDto save(LivreRequest request) {
        TypeLivre typeLivre = null;
        if (request.typeLivreId() != null) {
            typeLivre = typeLivreRepository.findById(request.typeLivreId())
                    .orElseThrow(() -> new RuntimeException("TypeLivre introuvable."));
        }
        Livre livre = new Livre(
                typeLivre,
                request.titre(),
                request.sousTitre(),
                request.saison(),
                request.auteur(),
                request.dateEdition(),
                request.description(),
                request.image(),
                request.document(),
                request.etat() != null ? request.etat() : 0);
        return toDto(livreRepository.save(livre));
    }

    /** liste des livres avec filtres */
    public List<LivreDto> findAll(String titre, String auteur, Integer etat, Long typeLivreId) {
        return livreRepository.findWithFilters(titre, auteur, etat, typeLivreId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /** livre par son identifiant */
    public LivreDto findById(Long id) {
        Livre livre = livreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livre introuvable."));
        return toDto(livre);
    }

    /** update livre existant */
    public LivreDto update(Long id, LivreRequest request) {
        Livre livre = livreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livre introuvable."));
        if (request.typeLivreId() != null) {
            TypeLivre typeLivre = typeLivreRepository.findById(request.typeLivreId())
                    .orElseThrow(() -> new RuntimeException("TypeLivre introuvable."));
            livre.setTypeLivre(typeLivre);
        }
        livre.setTitre(request.titre());
        livre.setSousTitre(request.sousTitre());
        livre.setSaison(request.saison());
        livre.setAuteur(request.auteur());
        livre.setDateEdition(request.dateEdition());
        livre.setDescription(request.description());
        livre.setImage(request.image());
        livre.setDocument(request.document());
        if (request.etat() != null)
            livre.setEtat(request.etat());
        return toDto(livreRepository.save(livre));
    }

    /** Supprime un livre par son identifiant */
    public void delete(Long id) {
        livreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Livre introuvable."));
        livreRepository.deleteById(id);
    }
}
