# Bibliothèque REST API

API REST pour la gestion d'une bibliothèque numérique, développée avec Spring Boot.

---

## Objectif

Ce projet a pour objectif de fournir une API RESTful complète pour la gestion d'une bibliothèque numérique. Il permet de gérer les utilisateurs, les livres, les abonnements, les paiements et l'historique de lecture, tout en offrant des statistiques analytiques à destination des administrateurs.

---

## Stack technique

- **Java 21** + **Spring Boot 4.0.3**
- **Spring Security** + **JWT** (JJWT 0.12.6)
- **Spring Data JPA** + **H2** (base de données en mémoire)
- **Spring HATEOAS**
- **SpringDoc OpenAPI** (Swagger UI)
- **Lombok**
- **BCrypt** pour le chiffrement des mots de passe

---

## Fonctionnalités

### Gestion des utilisateurs
- Inscription et connexion
- Mise à jour du profil et changement de mot de passe
- Attribution de rôles
- Consultation de l'historique d'abonnement, de lecture et de paiement par utilisateur

### Gestion des livres
- CRUD complet sur les livres
- Filtrage par titre, auteur, état ou type de livre
- Gestion des types de livres (catégories)

### Gestion des abonnements
- Types d'abonnements avec tarification
- Historique des abonnements par utilisateur
- Vérification de la validité d'un abonnement

### Gestion des paiements
- Modes de paiement configurables
- Historique des paiements de livres

### Historique de lecture
- Enregistrement et consultation des lectures par utilisateur

### Statistiques (Admin)
- Livres les plus lus par mois
- Types de livres les plus populaires
- Revenus mensuels (abonnements + achats)
- Lectures par utilisateur
- Nombre de consultations par livre
- Montants mensuels par utilisateur



### Authentification par JWT

L'API utilise des **JSON Web Tokens (JWT)** pour authentifier les requêtes.

- À la connexion (`POST /api/auth/login`), un token JWT est généré et retourné.
- Ce token doit être inclus dans le header `Authorization` de chaque requête protégée :
```
Authorization: Bearer <token>
```

- Durée de validité par défaut : **Une heure** (configurable via `jwt.expiration`)
- Algorithme de signature : **HS256**
- Le token embarque les informations de l'utilisateur : id, nom, prénom, email, rôles

### Gestion des rôles

L'accès aux endpoints est contrôlé par des rôles :

| Rôle | Permissions |
|------|-------------|
| `ADMIN` | Accès complet à toutes les ressources |
| `VIEW_USERS` | Consultation de la liste des utilisateurs |
| `ASSIGN_ROLE` | Attribution de rôles aux utilisateurs |

Les rôles sont extensibles et assignables dynamiquement par un administrateur.

### Endpoints publics

Les endpoints suivants sont accessibles sans authentification :

- `POST /api/auth/login` — Connexion
- `POST /api/auth/register` — Inscription
- `GET /swagger-ui/**` — Documentation Swagger
- `GET /v3/api-docs/**` — Schéma OpenAPI

### Gestion des erreurs de sécurité

| Code HTTP | Situation |
|-----------|-----------|
| `401 Unauthorized` | Token JWT absent ou invalide |
| `403 Forbidden` | Token valide mais rôle insuffisant |

### Chiffrement des mots de passe

Les mots de passe sont chiffrés avec **BCrypt** avant stockage. Ils ne sont jamais retournés dans les réponses de l'API.

### Prérequis

- Java 21+
- Maven

### Lancer le projet
```bash
./mvnw spring-boot:run
```

### Accès
**Localement**
| Interface | URL |
|-----------|-----|
| API | `http://localhost:8080` |
| Swagger UI | `http://localhost:8080/swagger-ui/index.html` |
| Docs JSON | `http://localhost:8080/v3/api-docs` |

**Déploiement**
| Interface | URL |
|-----------|-----|
| API | `https://restapi-kanto-asandratra.onrender.com` |
| Swagger UI | `https://restapi-kanto-asandratra.onrender.com/swagger-ui/index.html` |
| Docs JSON | `https://restapi-kanto-asandratra.onrender.com/v3/api-docs` |
>Déploiement gratuit avec Render

**Credentials H2 :**
- JDBC URL : `jdbc:h2:mem:bibliotheque`
- Username : `asa`
- Password : *(vide)*

---

## Structure des principaux endpoints

| Ressource | Base URL |
|-----------|----------|
| Authentification | `/api/auth` |
| Utilisateurs | `/api/users` |
| Livres | `/api/livres` |
| Types de livres | `/api/type-livre` |
| Abonnements | `/api/type-abonnement` |
| Modes de paiement | `/api/mode-paiement` |
| Historique abonnements | `/api/historique-abonnement` |
| Historique lectures | `/api/historique-lecture` |
| Historique paiements | `/api/historique-paiement-livre` |
| Statistiques | `/api/stats` |
| Rôles | `/api/role` |
