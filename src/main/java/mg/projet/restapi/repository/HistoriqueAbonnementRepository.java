package mg.projet.restapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import mg.projet.restapi.model.HistoriqueAbonnement;

public interface HistoriqueAbonnementRepository extends JpaRepository<HistoriqueAbonnement, Long> {

    /**
     * liste des abonnements d'un utilisateur avec condition (date d'expiration >=
     * aujourd'hui)
     */
    @Query("SELECT ha FROM HistoriqueAbonnement ha WHERE ha.utilisateur.id = :userId AND ha.dateExpiration >= CURRENT_DATE ORDER BY ha.dateExpiration DESC")
    List<HistoriqueAbonnement> findAbonnementsActifsByUserId(@Param("userId") Long userId);

    /** Calcul des revenus mensuels par rapport aux abonnements */
    @Query("SELECT COALESCE(SUM(ha.typeAbonnement.prix), 0) FROM HistoriqueAbonnement ha WHERE extract(year from ha.datePaiement) = :annee AND extract(month from ha.datePaiement) = :mois")
    Double findRevenusMensuelAbonnements(@Param("annee") int annee, @Param("mois") int mois);
}
