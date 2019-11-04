import { IUserApp } from 'app/shared/model/user-app.model';
import { IPrescription } from 'app/shared/model/prescription.model';
import { IMedicalAppointment } from 'app/shared/model/medical-appointment.model';
import { IElderly } from 'app/shared/model/elderly.model';

export interface IDoctor {
  id?: number;
  idDoctor?: string;
  email?: string;
  officeName?: string;
  address?: string;
  userApp?: IUserApp;
  prescriptions?: IPrescription[];
  medicalAppointments?: IMedicalAppointment[];
  elderlies?: IElderly[];
}

export class Doctor implements IDoctor {
  constructor(
    public id?: number,
    public idDoctor?: string,
    public email?: string,
    public officeName?: string,
    public address?: string,
    public userApp?: IUserApp,
    public prescriptions?: IPrescription[],
    public medicalAppointments?: IMedicalAppointment[],
    public elderlies?: IElderly[]
  ) {}
}
