# CleanExpress - Sistema de Gestión para Lavandería
## 1. Propósito y Descripción
CleanExpress es un sistema de información enfocado en la gestión operativa y de mostrador para una lavandería. Facilita el registro centralizado de clientes, la recepción y seguimiento de pedidos, la categorización de prendas, el control de pagos y el envío de notificaciones de estado.

## 2. Stack Tecnológico y Versiones
    • Lenguaje: Java 21 LTS
    • Framework Backend: Spring Boot 3.x
    • Acceso a Datos: Spring Data JPA / Hibernate
    • Base de Datos: PostgreSQL 15+
    • Herramienta de Construcción: Maven
    • Librerías Auxiliares: Lombok
    • IDE: IntelliJ IDEA Community Edition
    
## 3. Estado del Entregable (E1)
* Mapeo ORM completo de las 5 entidades del sistema (Cliente, Pedido, Prenda, Pago, BitacoraNotificacion).
* Conexión y persistencia verificada con PostgreSQL.
* Prueba de integración automatizada mediante CommandLineRunner en la clase principal.

## 4. Para ejecutar este proyecto se requiere contar con JDK 21, PostgreSQL y Git. 
A. Instalación en Windows
    1. Java Development Kit (JDK 21):
        ◦ Descargar el instalador .msi desde el sitio oficial de Eclipse Temurin / Oracle: https://adoptium.net/temurin/releases/?version=21
        ◦ Ejecutar el instalador y asegurarse de marcar la casilla “Set JAVA_HOME variable”.
    2. PostgreSQL:
        ◦ Descargar el instalador ejecutable desde EDB / PostgreSQL ficial: https://www.postgresql.org/download/windows/
        ◦ Durante la instalación, asignar la contraseña del usuario postgres (ejemplo: 1234 o la que prefiera) y mantener el puerto por defecto 5432.
    3. Git:
        ◦ Descargar el instalador desde el sitio oficial: https://git-scm.com/install/windows

B. Instalación en Linux (Ubuntu / Debian)
Abrir una terminal (Ctrl + Alt + T) y ejecutar los siguientes comandos paso a paso:
    1. Actualizar los repositorios del sistema:
	sudo apt update && sudo apt upgrade -y
    2. Instalar JDK 21:
	sudo apt install openjdk-21-jdk -y
	Verificar la instalación con el comando java -version
    3. Instalar y activar PostgreSQL:
	sudo apt install postgresql postgresql-contrib -y
	sudo systemctl start postgresql
	sudo systemctl enable postgresql
    4. Instalar Git y Maven:
	sudo apt install git maven -y
	
##5. Clonación del Repositorio
Abra su terminal o consola de comandos y ejecute:
	git clone https://github.com/jijuaquin/Lavanderia_CleanExpress.git
	cd Lavanderia_CleanExpress

##6. Configuración de la Base de Datos PostgreSQL
Paso 6.1: Crear la Base de Datos
Acceda a la consola interactiva de PostgreSQL (psql):
    • En Linux (Terminal):
	sudo -u postgres psql
    • En Windows (CMD / PowerShell):
	psql -U postgres


Dentro de la consola SQL, ejecute el comando de creación de la base de datos: 
	CREATE DATABASE clean_express;
	\c clean_express
	
Paso 6.2: Estructura de Tablas y Esquema Relacional (DDL)
Ejecute el script de creación de tablas contenido en /sql/schema_clean_express.sql

Paso 6.3: Configurar Credenciales en la Aplicación
Abre el proyecto y dirígete al archivo ubicado en clean-express/src/main/resources/application.properties. Asegúrate de que las credenciales coincidan con las de tu servidor PostgreSQL local:
# Configuración de Conexión a la Base de Datos PostgreSQL
spring.datasource.url=jdbc:postgresql://localhost:5432/clean_express
spring.datasource.username=postgres
spring.datasource.password=1234

# Configuración de JPA / Hibernate
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect

##7. Ejecución y Demostración Técnica
Opción 1: Ejecución desde IntelliJ IDEA
    1. Iniciar IntelliJ IDEA y seleccionar Open para abrir la carpeta del proyecto (clean-express).
    2. Esperar a que Maven descargue e indexe las dependencias definidas en el pom.xml.
    3. Localizar el archivo principal en la ruta:
	src/main/java/com/cleanexpress/clean_express/CleanExpressApplication.java
    4. Hacer clic derecho sobre CleanExpressApplication.java y seleccionar Run 'CleanExpressApplication' 

##8. Verificación de Datos Ficticios de Demostración
Al arrancar el sistema, el componente CommandLineRunner configurado dentro de CleanExpressApplication.java ejecuta automáticamente una rutina de persistencia completa de prueba:
    1. Registra un cliente base ("Joaquin Franco").
    2. Asigna un pedido en estado "Recibido".
    3. Registra el detalle de la prenda asociada.
    4. Genera el recibo de pago correspondiente.
    5. Emite la entrada inicial en la bitácora de notificaciones.
Para verificar que los datos se guardaron correctamente en la base de datos PostgreSQL, puede realizar la consulta SQL desde su consola psql o cliente gráfico:
	SELECT * FROM cliente;
	SELECT * FROM pedido;
	SELECT * FROM prenda;
	SELECT * FROM pago;
	SELECT * FROM bitacora_notificacion;
