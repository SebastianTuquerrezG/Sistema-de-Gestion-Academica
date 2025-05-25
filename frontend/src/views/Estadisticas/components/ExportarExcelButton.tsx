import React from "react";
import * as XLSX from "xlsx";
import { saveAs } from "file-saver";

interface ExportarExcelButtonProps {
  estadisticas: any;
  filtros: {
    materia: string;
    rubrica: string;
    periodo: string;
    resultadoAprendizaje: string;
  };
}

const ExportarExcelButton: React.FC<ExportarExcelButtonProps> = ({
  estadisticas,
  filtros,
}) => {
  const handleExport = () => {
    console.log('Datos recibidos en ExportarExcelButton:', {
      estadisticas,
      filtros
    });

    // Crear cabecera con datos de búsqueda
    const cabecera = [
      ["Datos de Búsqueda"],
      ["Materia", filtros.materia || "No especificado"],
      ["Rúbrica", filtros.rubrica || "No especificado"],
      ["Período", filtros.periodo || "No especificado"],
      ["Resultado de Aprendizaje", filtros.resultadoAprendizaje || "No especificado"],
      [], // Línea en blanco para separar
    ];

    // 1. Tarjetas
    const resumen = [
      ...cabecera,
      ["Resumen Estadístico"],
      ["Media", estadisticas.average],
      ["Mediana", estadisticas.median],
      ["Desviación Estándar", estadisticas.standardDeviation],
      ["Máximo", estadisticas.maxScore],
      ["Mínimo", estadisticas.minScore],
      ["Cantidad de Estudiantes", estadisticas.studentsCount],
    ];

    // 2. Promedio General
    const promedioGeneral = [
      ...cabecera,
      ["Promedio General"],
      ["Item", "Promedio"],
      ...(estadisticas.generalAverages || []).map((valor: number, idx: number) => [
        idx + 1,
        valor,
      ]),
    ];

    // 3. Promedio por Criterio
    const promedioPorCriterio = [
      ...cabecera,
      ["Promedio por Criterio"],
      ["Criterio", "Promedio"],
      ...(estadisticas.criteriaAverages || []).map((c: any) => [
        c.criterioDescripcion,
        c.promedioNota,
      ]),
    ];

    // 4. Histogramas
    const histogramas = [
      ...cabecera,
      ["Histogramas"],
      ["Criterio", "Descripción", "Nivel", "Cantidad"],
      ...(estadisticas.histograms || []).flatMap((h: any) => [
        [h.criteriaDescription, h.criteriaDescription, "Nivel 1", h.countNivel1],
        [h.criteriaDescription, h.criteriaDescription, "Nivel 2", h.countNivel2],
        [h.criteriaDescription, h.criteriaDescription, "Nivel 3", h.countNivel3],
      ]),
    ];

    // Crear libro de Excel
    const wb = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(
      wb,
      XLSX.utils.aoa_to_sheet(resumen),
      "Resumen"
    );
    XLSX.utils.book_append_sheet(
      wb,
      XLSX.utils.aoa_to_sheet(promedioGeneral),
      "Promedio General"
    );
    XLSX.utils.book_append_sheet(
      wb,
      XLSX.utils.aoa_to_sheet(promedioPorCriterio),
      "Promedio por Criterio"
    );
    XLSX.utils.book_append_sheet(
      wb,
      XLSX.utils.aoa_to_sheet(histogramas),
      "Histogramas"
    );

    // Guardar archivo
    const wbout = XLSX.write(wb, { bookType: "xlsx", type: "array" });
    saveAs(
      new Blob([wbout], { type: "application/octet-stream" }),
      "estadisticas_rubricas.xlsx"
    );
  };

  return (
    <button
      type="button"
      onClick={handleExport}
      className="bg-green-600 hover:bg-green-700 text-white rounded-lg px-6 py-2 font-semibold flex items-center gap-2 shadow-md"
    >
      <span className="material-symbols-outlined" style={{ fontSize: 22 }}>
        download
      </span>
      Exportar a Excel
    </button>
  );
};

export default ExportarExcelButton;
