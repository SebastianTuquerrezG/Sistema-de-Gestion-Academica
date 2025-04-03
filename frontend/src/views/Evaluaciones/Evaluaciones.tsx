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
  inferior: number;
  superior: number;
}

interface Criterio {
  criterio: string;
  porcentaje: number;
  descriptores: Descriptor[];
}

interface Rubrica {
  criterios: Criterio[];
  resultadoAprendizaje: string;
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
          criterio: "Dominio conceptual",
          porcentaje: 25,
          descriptores: [
            {
              nivel: "Nivel 5",
              texto: "Manejo sobresaliente de conceptos",
              inferior: 4.0,
              superior: 5.0,
            },
            {
              nivel: "Nivel 4",
              texto: "Manejo sólido de conceptos",
              inferior: 3.0,
              superior: 3.9,
            },
            {
              nivel: "Nivel 3",
              texto: "Manejo aceptable de conceptos",
              inferior: 2.0,
              superior: 2.9,
            },
            {
              nivel: "Nivel 2",
              texto: "Manejo limitado de conceptos",
              inferior: 1.0,
              superior: 1.9,
            },
            {
              nivel: "Nivel 1",
              texto: "Desconocimiento evidente",
              inferior: 0.0,
              superior: 0.9,
            },
          ],
        },
        {
          criterio: "Aplicación técnica",
          porcentaje: 25,
          descriptores: [
            {
              nivel: "Nivel 5",
              texto: "Aplica técnicas complejas con precisión",
              inferior: 4.0,
              superior: 5.0,
            },
            {
              nivel: "Nivel 4",
              texto: "Aplica técnicas correctamente",
              inferior: 3.0,
              superior: 3.9,
            },
            {
              nivel: "Nivel 3",
              texto: "Aplica técnicas básicas con errores menores",
              inferior: 2.0,
              superior: 2.9,
            },
            {
              nivel: "Nivel 2",
              texto: "Aplica técnicas de forma incompleta",
              inferior: 1.0,
              superior: 1.9,
            },
            {
              nivel: "Nivel 1",
              texto: "No aplica técnicas correctamente",
              inferior: 0.0,
              superior: 0.9,
            },
          ],
        },
        {
          criterio: "Presentación del trabajo",
          porcentaje: 25,
          descriptores: [
            {
              nivel: "Nivel 5",
              texto:
                "Presentación impecable con excelente organización y diseño",
              inferior: 4.0,
              superior: 5.0,
            },
            {
              nivel: "Nivel 4",
              texto:
                "Presentación bien organizada con detalles menores a mejorar",
              inferior: 3.0,
              superior: 3.9,
            },
            {
              nivel: "Nivel 3",
              texto:
                "Presentación adecuada pero con deficiencias de organización",
              inferior: 2.0,
              superior: 2.9,
            },
            {
              nivel: "Nivel 2",
              texto: "Presentación poco estructurada y con errores notables",
              inferior: 1.0,
              superior: 1.9,
            },
            {
              nivel: "Nivel 1",
              texto: "Presentación deficiente sin estructura ni claridad",
              inferior: 0.0,
              superior: 0.9,
            },
          ],
        },
        {
          criterio: "Trabajo en equipo",
          porcentaje: 25,
          descriptores: [
            {
              nivel: "Nivel 5",
              texto:
                "Colabora eficazmente con el equipo y contribuye significativamente",
              inferior: 4.0,
              superior: 5.0,
            },
            {
              nivel: "Nivel 4",
              texto:
                "Participa activamente en el equipo con pequeñas áreas de mejora",
              inferior: 3.0,
              superior: 3.9,
            },
            {
              nivel: "Nivel 3",
              texto:
                "Colabora pero con deficiencias en la comunicación o entrega",
              inferior: 2.0,
              superior: 2.9,
            },
            {
              nivel: "Nivel 2",
              texto: "Dificultades para integrarse al equipo",
              inferior: 1.0,
              superior: 1.9,
            },
            {
              nivel: "Nivel 1",
              texto: "No trabaja bien en equipo y no contribuye",
              inferior: 0.0,
              superior: 0.9,
            },
          ],
        },
      ],
      resultadoAprendizaje:
        "Desarrollo de pensamiento crítico y resolución de problemas",
    },
  },
  {
    nombre: "Harold Andrés Molano",
    rubrica: {
      criterios: [
        {
          criterio: "Investigación y análisis",
          porcentaje: 30,
          descriptores: [
            {
              nivel: "Nivel 5",
              texto: "Investigación rigurosa y detallada",
              inferior: 4.0,
              superior: 5.0,
            },
            {
              nivel: "Nivel 4",
              texto:
                "Investigación completa con algunos detalles menores omitidos",
              inferior: 3.0,
              superior: 3.9,
            },
            {
              nivel: "Nivel 3",
              texto: "Investigación adecuada pero con falta de profundidad",
              inferior: 2.0,
              superior: 2.9,
            },
            {
              nivel: "Nivel 2",
              texto: "Investigación superficial con datos insuficientes",
              inferior: 1.0,
              superior: 1.9,
            },
            {
              nivel: "Nivel 1",
              texto: "No presenta evidencia de investigación",
              inferior: 0.0,
              superior: 0.9,
            },
          ],
        },
      ],
      resultadoAprendizaje:
        "Mejora en habilidades de investigación y comunicación",
    },
  },
  {
    nombre: "Felipe Armand Pino Sierra",
    rubrica: {
      criterios: [],
      resultadoAprendizaje: "Aprendizaje en programación avanzada",
    },
  },
  {
    nombre: "Juan Carlos Fernández Cuetia",
    rubrica: {
      criterios: [],
      resultadoAprendizaje: "Desarrollo de liderazgo y gestión de proyectos",
    },
  },
];

const Evaluaciones: React.FC = () => {
  const [estudianteSeleccionado, setEstudianteSeleccionado] =
    useState<string>("");
  const estudiante = estudiantes.find(
    (e) => e.nombre === estudianteSeleccionado
  );
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
        resultadoAprendizaje={rubrica?.resultadoAprendizaje || ""}
        onSelectEstudiante={setEstudianteSeleccionado}
      />

      {rubrica ? (
        <EvaluationTable
          criterios={rubrica.criterios}
          estudiante={estudianteSeleccionado}
        />
      ) : (
        <p style={{ textAlign: "center", marginTop: "40px" }}>
          Seleccione un estudiante para cargar la rúbrica
        </p>
      )}
    </>
  );
};

export default Evaluaciones;
