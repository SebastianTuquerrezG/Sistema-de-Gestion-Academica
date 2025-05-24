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
    // Crear cabecera con datos de búsqueda
    const cabecera = [
      ["Datos de Búsqueda"],
      ["Materia", filtros.materia],
      ["Rúbrica", filtros.rubrica],
      ["Período", filtros.periodo],
      ["Resultado de Aprendizaje", filtros.resultadoAprendizaje],
      [], // Línea en blanco para separar
    ];

    // 1. Tarjetas
    const resumen = [
      ...cabecera,
      ["Resumen Estadístico"],
      ["Media", estadisticas.media],
      ["Mediana", estadisticas.mediana],
      ["Moda", estadisticas.moda],
      ["Desviación Estándar", estadisticas.desviacionEstandar],
      ["Máximo", estadisticas.maximo],
      ["Mínimo", estadisticas.minimo],
    ];

    // 2. Promedio General
    const promedioGeneral = [
      ...cabecera,
      ["Promedio General"],
      ["Item", "Promedio"],
      ...estadisticas.promedioGeneral.map((valor: number, idx: number) => [
        idx + 1,
        valor,
      ]),
    ];

    // 3. Promedio por Criterio
    const promedioPorCriterio = [
      ...cabecera,
      ["Promedio por Criterio"],
      ["Criterio", "Promedio"],
      ...estadisticas.criterios.map((c: any) => [c.nombre, c.promedio]),
    ];

    // 4. Histogramas
    const histogramas = [
      ...cabecera,
      ["Histogramas"],
      ["Criterio", "Descripción", "Nivel", "Cantidad"],
      ...estadisticas.histogramas.flatMap((h: any) =>
        h.niveles.map((n: any) => [h.criterio, h.descripcion, n.nivel, n.cantidad])
      ),
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
