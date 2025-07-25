import api from "../config/axiosConfig";
import type { RequestAck } from "../types/requestack";



export async function addInventoryRequest(): Promise<RequestAck> {
  const response = await api.post<RequestAck>("/api/quickbooks/inventory", {});
  return response.data;
}

export async function getInventoryRequest(): Promise<RequestAck> {
  const response = await api.post<RequestAck>("/api/quickbooks/inventory", {});
  return response.data;
}

export async function updateInventoryRequest(): Promise<RequestAck> {
  const response = await api.post<RequestAck>("/api/quickbooks/inventory", {});
  return response.data;
}