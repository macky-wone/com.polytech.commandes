# API Gestion de Commandes - Spring Boot

## 📋 Vue d'ensemble

Application REST complète pour la gestion des commandes, clients et produits, développée avec **Spring Boot 4.0**.

**Auteur :** Macky Mamadou WONE  
**Email :** <wone.macky-mamadou@polytech.edu.sn>  
**Institut :** Institut Polytechnique de Saint-Louis (IPSL)

---

## ✨ Fonctionnalités principales

✅ **Gestion des clients** - CRUD complet  
✅ **Gestion des produits** - CRUD avec gestion du stock  
✅ **Gestion des commandes** - Création, suivi, mise à jour du statut  
✅ **Gestion des lignes de commande** - Vérification du stock disponible  
✅ **Calcul automatique** - Total des commandes  
✅ **Documentation OpenAPI/Swagger** - API entièrement documentée  
✅ **Profils Spring** - dev, test, prod configurés  
✅ **Variables d'environnement** - Secrets externalisés  
✅ **Initialisation des données** - CommandLineRunner pour le profil dev

---

## 🏗️ Architecture

```
com.polytech.commandes/
├── entity/              # Entités JPA
│   ├── Client.java
│   ├── Commande.java
│   ├── LigneCommande.java
│   ├── Produit.java
│   └── StatusCommande.java
├── repository/          # Repositories JPA
├── service/             # Interfaces des services
│   └── impl/            # Implémentations
├── controller/          # Contrôleurs REST
├── dto/                 # Data Transfer Objects
├── exception/           # Exceptions personnalisées
├── config/              # Configurations (OpenAPI)
├── init/                # Initialisation des données
└── CommandesApplication.java
```

---

## 🗄️ Modèle de données

### Client

```json
{
  "id": 1,
  "nom": "Macky Mamadou WONE",
  "email": "wone.macky@polytech.edu.sn"
}
```

### Produit

```json
{
  "id": 1,
  "nom": "Laptop Dell",
  "prix": 850.00,
  "stock": 5
}
```

### Commande

```json
{
  "id": 1,
  "dateCommande": "2026-01-27T17:51:00",
  "status": "CREATEDCR",
  "clientId": 1,
  "total": 925.00
}
```

### LigneCommande

```json
{
  "id": 1,
  "commandeId": 1,
  "produitId": 1,
  "quantite": 1,
  "prixUnitaire": 850.00,
  "sousTotal": 850.00
}
```

---

## 📋 Pré-requis

- **Java 21** ou supérieur
- **Maven 3.8+**
- **MySQL 8.0+** (pour production)
- **Git**

---

## 🚀 Installation et lancement

### 1. Cloner le dépôt

```bash
git clone <url-du-repo>
cd com.polytech.commandes
```

### 2. Profil DEV (avec initialisation des données)

```bash
./mvnw spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=dev"
```

**Résultat :**

- Base de données MySQL locale : `commandes_dev`
- Application sur `http://localhost:8080`
- **Swagger UI** : `http://localhost:8080/swagger-ui.html`
- Les données de test sont automatiquement créées

### 3. Profil PROD

```bash
./mvnw spring-boot:run -Dspring-boot.run.arguments="--spring.profiles.active=prod"
```

**Requiert les variables d'environnement :**

```bash
export SPRING_PROFILES_ACTIVE=prod
export DB_URL=jdbc:mysql://prod-db:3306/commandes_prod
export DB_USER=votre_utilisateur
export DB_PWD=votre_mot_de_passe
export SERVER_PORT=8080
```

⚠️ **Voir `.env.example` pour la configuration complète**

### 4. Tests

```bash
./mvnw test -Dspring.profiles.active=test
```

### 5. Build JAR

```bash
./mvnw clean package
java -jar target/commandes-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev
```

---

## 📚 Documentation API

### Swagger UI

**URL :** `http://localhost:8080/swagger-ui.html`

Vous y trouverez :

- Tous les endpoints documentés
- Modèles de requête/réponse
- Possibilité de tester directement les API

