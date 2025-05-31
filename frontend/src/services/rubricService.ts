import { RubricInterface } from "../interfaces/RubricInterface";
import { MateriaInterface } from "@/interfaces/MateriaInterface.ts";
import { RubricInterfacePeticion } from "@/interfaces/RubricInterfacePeticion.ts";

const baseUrl = "http://localhost:8080/api";

function getAuthHeaders(): Record<string, string> {
  const access_token = localStorage.getItem('auth-token');
  const headers: Record<string, string> = {
    "Content-Type": "application/json",
  };
  if (access_token) {
    headers["Authorization"] = `Bearer ${access_token}`;
  }
  return headers;
}

export async function createRubric(
  rubric: RubricInterface
): Promise<RubricInterface> {
  const response = await fetch(`${baseUrl}/rubricas`, {
    method: "POST",
    headers: getAuthHeaders(),
    body: JSON.stringify(rubric),
  });
  return response.json();
}

export async function getRubricById(
  id: string
): Promise<RubricInterface | null> {
  const response = await fetch(`${baseUrl}/rubricas/${id}`, {
    headers: getAuthHeaders(),
  });
  if (!response.ok) {
    return null;
  }
  return response.json();
}

export async function getAllRubrics(): Promise<RubricInterface[]> {
  const response = await fetch(`${baseUrl}/rubricas`, {
    method: 'GET',
    headers: getAuthHeaders(),
  });
  return response.json();
}

export async function updateRubric(id: number | null, updatedRubric: RubricInterfacePeticion): Promise<RubricInterfacePeticion | null> {
  const response = await fetch(`${baseUrl}/rubricas?id=${id}`, {
    method: 'PUT',
    headers: getAuthHeaders(),
    body: JSON.stringify(updatedRubric),
  });
  if (!response.ok) {
    return null;
  }
  return response.json();
}

/*export async function deleteRubric(id: string): Promise<boolean> {
  const response = await fetch(`${baseUrl}/rubricas/${id}`, {
    method: "DELETE",
    headers: getAuthHeaders(),
  });
  return response.ok;
}*/

export async function deleteRubric(id: number): Promise<boolean> {
  const response = await fetch(`${baseUrl}/rubricas?id=${id}`, {
    method: 'PATCH',
    headers: getAuthHeaders(),
    body: JSON.stringify({ estado: 'DESACTIVAR' }),
  });
  return response.ok;
}

export async function getAllMaterias(): Promise<MateriaInterface[]> {
  const response = await fetch(`${baseUrl}/rubricas/materias`, {
    method: 'GET',
    headers: getAuthHeaders(),
  });
  if (!response.ok) {
    throw new Error('Error fetching materias');
  }
  return response.json();
}