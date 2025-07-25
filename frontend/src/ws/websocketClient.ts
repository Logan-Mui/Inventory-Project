import { Client, type IMessage } from "@stomp/stompjs";
import SockJS from "sockjs-client";
import type { Response } from "../types/response";

type Callback = (response: Response) => void;

let stompClient: Client | null = null;

export function connectWebSocket(onMessage: Callback) {
  const socket = new SockJS("http://localhost:8080/wsocket");
  stompClient = new Client({
    webSocketFactory: () => socket,
    reconnectDelay: 5000,
    debug: (str) => console.log("[STOMP]", str),
  });

  stompClient.onConnect = () => {
    console.log("WebSocket connected");
    stompClient?.subscribe("/topic/inventory-updates", (message: IMessage) => {
      const response: Response = JSON.parse(message.body);
      onMessage(response);
    });
  };

  stompClient.activate();
}

export function disconnectWebSocket() {
  stompClient?.deactivate();
}
