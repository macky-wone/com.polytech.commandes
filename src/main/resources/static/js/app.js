// API Base URL
const API_URL = 'http://localhost:8080/api';

// ===== MESSAGE HANDLING =====
function showMessage(message, type = 'info') {
    const messageDiv = document.getElementById('message');
    messageDiv.textContent = message;
    messageDiv.className = `message show ${type}`;
    setTimeout(() => {
        messageDiv.classList.remove('show');
    }, 5000);
}

// ===== TAB SWITCHING =====
function switchTab(tabName) {
    // Hide all tabs
    document.querySelectorAll('.tab-content').forEach(tab => {
        tab.classList.remove('active');
    });

    // Remove active state from all buttons
    document.querySelectorAll('.tab-btn').forEach(btn => {
        btn.classList.remove('active');
    });

    // Show selected tab
    document.getElementById(tabName + '-tab').classList.add('active');
    event.target.classList.add('active');

    // Load data for the tab
    if (tabName === 'clients') loadClients();
    if (tabName === 'produits') loadProduits();
    if (tabName === 'commandes') loadCommandes();
    if (tabName === 'lignes') loadLignesCommande();
}

// ===== CLIENTS =====
async function loadClients() {
    try {
        const response = await fetch(`${API_URL}/clients`);
        const clients = await response.json();

        const clientsList = document.getElementById('clients-list');
        clientsList.innerHTML = '';

        if (clients.length === 0) {
            clientsList.innerHTML = '<p class="loading">Aucun client</p>';
            return;
        }

        clients.forEach(client => {
            const card = document.createElement('div');
            card.className = 'card';
            card.innerHTML = `
                <div class="card-title">👤 ${client.nom}</div>
                <div class="card-content">
                    <strong>Email:</strong> ${client.email}
                </div>
                <div class="card-content">
                    <strong>ID:</strong> ${client.id}
                </div>
                <div class="card-actions">
                    <button class="btn-danger" onclick="deleteClient(${client.id})">Supprimer</button>
                </div>
            `;
            clientsList.appendChild(card);
        });

        // Update dropdown
        updateClientDropdown(clients);
    } catch (error) {
        showMessage('Erreur lors du chargement des clients', 'error');
        console.error(error);
    }
}

async function createClient(event) {
    event.preventDefault();

    const nom = document.getElementById('client-nom').value;
    const email = document.getElementById('client-email').value;

    try {
        const response = await fetch(`${API_URL}/clients`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ nom, email })
        });

        if (response.ok) {
            showMessage('✅ Client créé avec succès !', 'success');
            document.getElementById('client-form').reset();
            loadClients();
        } else {
            const error = await response.text();
            showMessage(`❌ Erreur: ${error}`, 'error');
        }
    } catch (error) {
        showMessage('Erreur lors de la création du client', 'error');
        console.error(error);
    }
}

async function deleteClient(clientId) {
    if (!confirm('Êtes-vous sûr?')) return;

    try {
        const response = await fetch(`${API_URL}/clients/${clientId}`, {
            method: 'DELETE'
        });

        if (response.ok) {
            showMessage('✅ Client supprimé !', 'success');
            loadClients();
        } else {
            showMessage('❌ Erreur lors de la suppression', 'error');
        }
    } catch (error) {
        showMessage('Erreur', 'error');
        console.error(error);
    }
}

function updateClientDropdown(clients) {
    const dropdown = document.getElementById('commande-client');
    dropdown.innerHTML = '<option value="">-- Sélectionner un client --</option>';
    clients.forEach(client => {
        const option = document.createElement('option');
        option.value = client.id;
        option.textContent = client.nom;
        dropdown.appendChild(option);
    });
}

