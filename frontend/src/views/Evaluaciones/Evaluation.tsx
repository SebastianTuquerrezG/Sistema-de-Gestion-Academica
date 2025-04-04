import React, { useEffect, useState } from "react";
import PageTitle from "../../components/pageTitle/pageTitle";
import RubricaInfo from "./rubricaInfo";
import EvaluationTable from "./evaluationTable";
import { getAllSubjects } from '../../services/evaluationService';
import ActionButtons from "../../components/utils/actionButtons";
import "../../assets/css/evaluaciones.css";

// Interfaces de los datos
interface Subject {
  id: number;
  name: string;
  code: string;
  credits: number;
  status: string;
}

const Evaluaciones: React.FC = () => {
  const [materias, setMaterias] = useState<string[]>([]);
  const [selectedMateria, setSelectedMateria] = useState<string>("");

  //  Cargar materias desde el backend
  useEffect(() => {
    getAllSubjects().then(setMaterias);
  }, []);

  return (
    <>
      <div className="header-row">
        <PageTitle title="Evaluaciones" />
        <ActionButtons />
      </div>

      <RubricaInfo
        materias={materias}
        rubricas={[]} // aún no cargamos estas
        periodos={[]} // tampoco estas
        estudiantes={[]} // tampoco aún
        materiaSeleccionada={selectedMateria}
        rubricaSeleccionada=""
        periodoSeleccionado=""
        estudianteSeleccionado=""
        onSelectMateria={setSelectedMateria}
        onSelectRubrica={() => {}}
        onSelectPeriodo={() => {}}
        onSelectEstudiante={() => {}}
        resultadoAprendizaje=""
      />

      {/* Aún no mostramos tabla */}
    </>
  );
};

export default Evaluaciones;
