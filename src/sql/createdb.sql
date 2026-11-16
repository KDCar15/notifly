-- Schema gestion

CREATE SCHEMA gestion;

CREATE TABLE gestion.tipos_nota(
    id_tiponota INT,
    nombre_tipo VARCHAR(30)
);

CREATE TABLE gestion.estados_nota(
    id_estadonota INT,
    nombre_estado VARCHAR(30)
);

CREATE TABLE gestion.clasificaciones(
    id_clasificacion INT,
    nombre_clasificacion VARCHAR(30)
);

CREATE TABLE gestion.contactos(
    id_contacto INT,
    nombres VARCHAR(60),
    apellidos VARCHAR(60),
    ocupacion VARCHAR(60),
    celular VARCHAR(30),
    correo VARCHAR(60)
);

CREATE TABLE gestion.areas(
    id_area INT,
    nombre_area VARCHAR(30),
    id_contacto INT
);

CREATE TABLE gestion.notas(
    id_nota INT
    id_estadonota INT
    id_tiponota INT
    id_clasificacion INT
    id_area INT
    nombres VARCHAR(60),
    apellidos VARCHAR(60),
    correo VARCHAR(30),
    celular VARCHAR(30),
    fechahora DATE,
    descripcion VARCHAR(MAX)
);


-- Schema Auditoria
CREATE SCHEMA auditoria;

CREATE TABLE auditoria.accion(
    id_accion INT,
    nombre_accion VARCHAR(30)
)

CREATE TABLE auditoria.seguimiento(
    id_seguimiento INT,
    id_nota INT,
    fechahora DATE
    id_accion INT, 
);


-- Schema Respuesta
CREATE SCHEMA respuestas;

CREATE TABLE respuestas.plantillas(
    id_plantilla,
    nombre_plantilla VARCHAR(90),
    contenido VARCHAR(MAX)
);