// ===== PRODUITS =====
async function loadProduits() {
    try {
        const response = await fetch(`${API_URL}/produits`);
        const produits = await response.json();

        const produitsList = document.getElementById('produits-list');
        produitsList.innerHTML = '';

        if (produits.length === 0) {
            produitsList.innerHTML = '<p class="loading">Aucun produit</p>';
            return;
        }

        produits.forEach(produit => {
            const card = document.createElement('div');
            card.className = 'card';
            const stockColor = produit.stock > 10 ? '#27ae60' : (produit.stock > 0 ? '#f39c12' : '#e74c3c');
            card.innerHTML = `
                <div class="card-title">📦 ${produit.nom}</div>
                <div class="card-content">
                    <strong>Prix:</strong> ${produit.prix.toFixed(2)} €
                </div>
                <div class="card-content">
                    <strong style="color: ${stockColor}">Stock:</strong> <span style="color: ${stockColor}; font-weight: bold;">${produit.stock}</span>
                </div>
                <div class="card-content">
                    <strong>ID:</strong> ${produit.id}
                </div>
                <div class="card-actions">
                    <button class="btn-danger" onclick="deleteProduit(${produit.id})">Supprimer</button>
                </div>
            `;
            produitsList.appendChild(card);
        });

        // Update dropdown
        updateProduitDropdown(produits);
    } catch (error) {
        showMessage('Erreur lors du chargement des produits', 'error');
        console.error(error);
    }
}

async function createProduit(event) {
    event.preventDefault();

    const nom = document.getElementById('produit-nom').value;
    const prix = parseFloat(document.getElementById('produit-prix').value);
    const stock = parseInt(document.getElementById('produit-stock').value);

    try {
        const response = await fetch(`${API_URL}/produits`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ nom, prix, stock })
        });

        if (response.ok) {
            showMessage('✅ Produit créé avec succès !', 'success');
            document.getElementById('produit-form').reset();
            loadProduits();
        } else {
            showMessage('❌ Erreur lors de la création', 'error');
        }
    } catch (error) {
        showMessage('Erreur', 'error');
        console.error(error);
    }
}

async function deleteProduit(produitId) {
    if (!confirm('Êtes-vous sûr?')) return;

    try {
        const response = await fetch(`${API_URL}/produits/${produitId}`, {
            method: 'DELETE'
        });

        if (response.ok) {
            showMessage('✅ Produit supprimé !', 'success');
            loadProduits();
        } else {
            showMessage('❌ Erreur', 'error');
        }
    } catch (error) {
        showMessage('Erreur', 'error');
        console.error(error);
    }
}

function updateProduitDropdown(produits) {
    const dropdown = document.getElementById('ligne-produit');
    dropdown.innerHTML = '<option value="">-- Sélectionner un produit --</option>';
    produits.forEach(produit => {
        const option = document.createElement('option');
        option.value = produit.id;
        option.textContent = `${produit.nom} (${produit.stock} en stock)`;
        dropdown.appendChild(option);
    });
}

// ===== COMMANDES =====
async function loadCommandes() {
    try {
        const response = await fetch(`${API_URL}/commandes`);
        const commandes = await response.json();

        const commandesList = document.getElementById('commandes-list');
        commandesList.innerHTML = '';

        if (commandes.length === 0) {
            commandesList.innerHTML = '<p class="loading">Aucune commande</p>';
            return;
        }

        commandes.forEach(commande => {
            const statusClass = `status-${commande.status.toLowerCase()}`;
            const dateFormatted = new Date(commande.dateCommande).toLocaleDateString('fr-FR');

            const card = document.createElement('div');
            card.className = 'card';
            card.innerHTML = `
                <div class="card-title">🛒 Commande #${commande.id}</div>
                <div class="card-content">
                    <strong>Client ID:</strong> ${commande.clientId}
                </div>
                <div class="card-content">
                    <strong>Date:</strong> ${dateFormatted}
                </div>
                <div class="card-content">
                    <strong>Total:</strong> ${commande.total.toFixed(2)} €
                </div>
                <div class="status-badge ${statusClass}">${commande.status}</div>
                <div class="card-actions">
                    <button class="btn-danger" onclick="deleteCommande(${commande.id})">Supprimer</button>
                </div>
            `;
            commandesList.appendChild(card);
        });

        // Update dropdown
        updateCommandeDropdown(commandes);
    } catch (error) {
        showMessage('Erreur lors du chargement des commandes', 'error');
        console.error(error);
    }
}

