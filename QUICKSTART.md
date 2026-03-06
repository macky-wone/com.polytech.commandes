# 📚 Aide Rapide - Application Gestion de Commandes

## 🎯 Commandes essentielles

### Lancer l'application

```bash
./run.sh dev              # Développement
./run.sh test             # Tests
./run.sh prod             # Production  
./run.sh build            # Builder le JAR
```

### Compiler

```bash
./mvnw clean compile      # Compiler sans tester
./mvnw clean package      # Package JAR
./mvnw test               # Lancer les tests
```

### Accéder à Swagger

```
http://localhost:8080/swagger-ui.html
```

---

## 📡 Exemples cURL rapides

### Authentification JWT

```bash
TOKEN=$(curl -s -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"admin123"}' | jq -r '.token')

echo "Token: $TOKEN"
```

Ajoutez `-H "Authorization: Bearer $TOKEN"` à chaque requête suivante.

```bash
# ===== CLIENTS =====
# Créer
curl -X POST http://localhost:8080/api/clients \
  -H "Content-Type: application/json" \
  -d '{"nom":"Jean","email":"jean@test.com"}'

# Lister tous
curl http://localhost:8080/api/clients

# Récupérer par ID
curl http://localhost:8080/api/clients/1

# Modifier
curl -X PUT http://localhost:8080/api/clients/1 \
  -H "Content-Type: application/json" \
  -d '{"nom":"Jean M","email":"jean.m@test.com"}'

# Supprimer
curl -X DELETE http://localhost:8080/api/clients/1


# ===== PRODUITS =====
# Créer
curl -X POST http://localhost:8080/api/produits \
  -H "Content-Type: application/json" \
  -d '{"nom":"Laptop","prix":1000,"stock":5}'

# Lister
curl http://localhost:8080/api/produits

# Mettre à jour stock
curl -X PATCH "http://localhost:8080/api/produits/1/stock?quantite=2"


# ===== COMMANDES =====
# Créer
curl -X POST http://localhost:8080/api/commandes \
  -H "Content-Type: application/json" \
  -d '{"clientId":1}'

# Lister
curl http://localhost:8080/api/commandes

# Voir d'un client
curl http://localhost:8080/api/commandes/client/1

# Changer statut
curl -X PATCH "http://localhost:8080/api/commandes/1/status?status=VALIDATED"


# ===== LIGNES DE COMMANDE =====
# Ajouter une ligne
curl -X POST http://localhost:8080/api/lignes-commande \
  -H "Content-Type: application/json" \
  -d '{"commandeId":1,"produitId":1,"quantite":2}'

# Voir lignes d'une commande
curl http://localhost:8080/api/lignes-commande/commande/1
```

---

## 📁 Fichiers importants

| Fichier                | Description               |
|------------------------|---------------------------|
| `README.md`            | Documentation complète    |
| `TESTING.md`           | Guide de test détaillé    |
| `run.sh`               | Script de lancement       |
| `.env.example`         | Variables d'environnement |
| `pom.xml`              | Dépendances Maven         |
| `application.yaml`     | Config par défaut         |
| `application-dev.yml`  | Config développement      |
| `application-prod.yml` | Config production         |

---

## 🔗 URLs importantes

| URL                                         |      Accès            |
|---------------------------------------------|-----------------------|
| `http://localhost:8080/swagger-ui.html`     | Documentation Swagger |
| `http://localhost:8080/api/clients`         | Endpoint clients      |
| `http://localhost:8080/api/produits`        | Endpoint produits     |
| `http://localhost:8080/api/commandes`       | Endpoint commandes    |
| `http://localhost:8080/api/lignes-commande` | Endpoint lignes       |

---

## ⚙️ Configuration variables d'environnement

```bash
export SPRING_PROFILES_ACTIVE=dev
export DB_URL=jdbc:mysql://localhost:3306/commandes_dev
export DB_USER=root
export DB_PWD=root123
export SERVER_PORT=8080
```

---

## 🐛 Problèmes courants

### Port 8080 déjà utilisé

```bash
lsof -ti:8080 | xargs kill -9
./run.sh dev
```

### Base de données non trouvée

```bash
# Vérifier MySQL
sudo service mysql status

# Créer la BD
mysql -u root -p -e "CREATE DATABASE commandes_dev;"
```

### Erreur compilation

```bash
./mvnw clean compile -X  # Voir les détails
```

---

## 📊 Statuts acceptés pour une commande

- `CREATED` - Commande créée (par défaut)
- `VALIDATED` - Commande validée
- `CANCELLED` - Commande annulée

---

## ✅ Checklist TP

- [x] Architecture en couches conforme
- [x] Repositories JPA
- [x] Services avec logique métier
- [x] API REST complète (CRUD)
- [x] DTOs (pas d'entités directes)
- [x] OpenAPI/Swagger
- [x] Profils Spring (dev, test, prod)
- [x] Variables d'environnement
- [x] DataInitializer
- [x] Gestion des erreurs
- [x] Documentation README
- [x] Codes HTTP corrects

---

## 📞 Support

**Encadrant:** Dr Samba SIDIBE  
**Email:** <ssidibe@ept.edu.sn>

---

*Dernière mise à jour: 27 janvier 2026*
