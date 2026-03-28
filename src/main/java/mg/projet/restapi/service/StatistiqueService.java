package mg.projet.restapi.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mg.projet.restapi.dto.ConsultationLivreDto;
import mg.projet.restapi.dto.LectureParUtilisateurDto;
import mg.projet.restapi.dto.LivrePopulaireDto;
import mg.projet.restapi.dto.RevenusMensuelDto;
import mg.projet.restapi.dto.TypeLivrePopulaireDto;
import mg.projet.restapi.dto.ValiditeAbonnementDto;
import mg.projet.restapi.exception.NotFoundException;
import mg.projet.restapi.model.HistoriqueAbonnement;
import mg.projet.restapi.model.Livre;
import mg.projet.restapi.model.TypeLivre;
import mg.projet.restapi.model.User;
import mg.projet.restapi.repository.HistoriqueAbonnementRepository;
import mg.projet.restapi.repository.HistoriqueLectureRepository;
import mg.projet.restapi.repository.HistoriquePaiementLivreRepository;
import mg.projet.restapi.repository.LivreRepository;
import mg.projet.restapi.repository.UserRepository;

@Service
public class StatistiqueService {

    @Autowired
    private HistoriqueLectureRepository historiqueLectureRepo;

    @Autowired
    private HistoriqueAbonnementRepository historiqueAbonnementRepo;

    @Autowired
    private HistoriquePaiementLivreRepository historiquePaiementLivreRepo;

    @Autowired
    private LivreRepository livreRepository;

    @Autowired
    private UserRepository userRepository;

    public List<LivrePopulaireDto> getLivresPopulairesByMois(int annee, int mois) {
        return historiqueLectureRepo.findLivresPopulairesByMois(annee, mois)
                .stream().map(row -> {
                    Livre livre = (Livre) row[0];
                    Long total = (Long) row[1];
                    return new LivrePopulaireDto(
                            livre.getId(),
                            livre.getTitre(),
                            livre.getTypeLivre() != null ? livre.getTypeLivre().getType_livre() : null,
                            total);
                }).collect(Collectors.toList());
    }

    /** liste des types de livre populaires pour un mois et annee inseree */
    public List<TypeLivrePopulaireDto> getTypeLivresPopulairesByMois(int annee, int mois) {
        return historiqueLectureRepo.findTypeLivresPopulairesByMois(annee, mois)
                .stream().map(row -> {
                    TypeLivre tl = (TypeLivre) row[0];
                    Long total = (Long) row[1];
                    return new TypeLivrePopulaireDto(tl.getId(), tl.getType_livre(), total);
                }).collect(Collectors.toList());
    }

    /** revenus mensuels combinés (abonnements + livres) */
    public RevenusMensuelDto getRevenusMensuel(int annee, int mois) {
        Double revenusAbo = historiqueAbonnementRepo.findRevenusMensuelAbonnements(annee, mois);
        Double revenusLivres = historiquePaiementLivreRepo.findRevenusMensuelLivres(annee, mois);
        revenusAbo = revenusAbo != null ? revenusAbo : 0.0;
        revenusLivres = revenusLivres != null ? revenusLivres : 0.0;
        return new RevenusMensuelDto(annee, mois, revenusAbo, revenusLivres, revenusAbo + revenusLivres);
    }

    /** nombre de lectures par utilisateur pour un mois et annee inseree */
    public List<LectureParUtilisateurDto> getLecturesParUtilisateur(int annee, int mois) {
        return historiqueLectureRepo.findLecturesParUtilisateur(annee, mois)
                .stream().map(row -> {
                    User u = (User) row[0];
                    Long total = (Long) row[1];
                    return new LectureParUtilisateurDto(u.getId(), u.getNom(), u.getPrenom(), total);
                }).collect(Collectors.toList());
    }

    /** nombre total de consultations d'un livre */
    public ConsultationLivreDto getConsultationsByLivre(Long livreId) {
        Livre livre = livreRepository.findById(livreId)
                .orElseThrow(() -> new NotFoundException("Livre introuvable."));
        Long total = historiqueLectureRepo.countConsultationsByLivreId(livreId);
        return new ConsultationLivreDto(livre.getId(), livre.getTitre(), total);
    }

    /** Vérification de la validité de l'abonnement d'un utilisateur */
    public ValiditeAbonnementDto checkValiditeAbonnement(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("Utilisateur introuvable."));
        List<HistoriqueAbonnement> abonnements = historiqueAbonnementRepo.findAbonnementsActifsByUserId(userId);
        if (abonnements.isEmpty()) {
            return new ValiditeAbonnementDto(userId, user.getNom(), false, null, null, "Aucun abonnement actif.");
        }
        HistoriqueAbonnement actif = abonnements.get(0);
        return new ValiditeAbonnementDto(
                userId,
                user.getNom(),
                true,
                actif.getTypeAbonnement().getTypeAbonnement(),
                actif.getDateExpiration(),
                "Abonnement valide jusqu'au " + actif.getDateExpiration());
    }
}
