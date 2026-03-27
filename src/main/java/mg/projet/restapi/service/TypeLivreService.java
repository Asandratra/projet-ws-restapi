package mg.projet.restapi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mg.projet.restapi.dto.TypeLivreDto;
import mg.projet.restapi.model.TypeLivre;
import mg.projet.restapi.repository.TypeLivreRepository;
import mg.projet.restapi.request.TypeLivreRequest;

@Service
public class TypeLivreService {

    @Autowired
    private TypeLivreRepository typeLivreRepository;

    private TypeLivreDto toDto(TypeLivre typeLivre) {
        return new TypeLivreDto(typeLivre.getId(), typeLivre.getType_livre());
    }

    public TypeLivreDto save(TypeLivreRequest request) {
        TypeLivre saved = typeLivreRepository.save(new TypeLivre(request.type_livre()));
        return toDto(saved);
    }

    public List<TypeLivreDto> findAll() {
        return typeLivreRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public TypeLivreDto findById(Long id) {
        TypeLivre typeLivre = typeLivreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TypeLivre inexistant."));
        return toDto(typeLivre);
    }

    public TypeLivreDto update(Long id, TypeLivreRequest request) {
        TypeLivre typeLivre = typeLivreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TypeLivre inexistant."));
        typeLivre.setType_livre(request.type_livre());
        return toDto(typeLivreRepository.save(typeLivre));
    }

    public void delete(Long id) {
        typeLivreRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("TypeLivre inexistant."));
        typeLivreRepository.deleteById(id);
    }
}
