import { Moment } from 'moment';

export interface IPharmacyHistory {
  id?: number;
  idPharmacyHistory?: number;
  code?: string;
  client?: string;
  phone?: string;
  purchaseMade?: string;
  date?: Moment;
}

export class PharmacyHistory implements IPharmacyHistory {
  constructor(
    public id?: number,
    public idPharmacyHistory?: number,
    public code?: string,
    public client?: string,
    public phone?: string,
    public purchaseMade?: string,
    public date?: Moment
  ) {}
}
