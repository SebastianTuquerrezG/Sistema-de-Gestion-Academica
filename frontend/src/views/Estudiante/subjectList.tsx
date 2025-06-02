import {getSubject, parseJwt,getIdStudent} from "@/services/subjectList.ts";
import React , { useEffect, useState } from "react";
import IconButton from "../../components/buttons/iconButton.tsx";
import CursosList from "../../components/layout/CursosList.tsx";
import { useNavigate, useLocation } from "react-router-dom";
import Notification from "@/components/notifications/notification.tsx";

const SubjectList: React.FC = () => {

    const navigate = useNavigate();
    const location = useLocation();
    const [showNotification, setShowNotification] = useState(false);
    const [notificationMessage, setNotificationMessage] = useState("");


    const handleClose = () => {
        setShowNotification(false);
    };


    const periodoSeleccionado = location.state?.periodoSeleccionado;

    const [idStudent, setIdStudent] = useState<number | null>(null); // Nuevo estado para el ID del estudiante


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
    const [period, setPeriod] = useState<string>(periodoSeleccionado ?? "2025-1");
    useEffect(() => {
        const tokenRaw = localStorage.getItem("auth-token");
        if (tokenRaw) {
            const payload = parseJwt(tokenRaw);
            console.log("Contenido del token:", payload);
        }
    }, []);



    // Si cambia el periodo seleccionado, actualiza el semestre
    useEffect(() => {
        if (periodoSeleccionado) {
            setPeriod(periodoSeleccionado);
        }
    }, [periodoSeleccionado]);

    // Cargar ID del estudiante al inicio
    useEffect(() => {
        const fetchStudentId = async () => {
            try {
                const id = await getIdStudent();

                if (id === null || id === undefined) {
                    throw new Error("No se encontró un estudiante asociado a este usuario.");
                }

                setIdStudent(id);
            } catch (error: any) {
                console.error("Error al obtener el ID del estudiante:", error);
                setNotificationMessage(error.message || "Ocurrió un error inesperado.");
                setShowNotification(true);
            }
        };

        fetchStudentId();
    }, []);

    // Cuando ya se tenga el ID y el periodo, obtener las materias
    useEffect(() => {
        const fetchData = async () => {
            if (idStudent === null) return;

            try {
                const subjectData = await getSubject(idStudent, period);
                const cursosTransformados = subjectData.map((curso: SubjectFromApi) => ({
                    nombre: curso.nameSubject,
                    docente: curso.nameTeacher,
                    id: curso.id,
                }));
                setSubjects(cursosTransformados);
            } catch (error:any) {
                setNotificationMessage(error.message);
                setShowNotification(true);
                console.error("Error al obtener datos:", error);
            }
        };

        fetchData();
    }, [idStudent, period]);



    // Función para manejar el clic en un curso
    const handleCursoClick = (curso: { nombre: string; docente: string; id:number; }) => {

        navigate(`/rubric/${idStudent}/${curso.id}/${period}`);
    };
    // Función para manejar el clic en "Otros Periodos"
    const handleClick = () => {
        navigate(`/otrosperiodos/${idStudent}`, {
            state: {
                idStudent: idStudent,
            }
        });
    };



    return (
        <div className= "w-full max-w-screen-lg mx-auto flex flex-col flex-1 p-4">
            {showNotification && (
                <Notification
                    type="error"
                    title="Error al cargar los datos"
                    message={notificationMessage}
                    onClose={handleClose}
                />
            )}
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
