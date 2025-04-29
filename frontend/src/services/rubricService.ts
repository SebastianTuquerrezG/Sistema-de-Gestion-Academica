import { RubricInterface } from "../interfaces/RubricInterface";

const baseUrl = "http://localhost:8080/api";

export async function createRubric(
  rubric: RubricInterface
): Promise<RubricInterface> {
  const response = await fetch(`${baseUrl}/rubricas`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(rubric),
  });
  return response.json();
}

export async function getRubricById(
  id: string
): Promise<RubricInterface | null> {
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

export async function updateRubric(id: string, updatedRubric: RubricInterface): Promise<RubricInterface | null> {
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

export async function deleteRubric(id: string): Promise<boolean> {
  const response = await fetch(`${baseUrl}/rubricas?id=${id}`, {
    method: 'PATCH',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify({ estado: 'DESACTIVAR' }),
  });
  return response.ok;
}
