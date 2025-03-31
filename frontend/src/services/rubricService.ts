import { RubricInterface } from '../interfaces/RubricInterface';

const baseUrl = 'https://api.example.com';


async function createRubric(rubric: RubricInterface): Promise<RubricInterface> {
  const response = await fetch(`${baseUrl}/rubrics`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(rubric),
  });
  return response.json();
}


async function getRubricById(id: string): Promise<RubricInterface | null> {
  const response = await fetch(`${baseUrl}/rubrics/${id}`);
  if (!response.ok) {
    return null;
  }
  return response.json();
}


async function getAllRubrics(): Promise<RubricInterface[]> {
  const response = await fetch(`${baseUrl}/rubrics`);
  return response.json();
}

async function updateRubric(id: string, updatedRubric: RubricInterface): Promise<RubricInterface | null> {
  const response = await fetch(`${baseUrl}/rubrics/${id}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
    },
    body: JSON.stringify(updatedRubric),
  });
  if (!response.ok) {
    return null;
  }
  return response.json();
}


async function deleteRubric(id: string): Promise<boolean> {
  const response = await fetch(`${baseUrl}/rubrics/${id}`, {
    method: 'DELETE',
  });
  return response.ok;
}