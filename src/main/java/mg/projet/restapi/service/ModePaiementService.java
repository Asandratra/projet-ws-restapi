package mg.projet.restapi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mg.projet.restapi.dto.ModePaiementDto;
import mg.projet.restapi.model.ModePaiement;
import mg.projet.restapi.repository.ModePaiementRepository;
import mg.projet.restapi.request.ModePaiementRequest;

@Service
public class ModePaiementService {

    @Autowired
    private ModePaiementRepository modePaiementRepository;

    private ModePaiementDto toDto(ModePaiement mp) {
        return new ModePaiementDto(mp.getId(), mp.getMode());
    }

    /** ajout nouveau mode de paiement */
    public ModePaiementDto save(ModePaiementRequest request) {
        ModePaiement saved = modePaiementRepository.save(new ModePaiement(request.mode()));
        return toDto(saved);
    }

    /** liste des modes de paiement */
    public List<ModePaiementDto> findAll() {
        return modePaiementRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /** un mode de paiement par son identifiant */
    public ModePaiementDto findById(Long id) {
        ModePaiement mp = modePaiementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ModePaiement introuvable."));
        return toDto(mp);
    }

    /** update mode de paiement existant */
    public ModePaiementDto update(Long id, ModePaiementRequest request) {
        ModePaiement mp = modePaiementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ModePaiement introuvable."));
        mp.setMode(request.mode());
        return toDto(modePaiementRepository.save(mp));
    }

    /** Supprime un mode de paiement par son identifiant */
    public void delete(Long id) {
        modePaiementRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ModePaiement introuvable."));
        modePaiementRepository.deleteById(id);
    }
}
