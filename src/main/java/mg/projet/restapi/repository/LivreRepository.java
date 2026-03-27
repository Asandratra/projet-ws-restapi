package mg.projet.restapi.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import mg.projet.restapi.model.Livre;

public interface LivreRepository extends JpaRepository<Livre, Long> {

       /**
        * liste des livres avec filtres dynamiques
        */
       @Query("SELECT l FROM Livre l WHERE " +
                     "(:titre IS NULL OR LOWER(l.titre) LIKE LOWER(CONCAT('%', :titre, '%'))) AND " +
                     "(:auteur IS NULL OR LOWER(l.auteur) LIKE LOWER(CONCAT('%', :auteur, '%'))) AND " +
                     "(:etat IS NULL OR l.etat = :etat) AND " +
                     "(:typeLivreId IS NULL OR l.typeLivre.id = :typeLivreId)")
       List<Livre> findWithFilters(@Param("titre") String titre,
                     @Param("auteur") String auteur,
                     @Param("etat") Integer etat,
                     @Param("typeLivreId") Long typeLivreId);
}
