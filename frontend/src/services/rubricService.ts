import api from "@/services/api";
import { RubricInterface } from "../interfaces/RubricInterface";
import { MateriaInterface } from "@/interfaces/MateriaInterface";
import { RubricInterfacePeticion } from "@/interfaces/RubricInterfacePeticion";

export async function createRubric(
  rubric: RubricInterface
): Promise<RubricInterface> {
  const { data } = await api.post("/api/rubricas", rubric);
  return data;
}

export async function getRubricById(
  id: string
): Promise<RubricInterface | null> {
  try {
    const { data } = await api.get(`/api/rubricas/${id}`);
    return data;
  } catch {
    return null;
  }
}

export async function getAllRubrics(): Promise<RubricInterface[]> {
  const { data } = await api.get("/api/rubricas");
  return data;
}

export async function updateRubric(
  id: number | null,
  updatedRubric: RubricInterfacePeticion
): Promise<RubricInterfacePeticion | null> {
  try {
    const { data } = await api.put(`/api/rubricas?id=${id}`, updatedRubric);
    return data;
  } catch {
    return null;
  }
}

export async function deleteRubric(id: number): Promise<boolean> {
  try {
    await api.patch(`/api/rubricas?id=${id}`, { estado: "DESACTIVAR" });
    return true;
  } catch {
    return false;
  }
}

export async function getAllMaterias(): Promise<MateriaInterface[]> {
  const { data } = await api.get("/api/rubricas/materias");
  return data;
}
