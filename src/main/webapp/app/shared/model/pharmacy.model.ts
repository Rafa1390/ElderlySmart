import { IUserApp } from 'app/shared/model/user-app.model';
import { IPrescription } from 'app/shared/model/prescription.model';
import { IProvider } from 'app/shared/model/provider.model';
import { IAsylum } from 'app/shared/model/asylum.model';

export interface IPharmacy {
  id?: number;
  idPharmacy?: string;
  email?: string;
  name?: string;
  address?: string;
  userApp?: IUserApp;
  prescriptions?: IPrescription[];
  providers?: IProvider[];
  asylums?: IAsylum[];
}

export class Pharmacy implements IPharmacy {
  constructor(
    public id?: number,
    public idPharmacy?: string,
    public email?: string,
    public name?: string,
    public address?: string,
    public userApp?: IUserApp,
    public prescriptions?: IPrescription[],
    public providers?: IProvider[],
    public asylums?: IAsylum[]
  ) {}
}
