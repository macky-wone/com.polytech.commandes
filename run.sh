#!/bin/bash

# Script de lancement de l'application Gestion de Commandes
# Usage: ./run.sh [dev|test|prod]

set -e

PROFILE="${1:-dev}"
PORT="${2:-8080}"

echo "🚀 Lancement de l'application avec le profil: $PROFILE"
echo "📍 Port: $PORT"
echo ""

case $PROFILE in
    dev)
        echo "✨ Mode DÉVELOPPEMENT"
        echo "📊 Initialisation des données..."
        echo "📖 Swagger UI sera disponible sur http://localhost:$PORT/swagger-ui.html"
        echo ""
        ./mvnw spring-boot:run \
            -Dspring-boot.run.arguments="--spring.profiles.active=dev --server.port=$PORT" \
            -Dspring-boot.run.jvmArguments="-Dspring.profiles.active=dev"
        ;;
    
    test)
        echo "✨ Mode TEST"
        echo "🧪 Exécution des tests..."
        ./mvnw test -Dspring.profiles.active=test
        ;;
    
    prod)
        echo "✨ Mode PRODUCTION"
        echo "⚠️  Assurez-vous que les variables d'environnement sont configurées:"
        echo "   - DB_URL, DB_USER, DB_PWD"
        echo ""
        ./mvnw spring-boot:run \
            -Dspring-boot.run.arguments="--spring.profiles.active=prod --server.port=$PORT" \
            -Dspring-boot.run.jvmArguments="-Dspring.profiles.active=prod"
        ;;
    
    build)
        echo "✨ BUILD PRODUCTION"
        ./mvnw clean package -DskipTests
        echo ""
        echo "✅ Build réussi !"
        echo "📦 JAR disponible: target/commandes-0.0.1-SNAPSHOT.jar"
        echo ""
        echo "Pour lancer:"
        echo "  java -jar target/commandes-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev"
        ;;
    
    *)
        echo "❌ Profil inconnu: $PROFILE"
        echo ""
        echo "Usage: ./run.sh [dev|test|prod|build]"
        echo ""
        echo "Exemples:"
        echo "  ./run.sh dev      # Lancer en développement"
        echo "  ./run.sh test     # Exécuter les tests"
        echo "  ./run.sh prod     # Lancer en production"
        echo "  ./run.sh build    # Builder le JAR"
        exit 1
        ;;
esac
