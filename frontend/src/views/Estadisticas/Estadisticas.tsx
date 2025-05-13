import React, { useEffect, useState } from "react";
import PageTitle from "../../components/pageTitle/pageTitle";
import ActionButtons from "../../components/utils/actionButtons";
import EstadisticasFilters from "./components/EstadisticasFilters";
import EstadisticasCards from "./components/EstadisticasCards";
import PromedioGeneralChart from "./components/PromedioGeneralChart";
import PromedioPorCriterioChart from "./components/PromedioPorCriterioChart";
import HistogramaCriterioChart from "./components/HistogramaCriterioChart";
import ExportarExcelButton from "./components/ExportarExcelButton";
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

  useEffect(() => {
    setLoading(true);
    getEstadisticasGenerales({}).then((data) => {
      setEstadisticas(data);
      setLoading(false);
    });
  }, []);

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

        {estadisticas && !loading && (
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
        {loading && <div style={{ textAlign: 'center', marginTop: 40 }}>Cargando estadísticas...</div>}
      </div>
    </div>
  );
};

export default Estadisticas; 