import React, { useEffect, useState } from "react";
import {useNavigate, useParams} from "react-router-dom";
import PageTitle from "../../components/pageTitle/pageTitle.tsx";
import SidebarStudent from "../../components/layout/sidebarStudent.tsx";
import HeaderStudent from "../../components/layout/headerStudent.tsx";
//import {getSubject} from "../../services/subjectList.ts";
import {getPeriod} from "@/services/subjectList.ts";
import PeriodosList from "@/components/layouts/PeriodosList.tsx"; // Asegúrate de que esta función esté exportada correctamente

const OtrosPeriodos: React.FC = () => {
  const { idStudent } = useParams();
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
  const handlePeriodoClick = (periodo: { nombre: string}) => {
    alert(`Seleccionaste el periodo: ${periodo.nombre}`);
    // Aquí podrías navegar a otra vista si lo necesitas
    navigate("/subjectList", { state: { periodoSeleccionado: periodo.nombre } });
  };

  return (
      <div className="layout-container">
        <SidebarStudent />
        <div className="content-container">
          <HeaderStudent />
          <PageTitle title="Otros Periodos" />
          <div className="content">
            <PeriodosList periodos={periodos} onPeriodoClick={handlePeriodoClick} />
          </div>
        </div>
      </div>
  );
};

export default OtrosPeriodos;
