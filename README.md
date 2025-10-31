# Gestor de Tareas (Task Manager)

**Desarrollado por:** Valeria Guillén valeria.mgl@outlook.com  
**Taller de Java Hitss TV**

## Descripción del Proyecto

Aplicación CRUD para gestionar tareas personales, desarrollada con Spring Boot para aprender los fundamentos de desarrollo web con Java. El proyecto implementa controladores, servicios, repositorios, entidades y uso de base de datos MySQL, siguiendo lo que se vio en el taller.

## Objetivo del Proyecto

Crear una aplicación CRUD para gestionar tareas personales, aprendiendo los fundamentos de Spring Boot, como controladores, servicios, repositorios, entidades, y uso de base de datos.

## Funcionalidades Básicas

1. Crear una tarea
2. Listar todas las tareas
3. Actualizar una tarea
4. Eliminar una tarea

## Entidades Principales

- **Task**: Representa una tarea con sus atributos y métodos
- **User**: Representa un usuario del sistema
- **Role**: Representa el rol de un usuario (USER, ADMIN)

## Endpoints REST (TaskController)

| MÉTODO | RUTA | DESCRIPCIÓN |
| :--- | :--- | :--- |
| GET | /tasks | Listar todas las tareas |
| GET | /tasks/{id} | Obtener una tarea específica |
| POST | /tasks | Crear una nueva tarea |
| PUT | /tasks/{id} | Actualizar una tarea |
| DELETE | /tasks/{id} | Eliminar una tarea |

## Características del Proyecto

- Uso de anotaciones: `@RestController`, `@Service`, `@Repository`, `@Entity`, etc.
- Validaciones implementadas
- CRUD con Spring Data JPA
- Base de datos MySQL
- Manejo de errores básicos
- Spring Security con JWT (gestión de usuarios y roles)
- Dos tipos de usuarios (USER, ADMIN):
  - Usuario USER: puede crear tareas y visualizarlas
  - Usuario ADMIN: puede realizar todas las operaciones CRUD
- API documentada con Swagger
- Pruebas con Postman (opcional)

## Configuración del Proyecto

1. Clonar el repositorio
2. Configurar la base de datos MySQL en `application.properties`
3. Ejecutar el proyecto con Maven: `./mvn spring-boot:run`
4. Acceder a la aplicación en `http://localhost:8080`
5. La documentación de la API está disponible en `http://localhost:8080/swagger-ui.html`

## Documentación Swagger

A continuación se muestran capturas de pantalla de la interfaz de Swagger UI donde se documentan los endpoints de la API:

![Imagen 0 Swagger](./imagenes/Documentacion_Swagger/Swagger UI-imágenes-0.jpg)
*Imagen inicial de Swagger UI mostrando la interfaz de documentación de la API*

![Imagen 1 Swagger](./imagenes/Documentacion_Swagger/Swagger UI-imágenes-1.jpg)
*Endpoint GET para listar todas las tareas*

![Imagen 2 Swagger](./imagenes/Documentacion_Swagger/Swagger UI-imágenes-2.jpg)
*Endpoint GET para obtener una tarea específica por ID*

![Imagen 3 Swagger](./imagenes/Documentacion_Swagger/Swagger UI-imágenes-3.jpg)
*Endpoint POST para crear una nueva tarea*

![Imagen 4 Swagger](./imagenes/Documentacion_Swagger/Swagger UI-imágenes-4.jpg)
*Endpoint PUT para actualizar una tarea existente*

![Imagen 5 Swagger](./imagenes/Documentacion_Swagger/Swagger UI-imágenes-5.jpg)
*Endpoint DELETE para eliminar una tarea*

![Imagen 6 Swagger](./imagenes/Documentacion_Swagger/Swagger UI-imágenes-6.jpg)
*Detalles del modelo de datos Task*

![Imagen 7 Swagger](./imagenes/Documentacion_Swagger/Swagger UI-imágenes-7.jpg)
*Autenticación y seguridad en Swagger*

![Imagen 8 Swagger](./imagenes/Documentacion_Swagger/Swagger UI-imágenes-8.jpg)
*Ejemplo de parámetros para las operaciones*

![Imagen 9 Swagger](./imagenes/Documentacion_Swagger/Swagger UI-imágenes-9.jpg)
*Modelos de datos utilizados en la API*

![Imagen 10 Swagger](./imagenes/Documentacion_Swagger/Swagger UI-imágenes-10.jpg)
*Opciones de autorización en los endpoints*

![Imagen 11 Swagger](./imagenes/Documentacion_Swagger/Swagger UI-imágenes-11.jpg)
*Ejemplos de respuestas de la API*

![Imagen 12 Swagger](./imagenes/Documentacion_Swagger/Swagger UI-imágenes-12.jpg)
*Configuración adicional de la API*

## Pruebas con Postman

A continuación se muestran capturas de pantalla de las pruebas realizadas con Postman para validar el funcionamiento de la API:

![Inicio de sesión exitoso](./imagenes/Pruebas_Postman/Inicio de sesion Login Exitoso.png)
*Ejemplo de inicio de sesión (login) exitoso en la aplicación*

![Inicio de sesión fallido](./imagenes/Pruebas_Postman/Inicio de sesion  Login Error.png)
*Ejemplo de inicio de sesión (login) fallido con mensaje de error*

![Usuario registrado exitosamente](./imagenes/Pruebas_Postman/Registro signup  Usuario registrado exitosamente.png)
*Ejemplo de registro de usuario exitoso*

![Error de registro duplicado](./imagenes/Pruebas_Postman/Registro signup Error  El nombre de usuario ya está en uso.png)
*Ejemplo de error de registro cuando el nombre de usuario ya está en uso*

![Listar tareas](./imagenes/Pruebas_Postman/Listar tareas.png)
*Ejemplo de obtención exitosa de la lista de tareas*

![Crear nueva tarea](./imagenes/Pruebas_Postman/Nueva tarea.png)
*Ejemplo de creación exitosa de una nueva tarea*

![Consultar tarea exitosamente](./imagenes/Pruebas_Postman/Consultar tarea correctamente.png)
*Ejemplo de consulta exitosa de una tarea específica*

![Error al consultar tarea](./imagenes/Pruebas_Postman/consultar tarea Error Forbidden.png)
*Ejemplo de error al consultar una tarea sin los permisos adecuados*

![Actualizar tarea exitosamente](./imagenes/Pruebas_Postman/Actualizar tarea exitosamente.png)
*Ejemplo de actualización exitosa de una tarea*

![Error al actualizar tarea](./imagenes/Pruebas_Postman/Actualizar tarea Error.png)
*Ejemplo de error al actualizar una tarea*

![Eliminar tarea exitosamente](./imagenes/Pruebas_Postman/Borrar tarea exitosamente.png)
*Ejemplo de eliminación exitosa de una tarea*

![Error al eliminar tarea](./imagenes/Pruebas_Postman/Borrar tarea Error .png)
*Ejemplo de error al eliminar una tarea*
