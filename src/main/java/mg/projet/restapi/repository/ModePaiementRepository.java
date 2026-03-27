package mg.projet.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mg.projet.restapi.model.ModePaiement;

public interface ModePaiementRepository extends JpaRepository<ModePaiement, Long> {
}
