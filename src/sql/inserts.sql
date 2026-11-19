-- Esquema gestion

-- Areas
INSERT INTO gestion.areas(nombre_area) VALUES
('Limpieza')
,('Cartera y cobro')
,('Vida estudiantil');

-- Facultades
INSERT INTO gestion.areas(nombre_area, es_facultad) VALUES
('Ingenieria y arquitectura', true)
,('Derecho', true)
,('Marketing', true)
,('Medicina', true);

-- Clasificaciones
INSERT INTO gestion.clasificaciones(nombre_clasificacion) VALUES
('Limpieza')
,('Aulas')
,('Servicios');

-- Clases atributivas
INSERT INTO gestion.tipos_nota (nombre_tipo) VALUES
('Queja'), ('Sugerencia');

INSERT INTO gestion.estados_nota (nombre_estado) VALUES
('Pendiente'), ('En revisión'), ('Completado');

INSERT INTO gestion.clasificaciones (nombre_clasificacion) VALUES
('Limpieza y mantenimiento');



-- Auditoria
INSERT INTO auditoria.accion (nombre_accion) VALUES
('Crear'), ('Editar'), ('Eliminar');

SELECT * FROM gestion.areas;
SELECT * FROM gestion.tipos_nota;
SELECT * FROM gestion.estados_nota;
SELECT * FROM gestion.clasificaciones;
SELECT * FROM auditoria.accion;