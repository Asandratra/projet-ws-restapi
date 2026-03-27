package mg.projet.restapi;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import mg.projet.restapi.model.HistoriqueAbonnement;
import mg.projet.restapi.model.HistoriqueLecture;
import mg.projet.restapi.model.HistoriquePaiementLivre;
import mg.projet.restapi.model.Livre;
import mg.projet.restapi.model.ModePaiement;
import mg.projet.restapi.model.TypeAbonnement;
import mg.projet.restapi.model.TypeLivre;
import mg.projet.restapi.model.User;
import mg.projet.restapi.repository.HistoriqueAbonnementRepository;
import mg.projet.restapi.repository.HistoriqueLectureRepository;
import mg.projet.restapi.repository.HistoriquePaiementLivreRepository;
import mg.projet.restapi.repository.LivreRepository;
import mg.projet.restapi.repository.ModePaiementRepository;
import mg.projet.restapi.repository.TypeAbonnementRepository;
import mg.projet.restapi.repository.TypeLivreRepository;
import mg.projet.restapi.repository.UserRepository;

/**
 * donnees de test
 */
@Component
public class DataInitializer implements CommandLineRunner {

        @Autowired
        private TypeLivreRepository typeLivreRepository;

        @Autowired
        private LivreRepository livreRepository;

        @Autowired
        private TypeAbonnementRepository typeAbonnementRepository;

        @Autowired
        private ModePaiementRepository modePaiementRepository;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private HistoriqueAbonnementRepository historiqueAbonnementRepository;

        @Autowired
        private HistoriqueLectureRepository historiqueLectureRepository;

        @Autowired
        private HistoriquePaiementLivreRepository historiquePaiementLivreRepository;

        @Override
        public void run(String... args) {
                // Ne pas réinsérer si des données existent déjà
                if (typeLivreRepository.count() > 0)
                        return;

                // Types de livre
                TypeLivre roman = typeLivreRepository.save(new TypeLivre("Roman"));
                TypeLivre scifi = typeLivreRepository.save(new TypeLivre("Science-Fiction"));
                TypeLivre policier = typeLivreRepository.save(new TypeLivre("Policier"));

                // Types d'abonnement
                TypeAbonnement mensuel = typeAbonnementRepository.save(new TypeAbonnement("Mensuel", 5000.0));
                TypeAbonnement trimestriel = typeAbonnementRepository.save(new TypeAbonnement("Trimestriel", 12000.0));
                TypeAbonnement annuel = typeAbonnementRepository.save(new TypeAbonnement("Annuel", 40000.0));

                // Modes de paiement
                ModePaiement especes = modePaiementRepository.save(new ModePaiement("Espèces"));
                ModePaiement mobileMoney = modePaiementRepository.save(new ModePaiement("Mobile Money"));

                // Livres
                Livre livre1 = livreRepository.save(new Livre(null, roman, "Le Petit Prince", null, null,
                                "Antoine de Saint-Exupéry", LocalDate.of(1943, 4, 6),
                                "Un conte poétique", null, null, 1));
                Livre livre2 = livreRepository.save(new Livre(null, scifi, "Fondation", null, "Tome 1",
                                "Isaac Asimov", LocalDate.of(1951, 5, 1),
                                "La chute de l'Empire Galactique", null, null, 1));
                Livre livre3 = livreRepository.save(new Livre(null, policier, "Sherlock Holmes", null, null,
                                "Arthur Conan Doyle", LocalDate.of(1887, 1, 1),
                                "Aventures du célèbre détective", null, null, 1));
                Livre livre4 = livreRepository.save(new Livre(null, roman, "Les Misérables", null, null,
                                "Victor Hugo", LocalDate.of(1862, 1, 1),
                                "Un roman historique", null, null, 1));
                Livre livre5 = livreRepository.save(new Livre(null, scifi, "Dune", null, null,
                                "Frank Herbert", LocalDate.of(1965, 8, 1),
                                "Un classique de la SF", null, null, 1));

                // Données liées aux utilisateurs existants
                List<User> users = userRepository.findAll();
                if (!users.isEmpty()) {
                        User user = users.get(0);
                        LocalDate now = LocalDate.now();

                        // Historique abonnement
                        historiqueAbonnementRepository.save(new HistoriqueAbonnement(
                                        null, now.minusMonths(1), mensuel, especes, user, now.plusDays(15)));

                        // Historiques de lecture du mois
                        historiqueLectureRepository.save(new HistoriqueLecture(null, now, livre1, user));
                        historiqueLectureRepository.save(new HistoriqueLecture(null, now, livre2, user));
                        historiqueLectureRepository.save(new HistoriqueLecture(null, now.minusDays(2), livre1, user));
                        historiqueLectureRepository.save(new HistoriqueLecture(null, now.minusDays(3), livre3, user));
                        historiqueLectureRepository.save(new HistoriqueLecture(null, now.minusDays(5), livre2, user));

                        // Historiques de paiement de livres
                        historiquePaiementLivreRepository.save(new HistoriquePaiementLivre(
                                        null, now, livre2, user, 2000.0));
                        historiquePaiementLivreRepository.save(new HistoriquePaiementLivre(
                                        null, now.minusDays(1), livre5, user, 3500.0));
                }
        }
}
