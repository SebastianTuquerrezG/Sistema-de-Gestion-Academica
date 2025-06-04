import React, { useState } from "react";
import { BarChart, Bar, XAxis, YAxis, Tooltip, ResponsiveContainer, CartesianGrid, LabelList } from "recharts";

interface NivelData {
  nivel: string;
  cantidad: number;
}

interface HistogramaCriterio {
  criterio: string;
  niveles: NivelData[];
  descripcion: string;
}

interface HistogramaCriterioChartProps {
  histogramas: HistogramaCriterio[];
  isPrintMode?: boolean;
}

const CustomTooltip = ({ active, payload, label }: any) => {
  if (active && payload && payload.length) {
    return (
      <div style={{ background: '#fff', border: '1px solid #0d47a1', borderRadius: 8, padding: '8px 14px', color: '#0d47a1', fontWeight: 600, fontSize: 15 }}>
        <div>Nivel: {label}</div>
        <div>Cantidad: <span style={{ color: '#0d47a1' }}>{payload[0].value}</span></div>
      </div>
    );
  }
  return null;
};

const HistogramaChart: React.FC<{ histograma: HistogramaCriterio; isPrintMode?: boolean }> = ({ histograma, isPrintMode }) => (
  <div style={{ 
    width: '100%', 
    marginBottom: isPrintMode ? '100px' : '20px',
    backgroundColor: '#ffffff',
    padding: isPrintMode ? '30px' : '0px',
    borderRadius: '12px'
  }}>
    <div style={{ 
      textAlign: 'center', 
      fontWeight: 600, 
      marginBottom: 24, 
      fontSize: isPrintMode ? 18 : 16, 
      color: '#0d47a1',
      paddingTop: isPrintMode ? '20px' : '0px'
    }}>
      {histograma.descripcion}
    </div>
    <div style={{ width: '100%', height: 300, paddingBottom: '20px' }}>
      <ResponsiveContainer>
        <BarChart 
          data={histograma.niveles} 
          margin={{ top: 30, right: 40, left: 40, bottom: 20 }}
        >
          <CartesianGrid strokeDasharray="3 3" vertical={false} />
          <XAxis 
            dataKey="nivel" 
            tick={{ fontSize: 15 }}
            dy={10}
          />
          <YAxis 
            allowDecimals={false} 
            tick={{ fontSize: 15 }}
            dx={-10}
            domain={[0, 4]}
            ticks={[0, 1, 2, 3, 4]}
          />
          {!isPrintMode && <Tooltip content={<CustomTooltip />} />}
          <Bar 
            dataKey="cantidad" 
            fill="#0d47a1" 
            radius={[8, 8, 0, 0]}
          >
            <LabelList 
              dataKey="cantidad" 
              position="top" 
              style={{ fontSize: '14px', fill: '#0d47a1', fontWeight: 600 }}
            />
          </Bar>
        </BarChart>
      </ResponsiveContainer>
    </div>
  </div>
);

const HistogramaCriterioChart: React.FC<HistogramaCriterioChartProps> = ({ histogramas, isPrintMode = false }) => {
  const [indice, setIndice] = useState(0);
  const actual = histogramas[indice];

  const handlePrev = () => setIndice((prev) => (prev === 0 ? histogramas.length - 1 : prev - 1));
  const handleNext = () => setIndice((prev) => (prev === histogramas.length - 1 ? 0 : prev + 1));

  if (isPrintMode) {
    return (
      <div style={{ background: "#fff", borderRadius: 12, padding: 40, marginTop: 40 }}>
        <h4 style={{ margin: 0, marginBottom: 40, color: '#22229e', textAlign: 'left', fontSize: '22px', fontWeight: 'bold' }}>
          Histogramas por criterios
        </h4>
        {histogramas.map((histograma, index) => (
          <HistogramaChart key={index} histograma={histograma} isPrintMode={true} />
        ))}
      </div>
    );
  }

  return (
    <div style={{ background: "#fff", borderRadius: 12, boxShadow: "0 2px 8px rgba(44,62,80,0.07)", padding: 24, minHeight: 350, marginTop: 40 }}>
      <h4 style={{ margin: 0, marginBottom: 18, color: '#22229e', textAlign: 'left' }}>Histogramas por criterios</h4>
      <div style={{ display: 'flex', alignItems: 'center', justifyContent: 'center', gap: 16 }}>
        <button onClick={handlePrev} style={{ fontSize: 0, background: 'none', border: 'none', cursor: 'pointer', color: '#0d47a1', marginRight: 8, padding: 8 }} aria-label="Anterior">
          <span className="material-symbols-outlined" style={{ fontSize: 36, color: '#0d47a1' }}>chevron_left</span>
        </button>
        <HistogramaChart histograma={actual} isPrintMode={false} />
        <button onClick={handleNext} style={{ fontSize: 0, background: 'none', border: 'none', cursor: 'pointer', color: '#0d47a1', marginLeft: 8, padding: 8 }} aria-label="Siguiente">
          <span className="material-symbols-outlined" style={{ fontSize: 36, color: '#0d47a1' }}>chevron_right</span>
        </button>
      </div>
      <div style={{ textAlign: 'center', marginTop: 12 }}>
        {histogramas.map((_, i) => (
          <span key={i} style={{
            display: 'inline-block',
            width: 12,
            height: 12,
            borderRadius: '50%',
            background: i === indice ? '#0d47a1' : '#e3e6f0',
            margin: '0 6px',
          }} />
        ))}
      </div>
    </div>
  );
};

export default HistogramaCriterioChart; 