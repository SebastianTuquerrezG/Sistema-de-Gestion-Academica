import api from "@/services/api";

export async function getRANameById(raId: number): Promise<string> {
  try {
    const { data } = await api.get(`/ra/${raId}`);
    return data?.name || "No definido";
  } catch {
    return "No definido";
  }
}

export async function getAllRAs(): Promise<any[]> {
  try {
    const { data } = await api.get("/ra");
    return data || [];
  } catch (error) {
    console.error("Error fetching RAs:", error);
    return [];
  }
}
