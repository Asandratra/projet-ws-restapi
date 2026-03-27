package mg.projet.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mg.projet.restapi.model.TypeLivre;

public interface TypeLivreRepository extends JpaRepository<TypeLivre, Long> {
}
