# ProyectoMiarmaVicenteRufo

***MiarmaApp** la API es una red social de gente de Sevilla. Esta red social está basada en imágenes o vídeos, por lo que cada publicación que se realiza debe incluir siempre al menos una imagen o vídeo.*
</br>
> ## ✒️ **Autor ✒️**
* #### Vicente Rufo Bru
</br>


### 📋 Las entidades que forman nuestra aplicación 📋
* #### UserEntity 🧍:

* #### Publicacion 📮:
* #### Peticion ↗️:

* #### EstadoPeticion ✔️:



</br>



## :wrench: ¿Qué hacer para arrancar el proyecto? :wrench:
* ### **Descargar IntelliJ IDEA Community Edition 2021.2.2**
* ### **Descargar JAVA JDK 17**
* ### **Abrir IntelliJ**
* ### **Configurar la version JDK**
1. Clicar a File.
2. Luego a Proyect Structure.
3. Seleccionar en Proyect SDK la version 17 descargada previamente

* ### **Arrancar el proyecto**
1. Clicar al boton Maven.
2. Luego a Pluggins.
3. Luego a spring-boot
4. Para finalizar clicar a spring-boot-run

## 🛠️ ¿Qué puede hacer MiarmaApp? 🛠️


* ### **Las funcionalidades que tiene la entidad Registro:**

1. Un usuario cualquiera podrá registrarse para comenzar a usar nuestra red social. Al hacerlo, los datos (como mínimo) que debería aportar son su nick, email, fecha de nacimiento y un fichero con su foto o avatar. Los usuarios pueden elegir si su perfil de usuario es público o privado. Si es privado, solamente lo podrán visualizar sus seguidores.
2. El usuario podrá loguearse, obteniendo un token para poder acceder al resto de endpoints.
3. Obtener los datos del usuario logueado, incluyendo la URL del avatar.


* ### **Las funcionalidades que tiene la entidad Publicaciones:**

1. Crear una nueva publicación. Una publicación tendrá al menos un título, un texto, y un fichero adjunto (imagen o vídeo). También habrá que indicar si nuestra publicación es pública o solamente accesible para nuestros seguidores.
2. Modificar una publicación. Se podrán cambiar tanto la imagen o vídeo (eliminando por tanto la anterior), como los campos textuales
3. Eliminar una publicación, eliminando a su vez el fichero adjunto.
4. Obtener todas las publicaciones públicas de todos los usuarios.
5. Obtener una publicación por su ID. Solamente la podremos ver si está marcada como pública o si nuestro usuario sigue al autor de la publicación.
6. Obtener todas las publicaciones de un usuario. Si no seguimos al usuario, solamente podremos ver las publicaciones guardadas como públicas. Si seguimos al usuario, podremos ver todas las publicaciones
7. Obtener todas las publicaciones del usuario logueado

* ### **Las funcionalidades que tiene la entidad Usuarios:**

1. Visualizar el perfil de un usuario.
  -Si seguimos al usuario, o tiene su perfil como público, se obtendrá su información.
  -Si no seguimos al usuario y su perfil es privado, obtendremos un mensaje de error.
2. Editar mi perfil de usuario. Se puede cambiar cualquier información, incluida la foto de avatar.
3. El usuario actual realiza una petición de seguimiento del usuario con nick. Es solamente una petición. El usuario nick lo tiene que aceptar.
4. Las peticiones de seguimiento se guardan en base de datos con un ID. Para confirmar una petición de seguimiento, el usuario debe aceptarla con este endpoint. Esto eliminará la petición de seguimiento, y habilitará el seguimiento en si.
5. Eliminar sin aceptar una petición de seguimiento.
6. Listar todas las peticiones de seguimiento que hay actualmente



* ### **El uso de la coleccion de Postman:**

1.Importa la colección de Postman añadida en el proyecto en tu propio programa de Postman
2. En el mismo proyecto tienes dos carpetas una con los ficheros de json que usaremos y otra de fotos para usar.
3. Empezaremos creando y logueando usuarios. 
4. Para registrar usuarios iremos a la peticion de Register en postman y en el Body usaremos el form-data, en las key tendremos file cuyo valor se lo daremos escogiendo el archivo en la carpeta de FotoPrueba y en el user la peticion en json de register de la carpeta JSON
5. A la hora de loguarnos escogeremos el email y la contraseña del usuario registrado
6. A la hora de obtener datos lo obtendremos del usuario logueado
7. Empezaremos a crear publicaciones
8. Para crear publicaciones iremos a la peticion de Crear publicacion en postman y en el Body usaremos el form-data, en las key tendremos file cuyo valor se lo daremos escogiendo el archivo en la carpeta de FotoPrueba y en el newPublicacion la peticion en json de publi de la carpeta JSON
9. Luuego podemos editar o borrarla(según lo que queramos) añadiendo en la ruta el id de la publicacion que hayamos obtenido
10. Tienes la opcion de buscar todas las publicaciones creadas repartidas en diferentes peticiones, puedes buscarla por la visibilidad de la publicación, por el nick del usuario que la ha creado o por el mismo id de la propia publicación.
11. Para crear peticiones del usuario iremos a la peticion de Hacer follow en postman y en el Body usaremos el form-data, en el key usaremos peticion y el value usaremos el peticion.json de la carpeta JSON, no olvides en la ruta añadir el nick de la persona a quien seguir.
12. Luego dirigite de nuevo al login para loguarte con el usuario que reibe la peticion, para asi poder aceptar o declinar dicha peticion
13. Tienes la opcion de modificar datos del usuario en la peticion de "Actualizar usuario" por si quieres cambiar algo.
14. En la orden de "Listar peticiones" puedes ver la lista de peticiones que se han realizado.
15. Si vas al perfil del usuario(recuerda añadir en la ruta el id del usuario) donde podras ver todos los datos del usuario incluidos los seguidores que tiene y las peticiones pendientes.


* ### **Extras:**
* Este proyecto tiene añadida seguridad de uusuario y uso de tokens, a la vez que añade algunas excepciones de usuarios y publicaciones
* Cualquier parecido o similitud con la famosa aplicación Instagram es pura casualidad o producto de tu imaginación  

