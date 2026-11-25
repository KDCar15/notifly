-- Seleccionar facultades
SELECT * FROM gestion.areas WHERE es_facultad = true;
-- Seleccionar areas MENOS facultades
SELECT * FROM gestion.areas WHERE es_facultad = false;


-- Seleccionar quejas
SELECT  en.nombre_estado AS "Estado"
        ,tn.nombre_tipo AS "Tipo"
        ,c.nombre_clasificacion AS "Clasificacion"
        ,a.nombre_area AS "Area"
        ,n.nombresapellidos AS "Nombres y apellidos"
        ,n.correo AS "Correo"
        ,n.celular AS "Telefono"
        ,n.fechahora AS "Fecha y hora"
        ,n.descripcion AS "Descripcion"
        ,n.completado
FROM gestion.notas n
INNER JOIN gestion.estados_nota en
    ON en.id_estadonota = n.id_estadonota
INNER JOIN gestion.tipos_nota tn
    ON tn.id_tiponota = n.id_tiponota
INNER JOIN gestion.clasificaciones c
    ON c.id_clasificacion = n.id_clasificacion
LEFT JOIN gestion.areas a
    ON a.id_area = n.id_area
WHERE tn.id_tiponota = 1;

-- Seleccionar quejas
SELECT  en.nombre_estado AS "Estado"
        ,tn.nombre_tipo AS "Tipo"
        ,c.nombre_clasificacion AS "Clasificacion"
        ,a.nombre_area AS "Area"
        ,n.nombresapellidos AS "Nombres y apellidos"
        ,n.correo AS "Correo"
        ,n.celular AS "Telefono"
        ,n.fechahora AS "Fecha y hora"
        ,n.descripcion AS "Descripcion"
FROM gestion.notas n
INNER JOIN gestion.estados_nota en
    ON en.id_estadonota = n.id_estadonota
INNER JOIN gestion.tipos_nota tn
    ON tn.id_tiponota = n.id_tiponota
INNER JOIN gestion.clasificaciones c
    ON c.id_clasificacion = n.id_clasificacion
LEFT JOIN gestion.areas a
    ON a.id_area = n.id_area
WHERE tn.id_tiponota = 2;

-- Seleccionar sugerencias
SELECT * FROM gestion.notas WHERE id_tiponota = 1;
-- Seleccionar sugerencias
SELECT * FROM gestion.notas WHERE id_tiponota = 2;

SELECT * FROM gestion.contactos;
SELECT * FROM gestion.checklists;