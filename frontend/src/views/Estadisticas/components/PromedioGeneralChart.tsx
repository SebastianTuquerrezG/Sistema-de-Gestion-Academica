import React from "react";
import { BarChart, Bar, XAxis, YAxis, Tooltip, ResponsiveContainer, CartesianGrid } from "recharts";

interface PromedioGeneralChartProps {
  data: number[];
}

const CustomTooltip = ({ active, payload, label }: any) => {
  if (active && payload && payload.length) {
    return (
      <div style={{ background: '#fff', border: '1px solid #0d47a1', borderRadius: 8, padding: '8px 14px', color: '#0d47a1', fontWeight: 600, fontSize: 15 }}>
        <div>Ítem: {label}</div>
        <div>Promedio: <span style={{ color: '#0d47a1' }}>{payload[0].value.toFixed(2)}</span></div>
      </div>
    );
  }
  return null;
};

const PromedioGeneralChart: React.FC<PromedioGeneralChartProps> = ({ data }) => {
  // Preparamos los datos para Recharts
  const chartData = data.map((valor, idx) => ({
    name: (idx + 1).toString(),
    promedio: valor,
  }));

  return (
    <div style={{ background: "#fff", borderRadius: 12, boxShadow: "0 2px 8px rgba(44,62,80,0.07)", padding: 24, minHeight: 350 }}>
      <h4 style={{ margin: 0, marginBottom: 18, color: '#22229e', textAlign: 'left' }}>Promedio General</h4>
      <ResponsiveContainer width="100%" height={280}>
        <BarChart data={chartData} margin={{ top: 10, right: 20, left: 0, bottom: 0 }}>
          <CartesianGrid strokeDasharray="3 3" vertical={false} />
          <XAxis dataKey="name" tick={{ fontSize: 15 }} />
          <YAxis domain={[0, 5]} tick={{ fontSize: 15 }} />
          <Tooltip content={<CustomTooltip />} />
          <Bar dataKey="promedio" fill="#0d47a1" radius={[8, 8, 0, 0]} />
        </BarChart>
      </ResponsiveContainer>
    </div>
  );
};

export default PromedioGeneralChart; 