INSERT INTO student (student_id, name, identification, identification_type) VALUES (1, 'María González', 1001234567, 'CC'), (2, 'Carlos Rodríguez', 1007654321, 'TI'), (3, 'Laura Martínez', 1001122334, 'CC'), (4, 'Jesus Parra', 1085903234, 'CC');

INSERT INTO subject (subject_id, credits, objectives, name, created_at, updated_at) VALUES (1, 4, '', 'Redes', '2025-01-10', '2025-01-10'), (2, 3, '', 'Base de datos 2', '2025-01-10', '2025-01-10'), (3, 3, '', 'Proyecto 1', '2025-01-10', '2025-01-10'), (4, 3, '', 'Calidad de software', '2025-01-10', '2025-01-10');

INSERT INTO teacher (teacher_id, name, identification, degree, identification_type, status, teacher_type) VALUES (1, 'Johanna Andrea', 2009876543, 'MSc', 'CC', 'ACTIVO', 'CATEDRA'), (2, 'Jimena Timaná', 2008765432, 'PhD', 'CC', 'ACTIVO', 'FIJO'), (3, 'Libardo Pantoja', 2007654321, 'MSc', 'CC', 'ACTIVO', 'CATEDRA');

INSERT INTO learning_results (ra_id, name) VALUES (101, 'Gestión de Proyectos TI'), (102, 'RA 102'), (103, 'RA 103'), (104, 'RA 104');

INSERT INTO course (course_id, subject_id, ra_id) VALUES (10, 1, 101), (20, 2, 102), (30, 3, 103), (40, 4, 104);

INSERT INTO enroll (enroll_id, course_id, student_id, semester) VALUES (1000, 10, 1, '2025-1'), (1001, 20, 1, '2024-1'), (1002, 30, 1, '2025-1'), (1003, 40, 2, '2025-1'), (1005, 30,2,'2025-1'), (1006, 40, 3, '2025-1'), (1007, 40, 4, '2025-1');

INSERT INTO rubrica (id_rubrica, nombre_rubrica, objetivo_estudio, nota_rubrica, subject_id, ra_id, estado) VALUES (100, 'Rúbrica Redes', 'Diseñar topologías de red', 5, 1, 101, 'ACTIVO'), (200, 'Rúbrica BD2', 'Modelado de bases de datos', 5, 2, 102, 'ACTIVO'), (300, 'Rúbrica Proyecto', 'Gestión de proyectos TI', 5, 3, 103, 'ACTIVO'), (400, 'Rúbrica Calidad', 'Pruebas de software', 5, 4, 104, 'ACTIVO');

INSERT INTO criterio (id_criterio, id_rubrica, crf_descripcion, crf_porcentaje, crf_nota, crf_comentario) VALUES (12, 400, 'Sabe integrar', 0.3, 1.2, 'F'), (1, 400, 'Calidad del código', 0.3, 4.2, "Mejorable"), (2, 400, 'Documentación técnica', 0.2, 3.0, "Regular mijo"), (3, 400, 'Cumplimiento de requisitos', 0.5, NULL, NULL), (9, 300, 'Planificación y gestión del proyecto', 0.3, NULL, NULL), (10, 300, 'Calidad del código y estructura del software', 0.4, NULL, NULL), (11, 300, 'Trabajo en equipo y comunicación', 0.3, NULL, NULL);

INSERT INTO nivel (id_nivel, nivel_descripcion, rango_nota, id_criterio) VALUES (1, 'Desempeño básico', '2.5', 1), (2, 'Desempeño intermedio', '4.0', 1), (3, 'Desempeño avanzado', '5.0', 1), (19, 'No hay planificación clara ni control del cronograma.', '2.5', 9), (20, 'Planificación parcial con algunos retrasos.', '4.0', 9), (21, 'Planificación detallada y cumplimiento de plazos.', '5.0', 9), (22, 'Código desorganizado con errores frecuentes.', '2.5', 10), (23, 'Código funcional pero con algunas malas prácticas.', '4.0', 10), (24, 'Código limpio, modular y bien documentado.', '5.0', 10), (25, 'Poca comunicación y colaboración entre los miembros.', '2.5', 11), (26, 'Buena comunicación, pero con dificultades ocasionales.', '4.0', 11), (27, 'Trabajo en equipo eficiente con comunicación fluida.', '5.0', 11);

INSERT INTO course_teacher (course_id, teacher_id) VALUES (10, 1), (20, 2), (30, 3), (40, 1);

INSERT INTO evaluation (evaluation_id, enroll_id, id_rubrica, evaluation_status, description, created_at, updated_at,score) VALUES (500, 1000, 100, 'EVALUADO', 'Evaluación parcial Redes', '2025-03-01', '2025-03-01',NULL), (501, 1001, 200, 'EVALUADO', 'Evaluación final BD2', '2025-03-15', '2025-03-20',NULL), (502, 1003, 400, 'EVALUADO', 'Avance proyecto Calidad', '2025-04-01', '2025-04-01',3.5),  (503, 1006, 400, 'EVALUADO', 'Evaluación parcial Redes', '2025-03-10', '2025-03-10', 4.7), (504, 1007, 400, 'EVALUADO', 'Evaluación parcial Redes', '2025-03-10', '2025-03-10', 1.2);

INSERT INTO calification_register (calification_id, calification, message, level, evaluation_id) VALUES (10000, 4.2, 'Buen trabajo en la práctica', 'Nivel 2', 502), (10001, 3.8, 'Debe mejorar documentación', 'Nivel 1', 502), (10002, 4.8, 'Excelente implementación', 'Nivel 3', 502);