### Groupes d'endpoints

#### 👥 Clients (`/api/clients`)

```
GET    /api/clients               # Lister tous les clients
GET    /api/clients/{id}          # Récupérer un client
GET    /api/clients/email/{email} # Rechercher par email
POST   /api/clients               # Créer un client
PUT    /api/clients/{id}          # Mettre à jour
DELETE /api/clients/{id}          # Supprimer
```

#### 📦 Produits (`/api/produits`)

```
GET    /api/produits              # Lister tous les produits
GET    /api/produits/{id}         # Récupérer un produit
GET    /api/produits/nom/{nom}    # Rechercher par nom
POST   /api/produits              # Créer un produit
PUT    /api/produits/{id}         # Mettre à jour
PATCH  /api/produits/{id}/stock   # Mettre à jour le stock
DELETE /api/produits/{id}         # Supprimer
```

#### 🛒 Commandes (`/api/commandes`)

```
GET    /api/commandes                # Lister toutes les commandes
GET    /api/commandes/{id}           # Récupérer une commande
GET    /api/commandes/client/{id}    # Commandes d'un client
GET    /api/commandes/status/{status}# Commandes par statut
POST   /api/commandes                # Créer une commande
PUT    /api/commandes/{id}           # Mettre à jour
PATCH  /api/commandes/{id}/status    # Changer le statut
DELETE /api/commandes/{id}           # Supprimer
```

#### 📝 Lignes de Commande (`/api/lignes-commande`)

```
GET    /api/lignes-commande                 # Lister
GET    /api/lignes-commande/{id}            # Récupérer
GET    /api/lignes-commande/commande/{id}   # Lignes d'une commande
POST   /api/lignes-commande                 # Créer
PUT    /api/lignes-commande/{id}            # Mettre à jour
DELETE /api/lignes-commande/{id}            # Supprimer
```

---

## 📡 Exemples d'appels API

### Créer un client

```bash
curl -X POST http://localhost:8080/api/clients \
  -H "Content-Type: application/json" \
  -d '{"nom":"Dioxy DIOKHANE","email":"diokhane@polytech.edu.sn"}'
```

### Créer un produit

```bash
curl -X POST http://localhost:8080/api/produits \
  -H "Content-Type: application/json" \
  -d '{"nom":"Souris Logitech","prix":25.00,"stock":50}'
```

### Créer une commande

```bash
curl -X POST http://localhost:8080/api/commandes \
  -H "Content-Type: application/json" \
  -d '{"clientId":1}'
```

### Ajouter une ligne à une commande

```bash
curl -X POST http://localhost:8080/api/lignes-commande \
  -H "Content-Type: application/json" \
  -d '{"commandeId":1,"produitId":1,"quantite":2}'
```

### Changer le statut d'une commande

```bash
curl -X PATCH "http://localhost:8080/api/commandes/1/status?status=VALIDATED"
```

### Récupérer une commande

```bash
curl http://localhost:8080/api/commandes/1
```

---

## 🔧 Configuration

### Variables d'environnement

| Variable                  | Description               |  Exemple                                |
|---------------------------|-------------------------- |-----------------------------------------|
| `SPRING_PROFILES_ACTIVE`  | Profil actif              | `dev`, `test``prod`                     |
| `DB_URL`                  | URL de la base de données | `jdbc:mysql://localhost:3306/commandes` |
| `DB_USER`                 | Utilisateur de la BD      | À configurer                            |
| `DB_PWD`                  | Mot de passe de la BD     | À configurer                            |
| `SERVER_PORT`             | Port du serveur           | `8080`                                  |

⚠️ **SÉCURITÉ : Ne JAMAIS commiter les vraies valeurs de `DB_USER` et `DB_PWD`**  
✅ Toujours utiliser `.env` ou variables d'environnement

### Fichiers de configuration

- `application.yaml` - Configuration par défaut
- `application-dev.yml` - Profil développement (MySQL, logs détaillés)
- `application-test.yml` - Profil test (MySQL, logs réduits)
- `application-prod.yml` - Profil production (MySQL, validation uniquement)
- `application-macky.yml` - Profil personnel

