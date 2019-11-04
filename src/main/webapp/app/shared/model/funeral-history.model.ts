import { Moment } from 'moment';
import { IMortuary } from 'app/shared/model/mortuary.model';

export interface IFuneralHistory {
  id?: number;
  idFuneralHistory?: number;
  customer?: string;
  phone?: string;
  purchaseMade?: string;
  date?: Moment;
  state?: string;
  mortuary?: IMortuary;
}

export class FuneralHistory implements IFuneralHistory {
  constructor(
    public id?: number,
    public idFuneralHistory?: number,
    public customer?: string,
    public phone?: string,
    public purchaseMade?: string,
    public date?: Moment,
    public state?: string,
    public mortuary?: IMortuary
  ) {}
}
