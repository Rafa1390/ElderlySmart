import { IMedicalAppointment } from 'app/shared/model/medical-appointment.model';

export interface IAppointmentNotification {
  id?: number;
  idAppointmentNotification?: number;
  description?: string;
  state?: string;
  medicalAppointment?: IMedicalAppointment;
}

export class AppointmentNotification implements IAppointmentNotification {
  constructor(
    public id?: number,
    public idAppointmentNotification?: number,
    public description?: string,
    public state?: string,
    public medicalAppointment?: IMedicalAppointment
  ) {}
}
