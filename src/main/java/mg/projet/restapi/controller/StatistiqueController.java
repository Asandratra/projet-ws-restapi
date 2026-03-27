package mg.projet.restapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import mg.projet.restapi.dto.ConsultationLivreDto;
import mg.projet.restapi.dto.LectureParUtilisateurDto;
import mg.projet.restapi.dto.LivrePopulaireDto;
import mg.projet.restapi.dto.RevenusMensuelDto;
import mg.projet.restapi.dto.TypeLivrePopulaireDto;
import mg.projet.restapi.dto.ValiditeAbonnementDto;
import mg.projet.restapi.service.StatistiqueService;

/**
 * 
 * seul ADMIN a acces
 */
@RestController
@RequestMapping("/api/stats")
@PreAuthorize("hasRole('ADMIN')")
public class StatistiqueController {

    @Autowired
    private StatistiqueService statistiqueService;

    /** Retourne les livres les plus lus pour un mois et une année donnés */
    @GetMapping("/livres-populaires/{annee}/{mois}")
    public CollectionModel<LivrePopulaireDto> getLivresPopulaires(
            @PathVariable int annee, @PathVariable int mois) {
        List<LivrePopulaireDto> list = statistiqueService.getLivresPopulairesByMois(annee, mois);
        return CollectionModel.of(list);
    }

    /** Retourne les types de livre populaires pour un mois et une année donnés */
    @GetMapping("/types-populaires/{annee}/{mois}")
    public CollectionModel<TypeLivrePopulaireDto> getTypesPopulaires(
            @PathVariable int annee, @PathVariable int mois) {
        List<TypeLivrePopulaireDto> list = statistiqueService.getTypeLivresPopulairesByMois(annee, mois);
        return CollectionModel.of(list);
    }

    /** Retourne les revenus mensuels combinés (abonnements + livres) */
    @GetMapping("/revenus/{annee}/{mois}")
    public ResponseEntity<RevenusMensuelDto> getRevenus(
            @PathVariable int annee, @PathVariable int mois) {
        return ResponseEntity.ok(statistiqueService.getRevenusMensuel(annee, mois));
    }

    /** Retourne le nombre de lectures par utilisateur pour un mois donné */
    @GetMapping("/lectures-par-utilisateur/{annee}/{mois}")
    public CollectionModel<LectureParUtilisateurDto> getLecturesParUtilisateur(
            @PathVariable int annee, @PathVariable int mois) {
        List<LectureParUtilisateurDto> list = statistiqueService.getLecturesParUtilisateur(annee, mois);
        return CollectionModel.of(list);
    }

    /** Retourne le nombre total de consultations d'un livre */
    @GetMapping("/consultations/{livreId}")
    public ResponseEntity<ConsultationLivreDto> getConsultations(@PathVariable Long livreId) {
        return ResponseEntity.ok(statistiqueService.getConsultationsByLivre(livreId));
    }

    /** Vérifie la validité de l'abonnement d'un utilisateur */
    @GetMapping("/validite-abonnement/{userId}")
    public ResponseEntity<ValiditeAbonnementDto> getValiditeAbonnement(@PathVariable Long userId) {
        return ResponseEntity.ok(statistiqueService.checkValiditeAbonnement(userId));
    }
}
