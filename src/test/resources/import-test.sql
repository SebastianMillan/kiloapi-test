
INSERT INTO clase (id, nombre, tutor) VALUES
(1, 'Clase1', 'Tutor1'),
(2, 'Clase2', 'Tutor2'),
(3, 'Clase3', 'Tutor3');

INSERT INTO tipo_alimento (id, nombre) VALUES
(1, 'TipoAlimento1'),
(2, 'TipoAlimento2'),
(3, 'TipoAlimento3');

INSERT INTO aportacion (id, fecha, clase_id) VALUES
(1, '2024-01-19', 1),
(2, '2024-01-20', 2),
(3, '2024-01-21', 3);

INSERT INTO detalle_aportacion (aportacion_id, cantidad_kg, tipo_alimento,linea_id) VALUES
(1, 10.5, 1,1),
(1, 8.2, 2,2),
(2, 5.0, 1,2);

INSERT INTO kilos_disponibles (tipo_alimento_id, kilos_disponibles) VALUES
(1, 100.0),
(2, 50.0),
(3, 75.0);
