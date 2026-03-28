package mg.projet.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import mg.projet.restapi.model.HistoriquePaiementLivre;
import mg.projet.restapi.model.User;

import java.util.List;


public interface HistoriquePaiementLivreRepository extends JpaRepository<HistoriquePaiementLivre, Long> {

    /** Calcul des revenus mensuels par rapport au ventes des livres */
    @Query("SELECT COALESCE(SUM(hpl.prix), 0) FROM HistoriquePaiementLivre hpl WHERE extract(year from hpl.dateLecture) = :annee AND extract(month from hpl.dateLecture) = :mois")
    Double findRevenusMensuelLivres(@Param("annee") int annee, @Param("mois") int mois);

    List<HistoriquePaiementLivre> findByUtilisateurOrderByDateLectureDesc(User utilisateur);

    @Query("SELECT COALESCE(SUM(hpl.prix), 0) FROM HistoriquePaiementLivre hpl WHERE extract(year from hpl.dateLecture) = :annee AND extract(month from hpl.dateLecture) = :mois AND hpl.utilisateur.id = :id")
    Double findMontantPaiementMensuelLivresParUtilisateur(
        @Param("annee") int annee, 
        @Param("mois") int mois,
        @Param("id") Long id
    );
}