---

## 🎯 Règles métier implémentées

✅ **Interdiction de commande si stock insuffisant**  
→ Une exception `StockInsuffisantException` est levée

✅ **Calcul automatique du total de la commande**  
→ Somme des `sousTotal` des lignes

✅ **Mise à jour du stock après validation**  
→ Stock décrémenté lors de l'ajout d'une ligne

✅ **Unicité des emails clients**  
→ Vérification avant création

✅ **Unicité des noms de produits**  
→ Vérification avant création

---

## 🧪 Tests

### Exécuter tous les tests

```bash
./mvnw test
```

### Exécuter une classe de test

```bash
./mvnw test -Dtest=ClientControllerTest
```

---

## 📦 Déploiement

### Build pour production

```bash
./mvnw clean package -DskipTests -Pprod
```

### Lancer le JAR

```bash
# Configurer les variables d'environnement AVANT de lancer
export DB_URL=jdbc:mysql://prod-db:3306/commandes
export DB_USER=votre_utilisateur
export DB_PWD=votre_mot_de_passe

# Puis lancer l'application
java -Dspring.profiles.active=prod \
     -jar target/commandes-0.0.1-SNAPSHOT.jar
```

⚠️ **Les identifiants ne doivent JAMAIS être codés en dur dans les scripts !**  
✅ Toujours passer par des variables d'environnement ou fichiers `.env`

---

## 🐛 Troubleshooting

**Erreur : Cannot connect to database**

```
→ Vérifier que MySQL est lancé
→ Vérifier les variables d'environnement
→ Vérifier l'URL de la BD
```

**Erreur : Port 8080 already in use**

```
→ Changer le port : --server.port=8081
→ Ou tuer le processus : lsof -ti:8080 | xargs kill -9
```

**DataInitializer ne s'exécute pas**

```
→ Vérifier que vous utilisez le profil dev
→ Vérifier les logs : grep "Initialisation" dans la console
```

---

## 📊 Structure des données

### Diagramme ER

```
CLIENT
  │ 1
  ├─────► COMMANDE (N)
           │ 1
           ├─────► LIGNE_COMMANDE (N)
                    │
                    └─────► PRODUIT (N)
                     N
```

### Relations JPA

- `Client` → `Commande` : `@OneToMany(mappedBy="client", cascade=ALL)`
- `Commande` → `LigneCommande` : `@OneToMany(mappedBy="commande", cascade=ALL)`
- `Produit` → `LigneCommande` : `@OneToMany(mappedBy="produit")`
- `Commande` → `Client` : `@ManyToOne(fetch=LAZY)`
- `LigneCommande` → `Produit` : `@ManyToOne(fetch=LAZY)`

---

## 👨‍💻 Développement

### Ajouter une nouvelle entité

1. Créer la classe dans `entity/`
2. Créer l'interface Repository dans `repository/`
3. Créer l'interface Service dans `service/`
4. Créer l'implémentation dans `service/impl/`
5. Créer le DTO dans `dto/`
6. Créer le Controller dans `controller/`

### Ajouter une méthode métier

1. Ajouter la signature dans l'interface Service
2. Implémenter dans ServiceImpl
3. Ajouter l'endpoint dans le Controller avec annotations OpenAPI

---

## 📝 Notes importantes

- Les entités ne sont **jamais exposées directement** - utilisation systématique de DTO
- Chaque ressource a un **CRUD complet**
- **Pas de logique métier dans les contrôleurs**
- Les **erreurs HTTP respectent les standards REST** (201, 204, 400, 404, etc.)
- L'API est **fully documented avec Swagger/OpenAPI**

---

## 📞 Support

**Encadrant :** Dr Samba SIDIBE  
**Email :** <ssidibe@ept.edu.sn>

---

## 📜 Licence

IPSL - Cycle Ingénieur - 2026

---

**Dernier commit :** 27 janvier 2026  
**Version :** 1.0.0-SNAPSHOT
