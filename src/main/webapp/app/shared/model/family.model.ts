import { IElderly } from 'app/shared/model/elderly.model';

export interface IFamily {
  id?: number;
  idFamily?: string;
  name?: string;
  name2?: string;
  lastName?: string;
  lastName2?: string;
  phone1?: string;
  phone2?: string;
  age?: number;
  familyRelation?: string;
  state?: string;
  elderlies?: IElderly[];
}

export class Family implements IFamily {
  constructor(
    public id?: number,
    public idFamily?: string,
    public name?: string,
    public name2?: string,
    public lastName?: string,
    public lastName2?: string,
    public phone1?: string,
    public phone2?: string,
    public age?: number,
    public familyRelation?: string,
    public state?: string,
    public elderlies?: IElderly[]
  ) {}
}
