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

-- Notas de muestra
INSERT INTO gestion.notas(id_tiponota, id_clasificacion, nombresapellidos, correo, celular, descripcion)
VALUES
(
1
,1
,'Charly Daniela Aguirre Molina'
,'caaguirre@uamv.edu.ni'
,'85332842'
,'Los pupitres de las aulas del primer piso del B en general estan en mal estado.'
);

-- Notas de muestra
INSERT INTO gestion.notas(id_tiponota, id_clasificacion, nombresapellidos, correo, celular, descripcion)
VALUES
(
2
,1
,'Charly Daniela Aguirre Molina'
,'caaguirre@uamv.edu.ni'
,'85332842'
,'Deberian darles horas laborales a los estudiantes por medio de limpiar los pisos de aulas y establecimientos.'
);

SELECT * FROM gestion.notas;
SELECT * FROM gestion.areas;
SELECT * FROM gestion.tipos_nota;
SELECT * FROM gestion.estados_nota;
SELECT * FROM gestion.clasificaciones;
SELECT * FROM auditoria.accion;