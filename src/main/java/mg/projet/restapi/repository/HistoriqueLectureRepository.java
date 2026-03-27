package mg.projet.restapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import mg.projet.restapi.model.HistoriqueLecture;

public interface HistoriqueLectureRepository extends JpaRepository<HistoriqueLecture, Long> {

    /** liste des livres les plus lus par rapport au mois et annee inseree */
    @Query("SELECT hl.livre, COUNT(hl) as total FROM HistoriqueLecture hl WHERE extract(year from hl.dateLecture) = :annee AND extract(month from hl.dateLecture) = :mois GROUP BY hl.livre ORDER BY total DESC")
    List<Object[]> findLivresPopulairesByMois(@Param("annee") int annee, @Param("mois") int mois);

    /** liste des type de livres populaires par rapport au mois et annee inseree */
    @Query("SELECT hl.livre.typeLivre, COUNT(hl) as total FROM HistoriqueLecture hl WHERE extract(year from hl.dateLecture) = :annee AND extract(month from hl.dateLecture) = :mois GROUP BY hl.livre.typeLivre ORDER BY total DESC")
    List<Object[]> findTypeLivresPopulairesByMois(@Param("annee") int annee, @Param("mois") int mois);

    /** nombre de lectures par utilisateur par rapport au mois et annee inseree */
    @Query("SELECT hl.utilisateur, COUNT(hl) as total FROM HistoriqueLecture hl WHERE extract(year from hl.dateLecture) = :annee AND extract(month from hl.dateLecture) = :mois GROUP BY hl.utilisateur ORDER BY total DESC")
    List<Object[]> findLecturesParUtilisateur(@Param("annee") int annee, @Param("mois") int mois);

    /** nombre total de consultations d'un livre */
    @Query("SELECT COUNT(hl) FROM HistoriqueLecture hl WHERE hl.livre.id = :livreId")
    Long countConsultationsByLivreId(@Param("livreId") Long livreId);
}
