# ListeEtudAPIREST Backend Module

This directory contains the monolithic Spring Boot backend service. Evolving with the requirements of **Partie 2**, this module has been enriched significantly to establish a clean, production-ready architecture.

## Overview of Enrichments (Version 2)

### 1. Architecture and Object Mapping
The codebase strictly relies on a layered structure isolating application logic correctly:
* `controller/`, `service/`, `repository/`, `entity/`, `dto/`, `mapper/`, `config/`
* Implementations decouple database Entities from the external presentation via **DTOs**, relying on Data Mappers.

### 2. Entity Relations & Logic
* The `Etudiant` schema now includes dynamic attributes (`age()` calculation using `LocalDate`).
* A new `Departement` entity is mapped with a `@ManyToOne` association linking an `Etudiant` to a single `Departement`.
* Repository has been expanded with a customized JPQL/derived query (`findByAnneePremiereInscription`).

### 3. API Endpoints (CRUD)
Comprehensive HTTP REST CRUD operations have been exposed adhering to status code mappings (`201 Created`, `204 No Content`, `404 Not Found`):
| Entity | Methods Supported | Route Prefix |
| :--- | :--- | :--- |
| **Etudiant** | GET, GET by ID, POST, PUT, DELETE | `/api/etudiants` |
| **Departement** | GET, GET by ID, POST, PUT, DELETE | `/api/departements` |

### 4. Resiliency & Enhancements
* **Global Error Handling**: Using a `@RestControllerAdvice`, exceptions such as `ResourceNotFoundException` (404) and `MethodArgumentNotValidException` (400) translate cleanly into structured JSON responses.
* **Redis Caching**: Frequently accessed data routes use Redis. Supported by `@EnableCaching`, fetching operations use `@Cacheable` and saving logic is synchronized with `@CacheEvict`.
* **OpenAPI Documentation**: Fully interactive API documentation is automatically generated. When the server runs, explore the schemas and test endpoints via Swagger UI at `http://localhost:8080/swagger-ui.html`.

### 5. Frontend Integration
A simplified JavaScript interface exists within `src/main/resources/static/index.html`. It executes a `fetch('/api/etudiants')` request and directly mounts the results onto the DOM without requiring a heavyweight JS Framework.

### 6. BDD Testing with Cucumber
This module employs BDD (Behavior-Driven Development) frameworks. Leveraging **Cucumber** combined with **JUnit 5**, scenarios defining behavior (e.g. `Calcul de l'âge d'un étudiant`) are established within `.feature` files in Gherkin structured formats, ensuring that the backend logic faithfully obeys human-readable acceptance rules.

## Deployment Ecosystem

#### Native Docker Image
The application defines a `Dockerfile`. The app is structured to be built and pushed to a Docker Hub registry as an artifact ready for scalable consumption:
```bash
docker build -t <votre-username>/etudiant-service:1.0 .
docker push <votre-username>/etudiant-service:1.0
```

#### Kubernetes (K3S) manifests
Accompanying YAML manifests are ready in the parent project's `k8s/` folder (`etudiant-deployment.yaml` and `postgres-deployment.yaml`). These definitions allow orchestrating pods using local or lightweight K8S installations such as K3S directly via `kubectl apply`.

---
