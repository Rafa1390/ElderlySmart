export interface IUserApp {
  id?: number;
  identification?: string;
  idTypeUser?: number;
  email?: string;
  name?: string;
  name2?: string;
  lastName?: string;
  lastName2?: string;
  phone1?: string;
  phone2?: string;
  age?: number;
  password?: string;
  state?: string;
}

export class UserApp implements IUserApp {
  constructor(
    public id?: number,
    public identification?: string,
    public idTypeUser?: number,
    public email?: string,
    public name?: string,
    public name2?: string,
    public lastName?: string,
    public lastName2?: string,
    public phone1?: string,
    public phone2?: string,
    public age?: number,
    public password?: string,
    public state?: string
  ) {}
}
