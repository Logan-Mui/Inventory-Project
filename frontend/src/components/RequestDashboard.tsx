import { useEffect, useState } from "react";
import { connectWebSocket, disconnectWebSocket } from "../ws/websocketClient";
import type { Response } from "../types/response";

interface RequestStatus {
  requestId: string;
  status: "Pending" | "Completed";
  operation: string;
  submittedAt: string;
}

export default function RequestDashboard() {
  const [requests, setRequests] = useState<RequestStatus[]>([]);

  // Example: simulate a new request being submitted from the UI
  const submitNewRequest = (operation: string) => {
    const newRequest: RequestStatus = {
      requestId: crypto.randomUUID(), // Replace with real requestId from backend
      status: "Pending",
      operation,
      submittedAt: new Date().toISOString(),
    };
    setRequests((prev) => [...prev, newRequest]);
  };

  useEffect(() => {
    connectWebSocket((response: Response) => {
      setRequests((prev) =>
        prev.map((req) =>
          req.requestId === response.requestId
            ? { ...req, status: "Completed" }
            : req
        )
      );
    });

    return () => {
      disconnectWebSocket();
    };
  }, []);

  return (
    <div>
      <h2>QuickBooks Request Monitor</h2>
      <button onClick={() => submitNewRequest("InventoryQuery")}>+ Simulate Request</button>
      <table>
        <thead>
          <tr>
            <th>Request ID</th>
            <th>Operation</th>
            <th>Status</th>
            <th>Submitted</th>
          </tr>
        </thead>
        <tbody>
          {requests.map((req) => (
            <tr key={req.requestId}>
              <td>{req.requestId}</td>
              <td>{req.operation}</td>
              <td>{req.status}</td>
              <td>{new Date(req.submittedAt).toLocaleString()}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
