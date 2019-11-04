import { Moment } from 'moment';
import { IPharmacy } from 'app/shared/model/pharmacy.model';
import { IDoctor } from 'app/shared/model/doctor.model';

export interface IPrescription {
  id?: number;
  idPrescription?: number;
  officeName?: string;
  creationDate?: Moment;
  doctorName?: string;
  patientName?: string;
  patientAge?: number;
  prescriptionDrugs?: string;
  state?: string;
  pharmacy?: IPharmacy;
  doctor?: IDoctor;
}

export class Prescription implements IPrescription {
  constructor(
    public id?: number,
    public idPrescription?: number,
    public officeName?: string,
    public creationDate?: Moment,
    public doctorName?: string,
    public patientName?: string,
    public patientAge?: number,
    public prescriptionDrugs?: string,
    public state?: string,
    public pharmacy?: IPharmacy,
    public doctor?: IDoctor
  ) {}
}
