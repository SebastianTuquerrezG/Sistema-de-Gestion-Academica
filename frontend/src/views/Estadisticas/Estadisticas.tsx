import React, { useEffect, useState } from "react";
import PageTitle from "../../components/pageTitle/pageTitle";
import ActionButtons from "../../components/utils/actionButtons";
import EstadisticasFilters from "./components/EstadisticasFilters";
import EstadisticasCards from "./components/EstadisticasCards";
import PromedioGeneralChart from "./components/PromedioGeneralChart";
import PromedioPorCriterioChart from "./components/PromedioPorCriterioChart";
import HistogramaCriterioChart from "./components/HistogramaCriterioChart";
import ExportarExcelButton from "./components/ExportarExcelButton";
import EstadisticasStateHandler from "./components/EstadisticasStateHandler";
import { useEstadisticasData } from "./hooks/useEstadisticasData";
import { getEstadisticasGenerales } from "./utils/mockEstadisticasService";
import "../../assets/css/estadisticas.css";

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

  const [estadisticas, setEstadisticas] = useState<any>(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);

  const cargarEstadisticas = async () => {
    try {
      setLoading(true);
      setError(null);
      const data = await getEstadisticasGenerales({});
      setEstadisticas(data);
    } catch (err) {
      setError('Error al cargar los datos. Por favor, inténtalo de nuevo más tarde.');
      console.error('Error al cargar estadísticas:', err);
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    cargarEstadisticas();
  }, []);

  const hasData = estadisticas && 
    (estadisticas.promedioGeneral?.length > 0 || 
     estadisticas.criterios?.length > 0 || 
     estadisticas.histogramas?.length > 0);

  return (
    <div className="estadisticas-main-container">
      <div className="header-row">
        <PageTitle title="Estadísticas" />
        <ActionButtons />
      </div>

      <div className="estadisticas-content-centered">
        <EstadisticasFilters
          materias={materias.map((m) => m.name)}
          rubricas={rubricas.map((r) => r.nombreRubrica)}
          periodos={periodos}
          materiaSeleccionada={selectedMateria}
          rubricaSeleccionada={selectedRubrica?.nombreRubrica || ""}
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

        {!loading && !error && hasData && (
          <>
            <EstadisticasCards
              media={estadisticas.media}
              mediana={estadisticas.mediana}
              moda={estadisticas.moda}
              desviacionEstandar={estadisticas.desviacionEstandar}
              maximo={estadisticas.maximo}
              minimo={estadisticas.minimo}
            />
            <div className="estadisticas-chart-block">
              <PromedioGeneralChart data={estadisticas.promedioGeneral} />
            </div>
            <div className="estadisticas-chart-block">
              <PromedioPorCriterioChart data={estadisticas.criterios} />
            </div>
            <div className="estadisticas-chart-block">
              <HistogramaCriterioChart histogramas={estadisticas.histogramas} />
            </div>
            <div style={{ display: 'flex', justifyContent: 'flex-end', marginTop: 32 }}>
              <ExportarExcelButton estadisticas={estadisticas} />
            </div>
          </>
        )}
      </div>
    </div>
  );
};

export default Estadisticas; 