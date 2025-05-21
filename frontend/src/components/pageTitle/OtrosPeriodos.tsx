import React, { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import PageTitle from "../pageTitle/pageTitle";
import Sidebar from "../layout/sidebar";
import Header from "../layout/header";
import CursosList from "../layout/CursosList";
//import {getSubject} from "../../services/subjectList.tsx";
import {getPeriod} from "../../services/subjectList.tsx"; // Asegúrate de que esta función esté exportada correctamente

const idStudent = 1; // Cambia esto por el id real del estudiante

const OtrosPeriodos = () => {
  const navigate = useNavigate();
  const [periodos, setPeriodos] = useState<
    { nombre: string; docente: string }[]
  >([]);

  useEffect(() => {
    const fetchPeriodo = async () => {
      try {
        const [subjectData] = await Promise.all([
             getPeriod(idStudent),

        ]);
        // Si data es un array de strings, lo transformamos:
        const periodosTransformados = subjectData.map((periodo: string) => ({
          nombre: periodo,
          docente: "", // O puedes poner "N/A" o algún texto
        }));
        setPeriodos(periodosTransformados);
        console.log("Periodos obtenidos:", periodosTransformados);
      } catch (error) {
        console.error("Error al obtener periodos:", error);
      }
    };
    fetchPeriodo();
  }, []);

  // Puedes reutilizar el handler si quieres hacer algo al hacer click en un periodo
  const handlePeriodoClick = (periodo: { nombre: string; docente: string }) => {
    alert(`Seleccionaste el periodo: ${periodo.nombre}`);
    // Aquí podrías navegar a otra vista si lo necesitas
    navigate("/", { state: { periodoSeleccionado: periodo.nombre } });
  };

  return (
    <div className="layout-container">
      <Sidebar />
      <div className="content-container">
        <Header />
        <PageTitle title="Otros Periodos" />
        <div className="content">
          <CursosList cursos={periodos} onCursoClick={handlePeriodoClick} />
        </div>
      </div>
    </div>
  );
};

export default OtrosPeriodos;
