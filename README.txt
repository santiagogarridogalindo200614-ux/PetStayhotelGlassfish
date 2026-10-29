================================================================================
 PETSTAY HOTEL - COMO EJECUTAR EL PROYECTO (.zip + .sql)
================================================================================

Guia paso a paso para personas sin experiencia, usando Apache NetBeans.
Con esta guia no necesitas crear nada a mano en GlassFish: el pool de
conexiones y el recurso jndi/jdbc los crea NetBeans solo al ejecutar el
proyecto.


ARCHIVOS QUE DEBES TENER
--------------------------------------------------------------------------------
  * El .zip del proyecto adjunto
      -> El proyecto de NetBeans.

  * petstayhotel.sql
      -> La base de datos con tablas y datos de prueba.


================================================================================
 1. INSTALAR LOS PROGRAMAS (solo la primera vez)
================================================================================

Instala en este orden:

  1. JDK 8 (Java 8).
     Es importante: con Java 11 o superior, GlassFish 5 no arranca.

  2. Apache NetBeans.
     Descarga: https://netbeans.apache.org/front/main/download/

  3. GlassFish 5.1 (el servidor).
     Descarga el .zip y descomprimelo en una carpeta facil como C:\glassfish5
     (sin espacios ni tildes en la ruta).

  4. XAMPP (trae MySQL y phpMyAdmin).
     Descarga: https://www.apachefriends.org

  5. Conector de MySQL.
     En https://dev.mysql.com/downloads/connector/j/ elige
     "Platform Independent", descarga el .zip y saca el archivo
     mysql-connector-j-8.x.x.jar (el nombre exacto cambia segun la version).


================================================================================
 2. CREAR LA BASE DE DATOS
================================================================================

  1. Abre XAMPP Control Panel y presiona "Start" en MySQL (se pone en verde).

  2. Presiona "Admin" en la fila de MySQL: se abre phpMyAdmin en el navegador.

  3. Arriba haz clic en "Nueva" (o "New") y crea una base de datos:
       - Nombre: petstayhotel
       - Cotejamiento: utf8mb4_general_ci
       - Clic en "Crear".

  4. Con petstayhotel seleccionada a la izquierda, ve a la pestana "Importar".

  5. Clic en "Seleccionar archivo", elige petstayhotel.sql, baja y presiona
     "Importar".

  6. Debe aparecer un mensaje verde y, a la izquierda, las tablas:
       habitacion, mascota, pago, reserva, reserva_servicio, servicio, usuario


CREAR EL USUARIO DE LA BASE DE DATOS
--------------------------------------------------------------------------------

  1. En phpMyAdmin ve a la pestana "SQL".

  2. Pega lo siguiente y presiona "Continuar":

     CREATE USER 'petstay'@'localhost' IDENTIFIED BY 'petstay123';
     GRANT ALL PRIVILEGES ON petstayhotel.* TO 'petstay'@'localhost';
     FLUSH PRIVILEGES;


================================================================================
 3. COPIAR EL CONECTOR DE MYSQL A GLASSFISH
================================================================================

  1. Copia el archivo mysql-connector-j-8.x.x.jar.

  2. Pegalo en esta carpeta (ajusta la ruta a donde descomprimiste GlassFish):

     C:\glassfish5\glassfish\domains\domain1\lib

  NOTA: Si GlassFish ya estaba iniciado, detenlo y vuelvelo a iniciar despues
  de copiar el archivo.


================================================================================
 4. REGISTRAR GLASSFISH EN NETBEANS (solo la primera vez)
================================================================================

  1. Abre Apache NetBeans.

  2. Ve a la pestana "Services"
     (si no la ves: menu Window -> Services).

  3. Clic derecho en "Servers" -> "Add Server..."

  4. Elige "GlassFish Server" y dale "Next".

  5. En "Installation Location" presiona "Browse" y selecciona la carpeta de
     GlassFish (por ejemplo C:\glassfish5\glassfish). Luego "Next".

  6. Deja el dominio domain1, el puerto de administracion 4848 y el de HTTP
     8080. Clic en "Finish".

  NOTA: Si NetBeans pide elegir una version de Java, selecciona JDK 8.


