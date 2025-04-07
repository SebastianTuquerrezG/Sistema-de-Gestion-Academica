import React, { useEffect, useState } from "react";
import PageTitle from "../../components/pageTitle/pageTitle";
import RubricaInfo from "./rubricaInfo";
import EvaluationTable from "./evaluationTable";
import ActionButtons from "../../components/utils/actionButtons";
import {
  getAllSubjects,
  getRubricsBySubjectId,
  getStudentsByCourseAndPeriod,
  getAllSemesters,
  getEnrollIdFromStudentAndPeriod,
} from "../../services/evaluationService";

import "../../assets/css/evaluaciones.css";

interface Rubrica {
  idRubrica: number;
  nombreRubrica: string;
  materia: string;
  notaRubrica: number;
  objetivoEstudio: string;
  courseId: number; // ✅ necesario para consultar estudiantes
  criterios: {
    crfDescripcion: string;
    crfPorcentaje: number;
    niveles: {
      nivelDescripcion: string;
      rangoNota: string;
    }[];
  }[];
}

interface Estudiante {
  id: number;
  name: string;
  lastName: string;
  identification: string;
}

const Evaluaciones: React.FC = () => {
  const [materias, setMaterias] = useState<{ id: number; name: string }[]>([]);
  const [selectedMateria, setSelectedMateria] = useState<string>("");

  const [rubricas, setRubricas] = useState<Rubrica[]>([]);
  const [selectedRubrica, setSelectedRubrica] = useState<Rubrica | null>(null);

  const [periodos, setPeriodos] = useState<string[]>([]);
  const [selectedPeriodo, setSelectedPeriodo] = useState<string>("");

  const [estudiantes, setEstudiantes] = useState<string[]>([]);
  const [selectedEstudiante, setSelectedEstudiante] = useState<string>("");

  const [enrollId, setEnrollId] = useState<number | null>(null);

  // ✅ Cargar materias
  useEffect(() => {
    getAllSubjects().then(setMaterias);
  }, []);

  // ✅ Obtener rúbricas por materia
  useEffect(() => {
    const materia = materias.find((m) => m.name === selectedMateria);
    if (materia) {
      getRubricsBySubjectId(materia.id).then(setRubricas);
    } else {
      setRubricas([]);
    }
    setSelectedRubrica(null);
    setSelectedPeriodo("");
    setSelectedEstudiante("");
    setEstudiantes([]);
    setEnrollId(null);
  }, [selectedMateria]);

  // ✅ Obtener períodos
  useEffect(() => {
    getAllSemesters().then(setPeriodos);
  }, []);

  // ✅ Obtener estudiantes por curso y período
  useEffect(() => {
    if (selectedPeriodo && selectedRubrica?.courseId) {
      getStudentsByCourseAndPeriod(selectedRubrica.courseId, selectedPeriodo).then((data: Estudiante[]) => {
        const nombres = data.map((e) => `${e.name ?? ""} ${e.lastName ?? ""}`.trim());
        setEstudiantes(nombres);
      });
    }
  }, [selectedPeriodo, selectedRubrica]);

  // ✅ Obtener enrollId
  useEffect(() => {
    if (selectedEstudiante && selectedPeriodo) {
      getEnrollIdFromStudentAndPeriod(selectedEstudiante, selectedPeriodo).then(
        (id) => setEnrollId(id)
      );
    }
  }, [selectedEstudiante, selectedPeriodo]);

  return (
    <>
      <div className="header-row">
        <PageTitle title="Evaluaciones" />
        <ActionButtons />
      </div>

      <RubricaInfo
        materias={materias.map((m) => m.name)}
        rubricas={rubricas.map((r) => r.nombreRubrica)}
        periodos={periodos}
        estudiantes={estudiantes}
        materiaSeleccionada={selectedMateria}
        rubricaSeleccionada={selectedRubrica?.nombreRubrica || ""}
        periodoSeleccionado={selectedPeriodo}
        estudianteSeleccionado={selectedEstudiante}
        resultadoAprendizaje={selectedRubrica?.objetivoEstudio || ""}
        onSelectMateria={setSelectedMateria}
        onSelectRubrica={(nombre) => {
          const rubrica = rubricas.find((r) => r.nombreRubrica === nombre) || null;
          setSelectedRubrica(rubrica);
          setSelectedPeriodo("");
          setSelectedEstudiante("");
          setEnrollId(null);
          setEstudiantes([]);
        }}
        onSelectPeriodo={(periodo) => {
          setSelectedPeriodo(periodo);
          if (!periodo) {
            setSelectedEstudiante("");
            setEstudiantes([]);
          }
        }}
        onSelectEstudiante={setSelectedEstudiante}
      />

      {selectedRubrica && enrollId && (
        <EvaluationTable
          estudiante={selectedEstudiante}
          criterios={selectedRubrica.criterios.map((c) => ({
            criterio: c.crfDescripcion,
            porcentaje: c.crfPorcentaje * 100,
            descriptores: c.niveles.map((n) => {
              const [inferior, superior] = n.rangoNota.split("-").map(Number);
              return {
                nivel: n.nivelDescripcion,
                texto: n.nivelDescripcion,
                inferior,
                superior,
              };
            }),
          }))}
          rubricaId={selectedRubrica.idRubrica}
          enrollId={enrollId}
        />
      )}

    </>
  );
};

export default Evaluaciones;
