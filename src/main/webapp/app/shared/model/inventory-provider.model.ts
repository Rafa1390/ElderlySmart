import { IProvider } from 'app/shared/model/provider.model';
import { IProduct } from 'app/shared/model/product.model';

export interface IInventoryProvider {
  id?: number;
  idInventoryProvider?: string;
  code?: string;
  name?: string;
  price?: number;
  cuantity?: number;
  provider?: IProvider;
  products?: IProduct[];
}

export class InventoryProvider implements IInventoryProvider {
  constructor(
    public id?: number,
    public idInventoryProvider?: string,
    public code?: string,
    public name?: string,
    public price?: number,
    public cuantity?: number,
    public provider?: IProvider,
    public products?: IProduct[]
  ) {}
}
