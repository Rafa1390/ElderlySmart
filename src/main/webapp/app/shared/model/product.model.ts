import { IInventoryProvider } from 'app/shared/model/inventory-provider.model';

export interface IProduct {
  id?: number;
  idProduct?: number;
  code?: string;
  name?: string;
  brand?: string;
  description?: string;
  type?: number;
  state?: string;
  inventoryProviders?: IInventoryProvider[];
}

export class Product implements IProduct {
  constructor(
    public id?: number,
    public idProduct?: number,
    public code?: string,
    public name?: string,
    public brand?: string,
    public description?: string,
    public type?: number,
    public state?: string,
    public inventoryProviders?: IInventoryProvider[]
  ) {}
}
