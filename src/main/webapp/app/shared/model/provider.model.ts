import { IUserApp } from 'app/shared/model/user-app.model';
import { IPharmacy } from 'app/shared/model/pharmacy.model';

export interface IProvider {
  id?: number;
  identification?: string;
  email?: string;
  name?: string;
  providerType?: string;
  address?: string;
  userApp?: IUserApp;
  pharmacies?: IPharmacy[];
}

export class Provider implements IProvider {
  constructor(
    public id?: number,
    public identification?: string,
    public email?: string,
    public name?: string,
    public providerType?: string,
    public address?: string,
    public userApp?: IUserApp,
    public pharmacies?: IPharmacy[]
  ) {}
}
