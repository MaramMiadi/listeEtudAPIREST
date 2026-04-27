# Full-Stack Microservices Project - Student & Grading Management

This repository contains a comprehensive microservices-based project evolving from a monolith to a distributed architecture. It includes a Spring Boot backend ecosystem, a modern Next.js web portal, and dual mobile frontends (React Native & Flutter).

## Project Evolution & Architecture (Part 1 - Part 7)

The project has evolved through several key integration phases:
- **Part 1 & 2**: Monolithic API with PostgreSQL, Redis Caching, and basic mobile apps.
- **Part 3 & 4**: Transition to Microservices with Service Discovery (Eureka), API Gateway, and Communication (Feign).
- **Part 5**: Mobile enhancements with department filtering and Gateway routing.
- **Part 6**: Development of a modern Web Frontend using Next.js (App Router).
- **Part 7**: Global orchestration of the entire stack using Docker Compose.

---

## 🏗️ Project Structure

### 🔌 Backend Services (Spring Cloud)
*   **`eureka-server`**: Service registry where all microservices register themselves for discovery.
*   **`api-gateway`**: The central entry point (Port 8080) that routes traffic to specific services.
*   **`listeEtudAPIREST`**: (Etudiant Service) Core service for managing student records and departments.
*   **`grading-service`**: Microservice dedicated to managing student grades and notes.

### 🌐 Web & Mobile Frontends
*   **`frontend`**: A modern Next.js portal (Tailwind CSS) for managing students and departments via the Gateway.
*   **`mobile-react-native`**: React Native (Expo) app featuring department-based filtering.
*   **`mobile_flutter`**: Flutter cross-platform app for student consultation.

### ⚙️ Infrastructure & DevOps
*   **`docker-compose.yml`**: Full stack orchestration (Postgres, Redis, Eureka, Gateway, Services, Web).
*   **`k8s`**: Kubernetes manifests for local cluster deployment (K3S).

---

## 🚀 How to Run the Project

### 🐳 Full Orchestration (Recommended)
The entire ecosystem (Databases, Infrastructure, Services, and Web Portal) can be launched with a single command:

```bash
docker-compose up -d --build
```

**Services will be available at:**
- **Gateway (Entry Point)**: `http://localhost:8080`
- **Web Frontend**: `http://localhost:3000`
- **Eureka Dashboard**: `http://localhost:8761`
- **Swagger Documentation**: Accessible via Gateway (e.g., `http://localhost:8080/swagger-ui.html`)

### 📱 Mobile Applications

#### React Native (Expo)
```bash
cd mobile-react-native
npm install
npm start
```

#### Flutter
```bash
cd mobile_flutter
flutter pub get
flutter run
```

---

## 🛠️ API Gateway Routing

| Route Path | Target Service | Purpose |
| :--- | :--- | :--- |
| `/api/etudiants/**` | `etudiant-service` | Student Management |
| `/api/departements/**` | `etudiant-service` | Department Management |
| `/api/notes/**` | `grading-service` | Grade Management |

---

## 📋 Prerequisites
- **Docker & Docker Compose**
- **Node.js** (for React Native and Next.js)
- **Flutter SDK**
- **Java 21+ & Maven** (for local development)

## 🎯 Project Features
- **Service Discovery**: Automated registration via Eureka.
- **Dynamic Routing**: Unified API access through Spring Cloud Gateway.
- **Inter-service Communication**: Feign clients for synchronous calls between services.
- **Caching**: Performance optimization using Redis.
- **Modern UI**: Responsive web design with Next.js and Tailwind CSS.
- **Agile Management**: Tasks tracked via Jira (Sprint 1 to Sprint 3).
