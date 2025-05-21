//import { use } from "react";
import { getSubject } from "../services/subjectList.tsx";
import React , { useEffect, useState } from "react";
import Layout   from "../components/layout/layout.tsx";
//import Sidebar from "../components/layout/sidebar.tsx";
//import Header from "../components/layout/header.tsx";
import IconButton from "../components/buttons/iconButton.tsx";
import CursosList from "../components/layout/CursosList.tsx";
//import Footer from "../components/layout/Footer .tsx";
import {useNavigate, useLocation} from "react-router-dom";

const SubjectList : React.FC = ({  }) => {


    const navigate = useNavigate();
    const location = useLocation();

    const periodoSeleccionado = location.state?.periodoSeleccionado;
    const idStudent = 1; // Cambia esto por el id real del estudiante

    type Curso = {
       // id: number
        nombre: string;
        docente: string;
        id: number
    };


    const [subjects, setSubjects] = useState<Curso[]>([]);
    const [period, setPeriod] = useState<string>(periodoSeleccionado || "2025-1");


    // Si cambia el periodo seleccionado, actualiza el semestre
    useEffect(() => {
        if (periodoSeleccionado) {
            setPeriod(periodoSeleccionado);
        }
    }, [periodoSeleccionado]);

    useEffect(() => {
        const fetchData = async () => {
            try {
                const [subjectData] = await Promise.all([
                 //   getPeriod(idStudent),
                    getSubject(idStudent, period)
                ]);
                // Transforma los datos para que tengan las claves que espera la interfaz
                const cursosTransformados = subjectData.map((curso: any) => ({
                    nombre: curso.nameSubject,
                    docente: curso.nameTeacher,
                    id: curso.id,
                }));
                setSubjects(cursosTransformados);
            } catch (error) {
                console.error("Error al obtener datos:", error);
            }
        };

        fetchData();
    }, [idStudent, period]);

    // Función para manejar el clic en un curso
    const handleCursoClick = (curso: { nombre: string; docente: string; id:number; }) => {
        alert(
            `Seleccionaste el curso: ${curso.nombre} con el docente: ${curso.docente}  id: ${curso.id}`
        );
       // const idSubject = 3;
        navigate(`/rubric/${idStudent}/${curso.id}/${period}`);
    };
    // Función para manejar el clic en "Otros Periodos"
    const handleClick = () => {
       navigate("/otrosperiodos");
        // alert("No hay otros periodos disponibles");
    };



    return (
        <Layout>
            <IconButton onClick={handleClick} icon="add">
                Otros Periodos
            </IconButton>
            <p className="periodo-actual">Periodo actual: 2025-1</p>{" "}
            <p className="Curso-actuales">Cursos Actuales</p>
            <CursosList cursos={subjects} onCursoClick={handleCursoClick} />
        </Layout>
    );
};

export default SubjectList;
