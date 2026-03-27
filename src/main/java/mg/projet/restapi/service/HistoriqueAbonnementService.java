package mg.projet.restapi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mg.projet.restapi.dto.HistoriqueAbonnementDto;
import mg.projet.restapi.model.HistoriqueAbonnement;
import mg.projet.restapi.model.ModePaiement;
import mg.projet.restapi.model.TypeAbonnement;
import mg.projet.restapi.model.User;
import mg.projet.restapi.repository.HistoriqueAbonnementRepository;
import mg.projet.restapi.repository.ModePaiementRepository;
import mg.projet.restapi.repository.TypeAbonnementRepository;
import mg.projet.restapi.repository.UserRepository;
import mg.projet.restapi.request.HistoriqueAbonnementRequest;

@Service
public class HistoriqueAbonnementService {

        @Autowired
        private HistoriqueAbonnementRepository historiqueAbonnementRepository;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private TypeAbonnementRepository typeAbonnementRepository;

        @Autowired
        private ModePaiementRepository modePaiementRepository;

        private HistoriqueAbonnementDto toDto(HistoriqueAbonnement ha) {
                return new HistoriqueAbonnementDto(
                                ha.getId(),
                                ha.getDatePaiement(),
                                ha.getTypeAbonnement() != null ? ha.getTypeAbonnement().getId() : null,
                                ha.getTypeAbonnement() != null ? ha.getTypeAbonnement().getTypeAbonnement() : null,
                                ha.getTypeAbonnement() != null ? ha.getTypeAbonnement().getPrix() : null,
                                ha.getModePaiement() != null ? ha.getModePaiement().getId() : null,
                                ha.getModePaiement() != null ? ha.getModePaiement().getMode() : null,
                                ha.getUtilisateur() != null ? ha.getUtilisateur().getId() : null,
                                ha.getUtilisateur() != null ? ha.getUtilisateur().getNom() : null,
                                ha.getUtilisateur() != null ? ha.getUtilisateur().getPrenom() : null,
                                ha.getDateExpiration());
        }

        /** ajout historique d'abonnement */
        public HistoriqueAbonnementDto save(HistoriqueAbonnementRequest request) {
                User user = userRepository.findById(request.utilisateurId())
                                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable."));
                TypeAbonnement typeAbonnement = typeAbonnementRepository.findById(request.typeAbonnementId())
                                .orElseThrow(() -> new RuntimeException("TypeAbonnement introuvable."));
                ModePaiement modePaiement = modePaiementRepository.findById(request.modePaiementId())
                                .orElseThrow(() -> new RuntimeException("ModePaiement introuvable."));
                HistoriqueAbonnement ha = new HistoriqueAbonnement(
                                null,
                                request.datePaiement(),
                                typeAbonnement,
                                modePaiement,
                                user,
                                request.dateExpiration());
                return toDto(historiqueAbonnementRepository.save(ha));
        }

        /** liste des historiques d'abonnement */
        public List<HistoriqueAbonnementDto> findAll() {
                return historiqueAbonnementRepository.findAll()
                                .stream()
                                .map(this::toDto)
                                .collect(Collectors.toList());
        }

        /** liste historique d'abonnement par son identifiant */
        public HistoriqueAbonnementDto findById(Long id) {
                HistoriqueAbonnement ha = historiqueAbonnementRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("HistoriqueAbonnement introuvable."));
                return toDto(ha);
        }

        /** update historique d'abonnement existant */
        public HistoriqueAbonnementDto update(Long id, HistoriqueAbonnementRequest request) {
                HistoriqueAbonnement ha = historiqueAbonnementRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("HistoriqueAbonnement introuvable."));
                User user = userRepository.findById(request.utilisateurId())
                                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable."));
                TypeAbonnement typeAbonnement = typeAbonnementRepository.findById(request.typeAbonnementId())
                                .orElseThrow(() -> new RuntimeException("TypeAbonnement introuvable."));
                ModePaiement modePaiement = modePaiementRepository.findById(request.modePaiementId())
                                .orElseThrow(() -> new RuntimeException("ModePaiement introuvable."));
                ha.setDatePaiement(request.datePaiement());
                ha.setTypeAbonnement(typeAbonnement);
                ha.setModePaiement(modePaiement);
                ha.setUtilisateur(user);
                ha.setDateExpiration(request.dateExpiration());
                return toDto(historiqueAbonnementRepository.save(ha));
        }

        /** Supprime un historique d'abonnement par son identifiant */
        public void delete(Long id) {
                historiqueAbonnementRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("HistoriqueAbonnement introuvable."));
                historiqueAbonnementRepository.deleteById(id);
        }
}
