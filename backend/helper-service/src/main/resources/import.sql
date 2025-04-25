INSERT INTO student (student_id, name, identification, identification_type) VALUES (1, 'María González', 1001234567, 'CC'), (2, 'Carlos Rodríguez', 1007654321, 'TI');

INSERT INTO subject (subject_id, credits, objectives, name, created_at, updated_at) VALUES (1, 4, '', 'Redes', '2025-01-10', '2025-01-10'), (2, 3, '', 'Base de datos 2', '2025-01-10', '2025-01-10'), (3, 3, '', 'Proyecto 1', '2025-01-10', '2025-01-10'), (4, 3, '', 'Calidad de software', '2025-01-10', '2025-01-10');

INSERT INTO teacher (teacher_id, name, identification, degree, identification_type, status, teacher_type) VALUES (1, 'Johanna Andrea', 2009876543, 'MSc', 'CC', 'ACTIVO', 'CATEDRA'), (2, 'Jimena Timaná', 2008765432, 'PhD', 'CC', 'ACTIVO', 'FIJO'), (3, 'Libardo Pantoja', 2007654321, 'MSc', 'CC', 'ACTIVO', 'CATEDRA');

INSERT INTO learning_results (ra_id) VALUES (101), (102), (103), (104);

INSERT INTO course (course_id, subject_id, ra_id) VALUES (10, 1, 101), (20, 2, 102), (30, 3, 103), (40, 4, 104);

INSERT INTO course_teacher (course_id, teacher_id) VALUES (10, 1), (20, 2), (30, 3), (40, 1);