import { IUserApp } from 'app/shared/model/user-app.model';
import { IFuneralPackages } from 'app/shared/model/funeral-packages.model';
import { IAsylum } from 'app/shared/model/asylum.model';

export interface IMortuary {
  id?: number;
  idMortuary?: string;
  email?: string;
  name?: string;
  address?: string;
  userApp?: IUserApp;
  funeralPackages?: IFuneralPackages[];
  asylums?: IAsylum[];
}

export class Mortuary implements IMortuary {
  constructor(
    public id?: number,
    public idMortuary?: string,
    public email?: string,
    public name?: string,
    public address?: string,
    public userApp?: IUserApp,
    public funeralPackages?: IFuneralPackages[],
    public asylums?: IAsylum[]
  ) {}
}
