# Tournoi de joueurs - API

Ce projet est une API REST simple pour gérer un classement de joueurs lors d'un tournoi.

## Fonctionnalités

L'API offre les fonctionnalités suivantes :

* Ajouter un nouveau joueur
* Mettre à jour les points d'un joueur
* Récupérer les informations d'un joueur
* Obtenir la liste des joueurs triés par points
* Supprimer tous les joueurs

## Technologies utilisées

* [Kotlin](https://kotlinlang.org/)
* [Ktor](https://ktor.io/)
* [Koin](https://insert-koin.io/)
* [MongoDB](https://www.mongodb.com/)
* [KMongo](https://litote.org/kmongo/)
* [Docker](https://www.docker.com/)

## Configuration

Assurez-vous d'avoir installé les dépendances nécessaires :

* Java 11 ou supérieur
* Gradle
* Docker (si vous souhaitez exécuter l'application avec Docker)
* MongoDB (si vous ne souhaitez pas utiliser Docker)

## Comment exécuter

### Localement

1. Clonez le dépôt
2. À la racine du projet, exécutez `./run.sh`

Le serveur démarre alors sur le port 8080.

### Avec Docker

1. Clonez le dépôt
2. À la racine du projet, exécutez `docker-compose up`

Le serveur démarre alors sur le port 8080.


## ROADMAP

* Mettre en place une architecture hexagonale : DDD, séparation des couches, DTO, etc.
* Utiliser MongoDb en reactive
* Optimisation calcul ranking des joueurs
* Gestion des erreurs
* Test integration avec Koin
* Métriques/logging
* Sécurisation des API
* Configuration par environnement (local, dev, preprod, prod)

## API Endpoints

Une interface Swagger UI est disponible à : http://localhost:8080/openapi

### POST /players

Crée un nouveau joueur.

### PUT /players/{id}

Mettre à jour le nombre de points d'un joueur.

### GET /players/{id}

Récupère les informations d'un joueur spécifique.

### GET /players

Récupère la liste de tous les joueurs, triés par points.

### DELETE /players

Supprime tous les joueurs.
