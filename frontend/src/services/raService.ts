import axios from "axios";
const baseUrl = "http://localhost:8080";
const headers = { "Content-Type": "application/json" };

export async function getRANameById(raId: number): Promise<string> {
  try {
    const response = await axios.get(`${baseUrl}/ra/${raId}`, { headers });
    return response.data?.name || "No definido";
  } catch {
    return "No definido";
  }
} 