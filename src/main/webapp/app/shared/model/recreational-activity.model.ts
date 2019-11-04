import { Moment } from 'moment';
import { IAsylum } from 'app/shared/model/asylum.model';

export interface IRecreationalActivity {
  id?: number;
  idRecreationalActivity?: number;
  name?: string;
  description?: string;
  date?: Moment;
  startTime?: string;
  endTime?: string;
  state?: string;
  asylum?: IAsylum;
}

export class RecreationalActivity implements IRecreationalActivity {
  constructor(
    public id?: number,
    public idRecreationalActivity?: number,
    public name?: string,
    public description?: string,
    public date?: Moment,
    public startTime?: string,
    public endTime?: string,
    public state?: string,
    public asylum?: IAsylum
  ) {}
}
