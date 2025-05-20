import React from "react";
import CursoItem from "./CursoItem";

interface Curso {
  nombre: string;
  docente: string;
}

interface CursosListProps {
  cursos: Curso[];
  onCursoClick?: (curso: Curso) => void;
}

const CursosList: React.FC<CursosListProps> = ({ cursos, onCursoClick }) => (
  <div>
    {cursos.map((curso, idx) => (
      <CursoItem
        key={idx}
        nombre={curso.nombre}
        docente={curso.docente}
        onClick={() => onCursoClick && onCursoClick(curso)}
      />
    ))}
  </div>
);

export default CursosList;
