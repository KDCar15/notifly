-- Seleccionar facultades
SELECT * FROM gestion.areas WHERE es_facultad = true;
-- Seleccionar areas MENOS facultades
SELECT * FROM gestion.areas WHERE es_facultad = false;


-- Seleccionar quejas
SELECT * FROM gestion.notas WHERE tipo_nota = 1;
-- Seleccionar sugerencias
SELECT * FROM gestion.notas WHERE tipo_nota = 2;