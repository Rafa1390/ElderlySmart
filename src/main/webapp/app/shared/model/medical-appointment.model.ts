import { Moment } from 'moment';
import { IEmployee } from 'app/shared/model/employee.model';
import { IDoctor } from 'app/shared/model/doctor.model';

export interface IMedicalAppointment {
  id?: number;
  idMedicalAppointment?: number;
  date?: Moment;
  time?: string;
  state?: string;
  employee?: IEmployee;
  doctor?: IDoctor;
}

export class MedicalAppointment implements IMedicalAppointment {
  constructor(
    public id?: number,
    public idMedicalAppointment?: number,
    public date?: Moment,
    public time?: string,
    public state?: string,
    public employee?: IEmployee,
    public doctor?: IDoctor
  ) {}
}
