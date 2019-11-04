import { IUserApp } from 'app/shared/model/user-app.model';
import { IAsylum } from 'app/shared/model/asylum.model';

export interface IPartner {
  id?: number;
  idPartner?: number;
  address?: string;
  userApp?: IUserApp;
  asylums?: IAsylum[];
}

export class Partner implements IPartner {
  constructor(
    public id?: number,
    public idPartner?: number,
    public address?: string,
    public userApp?: IUserApp,
    public asylums?: IAsylum[]
  ) {}
}
