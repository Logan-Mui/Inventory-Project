import axios from "axios";
import { PublicClientApplication } from "@azure/msal-browser";
import { loginRequest, msalConfig } from "./authConfig";

// Create MSAL instance to access tokens
const msalInstance = new PublicClientApplication(msalConfig);

const api = axios.create({
  baseURL: "http://localhost:8080", // 🔁 Your backend URL
  headers: {
    "Content-Type": "application/json",
  },
});

// Request interceptor — attach token
api.interceptors.request.use(async (config) => {
  const account = msalInstance.getAllAccounts()[0];
  if (!account) return config; // Not logged in

  const response = await msalInstance.acquireTokenSilent({
    ...loginRequest,
    account,
  });

  config.headers.Authorization = `Bearer ${response.accessToken}`;
  return config;
});

// Optional: Response interceptor (for error handling)
api.interceptors.response.use(
  (res) => res,
  (error) => {
    if (error.response?.status === 401 || error.response?.status === 403) {
      console.warn("Unauthorized or forbidden - redirecting to login.");
      // Optional: redirect or force logout
    }
    return Promise.reject(error);
  }
);

export default api;