================================================================================
 5. ABRIR EL PROYECTO
================================================================================

  1. Descomprime el .zip del proyecto en una carpeta facil (sin espacios ni
     tildes si es posible), por ejemplo C:\proyectos\

  2. En NetBeans ve a File -> Open Project...

  3. Entra a la carpeta descomprimida y selecciona la carpeta
     petStayGestionUsuarios (la que tiene el icono de proyecto de NetBeans).
     Presiona "Open Project".

  4. Si aparece un aviso de "Resolve Server Problem" (o "Resolve Reference
     Problems"), selecciona "GlassFish Server" y acepta.

  Verifica que dentro del proyecto exista la carpeta "setup" con el archivo
  glassfish-resources.xml. Ese archivo crea la conexion a la base de datos
  automaticamente.


================================================================================
 6. EJECUTAR
================================================================================

  1. Asegurate de que MySQL este iniciado en XAMPP (paso 2).
     Apache de XAMPP no hace falta.

  2. En NetBeans, clic derecho sobre el proyecto -> "Clean and Build"
     (espera a que diga BUILD SUCCESSFUL).

  3. Clic derecho sobre el proyecto -> "Run".

  4. NetBeans inicia GlassFish, crea el pool y el recurso, instala la
     aplicacion y abre el navegador. Si no se abre solo, entra a:

     http://localhost:8080/petStayGestionUsuarios/

  Veras la pagina de inicio de PetStay Hotel con el menu a la izquierda.


================================================================================
 7. PRUEBA RAPIDA
================================================================================

  1. Entra a "Reservar Estadia".

  2. Elige cliente, mascota, habitacion y fechas en formato dd/mm/aaaa.

  3. Guarda. Luego intenta otra reserva en la misma habitacion con fechas que
     se crucen: debe aparecer un mensaje de que no hay disponibilidad.


================================================================================
 PROBLEMAS COMUNES
================================================================================

PROBLEMA: El proyecto no compila o marca errores rojos
  SOLUCION: Revisa el aviso de servidor (paso 5.4): el proyecto debe tener
  asignado GlassFish Server.

PROBLEMA: GlassFish no arranca
  SOLUCION: Usa JDK 8. Con Java 11 o superior GlassFish 5 falla.

PROBLEMA: Error "Communications link failure"
  SOLUCION: MySQL esta apagado. Inicia MySQL en XAMPP y ejecuta de nuevo.

PROBLEMA: Error "Access denied for user"
  SOLUCION: El usuario petstay no existe o tiene otra contrasena. Vuelve a
  ejecutar el SQL del usuario (paso 2).

PROBLEMA: Error "jndi/jdbc not found"
  SOLUCION: No se creo el recurso. Verifica que setup/glassfish-resources.xml
  este dentro de la carpeta del proyecto, haz "Clean and Build" y "Run" de
  nuevo.

PROBLEMA: Error de driver / ClassNotFoundException de MySQL
  SOLUCION: Falta el .jar del conector en glassfish/domains/domain1/lib
  (paso 3). Reinicia GlassFish.

PROBLEMA: Puerto 8080 ocupado
  SOLUCION: Cierra otros programas que usen ese puerto (Tomcat, Skype, otro
  GlassFish).

PROBLEMA: Pagina en blanco o error 404
  SOLUCION: Revisa que la direccion sea exactamente
  http://localhost:8080/petStayGestionUsuarios/ (respeta mayusculas).

PROBLEMA: Las fechas quedan un dia antes
  SOLUCION: Avisa al equipo para revisar la zona horaria.


================================================================================
 DATOS DE CONEXION (RESUMEN)
================================================================================

  Base de datos : petstayhotel
  URL           : jdbc:mysql://localhost:3306/petstayhotel
  Usuario       : petstay
  Contrasena    : petstay123
  Recurso JNDI  : jndi/jdbc (pool petstayPool)
  Direccion app : http://localhost:8080/petStayGestionUsuarios/

================================================================================