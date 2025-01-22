# ForoHub

**Practicando Spring Framework, API REST y SOLID: Challenge Foro Hub**

Este proyecto es una API REST centrada en la gestión de tópicos (topics). La API permite realizar operaciones CRUD con tópicos y está diseñada siguiendo las mejores prácticas de REST, principios de SOLID y estándares modernos en desarrollo backend.

## Funcionalidades principales

La API está centrada específicamente en los tópicos y permite a los usuarios:

- Crear un nuevo tópico.
- Mostrar todos los tópicos creados.
- Mostrar un tópico específico.
- Actualizar un tópico existente.
- Eliminar un tópico (marcarlo como inactivo).

### Funcionalidades adicionales

Además de las operaciones básicas, la API incluye rutas para la gestión de usuarios y respuestas:

- **/usuario**: Gestión de usuarios.
- **/respuestas**: Gestión de respuestas asociadas a tópicos.

---

## Objetivos del Challenge

El objetivo principal del proyecto es implementar una API REST con las siguientes características:

- Rutas diseñadas siguiendo las mejores prácticas de REST.
- Validaciones realizadas según las reglas de negocio definidas.
- Persistencia de datos utilizando una base de datos relacional.
- Autenticación y autorización mediante **JWT (JSON Web Tokens)**.
- Documentación completa de la API generada con **Swagger**.

---

## Versiones y Tecnologías

- **Java**: Versión 17.
- **Maven**: Versión 4 en adelante.
- **Spring Framework**: Versión 3.2.3.
- **MySQL**: Versión 8 en adelante.
- **PostgreSQL**: Versión 16 en adelante.

---

## Dependencias principales

Las siguientes dependencias están integradas en el proyecto para asegurar su correcto funcionamiento:

- **Lombok**: Para reducir el boilerplate en las clases Java.
- **Spring Web**: Para construir la API REST.
- **Spring Boot DevTools**: Para facilitar el desarrollo y pruebas.
- **Spring Data JPA**: Para el acceso y manipulación de datos en la base de datos.
- **Flyway Migration**: Para la gestión de versiones de la base de datos.
- **MySQL Driver**: Conector para bases de datos MySQL.
- **Validation**: Para implementar validaciones en los datos recibidos.
- **Spring Security**: Para manejar autenticación y autorización.
- **Spring Doc**: Para la generación automática de documentación de la API.
- **Auth0 (Java JWT)**: Para la generación y validación de tokens JWT.

---

## Endpoints principales

### Rutas para la gestión de tópicos

- **GET /topicos**: Obtiene todos los tópicos activos.
- **GET /topicos/{id}**: Obtiene un tópico específico por su ID.
- **POST /topicos**: Registra un nuevo tópico en la base de datos.
- **PUT /topicos**: Actualiza la información de un tópico existente.
- **DELETE /topicos/{id}**: Marca un tópico como inactivo.

### Rutas adicionales

- **/usuario**: Incluye operaciones para la gestión de usuarios.
- **/respuestas**: Permite manejar respuestas asociadas a los tópicos.

---

## Documentación

Toda la documentación del proyecto fue generada utilizando la librería **SpringDoc**. Para acceder a una interfaz amigable de la documentación, puedes ingresar en tu navegador la siguiente ruta (reemplazando `{ruta_de_la_API}` por la URL base de tu API):

```
{ruta_de_la_API}/swagger-ui/index.html#/
```

---

## Base de datos

La API utiliza una base de datos relacional para la persistencia de la información. Puedes optar por usar **MySQL** o **PostgreSQL**, según tus necesidades. La gestión de las migraciones se realiza mediante **Flyway Migration** para garantizar la consistencia y versionado de la estructura de la base de datos.

---
