import Sidebar from "./sidebar";
import Header from "./header";
import React, { useEffect, useState } from "react";
import IconButton from "./../buttons/iconButton";
import CursosList from "../layout/CursosList";
import Footer from "./Footer ";
import { useNavigate, useLocation } from "react-router-dom";

import "./layout.css";
//import PageTitle from "../pageTitle/pageTitle.tsx";

const Layout = () => {
  const navigate = useNavigate();
  const location = useLocation();
  // Estado para los cursos
  const periodoSeleccionado = location.state?.periodoSeleccionado;
  //alert(periodoSeleccionado);
  const [semester, setSemester] = useState(periodoSeleccionado || "2025-1");
  // alert(semester);
  const [cursos, setCursos] = useState<{ nombre: string; docente: string }[]>(
    []
  );

  // Puedes cambiar estos valores por los reales del usuario logueado y semestre actual
  const idStudent = 1; // <-- Cambia por el id real del estudiante
  //const semester = "2025-1"; // <-- Cambia por el semestre real

  // Si cambia el periodo seleccionado, actualiza el semestre
  useEffect(() => {
    if (periodoSeleccionado) {
      setSemester(periodoSeleccionado);
    }
  }, [periodoSeleccionado]);

  // Consulta al backend
  useEffect(() => {
    const fetchCursos = async () => {
      try {
        const response = await fetch(
          `http://localhost:8080/api/RubricEvaluation/${idStudent}/${semester}`
        );
        if (!response.ok) {
          const errorText = await response.text();
          throw new Error(
            `Error al consultar cursos: ${response.status} - ${errorText}`
          );
        }
        const data = await response.json();
        // Transforma los datos para que tengan las claves que espera la interfaz
        const cursosTransformados = data.map((curso: any) => ({
          nombre: curso.nameSubject,
          docente: curso.nameTeacher,
        }));
        setCursos(cursosTransformados);
        console.log("Cursos obtenidos:", cursosTransformados);
      } catch (error) {
        console.error("Error al obtener cursos:", error);
      }
    };
    fetchCursos();
  }, [idStudent, semester]);

  // Función para manejar el clic en "Otros Periodos"
  const handleClick = () => {
    navigate("/otrosperiodos");
    // alert("No hay otros periodos disponibles");
  };
  // Dentro del componente Layout:
  const handleCursoClick = (curso: { nombre: string; docente: string }) => {
    alert(
      `Seleccionaste el curso: ${curso.nombre} con el docente: ${curso.docente}`
    );
    // navigate(`/curso/${curso.nombre}`);
  };

  return (
    <div className="layout-container">
      <Sidebar />
      <div className="content-container">
        <Header />
        <div className="content">
          <p className="periodo-actual">Periodo actual: 2025-1</p>{" "}
          <p className="Curso-actuales">Cursos Actuales</p> {/* Nuevo texto */}
          {/*   <PageTitle title="Buttons list" />*/}
          <IconButton onClick={handleClick} icon="add">
            Otros Periodos
          </IconButton>
          <CursosList cursos={cursos} onCursoClick={handleCursoClick} />
          <Footer />
        </div>
      </div>
    </div>
  );
};

export default Layout;
