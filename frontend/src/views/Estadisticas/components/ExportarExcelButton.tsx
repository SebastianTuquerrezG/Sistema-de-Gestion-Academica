import React from "react";
import * as XLSX from "xlsx";
import { saveAs } from "file-saver";

interface ExportarExcelButtonProps {
  estadisticas: any;
}

const ExportarExcelButton: React.FC<ExportarExcelButtonProps> = ({
  estadisticas,
}) => {
  const handleExport = () => {
    // 1. Tarjetas
    const resumen = [
      ["Media", estadisticas.media],
      ["Mediana", estadisticas.mediana],
      ["Moda", estadisticas.moda],
      ["Desviación Estándar", estadisticas.desviacionEstandar],
      ["Máximo", estadisticas.maximo],
      ["Mínimo", estadisticas.minimo],
    ];

    // 2. Promedio General
    const promedioGeneral = estadisticas.promedioGeneral.map(
      (valor: number, idx: number) => ({
        Item: idx + 1,
        Promedio: valor,
      })
    );

    // 3. Promedio por Criterio
    const promedioPorCriterio = estadisticas.criterios.map((c: any) => ({
      Criterio: c.nombre,
      Promedio: c.promedio,
    }));

    // 4. Histogramas
    const histogramas = estadisticas.histogramas.flatMap((h: any) =>
      h.niveles.map((n: any) => ({
        Criterio: h.criterio,
        Descripcion: h.descripcion,
        Nivel: n.nivel,
        Cantidad: n.cantidad,
      }))
    );

    // Crear libro de Excel
    const wb = XLSX.utils.book_new();
    XLSX.utils.book_append_sheet(
      wb,
      XLSX.utils.aoa_to_sheet(resumen),
      "Resumen"
    );
    XLSX.utils.book_append_sheet(
      wb,
      XLSX.utils.json_to_sheet(promedioGeneral),
      "Promedio General"
    );
    XLSX.utils.book_append_sheet(
      wb,
      XLSX.utils.json_to_sheet(promedioPorCriterio),
      "Promedio por Criterio"
    );
    XLSX.utils.book_append_sheet(
      wb,
      XLSX.utils.json_to_sheet(histogramas),
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
