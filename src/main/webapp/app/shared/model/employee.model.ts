import { IAsylum } from 'app/shared/model/asylum.model';
import { IUserApp } from 'app/shared/model/user-app.model';
import { IElderly } from 'app/shared/model/elderly.model';
import { IMedicalAppointment } from 'app/shared/model/medical-appointment.model';

export interface IEmployee {
  id?: number;
  idEmployee?: number;
  address?: string;
  asylum?: IAsylum;
  userApp?: IUserApp;
  elderlies?: IElderly[];
  medicalAppointments?: IMedicalAppointment[];
}

export class Employee implements IEmployee {
  constructor(
    public id?: number,
    public idEmployee?: number,
    public address?: string,
    public asylum?: IAsylum,
    public userApp?: IUserApp,
    public elderlies?: IElderly[],
    public medicalAppointments?: IMedicalAppointment[]
  ) {}
}
