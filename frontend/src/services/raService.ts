import axios from "axios";
const baseUrl = "http://localhost:8080";

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

export async function getRANameById(raId: number): Promise<string> {
  try {
    const response = await axios.get(`${baseUrl}/ra/${raId}`, { headers: getAuthHeaders() });
    return response.data?.name || "No definido";
  } catch {
    return "No definido";
  }
}

export async function getAllRAs(): Promise<any[]> {
  try {
    const response = await axios.get(`${baseUrl}/ra`, { headers: getAuthHeaders() });
    return response.data || [];
  } catch (error) {
    console.error("Error fetching RAs:", error);
    return [];
  }
}