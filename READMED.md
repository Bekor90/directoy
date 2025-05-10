# 📦 User API - Spring Boot 3  + H2

Este proyecto es una API REST construida con **Java 17**, **Spring Boot 3**, **Maven** y base de datos **H2 en memoria**. 
Implementa registro de usuarios y seguridad con Spring, siguiendo el patrón **MVC** y utilizando el patrón de diseño **Builder**.

---

## Características

- Registro de usuario con validaciones de email y contraseña
- Base de datos H2 embebida
- Patrón de diseño Builder aplicado en entidades

---

## ⚙ Requisitos

- Java 17+
- Maven

---

## Visualizar request y response posterior a ejecución 
## YAML
http://localhost:8080/v3/api-docs.yaml
## JSON
http://localhost:8080/v3/api-docs

## 🚀️ Cómo ejecutar el proyecto

### 1. Clona el repositorio
### 2. importa curl en postman, directorio docs, abrir archivo copiar y pegar en postman

```bash
git clone https://github.com/Bekor90/directoy.git
cd directoy
mvn spring-boot:run