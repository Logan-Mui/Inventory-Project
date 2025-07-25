export interface Request {
  requestId: string;
  status: "Pending" | "Completed" | "Failed";
  operation: string;
  submittedAt: string;
  completedAt?: string;
}
