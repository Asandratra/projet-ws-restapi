package mg.projet.restapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import mg.projet.restapi.model.User;
import java.util.Optional;


public interface UserRepository extends JpaRepository<User, Long>{
    Optional<User> findByMailAndMdp(String mail, String mdp);
    Optional<User> findByMail(String mail);

}
