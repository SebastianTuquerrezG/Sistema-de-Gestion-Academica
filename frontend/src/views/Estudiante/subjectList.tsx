//import { use } from "react";
import { getSubject } from "@/services/subjectList.ts";
import React , { useEffect, useState } from "react";
import IconButton from "../../components/buttons/iconButton.tsx";
import CursosList from "../../components/layout/CursosList.tsx";
import { useNavigate, useLocation } from "react-router-dom";

const SubjectList: React.FC = () => {

    const navigate = useNavigate();
    const location = useLocation();

    const periodoSeleccionado = location.state?.periodoSeleccionado;
    const idStudent = 2; // Cambia esto por el id real del estudiante

    type Curso = {
        nombre: string;
        docente: string;
        id: number
    };

    type SubjectFromApi = {
        nameSubject: string;
        nameTeacher: string;
        id: number;
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
                    getSubject(idStudent, period)
                ]);
                // Transforma los datos para que tengan las claves que espera la interfaz
                const cursosTransformados = subjectData.map((curso: SubjectFromApi) => ({
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
        // alert(
        //     `Seleccionaste el curso: ${curso.nombre} con el docente: ${curso.docente}  id: ${curso.id}`
        // );
       // const idSubject = 3;
        navigate(`/rubric/${idStudent}/${curso.id}/${period}`);
    };
    // Función para manejar el clic en "Otros Periodos"
    const handleClick = () => {
        navigate(`/otrosperiodos/${idStudent}`, {
            state: {
                idStudent: idStudent,
            }
        });
        // alert("No hay otros periodos disponibles");
    };



    return (
        <div className= "w-full max-w-screen-lg mx-auto flex flex-col flex-1 p-4">
            <div className=" flex justify-between items-center w-full mb-4">
                <h2 className="title2 border-b-2 border-red-500 inline-block" style={{ color: "var(--primary-regular-color)" }}>
                    Periodo actual: {period}
                </h2>
                <IconButton onClick={handleClick} icon="add">
                    Otros Periodos
                </IconButton>
            </div>
            <div className="w-full flex justify-center mt-6 mb-10">
                <p
                    className="text-2xl font-bold font-serif border-b-2 border-red-500 pb-1 inline-block"
                    style={{ color: "var(--primary-regular-color)" }}
                >
                    Cursos Actuales
                </p>
            </div>
            <CursosList cursos={subjects} onCursoClick={handleCursoClick} />
        </div>

    );
};

export default SubjectList;
