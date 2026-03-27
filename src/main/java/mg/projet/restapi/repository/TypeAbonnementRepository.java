package mg.projet.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mg.projet.restapi.model.TypeAbonnement;

public interface TypeAbonnementRepository extends JpaRepository<TypeAbonnement, Long> {
}
