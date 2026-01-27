# Guide de Test de l'API

## 🧪 Collection Postman (ou équivalent cURL)

### 1. Tester les Clients

#### Créer un client

```bash
curl -X POST http://localhost:8080/api/clients \
  -H "Content-Type: application/json" \
  -d '{
    "nom": "Jean Dupont",
    "email": "jean.dupont@example.com"
  }'
```

**Réponse (201 Created):**

```json
{
  "id": 1,
  "nom": "Jean Dupont",
  "email": "jean.dupont@example.com"
}
```

#### Récupérer tous les clients

```bash
curl http://localhost:8080/api/clients
```

#### Récupérer un client par ID

```bash
curl http://localhost:8080/api/clients/1
```

#### Récupérer un client par email

```bash
curl http://localhost:8080/api/clients/email/jean.dupont@example.com
```

#### Mettre à jour un client

```bash
curl -X PUT http://localhost:8080/api/clients/1 \
  -H "Content-Type: application/json" \
  -d '{
    "nom": "Jean Dupont Modifié",
    "email": "jean.nouveau@example.com"
  }'
```

#### Supprimer un client

```bash
curl -X DELETE http://localhost:8080/api/clients/1
```

---

### 2. Tester les Produits

#### Créer un produit

```bash
curl -X POST http://localhost:8080/api/produits \
  -H "Content-Type: application/json" \
  -d '{
    "nom": "iPhone 15",
    "prix": 1299.99,
    "stock": 10
  }'
```

#### Récupérer tous les produits

```bash
curl http://localhost:8080/api/produits
```

#### Mettre à jour le stock

```bash
curl -X PATCH "http://localhost:8080/api/produits/1/stock?quantite=2"
```

---

### 3. Tester les Commandes

#### Créer une commande

```bash
curl -X POST http://localhost:8080/api/commandes \
  -H "Content-Type: application/json" \
  -d '{
    "clientId": 1
  }'
```

#### Récupérer les commandes d'un client

```bash
curl http://localhost:8080/api/commandes/client/1
```

#### Changer le statut d'une commande

```bash
curl -X PATCH "http://localhost:8080/api/commandes/1/status?status=VALIDATED"
```

**Statuts valides:** CREATED, VALIDATED, CANCELLED

---

### 4. Tester les Lignes de Commande

#### Ajouter une ligne (vérification du stock)

```bash
curl -X POST http://localhost:8080/api/lignes-commande \
  -H "Content-Type: application/json" \
  -d '{
    "commandeId": 1,
    "produitId": 1,
    "quantite": 2
  }'
```

**Si stock insuffisant (400 Bad Request):**

```json
{
  "timestamp": "2026-01-27T17:51:00",
  "status": 400,
  "error": "Bad Request",
  "message": "Insufficient stock for product: iPhone 15"
}
```

#### Récupérer les lignes d'une commande

```bash
curl http://localhost:8080/api/lignes-commande/commande/1
```

---

## ✅ Scénario complet de test

### 1. Créer un client

```bash
CLIENT=$(curl -s -X POST http://localhost:8080/api/clients \
  -H "Content-Type: application/json" \
  -d '{"nom":"Test User","email":"test@test.com"}' | jq -r '.id')

echo "Client créé avec ID: $CLIENT"
```

### 2. Créer des produits

```bash
PROD1=$(curl -s -X POST http://localhost:8080/api/produits \
  -H "Content-Type: application/json" \
  -d '{"nom":"Laptop","prix":1000.00,"stock":5}' | jq -r '.id')

PROD2=$(curl -s -X POST http://localhost:8080/api/produits \
  -H "Content-Type: application/json" \
  -d '{"nom":"Mouse","prix":30.00,"stock":50}' | jq -r '.id')

echo "Produits créés: $PROD1, $PROD2"
```

### 3. Créer une commande

```bash
COMMANDE=$(curl -s -X POST http://localhost:8080/api/commandes \
  -H "Content-Type: application/json" \
  -d "{\"clientId\":$CLIENT}" | jq -r '.id')

echo "Commande créée avec ID: $COMMANDE"
```

### 4. Ajouter des lignes

```bash
curl -s -X POST http://localhost:8080/api/lignes-commande \
  -H "Content-Type: application/json" \
  -d "{\"commandeId\":$COMMANDE,\"produitId\":$PROD1,\"quantite\":1}" | jq .

curl -s -X POST http://localhost:8080/api/lignes-commande \
  -H "Content-Type: application/json" \
  -d "{\"commandeId\":$COMMANDE,\"produitId\":$PROD2,\"quantite\":2}" | jq .
```

### 5. Valider la commande

```bash
curl -s -X PATCH "http://localhost:8080/api/commandes/$COMMANDE/status?status=VALIDATED" | jq .
```

### 6. Vérifier le total

```bash
curl -s http://localhost:8080/api/commandes/$COMMANDE | jq '.total'
```

---

## 🔍 Codes HTTP attendus

| Méthode           | Code|        Description               |
|-------------------|-----|----------------------------------|
| GET               | 200 | OK - Ressource trouvée           |
| POST              | 201 | Created - Ressource créée        |
| PUT               | 200 | OK - Mise à jour réussie         |
| PATCH             | 200 | OK - Modification partielle      |
| DELETE            | 204 | No Content - Suppression réussie |
| GET (inexistant)  | 404 | Not Found                        |
| POST (validation) | 400 | Bad Request                      |

---

## 📊 Validation des règles métier

### ✅ Règle 1 : Stock insuffisant

**Action :** Tenter d'ajouter plus de quantité que disponible

```bash
curl -X POST http://localhost:8080/api/lignes-commande \
  -H "Content-Type: application/json" \
  -d '{"commandeId":1,"produitId":1,"quantite":100}'
```

**Résultat :** 400 Bad Request avec message d'erreur

### ✅ Règle 2 : Calcul du total

**Action :** Créer une commande avec plusieurs lignes et vérifier le total

```bash
curl http://localhost:8080/api/commandes/1 | jq '.total'
```

**Résultat :** Total = somme des (prixUnitaire × quantité)

### ✅ Règle 3 : Unicité de l'email

**Action :** Créer deux clients avec le même email

```bash
curl -X POST http://localhost:8080/api/clients \
  -H "Content-Type: application/json" \
  -d '{"nom":"Client 1","email":"duplicate@test.com"}'

curl -X POST http://localhost:8080/api/clients \
  -H "Content-Type: application/json" \
  -d '{"nom":"Client 2","email":"duplicate@test.com"}'
```

**Résultat :** La deuxième requête retourne 400 Bad Request

---

## 🐛 Troubleshooting

### "Connection refused"

- Vérifier que l'application est lancée
- Vérifier le port (8080 par défaut)
- Vérifier que MySQL est accessible (si profil prod)

### "JSON parse error"

- Vérifier que le Content-Type est "application/json"
- Vérifier la syntaxe JSON

### "404 Not Found"

- Vérifier que l'ID existe
- Vérifier l'URL et la méthode HTTP

### "400 Bad Request - Stock insuffisant"

- Vérifier le stock disponible du produit
- Réduire la quantité demandée

---

## 📝 Notes

- Les données du profil `dev` sont automatiquement initialisées au démarrage
- Swagger UI accessible sur : <http://localhost:8080/swagger-ui.html>
- Les tests sont lancés avec : `./mvnw test`

---

**Dernière mise à jour :** 27 janvier 2026
