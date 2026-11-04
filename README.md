# CleanExpress - Sistema de Gestión para Lavandería

## 1. Propósito y Descripción
CleanExpress es un sistema de información enfocado en la gestión operativa y de mostrador de una lavandería. Facilita el registro centralizado de clientes, la recepción y seguimiento de pedidos, la categorización de prendas, el control de pagos y el envío de notificaciones de estado.

## 2. Stack Tecnológico y Versiones
* **Lenguaje:** Java 21 LTS
* **Framework Backend:** Spring Boot 3.x
* **Acceso a Datos:** Spring Data JPA / Hibernate (ORM)
* **Base de Datos:** PostgreSQL 15+
* **Herramienta de Construcción:** Maven
* **Librerías Auxiliares:** Lombok
* **IDE:** IntelliJ IDEA Community Edition

## 3. Estado del Entregable (E1)
* Mapeo ORM completo de las 5 entidades del sistema (Cliente, Pedido, Prenda, Pago, BitacoraNotificacion).
* Conexión y persistencia verificada con PostgreSQL.
* Prueba de integración automatizada mediante `CommandLineRunner` en la clase principal.

## 4. Requisitos Previos e Instalación
1. Tener instalado **JDK 21** y **PostgreSQL**.
2. Clonar el repositorio:
   git clone https://github.com/jijuaquin/Lavanderia_CleanExpress
