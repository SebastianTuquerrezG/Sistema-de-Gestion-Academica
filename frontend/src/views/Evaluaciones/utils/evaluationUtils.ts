import { Descriptor } from '../types';

export const getNivel = (valor: number): string => {
  if (valor >= 4.0) return "Nivel 3";
  if (valor >= 3.0) return "Nivel 2";
  return "Nivel 1";
};

export const calculateTotalScore = (
  valores: (number | "")[],
  criterios: { porcentaje: number }[]
): number => {
  return criterios.reduce(
    (acc, row, i) =>
      typeof valores[i] === "number"
        ? acc + valores[i]! * (row.porcentaje / 100)
        : acc,
    0
  );
};

export const formatStudentName = (name: string, lastName: string): string => {
  return `${name ?? ""} ${lastName ?? ""}`.trim();
};

export const parseRangoNota = (rangoNota: string): { inferior: number; superior: number } => {
  const [inferior, superior] = rangoNota.split("-").map(Number);
  return { inferior, superior };
};

/**
 * Busca el nivel correspondiente a una nota, usando los niveles y sus rangos definidos en la rúbrica.
 * @param {number} valor - La calificación a evaluar.
 * @param {Array<{ nivelDescripcion: string, rangoNota: string }>} niveles - Los niveles del criterio.
 * @returns {string} - El nivel correspondiente (por ejemplo: "a", "b+", etc).
 */
export const getNivelDinamico = (
  valor: number,
  niveles: { nivelDescripcion: string; rangoNota: string }[]
): string => {
  for (const nivel of niveles) {
    const [inferior, superior] = nivel.rangoNota.split("-").map(Number);
    if (valor >= inferior && valor <= superior) {
      return nivel.nivelDescripcion;
    }
  }
  return "Sin nivel";
};

/**
 * Busca el nivel general correspondiente a una nota, usando los niveles y sus rangos definidos en la rúbrica.
 * @param {number} valor - La calificación a evaluar.
 * @param {Array<{ nivel: string, rangoNota: string }>} niveles - Los niveles del criterio.
 * @returns {string} - El nivel general (por ejemplo: "Nivel 1", "Nivel 2", etc).
 */
export const getNivelGenerico = (
  valor: number,
  niveles: { nivel: string; rangoNota: string }[]
): string => {
  for (const nivel of niveles) {
    const [inferior, superior] = nivel.rangoNota.split("-").map(Number);
    if (valor >= inferior && valor <= superior) {
      return nivel.nivel;
    }
  }
  return "Sin nivel";
}; 