import React, { useEffect, useState, useRef } from "react";
import PageTitle from "../../components/pageTitle/pageTitle";
import ActionButtons from "../../components/utils/actionButtons";
import EstadisticasFilters from "./components/EstadisticasFilters";
import EstadisticasCards from "./components/EstadisticasCards";
import PromedioPorCriterioChart from "./components/PromedioPorCriterioChart";
import HistogramaCriterioChart from "./components/HistogramaCriterioChart";
import ExportarExcelButton from "./components/ExportarExcelButton";
import ExportarPDFButton from "./components/ExportarPDFButton";
import EstadisticasStateHandler from "./components/EstadisticasStateHandler";
import { useEstadisticasData } from "./hooks/useEstadisticasData";
import { 
  getStatsByRubric, 
  getHistogramByCriteria,
  getCriteriaAverages,
  HistogramByCriteriaDTO,
  CriteriaAverageDTO,
  CourseStatsDTO
} from "../../services/estadisticasService";
import { CourseStatsDTO as CourseStatsDTOType } from "./types/index.ts"; // o la ruta relativa correcta

const Estadisticas: React.FC = () => {
  const {
    materias,
    selectedMateria,
    setSelectedMateria,
    rubricas,
    selectedRubrica,
    periodos,
    selectedPeriodo,
    setSelectedPeriodo,
    handleSelectRubrica,
  } = useEstadisticasData();

  const [estadisticas, setEstadisticas] = useState<CourseStatsDTO | null>(null);
  const [histogramas, setHistogramas] = useState<HistogramByCriteriaDTO[]>([]);
  const [promediosCriterios, setPromediosCriterios] = useState<CriteriaAverageDTO[]>([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const printRef = useRef<HTMLDivElement | null>(null);

  const cargarEstadisticas = async () => {
    try {
      setLoading(true);
      setError(null);
      
      if (!selectedMateria || !selectedRubrica || !selectedPeriodo) {
        setError("Por favor, selecciona todos los filtros necesarios");
        return;
      }

      const filter = {
        subjectName: selectedMateria,
        rubricName: selectedRubrica.nombreRubrica,
        period: selectedPeriodo
      };

      // Cargar estadísticas generales
      const statsData = await getStatsByRubric(filter);
      setEstadisticas(statsData);

      // Cargar histogramas por criterio
      const histogramData = await getHistogramByCriteria(filter);
      console.log('Datos de histogramas:', histogramData);
      setHistogramas(histogramData);

      // Cargar promedios por criterio
      const promediosData = await getCriteriaAverages(filter);
      console.log('Datos de promedios por criterio:', promediosData);
      setPromediosCriterios(promediosData);

    } catch (err) {
      setError(
        "Error al cargar los datos. Por favor, inténtalo de nuevo más tarde."
      );
      console.error("Error al cargar estadísticas:", err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    if (selectedMateria && selectedRubrica && selectedPeriodo) {
      cargarEstadisticas();
    }
  }, [selectedMateria, selectedRubrica, selectedPeriodo]);

  const hasData = Boolean(
    estadisticas &&
    (estadisticas.average !== null && estadisticas.median !== null && estadisticas.standardDeviation !== null &&
      estadisticas.maxScore !== null && estadisticas.minScore !== null)
  );

  return (
    <div ref={printRef} className="estadisticas-main-container">
      <div className="header-row">
        <PageTitle title="Estadísticas" />
        
      </div>

      <EstadisticasFilters
        materias={materias.map((m) => m.name)}
        rubricas={rubricas.map((r) => r.nombreRubrica || r.name).filter((x): x is string => Boolean(x))}
        periodos={periodos}
        materiaSeleccionada={selectedMateria}
        rubricaSeleccionada={selectedRubrica?.nombreRubrica || selectedRubrica?.name || ""}
        periodoSeleccionado={selectedPeriodo}
        resultadoAprendizaje={selectedRubrica?.objetivoEstudio || ""}
        onSelectMateria={setSelectedMateria}
        onSelectRubrica={handleSelectRubrica}
        onSelectPeriodo={setSelectedPeriodo}
      />

      <EstadisticasStateHandler
        loading={loading}
        error={error}
        hasData={hasData}
      />

      {!loading && !error && hasData && estadisticas && (
        <>
          {console.log('Contenido de estadisticas:', estadisticas)}
          <div className="estadisticas-content-centered">
            <EstadisticasCards
              media={estadisticas.average}
              desviacionEstandar={estadisticas.standardDeviation}
              maximo={estadisticas.maxScore}
              minimo={estadisticas.minScore}
            />
            <HistogramaCriterioChart 
              histogramas={histogramas.map(h => ({
                criterio: h.criteriaDescription,
                descripcion: h.criteriaDescription,
                niveles: [
                  { nivel: 'Nivel 1', cantidad: h.countNivel1 },
                  { nivel: 'Nivel 2', cantidad: h.countNivel2 },
                  { nivel: 'Nivel 3', cantidad: h.countNivel3 }
                ]
              }))} 
            />
            <PromedioPorCriterioChart 
              data={promediosCriterios.map(c => ({
                nombre: c.criterioDescripcion,
                promedio: c.promedioNota
              }))} 
            />
            <div
              className="estadisticas-export-buttons"
              style={{
                display: "flex",
                justifyContent: "flex-end",
                alignItems: "center",
                verticalAlign: "middle",
                gap: 16,
                paddingTop: 24,
              }}
            >
              <ExportarExcelButton 
                estadisticas={estadisticas} 
                filtros={{
                  materia: selectedMateria,
                  rubrica: selectedRubrica?.nombreRubrica || "",
                  periodo: selectedPeriodo,
                  resultadoAprendizaje: selectedRubrica?.objetivoEstudio || "",
                }}
              />
              <ExportarPDFButton targetRef={printRef} />
            </div>
          </div>
        </>
      )}
    </div>
  );
};

export default Estadisticas;
