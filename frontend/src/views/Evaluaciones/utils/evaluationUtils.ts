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