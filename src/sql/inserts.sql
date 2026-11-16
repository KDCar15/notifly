SELECT * FROM gestion.tipos_nota;
SELECT * FROM gestion.clasificaciones;
SELECT * FROM gestion.estados_nota;

INSERT INTO gestion.tipos_nota (nombre_tipo) VALUES
('Queja'), ('Sugerencia');

INSERT INTO gestion.estados_nota (nombre_estado) VALUES
('Pendiente'), ('En revision'), ('Completado');

INSERT INTO gestion.clasificaciones (nombre_clasificacion) VALUES
('Limpieza y mantenimiento');