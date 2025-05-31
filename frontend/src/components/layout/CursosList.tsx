import React from "react";
import CursoItem from "./CursoItem";

interface Curso {
  nombre: string;
  docente: string;
    id: number;
}

interface CursosListProps {
  cursos: Curso[];
  onCursoClick?: (curso: Curso) => void;
}

const CursosList: React.FC<CursosListProps> = ({ cursos, onCursoClick }) => (
  <div className= "flex flex-col">
    {cursos.map((curso, idx) => (
      <CursoItem
        key={idx}
        nombre={curso.nombre}
        docente={curso.docente}
        id={curso.id}
        onClick={() => onCursoClick && onCursoClick(curso)}
      />
    ))}
  </div>
);

export default CursosList;
