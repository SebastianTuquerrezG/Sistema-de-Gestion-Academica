import React, { useState } from "react";
import PageTitle from "../../components/pageTitle/pageTitle";
import RubricaInfo from "./rubricaInfo";
import EvaluationTable from "./evaluationTable";
import ActionButtons from "../../components/utils/actionButtons";
import "../../assets/css/evaluaciones.css";

// Tipos
interface Descriptor {
  nivel: string;
  texto: string;
}

interface Criterio {
  criterio: string;
  porcentaje: number;
  descriptores: Descriptor[];
}

interface Rubrica {
  criterios: Criterio[];
}

interface Estudiante {
  nombre: string;
  rubrica: Rubrica;
}

// Datos simulados (como si vinieran del backend)
const estudiantes: Estudiante[] = [
  {
    nombre: "Jonathan Felipe Hurtado",
    rubrica: {
      criterios: [
        {
          criterio: "Criterio A",
          porcentaje: 20,
          descriptores: [
            { nivel: "Nivel 3", texto: "Excelente desempeño" },
            { nivel: "Nivel 4", texto: "Excelente desempeño" },
           // { nivel: "Nivel 5", texto: "Excelente desempeño" },
            { nivel: "Nivel 2", texto: "Buen desempeño" },
            { nivel: "Nivel 1", texto: "Desempeño insuficiente" }
          ]
        },
        {
          criterio: "Criterio B",
          porcentaje: 30,
          descriptores: [
            { nivel: "Nivel 3", texto: "Uso avanzado de herramientas" },
            { nivel: "Nivel 2", texto: "Uso correcto de herramientas" },
            { nivel: "Nivel 1", texto: "Uso incorrecto de herramientas" }
          ]
        }
      ]
    }
  },
  {
    nombre: "Harold Andrés Molano",
    rubrica: {
      criterios: [
        {
          criterio: "Análisis de requisitos",
          porcentaje: 25,
          descriptores: [
            { nivel: "Nivel 3", texto: "Analiza a profundidad" },
            { nivel: "Nivel 2", texto: "Analiza parcialmente" },
            { nivel: "Nivel 1", texto: "Análisis superficial o inexistente" },
            { nivel: "Nivel 5", texto: "Análisis superficial o inexistente" }
          ]
        }
      ]
    }
  }
];

const Evaluaciones: React.FC = () => {
  const [estudianteSeleccionado, setEstudianteSeleccionado] = useState<string>("");
  const estudiante = estudiantes.find((e) => e.nombre === estudianteSeleccionado);
  const rubrica = estudiante?.rubrica;

  return (
    <>
      <div className="header-row">
        <PageTitle title="Evaluaciones" />
        <ActionButtons />
      </div>

      <RubricaInfo
        estudiantes={estudiantes.map((e) => e.nombre)}
        periodos={["2024-I", "2024-II", "2025-I"]}
        materia="Ingeniería de Software III"
        rubricaNombre={rubrica ? "Evaluación personalizada" : ""}
        onSelectEstudiante={setEstudianteSeleccionado}
      />

      {rubrica ? (
        <EvaluationTable criterios={rubrica.criterios} />
      ) : (
        <p style={{ textAlign: "center", marginTop: "40px" }}>
          Seleccione un estudiante para cargar la rúbrica
        </p>
      )}
    </>
  );
};

export default Evaluaciones;
