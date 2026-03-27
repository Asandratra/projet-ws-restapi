package mg.projet.restapi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mg.projet.restapi.dto.HistoriqueLectureDto;
import mg.projet.restapi.model.HistoriqueLecture;
import mg.projet.restapi.model.Livre;
import mg.projet.restapi.model.User;
import mg.projet.restapi.repository.HistoriqueLectureRepository;
import mg.projet.restapi.repository.LivreRepository;
import mg.projet.restapi.repository.UserRepository;
import mg.projet.restapi.request.HistoriqueLectureRequest;

@Service
public class HistoriqueLectureService {

        @Autowired
        private HistoriqueLectureRepository historiqueLectureRepository;

        @Autowired
        private LivreRepository livreRepository;

        @Autowired
        private UserRepository userRepository;

        private HistoriqueLectureDto toDto(HistoriqueLecture hl) {
                return new HistoriqueLectureDto(
                                hl.getId(),
                                hl.getDateLecture(),
                                hl.getLivre() != null ? hl.getLivre().getId() : null,
                                hl.getLivre() != null ? hl.getLivre().getTitre() : null,
                                hl.getUtilisateur() != null ? hl.getUtilisateur().getId() : null,
                                hl.getUtilisateur() != null ? hl.getUtilisateur().getNom() : null,
                                hl.getUtilisateur() != null ? hl.getUtilisateur().getPrenom() : null);
        }

        /** ajout nouvel historique de lecture */
        public HistoriqueLectureDto save(HistoriqueLectureRequest request) {
                Livre livre = livreRepository.findById(request.livreId())
                                .orElseThrow(() -> new RuntimeException("Livre introuvable."));
                User user = userRepository.findById(request.utilisateurId())
                                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable."));
                HistoriqueLecture hl = new HistoriqueLecture(null, request.dateLecture(), livre, user);
                return toDto(historiqueLectureRepository.save(hl));
        }

        /** liste des historiques de lecture */
        public List<HistoriqueLectureDto> findAll() {
                return historiqueLectureRepository.findAll()
                                .stream()
                                .map(this::toDto)
                                .collect(Collectors.toList());
        }

        /** liste historique de lecture par son identifiant */
        public HistoriqueLectureDto findById(Long id) {
                HistoriqueLecture hl = historiqueLectureRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("HistoriqueLecture introuvable."));
                return toDto(hl);
        }

        /** update historique de lecture existant */
        public HistoriqueLectureDto update(Long id, HistoriqueLectureRequest request) {
                HistoriqueLecture hl = historiqueLectureRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("HistoriqueLecture introuvable."));
                Livre livre = livreRepository.findById(request.livreId())
                                .orElseThrow(() -> new RuntimeException("Livre introuvable."));
                User user = userRepository.findById(request.utilisateurId())
                                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable."));
                hl.setDateLecture(request.dateLecture());
                hl.setLivre(livre);
                hl.setUtilisateur(user);
                return toDto(historiqueLectureRepository.save(hl));
        }

        /** Supprime un historique de lecture par son identifiant */
        public void delete(Long id) {
                historiqueLectureRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("HistoriqueLecture introuvable."));
                historiqueLectureRepository.deleteById(id);
        }
}
