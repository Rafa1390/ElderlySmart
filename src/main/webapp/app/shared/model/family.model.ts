import { IUserApp } from 'app/shared/model/user-app.model';
import { IElderly } from 'app/shared/model/elderly.model';

export interface IFamily {
  id?: number;
  idFamily?: number;
  familyRelation?: string;
  userApp?: IUserApp;
  elderlies?: IElderly[];
}

export class Family implements IFamily {
  constructor(
    public id?: number,
    public idFamily?: number,
    public familyRelation?: string,
    public userApp?: IUserApp,
    public elderlies?: IElderly[]
  ) {}
}
