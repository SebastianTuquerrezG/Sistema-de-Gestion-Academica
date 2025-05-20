// Simulación de datos de estadísticas

export const getEstadisticasGenerales = async (filtros: any) => {
  // Simula una llamada a backend
  return {
    media: 3.8,
    mediana: 4.0,
    moda: 4.2,
    desviacionEstandar: 1.5,
    maximo: 4.2,
    minimo: 2.3,
    promedioGeneral: [3.2, 2.5, 3.7, 3.1, 3.6, 3.8, 3.9, 4.2],
    criterios: [
      { nombre: "Pensamiento Crítico", promedio: 3.5 },
      { nombre: "Comunicación", promedio: 4.2 },
      { nombre: "Resolución de Problemas", promedio: 2.8 },
    ],
    histogramas: [
      {
        criterio: "Pensamiento Crítico",
        niveles: [
          { nivel: "Nivel 1", cantidad: 5 },
          { nivel: "Nivel 2", cantidad: 13 },
          { nivel: "Nivel 3", cantidad: 9 },
        ],
        descripcion: "Genera ideas que dan solución a situaciones planteadas",
      },
      {
        criterio: "Comunicación",
        niveles: [
          { nivel: "Nivel 1", cantidad: 3 },
          { nivel: "Nivel 2", cantidad: 10 },
          { nivel: "Nivel 3", cantidad: 14 },
        ],
        descripcion: "Expresa ideas de manera clara y efectiva",
      },
      {
        criterio: "Resolución de Problemas",
        niveles: [
          { nivel: "Nivel 1", cantidad: 7 },
          { nivel: "Nivel 2", cantidad: 8 },
          { nivel: "Nivel 3", cantidad: 12 },
        ],
        descripcion: "Resuelve problemas aplicando conocimientos previos",
      },
    ],
  };
}; 