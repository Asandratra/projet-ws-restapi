package mg.projet.restapi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mg.projet.restapi.dto.HistoriquePaiementLivreDto;
import mg.projet.restapi.model.HistoriquePaiementLivre;
import mg.projet.restapi.model.Livre;
import mg.projet.restapi.model.User;
import mg.projet.restapi.repository.HistoriquePaiementLivreRepository;
import mg.projet.restapi.repository.LivreRepository;
import mg.projet.restapi.repository.UserRepository;
import mg.projet.restapi.request.HistoriquePaiementLivreRequest;

@Service
public class HistoriquePaiementLivreService {

        @Autowired
        private HistoriquePaiementLivreRepository historiquePaiementLivreRepository;

        @Autowired
        private LivreRepository livreRepository;

        @Autowired
        private UserRepository userRepository;

        private HistoriquePaiementLivreDto toDto(HistoriquePaiementLivre hpl) {
                return new HistoriquePaiementLivreDto(
                                hpl.getId(),
                                hpl.getDateLecture(),
                                hpl.getLivre() != null ? hpl.getLivre().getId() : null,
                                hpl.getLivre() != null ? hpl.getLivre().getTitre() : null,
                                hpl.getUtilisateur() != null ? hpl.getUtilisateur().getId() : null,
                                hpl.getUtilisateur() != null ? hpl.getUtilisateur().getNom() : null,
                                hpl.getUtilisateur() != null ? hpl.getUtilisateur().getPrenom() : null,
                                hpl.getPrix());
        }

        /** ajout nouvel historique de paiement de livre */
        public HistoriquePaiementLivreDto save(HistoriquePaiementLivreRequest request) {
                Livre livre = livreRepository.findById(request.livreId())
                                .orElseThrow(() -> new RuntimeException("Livre introuvable."));
                User user = userRepository.findById(request.utilisateurId())
                                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable."));
                HistoriquePaiementLivre hpl = new HistoriquePaiementLivre(
                                null, request.dateLecture(), livre, user, request.prix());
                return toDto(historiquePaiementLivreRepository.save(hpl));
        }

        /** liste des historiques de paiement de livres */
        public List<HistoriquePaiementLivreDto> findAll() {
                return historiquePaiementLivreRepository.findAll()
                                .stream()
                                .map(this::toDto)
                                .collect(Collectors.toList());
        }

        /** historique de paiement de livre par son identifiant */
        public HistoriquePaiementLivreDto findById(Long id) {
                HistoriquePaiementLivre hpl = historiquePaiementLivreRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("HistoriquePaiementLivre introuvable."));
                return toDto(hpl);
        }

        /** update historique de paiement de livre existant */
        public HistoriquePaiementLivreDto update(Long id, HistoriquePaiementLivreRequest request) {
                HistoriquePaiementLivre hpl = historiquePaiementLivreRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("HistoriquePaiementLivre introuvable."));
                Livre livre = livreRepository.findById(request.livreId())
                                .orElseThrow(() -> new RuntimeException("Livre introuvable."));
                User user = userRepository.findById(request.utilisateurId())
                                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable."));
                hpl.setDateLecture(request.dateLecture());
                hpl.setLivre(livre);
                hpl.setUtilisateur(user);
                hpl.setPrix(request.prix());
                return toDto(historiquePaiementLivreRepository.save(hpl));
        }

        /** Supprime un historique de paiement de livre par son identifiant */
        public void delete(Long id) {
                historiquePaiementLivreRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("HistoriquePaiementLivre introuvable."));
                historiquePaiementLivreRepository.deleteById(id);
        }
}
