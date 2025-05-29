import { RubricInterface } from "../interfaces/RubricInterface";
import {MateriaInterface} from "@/interfaces/MateriaInterface.ts";
import {RubricInterfacePeticion} from "@/interfaces/RubricInterfacePeticion.ts";

const baseUrl = "http://localhost:8080/api";

export async function createRubric(rubric: RubricInterfacePeticion): Promise<RubricInterfacePeticion> {
  const response = await fetch(`${baseUrl}/rubricas`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(rubric),
  });
  return response.json();
}

export async function getRubricById(id: string): Promise<RubricInterface | null> {
  const response = await fetch(`${baseUrl}/rubricas/${id}`);
  if (!response.ok) {
    return null;
  }
  return response.json();
}

export async function getAllRubrics(): Promise<RubricInterface[]> {
  const response = await fetch(`${baseUrl}/rubricas`, {
    method: 'GET',
  });
  console.log(response);
  return response.json();
}

export async function updateRubric(id: number | null, updatedRubric: RubricInterfacePeticion): Promise<RubricInterfacePeticion | null> {
  const response = await fetch(`${baseUrl}/rubricas?id=${id}`, {
    method: 'PUT',
    headers: {
      "Content-Type": "application/json",
    },
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
  });
  return response.ok;
}*/

export async function deleteRubric(id: number): Promise<boolean> {
  const response = await fetch(`${baseUrl}/rubricas?id=${id}`, {
    method: 'PATCH',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({ estado: 'DESACTIVAR' }),
  });
  return response.ok;
}

export async function getAllMaterias(): Promise<MateriaInterface[]> {
  const response = await fetch(`${baseUrl}/rubricas/materias`, {
    method: 'GET',
    headers: {
      'Content-Type': 'application/json',
    },
  });
  if (!response.ok) {
    throw new Error('Error fetching materias');
  }
  return response.json();
}