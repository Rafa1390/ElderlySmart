import { Moment } from 'moment';
import { IEmployee } from 'app/shared/model/employee.model';
import { IFamily } from 'app/shared/model/family.model';
import { IDoctor } from 'app/shared/model/doctor.model';

export interface IElderly {
  id?: number;
  idElderly?: string;
  name?: string;
  name2?: string;
  lastName?: string;
  lastName2?: string;
  age?: number;
  nationality?: string;
  address?: string;
  admissionDate?: Moment;
  state?: string;
  employee?: IEmployee;
  families?: IFamily[];
  doctors?: IDoctor[];
}

export class Elderly implements IElderly {
  constructor(
    public id?: number,
    public idElderly?: string,
    public name?: string,
    public name2?: string,
    public lastName?: string,
    public lastName2?: string,
    public age?: number,
    public nationality?: string,
    public address?: string,
    public admissionDate?: Moment,
    public state?: string,
    public employee?: IEmployee,
    public families?: IFamily[],
    public doctors?: IDoctor[]
  ) {}
}
