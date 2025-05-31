import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { getPeriod } from "@/services/subjectList.ts";
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
      } catch (error) {
        console.error("Error al obtener periodos:", error);
      }
    };
    fetchPeriodo();
  }, []);

  // Puedes reutilizar el handler si quieres hacer algo al hacer click en un periodo
  const handlePeriodoClick = (periodo: { nombre: string }) => {
    // alert(`Seleccionaste el periodo: ${periodo.nombre}`);
    // Aquí podrías navegar a otra vista si lo necesitas
    navigate("/estudiante", { state: { periodoSeleccionado: periodo.nombre } });
  };

  return (
      <div className="w-full max-w-screen-lg mx-auto flex flex-col flex-1 p-4">
        <div className="w-full flex justify-start mb-4"><h2
              className="title2 border-b-2 border-red-500 inline-block"
              style={{ color: "var(--primary-regular-color)" }}
          >
            Otros Periodos
          </h2>
        </div>

        <div className="p-4">
          <PeriodosList periodos={periodos} onPeriodoClick={handlePeriodoClick} />
        </div>
      </div>
  );
};

export default OtrosPeriodos;
