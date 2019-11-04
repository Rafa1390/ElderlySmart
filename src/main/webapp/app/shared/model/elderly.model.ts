import { Moment } from 'moment';
import { IEmployee } from 'app/shared/model/employee.model';
import { IUserApp } from 'app/shared/model/user-app.model';
import { IFamily } from 'app/shared/model/family.model';
import { IDoctor } from 'app/shared/model/doctor.model';

export interface IElderly {
  id?: number;
  idElderly?: number;
  nationality?: string;
  address?: string;
  admissionDate?: Moment;
  state?: string;
  employee?: IEmployee;
  userApp?: IUserApp;
  families?: IFamily[];
  doctors?: IDoctor[];
}

export class Elderly implements IElderly {
  constructor(
    public id?: number,
    public idElderly?: number,
    public nationality?: string,
    public address?: string,
    public admissionDate?: Moment,
    public state?: string,
    public employee?: IEmployee,
    public userApp?: IUserApp,
    public families?: IFamily[],
    public doctors?: IDoctor[]
  ) {}
}
