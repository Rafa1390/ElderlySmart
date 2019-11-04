import { Moment } from 'moment';
import { IProduct } from 'app/shared/model/product.model';

export interface IMedicament {
  id?: number;
  idMedicament?: number;
  presentation?: string;
  dateExpiry?: Moment;
  cuantity?: string;
  product?: IProduct;
}

export class Medicament implements IMedicament {
  constructor(
    public id?: number,
    public idMedicament?: number,
    public presentation?: string,
    public dateExpiry?: Moment,
    public cuantity?: string,
    public product?: IProduct
  ) {}
}
