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
            { nivel: "Nivel 5", texto: "Manejo sobresaliente de conceptos", inferior: 4.0, superior: 5.0 },
            { nivel: "Nivel 4", texto: "Manejo sólido de conceptos", inferior: 3.0, superior: 3.9 },
            { nivel: "Nivel 3", texto: "Manejo aceptable de conceptos", inferior: 2.0, superior: 2.9 },
            { nivel: "Nivel 2", texto: "Manejo limitado de conceptos", inferior: 1.0, superior: 1.9 },
            { nivel: "Nivel 1", texto: "Desconocimiento evidente", inferior: 0.0, superior: 0.9 }
          ]
        },
        {
          criterio: "Aplicación técnica",
          porcentaje: 25,
          descriptores: [
            { nivel: "Nivel 5", texto: "Aplica técnicas complejas con precisión", inferior: 4.0, superior: 5.0 },
            { nivel: "Nivel 4", texto: "Aplica técnicas correctamente", inferior: 3.0, superior: 3.9 },
            { nivel: "Nivel 3", texto: "Aplica técnicas básicas con errores menores", inferior: 2.0, superior: 2.9 },
            { nivel: "Nivel 2", texto: "Aplica técnicas de forma incompleta", inferior: 1.0, superior: 1.9 },
            { nivel: "Nivel 1", texto: "No aplica técnicas correctamente", inferior: 0.0, superior: 0.9 }
          ]
        },
        {
          criterio: "Presentación del trabajo",
          porcentaje: 25,
          descriptores: [
            { nivel: "Nivel 5", texto: "Presentación clara, profesional e impecable", inferior: 4.0, superior: 5.0 },
            { nivel: "Nivel 4", texto: "Presentación ordenada y clara", inferior: 3.0, superior: 3.9 },
            { nivel: "Nivel 3", texto: "Presentación aceptable con fallos menores", inferior: 2.0, superior: 2.9 },
            { nivel: "Nivel 2", texto: "Presentación desordenada", inferior: 1.0, superior: 1.9 },
            { nivel: "Nivel 1", texto: "Presentación deficiente o ausente", inferior: 0.0, superior: 0.9 }
          ]
        },
        {
          criterio: "Trabajo en equipo",
          porcentaje: 25,
          descriptores: [
            { nivel: "Nivel 5", texto: "Colabora activamente y lidera iniciativas", inferior: 4.0, superior: 5.0 },
            { nivel: "Nivel 4", texto: "Colabora de forma constante", inferior: 3.0, superior: 3.9 },
            { nivel: "Nivel 3", texto: "Participa con regularidad", inferior: 2.0, superior: 2.9 },
            { nivel: "Nivel 2", texto: "Participación limitada", inferior: 1.0, superior: 1.9 },
            { nivel: "Nivel 1", texto: "No colabora con el equipo", inferior: 0.0, superior: 0.9 }
          ]
        }
      ]
    }
  },
  {
    nombre: "Harold Andrés Molano",
    rubrica: {
      criterios: [] // luego lo llenamos igual que el primero
    }
  },
  {
    nombre: "Juan Carlos Fernández Cuetia",
    rubrica: {
      criterios: [] // luego lo llenamos igual que el primero
    }
  }
];

// Copiar la misma rúbrica del primero a los otros dos
estudiantes[1].rubrica.criterios = [...estudiantes[0].rubrica.criterios];
estudiantes[2].rubrica.criterios = [...estudiantes[0].rubrica.criterios];




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
        <EvaluationTable criterios={rubrica.criterios} estudiante={estudianteSeleccionado} />
      ) : (
        <p style={{ textAlign: "center", marginTop: "40px" }}>
          Seleccione un estudiante para cargar la rúbrica
        </p>
      )}
    </>
  );
};

export default Evaluaciones;
