import { IPharmacy } from 'app/shared/model/pharmacy.model';

export interface IInventoryPharmacy {
  id?: number;
  idInventoryPharmacy?: string;
  code?: string;
  name?: string;
  purchasePrice?: number;
  salePrice?: number;
  cuantity?: number;
  pharmacy?: IPharmacy;
}

export class InventoryPharmacy implements IInventoryPharmacy {
  constructor(
    public id?: number,
    public idInventoryPharmacy?: string,
    public code?: string,
    public name?: string,
    public purchasePrice?: number,
    public salePrice?: number,
    public cuantity?: number,
    public pharmacy?: IPharmacy
  ) {}
}
