import { IPrescription } from 'app/shared/model/prescription.model';

export interface IPrescriptionNotification {
  id?: number;
  idPrescriptionNotification?: number;
  description?: string;
  state?: string;
  prescription?: IPrescription;
}

export class PrescriptionNotification implements IPrescriptionNotification {
  constructor(
    public id?: number,
    public idPrescriptionNotification?: number,
    public description?: string,
    public state?: string,
    public prescription?: IPrescription
  ) {}
}
