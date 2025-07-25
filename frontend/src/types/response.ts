import type { InventoryItem } from "./inventory";

export interface Response {
    requestId: string;
    inventoryItems: InventoryItem[];
}