package mg.projet.restapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import mg.projet.restapi.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