async function createCommande(event) {
    event.preventDefault();

    const clientId = parseInt(document.getElementById('commande-client').value);

    try {
        const response = await fetch(`${API_URL}/commandes`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ clientId })
        });

        if (response.ok) {
            showMessage('✅ Commande créée !', 'success');
            document.getElementById('commande-form').reset();
            loadCommandes();
        } else {
            showMessage('❌ Erreur', 'error');
        }
    } catch (error) {
        showMessage('Erreur', 'error');
        console.error(error);
    }
}

async function deleteCommande(commandeId) {
    if (!confirm('Êtes-vous sûr?')) return;

    try {
        const response = await fetch(`${API_URL}/commandes/${commandeId}`, {
            method: 'DELETE'
        });

        if (response.ok) {
            showMessage('✅ Commande supprimée !', 'success');
            loadCommandes();
        } else {
            showMessage('❌ Erreur', 'error');
        }
    } catch (error) {
        showMessage('Erreur', 'error');
        console.error(error);
    }
}

function updateCommandeDropdown(commandes) {
    const dropdown = document.getElementById('ligne-commande');
    dropdown.innerHTML = '<option value="">-- Sélectionner une commande --</option>';
    commandes.forEach(commande => {
        const option = document.createElement('option');
        option.value = commande.id;
        option.textContent = `Commande #${commande.id} - ${commande.status}`;
        dropdown.appendChild(option);
    });
}

// ===== LIGNES COMMANDE =====
async function loadLignesCommande() {
    try {
        const response = await fetch(`${API_URL}/lignesCommande`);
        const lignes = await response.json();

        const lignesList = document.getElementById('lignes-list');
        lignesList.innerHTML = '';

        if (lignes.length === 0) {
            lignesList.innerHTML = '<p class="loading">Aucune ligne de commande</p>';
            return;
        }

        lignes.forEach(ligne => {
            const card = document.createElement('div');
            card.className = 'card';
            card.innerHTML = `
                <div class="card-title">📝 Ligne #${ligne.id}</div>
                <div class="card-content">
                    <strong>Commande ID:</strong> ${ligne.commandeId}
                </div>
                <div class="card-content">
                    <strong>Produit ID:</strong> ${ligne.produitId}
                </div>
                <div class="card-content">
                    <strong>Quantité:</strong> ${ligne.quantite}
                </div>
                <div class="card-content">
                    <strong>Prix Unitaire:</strong> ${ligne.prixUnitaire.toFixed(2)} €
                </div>
                <div class="card-content">
                    <strong>Sous-total:</strong> ${(ligne.quantite * ligne.prixUnitaire).toFixed(2)} €
                </div>
                <div class="card-actions">
                    <button class="btn-danger" onclick="deleteLigneCommande(${ligne.id})">Supprimer</button>
                </div>
            `;
            lignesList.appendChild(card);
        });
    } catch (error) {
        showMessage('Erreur lors du chargement des lignes', 'error');
        console.error(error);
    }
}

async function createLigneCommande(event) {
    event.preventDefault();

    const commandeId = parseInt(document.getElementById('ligne-commande').value);
    const produitId = parseInt(document.getElementById('ligne-produit').value);
    const quantite = parseInt(document.getElementById('ligne-quantite').value);

    try {
        const response = await fetch(`${API_URL}/lignesCommande`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ commandeId, produitId, quantite })
        });

        if (response.ok) {
            showMessage('✅ Ligne ajoutée !', 'success');
            document.getElementById('ligne-form').reset();
            loadLignesCommande();
            loadCommandes(); // Refresh pour voir le total mis à jour
        } else {
            const error = await response.text();
            showMessage(`❌ Erreur: ${error}`, 'error');
        }
    } catch (error) {
        showMessage('Erreur', 'error');
        console.error(error);
    }
}

async function deleteLigneCommande(ligneId) {
    if (!confirm('Êtes-vous sûr?')) return;

    try {
        const response = await fetch(`${API_URL}/lignesCommande/${ligneId}`, {
            method: 'DELETE'
        });

        if (response.ok) {
            showMessage('✅ Ligne supprimée !', 'success');
            loadLignesCommande();
            loadCommandes();
        } else {
            showMessage('❌ Erreur', 'error');
        }
    } catch (error) {
        showMessage('Erreur', 'error');
        console.error(error);
    }
}

// ===== INIT =====
document.addEventListener('DOMContentLoaded', () => {
    loadClients();
});
