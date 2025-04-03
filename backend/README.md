# Despliegue de la Aplicación en Entorno Local

Este documento proporciona los pasos necesarios para desplegar la aplicación Spring Boot en un entorno local.

## Prerrequisitos

Asegúrate de tener instalados los siguientes requisitos antes de continuar:

- [Java 17](https://adoptopenjdk.net/)
- [Maven](https://maven.apache.org/) (versión recomendada: 3.8+)
- [MySQL](https://www.mysql.com/) o un servidor de base de datos compatible
- [Git](https://git-scm.com/)
- [Docker](https://www.docker.com/) (opcional, si deseas ejecutar la base de datos en un contenedor)

## Instalación

1. **Clonar el repositorio:**
   ```sh
   git clone https://github.com/DavidSantana-07/franquicias.git
   cd franquicias
   ```

2. **Verificar las variables de entorno:**
   - La aplicación ya cuenta con las variables de entorno configuradas en `src/main/resources/application.properties`.
   - Si necesitas modificar la configuración, edita `application.properties` o usa un archivo `.env` con los valores adecuados.

3. **Configurar la base de datos:**
   - Crea una base de datos en MySQL con el nombre correspondiente (ejemplo: `franchises_db`).
   - Ejecuta el siguiente script SQL para crear las tablas y datos iniciales:

   ```sql
   CREATE DATABASE IF NOT EXISTS franchises_db;
   USE franchises_db;

   -- Franchise Table
   CREATE TABLE IF NOT EXISTS franchise (
       id INT AUTO_INCREMENT PRIMARY KEY,
       name VARCHAR(100) NOT NULL,
       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
       deleted_at TIMESTAMP NULL,
       UNIQUE KEY uk_name (name)
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

   -- Branch Table
   CREATE TABLE IF NOT EXISTS branch (
       id INT AUTO_INCREMENT PRIMARY KEY,
       name VARCHAR(100) NOT NULL,
       franchise_id INT NOT NULL,
       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
       deleted_at TIMESTAMP NULL,
       UNIQUE KEY uk_name_franchise (name, franchise_id),
       CONSTRAINT fk_branch_franchise FOREIGN KEY (franchise_id) 
           REFERENCES franchise(id) ON DELETE CASCADE
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

   -- Product Table
   CREATE TABLE IF NOT EXISTS product (
       id INT AUTO_INCREMENT PRIMARY KEY,
       name VARCHAR(100) NOT NULL,
       stock INT NOT NULL DEFAULT 0,
       branch_id INT NOT NULL,
       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
       deleted_at TIMESTAMP NULL,
       UNIQUE KEY uk_name_branch (name, branch_id),
       CONSTRAINT fk_product_branch FOREIGN KEY (branch_id) 
           REFERENCES branch(id) ON DELETE CASCADE
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

   -- User Table
   CREATE TABLE IF NOT EXISTS user (
       id INT AUTO_INCREMENT PRIMARY KEY,
       username VARCHAR(50) NOT NULL UNIQUE,
       email VARCHAR(100) NOT NULL UNIQUE,
       password VARCHAR(255) NOT NULL,
       created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
       updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

   -- Role Table
   CREATE TABLE IF NOT EXISTS role (
       id INT AUTO_INCREMENT PRIMARY KEY,
       name ENUM('USER', 'ADMIN') NOT NULL UNIQUE
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

   -- User_Role (Many-to-Many Relationship)
   CREATE TABLE IF NOT EXISTS user_role (
       user_id INT NOT NULL,
       role_id INT NOT NULL,
       PRIMARY KEY (user_id, role_id),
       FOREIGN KEY (user_id) REFERENCES user(id) ON DELETE CASCADE,
       FOREIGN KEY (role_id) REFERENCES role(id) ON DELETE CASCADE
   ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

   -- Insert default roles
   INSERT INTO role (name) VALUES ('USER') ON DUPLICATE KEY UPDATE name=name;
   INSERT INTO role (name) VALUES ('ADMIN') ON DUPLICATE KEY UPDATE name=name;

   -- Insert default admin user
   INSERT INTO user (username, email, password)
   VALUES ('admin', 'admin@example.com', '$2a$10$pRamXDPMoS96Y/TJKiM24Ou/ZUDN0sWpvXxm66HTnVtvvMQO94bWi')
   ON DUPLICATE KEY UPDATE username=username;

   -- Assign ADMIN role to default user
   INSERT INTO user_role (user_id, role_id)
   VALUES ((SELECT id FROM user WHERE username='admin'), (SELECT id FROM role WHERE name='ADMIN'))
   ON DUPLICATE KEY UPDATE user_id=user_id;
   ```

## Ejecución

1. **Compilar y ejecutar la aplicación:**
   ```sh
   mvn clean install
   mvn spring-boot:run
   ```

2. **Abrir en el navegador:**
   - La aplicación estará disponible en `http://localhost:8080` (o el puerto configurado).

## Uso de Swagger ( programación funcional, reactiva)

- Puedes acceder a la visual de la API en:
  ```
  http://localhost:8080/swagger-ui/index.html
  ```

NOTA IMPORTANTE:
para generar el token de autentificación
usuario: admin
contraseña: 123456

¡Gracias por usar nuestra aplicación! 🚀
