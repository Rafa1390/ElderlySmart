import { Moment } from 'moment';
import { IAsylum } from 'app/shared/model/asylum.model';

export interface ICleaningProgram {
  id?: number;
  idCleaningProgram?: number;
  day?: Moment;
  time?: string;
  cleaningTask?: string;
  asylum?: IAsylum;
}

export class CleaningProgram implements ICleaningProgram {
  constructor(
    public id?: number,
    public idCleaningProgram?: number,
    public day?: Moment,
    public time?: string,
    public cleaningTask?: string,
    public asylum?: IAsylum
  ) {}
}
