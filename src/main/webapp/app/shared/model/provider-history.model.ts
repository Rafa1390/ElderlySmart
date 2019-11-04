import { Moment } from 'moment';
import { IProvider } from 'app/shared/model/provider.model';

export interface IProviderHistory {
  id?: number;
  idProviderHistory?: number;
  code?: string;
  client?: string;
  phone?: string;
  purchaseMade?: string;
  date?: Moment;
  provider?: IProvider;
}

export class ProviderHistory implements IProviderHistory {
  constructor(
    public id?: number,
    public idProviderHistory?: number,
    public code?: string,
    public client?: string,
    public phone?: string,
    public purchaseMade?: string,
    public date?: Moment,
    public provider?: IProvider
  ) {}
}
